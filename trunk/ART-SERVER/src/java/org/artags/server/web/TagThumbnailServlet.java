/* Copyright (c) 2010 ARTags project owners (see http://www.artags.org)
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

import org.artags.server.business.TagThumbnail;
import org.artags.server.business.TagThumbnailDAO;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Pierre Levy
 */
public class TagThumbnailServlet extends HttpServlet
{
    private static final long EXPIRES = 36000000L * 24L; // one day

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String sId = req.getParameter( Constants.PARAMATER_ID );
        long id = Long.parseLong(sId);
        TagThumbnailDAO dao = new TagThumbnailDAO();
        TagThumbnail tagThumbnail= dao.findById(id);
            if (tagThumbnail != null)
            {
                resp.setContentType( tagThumbnail.getContentType() );
                long now = System.currentTimeMillis();
                resp.setDateHeader("Expires", now + EXPIRES);
                OutputStream out = resp.getOutputStream();
                out.write(tagThumbnail.getImage());
                out.close();
            }

    }
}