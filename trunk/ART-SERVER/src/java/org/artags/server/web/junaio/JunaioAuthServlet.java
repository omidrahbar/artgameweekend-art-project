/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.artags.server.web.junaio;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.server.web.Constants;
import org.artags.server.web.Security;

/**
 *
 * @author Pierre Levy
 */
public class JunaioAuthServlet extends HttpServlet
{


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if ( ! JunaioUtils.isAuthorized( request ))
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Writer out = response.getWriter();
        response.setContentType(Constants.CONTENT_TYPE_XML);

        out.write( "URI:" + request.getRequestURI() + "?" + request.getQueryString() + "\n" );

        out.write("Request Signature  HASH:\"" + JunaioUtils.getHash(request) + "\"\n");
        out.write("Request Calculated HASH:\"" + JunaioUtils.calculateHash(request, Security.JUNAIO_KEY ) + "\"\n");



        out.close();


    }


    
}



