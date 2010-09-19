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
package org.artags.site.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.artags.site.business.Tag;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author pierre
 */
public class FetchService
{

    public List<Tag> getTags() throws MalformedURLException, ParserConfigurationException, SAXException, IOException
    {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        TagParser parser = new TagParser();
        String xml = fetchUrl( Constants.SERVER + "/tags" );

        sp.parse( new InputSource( new StringReader( xml )), parser);
        return parser.getTags();

    }

    private String fetchUrl(String url)
    {

        StringBuffer sb = new StringBuffer("");

        try
        {
            URL u = new URL(url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(u.openStream()));
            String line;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            reader.close();

        } catch (MalformedURLException e)
        {
            Logger.getLogger( FetchService.class.getName()).log(Level.SEVERE, null, e);
        } catch (IOException e)
        {
            Logger.getLogger( FetchService.class.getName()).log(Level.SEVERE, null, e);
        }
        return sb.toString();
    }
}
