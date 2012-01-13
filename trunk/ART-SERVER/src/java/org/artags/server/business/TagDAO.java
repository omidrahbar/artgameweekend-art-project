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
package org.artags.server.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 *
 * @author Pierre Levy
 */
public class TagDAO extends GenericDAO<Tag>
{
    @Override
    public void remove( long id )
    {
        Tag tag = findById( id );
        TagImageDAO daoImage = new TagImageDAO();
        daoImage.remove( tag.getKeyImage().getId() );
        TagThumbnailDAO daoThumbnail = new TagThumbnailDAO();
        daoThumbnail.remove( tag.getKeyThumbnail().getId() );
        super.remove(id);

    }
/*
    public List<Tag> findLast(long lastUpdate)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("SELECT FROM " + Tag.class.getName() + " WHERE dateUpdate > " + lastUpdate );
        List<Tag> list = (List<Tag>) query.execute();
        Tag[] array = (Tag[]) pm.detachCopyAll( list.toArray() );
        List<Tag> listDetached = new ArrayList<Tag>();
        listDetached.addAll(Arrays.asList(array));

        return  listDetached;
        
    }
    
    */
}
