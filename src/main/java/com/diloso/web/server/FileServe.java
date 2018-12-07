package com.diloso.web.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class FileServe extends HttpServlet {
	private final static BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	
	private final static BlobInfoFactory blobInfoFactory = new
	 BlobInfoFactory(DatastoreServiceFactory.getDatastoreService());

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		try {
			BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
			BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
			res.setContentLength(new Long(blobInfo.getSize()).intValue());
			res.setHeader("content-type", blobInfo.getContentType());
			res.setHeader("content-disposition", "attachment; filename="
					+ blobInfo.getFilename());
			blobstoreService.serve(blobKey, res);
		} catch (Exception e) {
		}

	}
}
