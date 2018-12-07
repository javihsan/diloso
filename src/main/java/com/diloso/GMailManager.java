package com.diloso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.diloso.app.persist.manager.Manager;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Draft;
import com.google.api.services.gmail.model.ListDraftsResponse;
import com.google.api.services.gmail.model.Message;



@Component
@Scope(value = "singleton")
public class GMailManager extends Manager {

	protected static final Logger log = Logger.getLogger(GMailManager.class.getName());
	
	protected static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	protected static Map<String,AbstractGoogleJsonClient> gmailsPull;
	protected static String ACCOUNT_ID_OAUTH2 = "dilosoweb@appspot.gserviceaccount.com";
	protected static String FILE_OAUTH2 = "dilosoweb-b077f92afc0b.p12";
	
	public GMailManager() {
	}

	
	public static AbstractGoogleJsonClient getGmailService(Long id, String service, String user) throws AuthenticationException {
		if (gmailsPull == null) {
			gmailsPull = new HashMap<String, AbstractGoogleJsonClient>();
		}	
		if (!gmailsPull.containsKey(id+service)) {
		    
			GoogleCredential credential = null;
			try {
				List<String> scopes = Arrays.asList(GmailScopes.GMAIL_READONLY, GmailScopes.GMAIL_COMPOSE, GmailScopes.GMAIL_LABELS/*, CalendarScopes.CALENDAR, CalendarScopes.CALENDAR_READONLY*/);

				URL credentialsJSON = GMailManager.class.getClassLoader().getResource(FILE_OAUTH2);

				credential = new GoogleCredential.Builder()
						    .setTransport(HTTP_TRANSPORT)
						    .setJsonFactory(JSON_FACTORY)
						    .setServiceAccountId(ACCOUNT_ID_OAUTH2)
						    .setServiceAccountPrivateKeyFromP12File(new File(credentialsJSON.getFile()))
						    .setServiceAccountScopes(scopes)
						    .setServiceAccountUser(user)
						    .build();
				
				Gmail gmailServiceInstance = new Gmail.Builder(HTTP_TRANSPORT,
			            JSON_FACTORY,credential).build();
				
				log.info("gmailServiceInstance creada para : "+ credential.getServiceAccountId());
				
				gmailsPull.put(id+"gmail", gmailServiceInstance);
				/*
				Calendar calendarServiceInstance = new Calendar.Builder(HTTP_TRANSPORT,
			            JSON_FACTORY,credential).build();
				
				log.info("calendarServiceInstance creada para : "+ credential.getServiceAccountId());
				
				gmailsPull.put(id+"calendar", calendarServiceInstance);
				*/
				
			} catch (Exception e) {
				return null;
			} 
		}	
		return gmailsPull.get(id+service);

	}
		
	
	public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Gmail service = (Gmail)getGmailService(new Long(111),"gmail","info@bookingprof.com");
        //Calendar serviceCal = (Calendar)getGmailService(new Long(111),"calendar","info@bookingprof.com");

        //Events events = serviceCal.events().list("primary").execute();
        
        String user = "me";
//        ListLabelsResponse listResponse = service.users().labels().list(user).execute();
//        List<Label> labels = listResponse.getLabels();
//        if (labels!=null && labels.size() == 0) {
//            System.out.println("No labels found.");
//        } else {
//            System.out.println("Labels:");
//            for (Label label : labels) {
//                System.out.printf("- %s\n", label.getName());
//            }
//        }

        
        ListDraftsResponse listResponseDr = service.users().drafts().list(user).execute();
        List<Draft> drafts = listResponseDr.getDrafts();
        if (drafts!=null && drafts.size() == 0) {
            System.out.println("No drafts found.");
        } else {
            System.out.println("Drafts:");
            for (Draft draft : drafts) {
                System.out.printf("- %s\n", draft.getMessage());
            }
        }
    }
	
	/**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param from email address of the sender, the mailbox account
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public static MimeMessage createEmail(String to,
                                          String from,
                                          String subject,
                                          String bodyText)
            throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }
    
    /**
     * Create a message from an email.
     *
     * @param emailContent Email to be set to raw of message
     * @return a message containing a base64url encoded email
     * @throws IOException
     * @throws MessagingException
     */
    public static Message createMessageWithEmail(MimeMessage emailContent)
            throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        return message;
    }

    /**
     * Create draft email.
     *
     * @param service an authorized Gmail API instance
     * @param userId user's email address. The special value "me"
     * can be used to indicate the authenticated user
     * @param emailContent the MimeMessage used as email within the draft
     * @return the created draft
     * @throws MessagingException
     * @throws IOException
     */
    public static Draft createDraft(Gmail service,
                                    String userId,
                                    MimeMessage emailContent)
            throws MessagingException, IOException {
        Message message = createMessageWithEmail(emailContent);
        Draft draft = new Draft();
        draft.setMessage(message);
        draft = service.users().drafts().create(userId, draft).execute();

        System.out.println("Draft id: " + draft.getId());
        System.out.println(draft.toString());
        return draft;
    }

    

}