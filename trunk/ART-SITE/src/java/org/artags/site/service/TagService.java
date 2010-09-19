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
            removeNotRated( latestTags );
            Collections.sort(latestTags, new RecentnessComparator());
            CacheService.instance().put(CACHE_KEY_LATEST, latestTags  );
        }
        return latestTags;

    }

    private List<Tag> getAllTags()
    {
        List<Tag> allTags = (List<Tag>) CacheService.instance().get(CACHE_KEY_ALL_TAGS);
        if (allTags == null)
        {
            try
            {
                allTags = new FetchService().getTags();
                CacheService.instance().put(CACHE_KEY_ALL_TAGS, allTags);
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
        return allTags;

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
