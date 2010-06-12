/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artgameweekend.projects.art.web;

import com.artgameweekend.projects.art.business.PMF;
import com.artgameweekend.projects.art.business.Tag;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierre
 */
public class TagsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        Writer out = resp.getWriter();
        resp.setContentType("application/xml");
        out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.write("<tags>");

        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery( "SELECT FROM " + Tag.class.getName() );
        List<Tag> list = (List<Tag>) query.execute();
        for( Tag tag : list )
        {
        out.write("<tag>");

        out.write("<id>");
        out.write( "" + tag.getId() );
        out.write("</id>");
        out.write("<lat>");
        out.write( Double.toString(tag.getLat()));
        out.write("</lat>");
        out.write("<lon>");
        out.write(Double.toString(tag.getLon()));
        out.write("</lon>");
        out.write("<name>");
        out.write( tag.getName() );
        out.write("</name>");

        out.write("</tag>");
        }


        out.write("</tags>");
        out.close();
    }


}
