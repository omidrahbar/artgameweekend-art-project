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
package org.artags.site.web;

import java.io.IOException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import org.artags.site.business.Tag;
import org.artags.site.service.Constants;
import org.artags.site.service.FetchService;
import org.artags.site.service.TagService;
import org.xml.sax.SAXException;

/**
 *
 * @author pierre@artags.org
 */
public class GalleryServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, MalformedURLException
    {
        String selection = req.getParameter("selection");
        FetchService fs = new FetchService();
        Writer out = resp.getWriter();
        resp.setContentType(Constants.CONTENT_TYPE_XML);
        try
        {
            List<Tag> list = null;
            String title = "TAGS";
            if (selection != null)
            {
                if (selection.equals("bestrated"))
                {
                    list = TagService.instance().getBestRatedTags();
                    title = "BEST RATED";
                } else if (selection.equals("latest"))
                {
                    list = TagService.instance().getLatestTags();
                    title = "LATEST";
                } else if (selection.equals("ourselection"))
                {
                    // todo
                    title = "OUR SELECTION";
                }
            }

            if( list == null )
            {
                list = fs.getTags();
            }

            build(out, list, title);
        } catch (ParserConfigurationException ex)
        {
            Logger.getLogger(GalleryServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.write(ex.getMessage());
        } catch (SAXException ex)
        {
            Logger.getLogger(GalleryServlet.class.getName()).log(Level.SEVERE, null, ex);
            out.write(ex.getMessage());
        }
        out.close();

    }

    private void build(Writer out, List<Tag> list, String title) throws IOException
    {
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("<content Title=\"TAG GALLERY - ");
        out.write(title);
        out.write("\" TitleColour=\"#FFFFFF\" CategoryLinkColour=\"#99cc00\" CategorySelectColour=\"#FFFFFF\" LinkColour=\"#99cc00\" HoverColour=\"#99cc00\" ");
        out.write(" MaxRows=\"1\" MaxColumns=\"6\" ItemSpace=\"10\" ThumbWidth=\"120\" ThumbHeight=\"120\" ThumbBorder=\"10\" ThumbBgColour=\"#000000\" ThumbBgAlpha=\"70\" ThumbZoomAmount=\"110\" ThumbZoomSpeed=\"20\" ");
        out.write(" SummaryHeight=\"30\" SummaryBorder=\"10\" SummaryBorderColour=\"#000000\" SummaryBgAlpha=\"50\" ContentBgColour=\"#000000\" ContentBgAlpha=\"70\" ContentBgBorder=\"20\"");
        out.write(" PageBttnSpace=\"30\" InfoWidth=\"500\" InfoHeight=\"300\" InfoBorder=\"10\" InfoBgColour=\"#000000\" InfoBgAlpha=\"90\" ");
        out.write(" ScrollBttnColour=\"#99cc00\" ScrollBarColour=\"#99cc00\" ScrollBarAlpha=\"10\"> ");
        out.write("<category Title=\"IMAGE\">");

        for (Tag tag : list)
        {
            out.write("<item Title=\"");
            out.write(tag.getName() + "  " + tag.getRating() + "  " + tag.getRatingCount() + " votes" );
            out.write("\" Thumb=\"thumbnail?id=" + tag.getThumbnailId());

            out.write("\" LinkThumb=\"\" Content=\"thumbnail?id=" + tag.getThumbnailId() );
//            out.write("\" LinkThumb=\"\" Content=\"media_gallery/image/image1.jpg" );
//            out.write("\" LinkThumb=\"\" Content=\"display?id=" + tag.getId());
            out.write("\" >");
            out.write("<copy><![CDATA[<font color=\"#999999\" size=\"11\">");
            out.write("ID : ");
            out.write(tag.getId());
            out.write("\nPosted : ");
            out.write(tag.getDate());
            out.write("\nRated :");
            out.write(tag.getRating());
            out.write("\nLatitude : ");
            out.write(tag.getLatitude());
            out.write("\nLongitude : ");
            out.write(tag.getLongitude());
            out.write("</font>]]></copy>");
            out.write("</item>");
        }

        out.write("</category>");

        out.write("</content>");

    }
}
