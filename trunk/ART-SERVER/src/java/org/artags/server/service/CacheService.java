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
package org.artags.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;
import org.artags.server.business.Tag;

/**
 *
 * @author pierre
 */
public class CacheService
{
    private static final int CACHE_SUBLIST_SIZE = 1000;
    private static final String KEY_LIST_CHUNK = "LIST_CHUNK";
    private static final LogService log = LogService.getLogger();
    private static CacheService _singleton;
    private static Cache _cache;
    private boolean _invalidated;
    private long _lastUpdate;

    private CacheService()
    {

        try
        {
            _cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
        }
        catch (CacheException e)
        {
            // ...
        }

    }

    public static CacheService instance()
    {
        if (_singleton == null)
        {
            _singleton = new CacheService();
        }
        return _singleton;
    }

    public void put(String key, Object value)
    {
        _cache.put(key, value);

    }

    public Object get(String key)
    {
        return _cache.get(key);
    }

    public void clear()
    {
        _cache.clear();
    }

    public void invalidate()
    {
        invalidate(true);
    }

    public void invalidate(boolean invalidate)
    {
        _invalidated = invalidate;
    }

    public boolean isInvalidated()
    {
        return _invalidated;
    }

    /**
     * @return the _lastUpdate
     */
    public long getLastUpdate()
    {
        return _lastUpdate;
    }

    /**
     * @param lastUpdate the _lastUpdate to set
     */
    public void setLastUpdate(long lastUpdate)
    {
        _lastUpdate = lastUpdate;
    }

    public void addTags(List<Tag> listLast , long lastUpdate )
    {
        List<Tag> listTags = getCachedTags();
        if( listTags != null )
        {
            for( Tag tag : listLast )
            {
                if( tag.getDate() > lastUpdate )
                {
                    // new tag
                    listTags.add(tag);
                }
                else
                {
                    // tag recently rated
                }
            }
            saveTags(listTags);
        }
    }
    
    public List<Tag> getCachedTags()
    {
        List<Tag> listTags = (List<Tag>) CacheService.instance().get(KEY_LIST_CHUNK + 0);
        if (listTags != null)
        {
            int i = 1;
            while( true )
            {
                log.log( "Reading cache chunk number  {0}", i );
                List<Tag> listChunk = (List<Tag>) CacheService.instance().get(KEY_LIST_CHUNK + i);
                if (listChunk != null)
                {
                    listTags.addAll(listChunk);
                    i++;
                }
                else
                {
                    break;
                }
            }
            log.log( "Number of tags retrieved from the cache : {0}", listTags.size());
        }
        return listTags;
        
    }

    public void saveTags(List<Tag> listTags)
    {
        int nTagCount = listTags.size();
        log.log( "Storing tags into the cache. Number of tags stored : {0}", nTagCount);

        int i = 0;
        boolean done = false;
        do
        {

            int start = i * CACHE_SUBLIST_SIZE;
            int end = (i + 1) * CACHE_SUBLIST_SIZE;

            if (end > nTagCount)
            {
                done = true;
                end = nTagCount;
            }
            List<Tag> list = new ArrayList<Tag>(listTags.subList(start, end));
            put(KEY_LIST_CHUNK + i, list);
            i++;
        }
        while (!done);

        setLastUpdate( new Date().getTime() );
    }
}
