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
 * @author Pierre Levy
 */
public class LayarParamsServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String type = req.getParameter("type");
        if( type != null )
        {    
            LayarParamsService.instance().setType( Integer.parseInt(type));
        }
        String dimension = req.getParameter("dimension");
        if( dimension != null )
        {    
            LayarParamsService.instance().setDimension( Integer.parseInt(dimension));
        }
        String rel = req.getParameter("rel");
        if( rel != null )
        {
            LayarParamsService.instance().setRel( Boolean.parseBoolean(rel));
        }
        String angle = req.getParameter("angle");
        if( angle != null )
        {
            LayarParamsService.instance().setAngle( Integer.parseInt(angle));
        }
        String scale = req.getParameter("scale");
        if( scale != null )
        {
            LayarParamsService.instance().setScale( Double.parseDouble(scale));
        }
        String size = req.getParameter("size");
        if( size != null )
        {
            LayarParamsService.instance().setSize( Integer.parseInt(size));
        }

        resp.sendRedirect("layarParams.jsp");
    }


}
