/* Copyright (c) 2010 ARTags project owners (see http://artags.org)
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

import com.google.appengine.repackaged.com.google.common.util.Base64;
import com.google.appengine.repackaged.com.google.common.util.Base64DecoderException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.artags.server.util.security.SecurityUtils;

/**
 *
 * @author pierre@artags.org
 */
public class JunaioUtils
{

    private static final String HEADER_AUTH = "Authorization";
    private static final String HEADER_DATE = "Date";

    public static String getHash(HttpServletRequest request)
    {
        try
        {
            String headerAuthorisation = request.getHeader(HEADER_AUTH);
            String requestSignature = headerAuthorisation.substring("junaio ".length());
            return new String(Base64.decode(requestSignature));
        } catch (Base64DecoderException ex)
        {
            Logger.getLogger(JunaioUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }

    public static boolean isAuthorized(HttpServletRequest request)
    {
        String headerAuthorisation = request.getHeader(HEADER_AUTH);
        return ((headerAuthorisation != null) && (headerAuthorisation.indexOf("junaio") == 0));

    }

    public static boolean isValidSignature( HttpServletRequest request , String key )
    {
        String calculatedHash = calculateHash( request , key );
        String hash = getHash( request );
        return calculatedHash.equals( hash );

    }

    public static String calculateHash( HttpServletRequest request , String key )
    {
        return buildSignature( key , "GET" , request.getRequestURI() + "?" + request.getQueryString() , getDate( request ));
    }

    static String buildSignature(String key, String verb, String uri, String date)
    {
        String signatureString = verb + "\n" + uri + "\n" + "Date: " + date + "\n";

        return SecurityUtils.sha1(key + SecurityUtils.sha1(key + signatureString));

    }



    private static String getDate(HttpServletRequest request)
    {
        return request.getHeader(HEADER_DATE);
    }

}
