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

import org.artags.server.business.Tag;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.server.business.TagDAO;

/**
 *
 * @author Pierre Levy
 */
public class KmlServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Writer out = resp.getWriter();
        resp.setContentType(Constants.CONTENT_TYPE_KML);
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" > ");

        out.write("<Document>");

        TagDAO dao = new TagDAO();
        for (Tag tag : dao.findAll() )
        {
            out.write("<Placemark id=\"" + tag.getId() + "\">");
            out.write("<name>");
            out.write( "<![CDATA[" + tag.getName() + "]]>");
            out.write("</name>");
            out.write("<description>");
            out.write("Date : " + tag.getFormatedDate(req.getLocale()) + " Rating : " + tag.getRating());
            out.write("</description>");


            out.write("<Point>");
            out.write("<coordinates>");
            out.write(Double.toString(tag.getLon()) + "," + Double.toString(tag.getLat()));
            out.write("</coordinates>");
            out.write("</Point>");

            out.write("</Placemark>");
        }


        out.write("</Document>");
        out.write("</kml>");
        out.close();
    }
}
