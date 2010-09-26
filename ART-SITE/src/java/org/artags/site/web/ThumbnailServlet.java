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
package org.artags.site.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.site.service.CacheService;
import org.artags.site.service.CachedImage;
import org.artags.site.service.Constants;

/**
 *
 * @author pierre
 */
public class ThumbnailServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException
    {
        Writer out = resp.getWriter();
        resp.setContentType("image/png");

        String id = request.getParameter("id");
        if( id == null ) id = "45001";
        CachedImage image = (CachedImage) CacheService.instance().get(id);
        if (image == null)
        {
            try
            {
                StringBuffer sb = new StringBuffer();
                URL u = new URL(Constants.SERVER + "/thumbnail?id=" + id);

                BufferedInputStream bufferedInputStream = new BufferedInputStream(u.openStream());
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int intNextByte;
                while ((intNextByte = bufferedInputStream.read()) != -1)
                {
                    out.write(intNextByte);
                    bos.write(intNextByte);
                }
                CachedImage cache = new CachedImage();
                cache.setData(bos.toByteArray());
                CacheService.instance().put(id, cache);
                out.close();
                Logger.getLogger(ThumbnailServlet.class.getName()).log(Level.INFO, "Thumbnail read from distant URL");

            } catch (IOException e)
            {
                Logger.getLogger(ThumbnailServlet.class.getName()).log(Level.SEVERE, null, e);
            }
        } else
        {
            Logger.getLogger(ThumbnailServlet.class.getName()).log(Level.INFO, "Thumbnail read from cache");
            ByteArrayInputStream bis = new ByteArrayInputStream(image.getData());
            int intNextByte;
            while ((intNextByte = bis.read()) != -1)
            {
                out.write(intNextByte);
            }
            out.close();
        }
    }
}
