/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artgameweekend.projects.art.web;

import com.artgameweekend.projects.art.business.PMF;
import com.artgameweekend.projects.art.business.Tag;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.jdo.JDOObjectNotFoundException;
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
public class TagImageServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        String sId = req.getParameter("id");
        long id = Long.parseLong(sId);
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Tag tag = null;
        try
        {
            tag = pm.getObjectById(Tag.class, id);
            if (tag != null)
            {
                resp.setContentType("image/png");
                OutputStream out = resp.getOutputStream();
                out.write(tag.getImage());
                out.close();
            }
        } catch (JDOObjectNotFoundException e)
        {
//            log.error("Api not found", e);
        }


    }
}
