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
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierre@artags.org
 */
public class TagImageServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String sId = req.getParameter( Constants.PARAMATER_ID );
        long id = Long.parseLong(sId);
        TagDAO daoTag = new TagDAO();
        Tag tag = daoTag.findById(id);
        TagImageDAO dao = new TagImageDAO();
        TagImage tagImage= dao.findById( tag.getKeyImage().getId() );
            if (tagImage != null)
            {
//                resp.setContentType( tagImage.getContentType() );
                resp.setContentType( "image/png" );
                  OutputStream out = resp.getOutputStream();
                out.write(tagImage.getImage());
                out.close();
            }

    }
}
