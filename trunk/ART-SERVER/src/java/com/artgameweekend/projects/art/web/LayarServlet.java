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
import com.artgameweekend.projects.art.service.LayarParamsService;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author pierre
 */
public class LayarServlet extends HttpServlet
{
    private static final String SERVER_URL = "http://art-server.appspot.com";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Writer out = resp.getWriter();
        resp.setContentType(Constants.CONTENT_TYPE_JSON);

        TagDAO dao = new TagDAO();

        JSONObject layer = new JSONObject();
        JSONArray hotspots = new JSONArray();
        List<Tag> list = dao.findAll();
        for (Tag tag : list)
        {
            JSONObject poi = new JSONObject();
            poi.accumulate("distance", LayarParamsService.instance().getDistance());
            poi.accumulate("attribution", "ARt test layer");
            poi.accumulate("id", tag.getId());
            poi.accumulate("title", tag.getName());
            poi.accumulate("imageUrl", SERVER_URL + "/thumbnail?id=" + tag.getKeyThumbnail().getId() );
            poi.accumulate("lat", tag.getLat10e6() );
            poi.accumulate("lon", tag.getLon10e6() );
            poi.accumulate("line2", "Posted : " + tag.getFormatedDate(req.getLocale()));
            poi.accumulate("line3", "Rating : " + tag.getRating() );
            poi.accumulate("line4", "Application platform : Android");
            poi.accumulate("type", LayarParamsService.instance().getType());
            poi.accumulate("dimension", LayarParamsService.instance().getDimension());
            
            // Actions
            JSONArray actions = new JSONArray();
            JSONObject action1 = new JSONObject();
            action1.accumulate("uri", SERVER_URL + "/display?id=" + tag.getKeyImage().getId());
            action1.accumulate("label", "View tag");
            actions.add(action1);
            JSONObject action2 = new JSONObject();
            action2.accumulate("uri", SERVER_URL + "/jsp/client/rate.jsp?id=" + tag.getId()+"&id_thumbnail=" + tag.getKeyThumbnail().getId());
            action2.accumulate("label", "Rate this tag");
            actions.add(action2);
            JSONObject action3 = new JSONObject();
            action3.accumulate("uri", SERVER_URL + "/jsp/client/flag.jsp?id=" + tag.getId()+"&id_thumbnail=" + tag.getKeyThumbnail().getId());
            action3.accumulate("label", "Flag as inappropriate");
            actions.add(action3);
            JSONObject action4 = new JSONObject();
            action4.accumulate("uri", SERVER_URL + "/jsp/client/getdraw.jsp");
            action4.accumulate("label", "Get ARt Draw");
            actions.add(action4);
            poi.accumulate("actions", actions);

            // Transform values
            JSONObject transform = new JSONObject();
            transform.accumulate("rel", true );
            transform.accumulate("angle", LayarParamsService.instance().getAngle() );
            transform.accumulate("scale", 1.0 );
            poi.accumulate("transform", transform);

            // Transform values
            JSONObject object = new JSONObject();
            object.accumulate("baseURL", SERVER_URL + "/thumbnail" );
            object.accumulate("full", "?id=" + tag.getKeyThumbnail().getId() );
            object.accumulate("reduced", "?id=" + tag.getKeyThumbnail().getId() );
            object.accumulate("icon", "?id=" + tag.getKeyThumbnail().getId() );
            object.accumulate("size", 1);
            poi.accumulate("object", object);


            hotspots.add(poi);
        }
        layer.accumulate("hotspots", hotspots);
        layer.accumulate("layer", "artag2");
        layer.accumulate("errorString", "ok");
        layer.accumulate("morePages", false);
        layer.accumulate("errorCode", 0);
        layer.accumulate("nextPageKey", null);


        out.write(layer.toString(2));
        out.close();
    }
}

