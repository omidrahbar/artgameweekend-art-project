/* Copyright (c) 2010-2012 ARTags project owners (see http://www.artags.org)
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
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
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
    private static final LogService log = new LogService();

    public List<Tag> getLastTags( long dateUpdate )
    {
        log.log("Fetching latest tags");
        return getTags(Constants.SERVER + "/tags?dateUpdate=" + dateUpdate );
    }

    public List<Tag> getBestTags( int minRating )
    {
        log.log("Fetching best tags");
        return getTags(Constants.SERVER + "/tags?minRating=" + minRating );
    }

    private List<Tag> getTags(String url) 
    {
        List<Tag> listTags = new ArrayList<Tag>();
        try
        {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            TagParser parser = new TagParser();
            String xml = fetchUrl( url );

            sp.parse(new InputSource(new StringReader(xml)), parser);
            listTags = parser.getTags();
        }
        catch (MalformedURLException ex)
        {
            log.log( "Error fetching tags {0}", ex.getMessage() );
        }
        catch (ParserConfigurationException ex)
        {
            log.log( "Error fetching tags {0}", ex.getMessage() );
        }
        catch (SAXException ex)
        {
            log.log( "Error fetching tags {0}", ex.getMessage() );
        }
        catch (IOException ex)
        {
            log.log( "Error fetching tags {0}", ex.getMessage() );
        }
        log.log("{0} tags fetched " , listTags.size() );
        return listTags;

    }

    private String fetchUrl(String url)
    {

        StringBuilder sb = new StringBuilder("");

        try
        {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            conn.setReadTimeout( 20000 );
            conn.setConnectTimeout( 20000 );
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
            reader.close();

        }
        catch (MalformedURLException ex)
        {
            log.log( "Error fetching tags {0}", ex.getMessage() );
        }
        catch (IOException ex)
        {
            log.log( "Error fetching tags {0}", ex.getMessage() );
        }
        return sb.toString();
    }
}
