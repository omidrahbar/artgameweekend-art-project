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

import java.util.Collections;
import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheManager;

/**
 *
 * @author pierre
 */
public class CacheService
{
    private static final LogService log = LogService.getLogger();
    private static CacheService _singleton = new CacheService();
    private static Cache _cache;
    private static long _lastUpdate;

    private CacheService()
    {

        try
        {
            _cache = CacheManager.getInstance().getCacheFactory().createCache(Collections.emptyMap());
        } catch (CacheException e)
        {
            // ...
        }

    }

    public static CacheService instance()
    {
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


}
