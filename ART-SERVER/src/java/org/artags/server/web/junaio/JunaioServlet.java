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
package org.artags.server.web.junaio;

import java.io.IOException;
import java.io.Writer;
import java.util.StringTokenizer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.server.business.Tag;
import org.artags.server.service.TagService;
import org.artags.server.web.Constants;
import org.artags.server.web.Security;
import org.artags.server.web.Utils;

/**
 *
 * @author Pierre Levy
 */
public class JunaioServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String adminKey = request.getParameter( "adminKey" );

        if (!JunaioUtils.isAuthorized(request) || !JunaioUtils.isValidSignature(request, Security.JUNAIO_KEY))
        {
            if( adminKey == null || ! adminKey.equals( Security.ADMIN_KEY ))
            {
                // Returns Unauthorized 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        double latitude = 48.8;
        double longitude = 2.3;

        String lparameter = request.getParameter("l");

        if (lparameter != null)
        {
            StringTokenizer st = new StringTokenizer(lparameter, ",");
            latitude = Double.parseDouble(st.nextToken());
            longitude = Double.parseDouble(st.nextToken());
        }
        int max = Utils.getInt(request, "m", 100);

        Writer out = response.getWriter();
        response.setContentType(Constants.CONTENT_TYPE_XML);

        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("<results>");

        for (Tag tag : TagService.getNearestTags(latitude, longitude, max))
        {

            out.write("<poi id=\"" + tag.getId() + "\" interactionfeedback=\"none\">");
            out.write("<name><![CDATA[" + tag.getName() + "]]></name>");

            out.write("<description><![CDATA[" + "Tag made on Android platform - Rating : " + tag.getRating() + "]]></description>");
            out.write("<date><![CDATA[" + tag.getFormatedDate(request.getLocale()) + "]]></date>");

            out.write("<l>" + tag.getLat() + "," + tag.getLon() + ",0</l>");
            out.write("<o>0,0,0</o>");

            out.write("<mime-type>image/png</mime-type>");

            out.write("<mainresource>" + Constants.URL_SERVER + "/display?id=" + tag.getId() + "</mainresource>");
            out.write("<thumbnail>" + Constants.URL_SERVER + "/thumbnail?id=" + tag.getKeyThumbnail().getId() + "</thumbnail>");
            out.write("<icon>" + Constants.URL_SERVER + "/images/icon.png</icon>");
            out.write("<homepage>" + Constants.URL_SERVER + "/client/tag.jsp?id=" + tag.getId() + "</homepage>");

            out.write("</poi>");
        }

        out.write("</results>");
        out.close();


    }
}

