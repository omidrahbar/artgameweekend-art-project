/* Copyright (c) 2010 ARTags project owners (see http://artags.org)
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
                            String name1 = asString(in , "UTF-8");
                            out.println(name1);

                            tag.setName(name1);
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

            contentType = (contentType != null) ? contentType : "image/jpeg";

            if (bLandscape)
            {
                image = rotate(image);
                if (thumbnail != null)
                {
                    thumbnail = rotate(thumbnail);
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

    /**
     * This convenience method allows to read a
     * {@link org.apache.commons.fileupload.FileItemStream}'s
     * content into a string. The platform's default character encoding
     * is used for converting bytes into characters.
     * @param pStream The input stream to read.
     * @see #asString(InputStream, String)
     * @return The streams contents, as a string.
     * @throws IOException An I/O error occurred.
     */
    public static String asString(InputStream pStream) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(pStream, baos, true);
        return baos.toString();
    }

    /**
     * This convenience method allows to read a
     * {@link org.apache.commons.fileupload.FileItemStream}'s
     * content into a string, using the given character encoding.
     * @param pStream The input stream to read.
     * @param pEncoding The character encoding, typically "UTF-8".
     * @see #asString(InputStream)
     * @return The streams contents, as a string.
     * @throws IOException An I/O error occurred.
     */
    public static String asString(InputStream pStream, String pEncoding)
            throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        copy(pStream, baos, true);
        return baos.toString(pEncoding);
    }
    /**
     * Default buffer size for use in
     * {@link #copy(InputStream, OutputStream, boolean)}.
     */
    private static final int DEFAULT_BUFFER_SIZE = 8192;

    /**
     * Copies the contents of the given {@link InputStream}
     * to the given {@link OutputStream}. Shortcut for
     * <pre>
     *   copy(pInputStream, pOutputStream, new byte[8192]);
     * </pre>
     * @param pInputStream The input stream, which is being read.
     * It is guaranteed, that {@link InputStream#close()} is called
     * on the stream.
     * @param pOutputStream The output stream, to which data should
     * be written. May be null, in which case the input streams
     * contents are simply discarded.
     * @param pClose True guarantees, that {@link OutputStream#close()}
     * is called on the stream. False indicates, that only
     * {@link OutputStream#flush()} should be called finally.
     *
     * @return Number of bytes, which have been copied.
     * @throws IOException An I/O error occurred.
     */
    public static long copy(InputStream pInputStream,
            OutputStream pOutputStream, boolean pClose)
            throws IOException
    {
        return copy(pInputStream, pOutputStream, pClose,
                new byte[DEFAULT_BUFFER_SIZE]);
    }

    /**
     * Copies the contents of the given {@link InputStream}
     * to the given {@link OutputStream}.
     * @param pIn The input stream, which is being read.
     *   It is guaranteed, that {@link InputStream#close()} is called
     *   on the stream.
     * @param pOut The output stream, to which data should
     *   be written. May be null, in which case the input streams
     *   contents are simply discarded.
     * @param pClose True guarantees, that {@link OutputStream#close()}
     *   is called on the stream. False indicates, that only
     *   {@link OutputStream#flush()} should be called finally.
     * @param pBuffer Temporary buffer, which is to be used for
     *   copying data.
     * @return Number of bytes, which have been copied.
     * @throws IOException An I/O error occurred.
     */
    public static long copy(InputStream pIn,
            OutputStream pOut, boolean pClose,
            byte[] pBuffer)
            throws IOException
    {
        OutputStream out = pOut;
        InputStream in = pIn;
        try
        {
            long total = 0;
            for (;;)
            {
                int res = in.read(pBuffer);
                if (res == -1)
                {
                    break;
                }
                if (res > 0)
                {
                    total += res;
                    if (out != null)
                    {
                        out.write(pBuffer, 0, res);
                    }
                }
            }
            if (out != null)
            {
                if (pClose)
                {
                    out.close();
                } else
                {
                    out.flush();
                }
                out = null;
            }
            in.close();
            in = null;
            return total;
        } finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (Throwable t)
                {
                    /* Ignore me */
                }
            }
            if (pClose && out != null)
            {
                try
                {
                    out.close();
                } catch (Throwable t)
                {
                    /* Ignore me */
                }
            }
        }
    }
}
