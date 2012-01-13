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
package org.artags.server.web;

import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.artags.server.business.Tag;
import org.artags.server.service.CacheService;
import org.artags.server.service.LogService;
import org.artags.server.service.TagService;

/**
 *
 * @author Pierre Levy
 */
public class UpdateCacheServlet extends HttpServlet
{
    private static final LogService log = LogService.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException
    {
        if (CacheService.instance().isInvalidated())
        {
            // Clear the cache
            // CacheService.instance().clear();

            long lastUpdate = CacheService.instance().getLastUpdate();
            List<Tag> listLast = TagService.getLastTags( lastUpdate );
            log.log( "Cache update requested. {0} new or modified tags found.", listLast.size());

            CacheService.instance().addTags( listLast , lastUpdate );
            
            
            
            // Fetch all tags to populate the cache with fresh tags
            // TagService.getAllTags();
            
            CacheService.instance().invalidate(false);
        }
        else
        {
            log.log( "Cache update requested. Cache is valid. No need to update.");
        }

    }
}
