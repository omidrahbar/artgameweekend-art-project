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

import java.util.*;
import org.artags.site.business.Tag;

/**
 *
 * @author pierre
 */
public class TagService
{
    private static final LogService log = LogService.getLogger();
    private static final String CACHE_KEY_BEST_RATED = "bestrated";
    private static final String CACHE_KEY_LATEST = "latest";
    private static final long DAY = 86400000L;
    private static final long DAY_COUNT = 20L;
    private static final int MIN_RATING_COUNT = 1;
    private static TagService _singleton = new TagService();
    

   
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
            bestRatedTags = new FetchService().getBestTags( MIN_RATING_COUNT );
            Collections.sort(bestRatedTags, new RatingComparator());
            CacheService.instance().put(CACHE_KEY_BEST_RATED, bestRatedTags);
        }
        return bestRatedTags;

    }

    public List<Tag> getLatestTags()
    {
        List<Tag> latestTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_LATEST);
        if (latestTags == null)
        {
            long dateUpdate = new Date().getTime() - DAY_COUNT * DAY; 
            latestTags = new ArrayList<Tag>( new FetchService().getLastTags( dateUpdate ));
            Collections.sort(latestTags, new RecentnessComparator());
            CacheService.instance().put(CACHE_KEY_LATEST, latestTags  );
        }
        return latestTags;

    }

    
    private List<Tag> getSubList( List<Tag> tags , int min , int max )
    {
        List<Tag> list = new ArrayList<Tag>( tags.subList( min , max));
        return list;
    }
    
    
    public Tag getTag( String id )
    {
        for( Tag t : getLatestTags() )
        {
            if( t.getId().equals( id )) return t;
        }
        for( Tag t : getBestRatedTags() )
        {
            if( t.getId().equals( id )) return t;
        }
        return null;
    }

    class RatingComparator implements Comparator
    {

        public int compare(Object tag1, Object tag2)
        {
            Tag t1 = (Tag) tag1;
            Tag t2 = (Tag) tag2;
            
            int r1 = t1.getRatingValue() + t1.getRatingCount();
            int r2 = t2.getRatingValue() + t2.getRatingCount();

            return r2 - r1;
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
