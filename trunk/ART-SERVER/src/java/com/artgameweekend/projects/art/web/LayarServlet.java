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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author pierre
 */
public class LayarServlet extends HttpServlet
{

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
            poi.accumulate("distance", 100);
            poi.accumulate("attribution", "ARt test layer");
            poi.accumulate("id", tag.getId());
            poi.accumulate("title", tag.getName());
            poi.accumulate("imageUrl", null );
            poi.accumulate("lat", tag.getLat10e6() );
            poi.accumulate("lon", tag.getLon10e6() );
            poi.accumulate("line2", "line2");
            poi.accumulate("line3", "line3");
            poi.accumulate("line4", "line4");
            poi.accumulate("type", 0);
            poi.accumulate("dimension", 2);
            
            // Actions
            JSONArray actions = new JSONArray();
            JSONObject action1 = new JSONObject();
            action1.accumulate("uri", "http://art-server.appspot.com/display?id=" + tag.getId());
            action1.accumulate("label", "View tag");
            actions.add(action1);
            poi.accumulate("actions", actions);

            // Transform values
            JSONObject transform = new JSONObject();
            transform.accumulate("rel", true );
            transform.accumulate("angle", 0 );
            transform.accumulate("scale", 3.0 );
            poi.accumulate("transform", transform);

            // Transform values
            JSONObject object = new JSONObject();
            object.accumulate("baseURL", "http://art-server.appspot.com/thumbnail" );
            object.accumulate("full", "?id=" + tag.getKeyThumbnail().getId() );
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

