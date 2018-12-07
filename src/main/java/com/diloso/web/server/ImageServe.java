package com.diloso.web.server;

import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;

public class ImageServe extends HttpServlet {

	private final static BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();
	
	private final static ImagesService imagesService = ImagesServiceFactory.getImagesService();

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {

		try {
			BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
			
			if (req.getParameter("x") != null){
				BufferedOutputStream outputStream  = new  BufferedOutputStream(res.getOutputStream());
				int xImage = Integer.parseInt(req.getParameter("x"));
				int yImage = Integer.parseInt(req.getParameter("y"));
				try{
					Image oldImage = ImagesServiceFactory.makeImageFromBlob(blobKey);
	 
					Transform resize = ImagesServiceFactory.makeResize(xImage, yImage);
					Image newImage = imagesService.applyTransform(resize, oldImage);
	
					byte[] newImageData = newImage.getImageData();
				
					outputStream.write(newImageData, 0, newImageData.length);
					outputStream.flush();
				
				} catch (Exception e) {
				}
				finally{
					outputStream.close();
				}
			}
			else {
				blobstoreService.serve(blobKey, res);
			}
		} catch (Exception e) {
		}

	}
}
