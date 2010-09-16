/* Copyright (c) 2010 ARtags project owners (see http://artags.org)
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
package org.artags.server.web;

import org.artags.server.business.Tag;
import org.artags.server.business.TagDAO;
import org.artags.server.business.TagImage;
import org.artags.server.business.TagImageDAO;
import org.artags.server.business.TagThumbnail;
import org.artags.server.business.TagThumbnailDAO;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
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
 * @author pierre@artags.org
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
            byte[] thumbnail = null;

            Tag tag = new Tag();
            TagImage tagImage = new TagImage();
            TagThumbnail tagThumbnail = new TagThumbnail();
            String contentType = null;
            boolean bLandscape = false;

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
                        if (Constants.PARAMATER_NAME.equals(item.getFieldName()))
                        {
                            tag.setName(IOUtils.toString(in));
                        }
                        if (Constants.PARAMATER_LAT.equals(item.getFieldName()))
                        {
                            tag.setLat(Double.parseDouble(IOUtils.toString(in)));
                        }
                        if (Constants.PARAMATER_LON.equals(item.getFieldName()))
                        {
                            tag.setLon(Double.parseDouble(IOUtils.toString(in)));
                        }
                        if (Constants.PARAMATER_LANDSCAPE.equals(item.getFieldName()))
                        {
                            bLandscape = IOUtils.toString(in).equals("on");
                        }
                    } else
                    {
                        String fieldName = item.getFieldName();
                        String fileName = item.getName();
                        contentType = item.getContentType();

                        out.println("--------------");
                        out.println("fileName = " + fileName);
                        out.println("field name = " + fieldName);
                        out.println("contentType = " + contentType);

                        try
                        {
                            if (fieldName.equals("thumbnail"))
                            {
                                thumbnail = IOUtils.toByteArray(in);
                            } else
                            {
                                image = IOUtils.toByteArray(in);
                            }
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

            contentType = (contentType != null) ? contentType : "image/png";

            if (bLandscape)
            {
                image = rotate(image);
                if( thumbnail != null )
                {
                    thumbnail = rotate( thumbnail );
                }
            }
            tagImage.setImage(image);
            tagImage.setContentType(contentType);
            if (thumbnail != null)
            {
                tagThumbnail.setImage(thumbnail);
            } else
            {
                tagThumbnail.setImage(createThumbnail(image));
            }

            tagThumbnail.setContentType(contentType);

            TagImageDAO daoImage = new TagImageDAO();
            daoImage.create(tagImage);

            TagThumbnailDAO daoThumbnail = new TagThumbnailDAO();
            daoThumbnail.create(tagThumbnail);

            TagDAO dao = new TagDAO();
            tag.setKeyImage(tagImage.getKey());
            tag.setKeyThumbnail(tagThumbnail.getKey());
            tag.setDate(new Date().getTime());
            dao.create(tag);


        } catch (Exception ex)
        {

            throw new ServletException(ex);
        }
    }

    private byte[] createThumbnail(byte[] image)
    {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        Image oldImage = ImagesServiceFactory.makeImage(image);

        int width = THUMBNAIL_MIN;
        int height = THUMBNAIL_MAX;

        if (oldImage.getWidth() > oldImage.getHeight())
        {
            width = THUMBNAIL_MAX;
            height = THUMBNAIL_MIN;
        }

        Transform resize = ImagesServiceFactory.makeResize(width, height);

        Image newImage = imagesService.applyTransform(resize, oldImage);

        return newImage.getImageData();
    }

    private byte[] rotate(byte[] image)
    {
        ImagesService imagesService = ImagesServiceFactory.getImagesService();

        Image oldImage = ImagesServiceFactory.makeImage(image);

        Transform rotate = ImagesServiceFactory.makeRotate(-90);

        Image newImage = imagesService.applyTransform(rotate, oldImage);

        return newImage.getImageData();
    }
}
