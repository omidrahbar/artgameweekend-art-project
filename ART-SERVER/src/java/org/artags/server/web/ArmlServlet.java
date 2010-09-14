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
import org.artags.server.service.TagService;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierre@artags.org
 */
public class ArmlServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        double latitude = Utils.getDouble( req , "latitude" , 48.8);
        double longitude = Utils.getDouble( req , "longitude" , 2.3);
        int max = Utils.getInt(req, "maxNumberOfPois", 100 );

        Writer out = resp.getWriter();
        resp.setContentType(Constants.CONTENT_TYPE_XML);
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?> " +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\" " +
                " xmlns:ar=\"http://www.openarml.org/arml/1.0\" " +
                " xmlns:wikitude=\"http://www.openarml.org/wikitude/1.0\"> ");

        out.write("<Document>");
            out.write("<ar:provider id=\"ARt\">");
            out.write("<ar:name>ARtags</ar:name>");
            out.write("<ar:description>Augmented Reality tags - Tags made on smartphones</ar:description>");
            out.write("<wikitude:icon>" + Constants.URL_SERVER + "/images/icon.png</wikitude:icon>");
            out.write("</ar:provider>");

        for (Tag tag : TagService.getNearestTags(latitude, longitude, max))
        {
            out.write("<Placemark id=\"" + tag.getId() + "\">");
            out.write("<ar:provider>");
            out.write("ARt");
            out.write("</ar:provider>");
            out.write("<name>");
            out.write( "<![CDATA[" + tag.getName() + "]]>");
            out.write("</name>");
            out.write("<description>");
            out.write("Date : " + tag.getFormatedDate(req.getLocale()) + " Rating : " + tag.getRating());
            out.write("</description>");

            out.write("<wikitude:info>");
            out.write("<wikitude:thumbnail>");
            out.write( Constants.URL_SERVER + "/thumbnail?id=" + tag.getKeyThumbnail().getId());
            out.write("</wikitude:thumbnail>");
            out.write("<wikitude:url>");
            out.write( Constants.URL_SERVER + "/client/tag.jsp?id=" + tag.getId());
            out.write("</wikitude:url>");
            out.write("</wikitude:info>");

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
