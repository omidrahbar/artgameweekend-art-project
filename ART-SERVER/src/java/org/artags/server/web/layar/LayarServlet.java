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
package org.artags.server.web.layar;

import org.artags.server.business.Tag;
import org.artags.server.service.TagService;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.artags.server.web.Constants;
import org.artags.server.web.Security;
import org.artags.server.web.Utils;

/**
 *
 * @author pierre@artags.org
 */
public class LayarServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        LayarParamsService params = LayarParamsService.instance();

        String layerName = request.getParameter("layerName");
        String developerId = request.getParameter("developerId");
        double latitude = Utils.getDouble( request , "lat" , 48.8);
        double longitude = Utils.getDouble( request , "lon" , 2.3);
        int max = Utils.getInt(request, "max", params.getMaxPOIs() ); // not provided by Layar

        Writer out = response.getWriter();
        if( developerId == null || ! developerId.equals(Security.LAYAR_DEVELOPER_ID))
        {
            response.setStatus( HttpServletResponse.SC_UNAUTHORIZED );
            response.setContentType( Constants.CONTENT_TYPE_TEXT);
            out.write( "Unauthorized 401");
            out.close();
            return;
        }

        response.setContentType(Constants.CONTENT_TYPE_JSON);

        JSONObject layer = new JSONObject();
        JSONArray hotspots = new JSONArray();
        for (Tag tag : TagService.getNearestTags(latitude, longitude, max))
        {
            JSONObject poi = new JSONObject();
            poi.accumulate("distance", params.getDistance());
            poi.accumulate("attribution", "ARtags");
            poi.accumulate("id", tag.getId());
            poi.accumulate("title", tag.getName());
            poi.accumulate("imageUrl", Constants.URL_SERVER + "/thumbnail?id=" + tag.getKeyThumbnail().getId() );
            poi.accumulate("lat", tag.getLat10e6() );
            poi.accumulate("lon", tag.getLon10e6() );
            poi.accumulate("line2", "Posted : " + tag.getFormatedDate(request.getLocale()));
            poi.accumulate("line3", "Rating : " + tag.getRating() );
            poi.accumulate("line4", "Application platform : Android");
            poi.accumulate("type", params.getType());
            poi.accumulate("dimension", params.getDimension());
            
            // Actions
            JSONArray actions = new JSONArray();
            JSONObject action1 = new JSONObject();
            action1.accumulate("uri", Constants.URL_SERVER + "/display?id=" + tag.getId());
            action1.accumulate("label", "View tag");
            actions.add(action1);
            JSONObject action2 = new JSONObject();
            action2.accumulate("uri", Constants.URL_SERVER + "/client/rate.jsp?id=" + tag.getId()+"&id_thumbnail=" + tag.getKeyThumbnail().getId());
            action2.accumulate("label", "Rate this tag");
            actions.add(action2);
            JSONObject action3 = new JSONObject();
            action3.accumulate("uri", Constants.URL_SERVER + "/client/flag.jsp?id=" + tag.getId()+"&id_thumbnail=" + tag.getKeyThumbnail().getId());
            action3.accumulate("label", "Flag as inappropriate");
            actions.add(action3);
            /*
            JSONObject action4 = new JSONObject();
            action4.accumulate("uri", Constants.URL_SERVER + "/client/getdraw.jsp");
            action4.accumulate("label", "Get ARtags Draw");
            actions.add(action4);
             */

            poi.accumulate("actions", actions);

            // Transform values
            JSONObject transform = new JSONObject();
            transform.accumulate("rel", true );
            transform.accumulate("angle", params.getAngle() );
            transform.accumulate("scale", 1.0 );
            poi.accumulate("transform", transform);

            // Transform values
            JSONObject object = new JSONObject();
            object.accumulate("baseURL", Constants.URL_SERVER + "/thumbnail" );
            object.accumulate("full", "?id=" + tag.getKeyThumbnail().getId() );
            object.accumulate("reduced", "?id=" + tag.getKeyThumbnail().getId() );
            object.accumulate("icon", "?id=" + tag.getKeyThumbnail().getId() );
            object.accumulate("size", 1);
            poi.accumulate("object", object);


            hotspots.add(poi);
        }
        layer.accumulate("hotspots", hotspots);
        layer.accumulate("layer", layerName);
        layer.accumulate("errorString", "ok");
        layer.accumulate("morePages", false);
        layer.accumulate("errorCode", 0);
        layer.accumulate("nextPageKey", null);


        out.write(layer.toString(2));
        out.close();
    }
}

