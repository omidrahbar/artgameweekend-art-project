/* Copyright (c) 2010 ARTags project owners (see http://www.artags.org)
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.artags.site.business.Tag;
import org.xml.sax.SAXException;

/**
 *
 * @author pierre
 */
public class TagService
{

    private static final String CACHE_KEY_ALL_TAGS = "alltags";
    private static final String CACHE_KEY_BEST_RATED = "bestrated";
    private static final String CACHE_KEY_LATEST = "latest";
    private static TagService _singleton = new TagService();
    
    // Item size of cached object should be < 1 MB so cached lists should be sliced into chunks
    private static final int CHUNK_SIZE = 2000; 

    
    private TagService()
    {
    }

    public static TagService instance()
    {
        return _singleton;
    }

    public List<Tag> getBestRatedTags()
    {
        List<Tag> bestRatedTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_BEST_RATED);
        if (bestRatedTags == null)
        {
            bestRatedTags = new ArrayList<Tag>(getAllTags());
            removeNotRated(bestRatedTags);
            Collections.sort(bestRatedTags, new RatingComparator());
            int max = ( bestRatedTags.size() < CHUNK_SIZE ) ? bestRatedTags.size() : CHUNK_SIZE;
            bestRatedTags = getSubList(bestRatedTags, 0 , max );
            CacheService.instance().put(CACHE_KEY_BEST_RATED, bestRatedTags);
        }
        return bestRatedTags;

    }

    public List<Tag> getLatestTags()
    {
        List<Tag> latestTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_LATEST);
        if (latestTags == null)
        {
            latestTags = new ArrayList<Tag>(getAllTags());
//            removeNotRated( latestTags );
            Collections.sort(latestTags, new RecentnessComparator());
            int max = ( latestTags.size() < CHUNK_SIZE ) ? latestTags.size() : CHUNK_SIZE ;
            latestTags = getSubList(latestTags, 0 , max );
            CacheService.instance().put(CACHE_KEY_LATEST, latestTags  );
        }
        return latestTags;

    }

    public List<Tag> getAllTags()
    {
        List<Tag> allTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_ALL_TAGS + 0 );
        if (allTags == null)
        {
            try
            {
                allTags = new FetchService().getTags();
                int numberOfTags = allTags.size();
                int numberOfChunks = 1 + numberOfTags / CHUNK_SIZE;
                for( int i = 0 ; i < numberOfChunks ; i++ )
                {
                    int max = (( i + 1 ) * CHUNK_SIZE) - 1;
                    if( i == (numberOfChunks - 1 ))
                    {
                        max = numberOfTags; 
                    }
                    CacheService.instance().put(CACHE_KEY_ALL_TAGS + i, getSubList( allTags , i * CHUNK_SIZE , max ));
                }
                
            } catch (MalformedURLException ex)
            {
                Logger.getLogger(TagService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParserConfigurationException ex)
            {
                Logger.getLogger(TagService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SAXException ex)
            {
                Logger.getLogger(TagService.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex)
            {
                Logger.getLogger(TagService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            // get all chunks
            int i = 1;
            List<Tag> moreTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_ALL_TAGS + i );
            while( moreTags != null )
            {
                allTags.addAll( moreTags );
                i++;
                moreTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_ALL_TAGS + i );
            }
        }
        return allTags;

    }
    
    private List<Tag> getSubList( List<Tag> tags , int min , int max )
    {
        List<Tag> list = new ArrayList<Tag>( tags.subList( min , max));
        return list;
    }
    
    
    public Tag getTag( String id )
    {
        for( Tag t : getAllTags() )
        {
            if( t.getId().equals( id )) return t;
        }
        return null;
    }

    private void removeNotRated(List<Tag> list)
    {
        for (int i = list.size(); i > 0; i--)
        {
            Tag t = list.get(i - 1);
            if (t.getRatingValue() == 0)
            {
                list.remove(i - 1);
            }
        }
    }

    class RatingComparator implements Comparator
    {

        public int compare(Object t1, Object t2)
        {
            int ret = ((Tag) t2).getRatingValue() - ((Tag) t1).getRatingValue();
            if( ret == 0 )
            {
                ret = ((Tag) t2).getRatingCount() - ((Tag) t1).getRatingCount();
            }
            return ret;
        }
    }

    class RecentnessComparator implements Comparator
    {

        public int compare(Object t1, Object t2)
        {
            long date1 = ((Tag) t1).getDateValue();
            long date2 = ((Tag) t2).getDateValue();
            return ( date2 > date1 ) ? 1 : -1;
        }
    }
}
