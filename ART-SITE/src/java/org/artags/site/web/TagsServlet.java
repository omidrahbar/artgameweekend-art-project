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
package org.artags.site.web;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.site.business.Tag;
import org.artags.site.service.TagService;

/**
 *
 * @author Pierre Levy
 */
public class TagsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        Writer out = resp.getWriter();
        resp.setContentType( "application/xml");
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("<tags>");

        List<Tag> list = TagService.instance().getBestRatedTags();
        for (Tag tag : list )
        {
            out.write("<tag>");

            out.write("<id>");
            out.write("" + tag.getId());
            out.write("</id>");
            out.write("<lat>");
            out.write( tag.getLatitude());
            out.write("</lat>");
            out.write("<lon>");
            out.write( tag.getLongitude());
            out.write("</lon>");
            out.write("<name>");
            out.write( "<![CDATA[" + tag.getName() + "]]>" );
            out.write("</name>");
            if (tag.getThumbnailId() != null)
            {
                out.write("<image-id>");
                out.write("" + tag.getThumbnailId());
                out.write("</image-id>");
            }
            if (tag.getThumbnailId() != null)
            {
                out.write("<thumbnail-id>");
                out.write("" + tag.getThumbnailId());
                out.write("</thumbnail-id>");
            }
            out.write("<date>");
            out.write( tag.getDate());
            out.write("</date>");
            out.write("<date-value>");
            out.write("" + tag.getDate());
            out.write("</date-value>");
            out.write("<rating>");
            out.write( tag.getRating());
            out.write("</rating>");
/*            out.write("<rating-value>");
            out.write( tag.getRatingValue() );
            out.write("</rating-value>");
            out.write("<rating-count>");
            out.write( tag.getRatingCount() );
            out.write("</rating-count>");
*/
            out.write("</tag>");
        }


        out.write("</tags>");
        out.close();
    }
}
