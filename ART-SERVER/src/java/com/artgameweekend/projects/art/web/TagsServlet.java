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
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierre
 */
public class TagsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Writer out = resp.getWriter();
        resp.setContentType(Constants.CONTENT_TYPE_XML);
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("<tags>");

        TagDAO dao = new TagDAO();

        List<Tag> list = dao.findAll();
        for (Tag tag : list)
        {
            out.write("<tag>");

            out.write("<id>");
            out.write("" + tag.getId());
            out.write("</id>");
            out.write("<lat>");
            out.write(Double.toString(tag.getLat()));
            out.write("</lat>");
            out.write("<lon>");
            out.write(Double.toString(tag.getLon()));
            out.write("</lon>");
            out.write("<name>");
            out.write(tag.getName());
            out.write("</name>");
            if (tag.getKeyImage() != null)
            {
                out.write("<image-id>");
                out.write("" + tag.getKeyImage().getId());
                out.write("</image-id>");
            }
            if (tag.getKeyThumbnail() != null)
            {
                out.write("<thumbnail-id>");
                out.write("" + tag.getKeyThumbnail().getId());
                out.write("</thumbnail-id>");
            }
            out.write("</tag>");
        }


        out.write("</tags>");
        out.close();
    }
}
