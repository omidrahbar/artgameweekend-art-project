/* Copyright (c) 2010 ARt Project owners
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.artgameweekend.projects.art.web;

import com.artgameweekend.projects.art.business.Tag;
import com.artgameweekend.projects.art.business.TagDAO;
import com.artgameweekend.projects.art.business.TagImage;
import com.artgameweekend.projects.art.business.TagImageDAO;
import com.artgameweekend.projects.art.business.TagThumbnail;
import com.artgameweekend.projects.art.business.TagThumbnailDAO;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
    private static final int THUMBNAIL_MIN = 180;
    private static final int THUMBNAIL_MAX = 240;

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException
    {
        try
        {

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload();
            upload.setSizeMax(500000);
            res.setContentType(Constants.CONTENT_TYPE_TEXT);
            PrintWriter out = res.getWriter();
            byte[] image = null;

            Tag tag = new Tag();
            TagImage tagImage = new TagImage();
            TagThumbnail tagThumbnail = new TagThumbnail();

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
                        if ( Constants.PARAMATER_NAME.equals(item.getFieldName()))
                        {
                            tag.setName( IOUtils.toString(in));
                        }
                        if ( Constants.PARAMATER_LAT.equals(item.getFieldName()))
                        {
                            tag.setLat(Double.parseDouble( IOUtils.toString(in)));
                        }
                        if ( Constants.PARAMATER_LON.equals(item.getFieldName()))
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
                            tagImage.setImage(image);
                            tagImage.setContentType(contentType);
                            tagThumbnail.setImage( createThumbnail( image ));
                            tagThumbnail.setContentType(contentType);
                        } finally
                        {
                            IOUtils.closeQuietly(in);
                        }

                    }
                }
            } catch (SizeLimitExceededException e)
            {
                out.println("You exceeded the maximum size ("
                        + e.getPermittedSize() + ") of the file ("
                        + e.getActualSize() + ")");
            }

            TagImageDAO daoImage = new TagImageDAO();
            daoImage.create(tagImage);

            TagThumbnailDAO daoThumbnail = new TagThumbnailDAO();
            daoThumbnail.create(tagThumbnail);

            TagDAO dao = new TagDAO();
            tag.setKeyImage(tagImage.getKey());
            tag.setKeyThumbnail( tagThumbnail.getKey());
            dao.create(tag);


        } catch (Exception ex)
        {

            throw new ServletException(ex);
        }
    }

    private byte[] createThumbnail( byte[] image )
    {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        Image oldImage = ImagesServiceFactory.makeImage(image);

        int width = THUMBNAIL_MIN;
        int height = THUMBNAIL_MAX;

        if( oldImage.getWidth() > oldImage.getHeight() )
        {
            width = THUMBNAIL_MAX;
            height = THUMBNAIL_MIN;
        }

        Transform resize = ImagesServiceFactory.makeResize( width , height );

        Image newImage = imagesService.applyTransform(resize, oldImage);

        return newImage.getImageData();
    }
}
