/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.artags.site.web;

import com.google.appengine.repackaged.org.json.JSONArray;
import com.google.appengine.repackaged.org.json.JSONException;
import com.google.appengine.repackaged.org.json.JSONObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.site.business.Tag;
import org.artags.site.service.Constants;
import org.artags.site.service.TagService;

/**
 *
 * @author pierre
 */
public class JsonServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        List<Tag> list;
        String gallery = req.getParameter("gallery");
        if ( (gallery != null) && gallery.equals("latest"))
        {
            list = TagService.instance().getLatestTags();
        } else
        {
            list = TagService.instance().getBestRatedTags();
        }
        Writer out = resp.getWriter();
        resp.setContentType("application/x-javascript");
        resp.setCharacterEncoding("UTF-8");
        JSONObject flow = new JSONObject();
        JSONArray tags = new JSONArray();
        try
        {
            for (Tag tag : list)
            {
                JSONObject jsonTag = new JSONObject();
                jsonTag.accumulate("id", tag.getId());
                jsonTag.accumulate("title", tag.getName());
                jsonTag.accumulate("imageUrl", Constants.SERVER_URL + "/thumbnail?id=" + tag.getThumbnailId());
                jsonTag.accumulate("lat", tag.getLatitude());
                jsonTag.accumulate("lon", tag.getLongitude());
                jsonTag.accumulate("date", tag.getDate());
                jsonTag.accumulate("rating", tag.getRating());
                tags.put(jsonTag);
            }
            flow.put("tags", tags);
            out.write(flow.toString(2));
            out.close();
        } catch (JSONException ex)
        {
            Logger.getLogger(JsonServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
