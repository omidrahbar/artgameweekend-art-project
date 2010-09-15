/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.artags.server.web.layar;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pierre@artags.org
 */
public class LayarParamsServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String distance = req.getParameter("distance");
        LayarParamsService.instance().setDistance( Integer.parseInt(distance));
        String type = req.getParameter("type");
        LayarParamsService.instance().setType( Integer.parseInt(type));
        String dimension = req.getParameter("dimension");
        LayarParamsService.instance().setDimension( Integer.parseInt(dimension));
        String rel = req.getParameter("rel");
        LayarParamsService.instance().setRel( Boolean.parseBoolean(rel));
        String angle = req.getParameter("angle");
        LayarParamsService.instance().setAngle( Integer.parseInt(angle));
        String scale = req.getParameter("scale");
        LayarParamsService.instance().setScale( Double.parseDouble(scale));

        resp.sendRedirect("layarParams.jsp");
    }


}
