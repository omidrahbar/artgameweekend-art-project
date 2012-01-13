/* Copyright (c) 2010-2012 ARTags project owners (see http://www.artags.org)
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
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Pierre Levy
 */
public class TagsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        double latitude = Utils.getDouble( req , "lat" , 48.8);
        double longitude = Utils.getDouble( req , "lon" , 2.3);
        int max = Utils.getInt(req, "max", 100 );
        String all = req.getParameter( "all" );
        String dateUpdate = req.getParameter("dateUpdate");

        Writer out = resp.getWriter();
        resp.setContentType(Constants.CONTENT_TYPE_XML);
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("<tags>");

        List<Tag> list;
        if( (all != null) && ( all.equals("true")))
        {
            list = TagService.getAllTags();
        }
        else if( dateUpdate != null )
        {
            list = TagService.getLastTags( Long.parseLong( dateUpdate ));
        }
        else
        {
            list = TagService.getNearestTags(latitude, longitude, max);
        }

        for (Tag tag : list )
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
            out.write( StringEscapeUtils.escapeXml( tag.getName()));
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
            out.write("<date>");
            out.write(tag.getFormatedDate(req.getLocale()));
            out.write("</date>");
            out.write("<date-value>");
            out.write("" + tag.getDate());
            out.write("</date-value>");
            out.write("<date-update>");
            out.write("" + tag.getDateUpdate());
            out.write("</date-update>");
            out.write("<rating>");
            out.write(tag.getRating());
            out.write("</rating>");
            out.write("<rating-value>");
            out.write("" + (int) ( (float) tag.getRatingSum() * 100 / (float) tag.getRatingCount() ));
            out.write("</rating-value>");
            out.write("<rating-count>");
            out.write("" + tag.getRatingCount() );
            out.write("</rating-count>");
            out.write("</tag>");
        }


        out.write("</tags>");
        out.close();
    }
}
