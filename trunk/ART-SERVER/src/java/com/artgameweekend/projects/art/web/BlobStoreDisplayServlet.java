/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artgameweekend.projects.art.web;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierre
 */
public class BlobStoreDisplayServlet extends HttpServlet
{

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
        blobstoreService.serve(blobKey, resp);
    }
}
