/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artgameweekend.projects.art.web;

import com.artgameweekend.projects.art.business.PMF;
import com.artgameweekend.projects.art.business.Tag;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author pierre
 */
public class TagUploadServlet extends HttpServlet
{

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        try
        {

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload();
            upload.setSizeMax(200000);
            res.setContentType("text/plain");
            PrintWriter out = res.getWriter();
            byte[] image = null;

            Tag tag = new Tag();

            try
            {
                FileItemIterator iterator = upload.getItemIterator(req);
                while (iterator.hasNext())
                {
                    FileItemStream item = iterator.next();
                    InputStream in = item.openStream();

                    if (item.isFormField())
                    {
                        out.println("Got a form field: " + item.getFieldName());
                        if ("name".equals(item.getFieldName()))
                        {
                            tag.setName( IOUtils.toString(in));
                        }
                        if ("lat".equals(item.getFieldName()))
                        {
                            tag.setLat(Double.parseDouble( IOUtils.toString(in)));
                        }
                        if ("lon".equals(item.getFieldName()))
                        {
                            tag.setLon(Double.parseDouble( IOUtils.toString(in)));
                        }
                    } else
                    {
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        String contentType = item.getContentType();

                        out.println("--------------");
                        out.println("fileName = " + fileName);
                        out.println("field name = " + fieldName);
                        out.println("contentType = " + contentType);

                        try
                        {
                            image = IOUtils.toByteArray(in);
                            tag.setImage(image);
                        } finally
                        {
                            IOUtils.closeQuietly(in);
                        }

                    }
                }
            } catch (SizeLimitExceededException e)
            {
                out.println("You exceeded the maximu size ("
                        + e.getPermittedSize() + ") of the file ("
                        + e.getActualSize() + ")");
            }

            PersistenceManager pm = PMF.get().getPersistenceManager();

            try
            {
                pm.makePersistent(tag);
            } finally
            {
                pm.close();
            }

        } catch (Exception ex)
        {

            throw new ServletException(ex);
        }
    }
}
