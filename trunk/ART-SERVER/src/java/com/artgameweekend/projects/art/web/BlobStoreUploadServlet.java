/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artgameweekend.projects.art.web;

import com.artgameweekend.projects.art.business.PMF;
import com.artgameweekend.projects.art.business.Tag;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import javax.jdo.PersistenceManager;

public class BlobStoreUploadServlet extends HttpServlet
{

    private final static Logger _logger = Logger.getLogger(BlobStoreUploadServlet.class.getName());
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {

        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
        BlobKey blobKey = blobs.get("uploadedfile");

        if (blobKey == null)
        {
            res.sendRedirect("/");
        } else
        {
            String name = req.getParameter("name");

            Tag tag = new Tag( name, blobKey.getKeyString() , 48.0 , 2.0 );
            // persist image
            PersistenceManager pm = PMF.get().getPersistenceManager();
            pm.makePersistent(tag);
            pm.close();

                res.sendRedirect("/display.jsp?blob-key=" + blobKey.getKeyString());
        }
    }
}

