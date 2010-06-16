/* Copyright (c) 2010 ARt Project owners
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
package com.artgameweekend.projects.art.business;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 *
 * @author pierre
 */
public class TagDAO
{

    static Logger _logger = Logger.getLogger(TagDAO.class.getName());

    public Tag findById(long id)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        Tag tag = null;
        try
        {
            tag = pm.getObjectById(Tag.class, id);
        } catch (JDOObjectNotFoundException e)
        {
            _logger.log(Level.SEVERE, "Error findById" + e.getMessage(), e);
        }
        return tag;
    }

    public List<Tag> findAll()
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("SELECT FROM " + Tag.class.getName());
        return (List<Tag>) query.execute();

    }

    public void create(Tag tag)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try
        {
            pm.makePersistent(tag);
        } finally
        {
            pm.close();
        }

    }

    public void remove(long id)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Tag tag = null;
        try
        {
            tag = pm.getObjectById(Tag.class, id);
            pm.deletePersistent(tag);

        } catch (JDOObjectNotFoundException e)
        {
            _logger.log(Level.SEVERE, "Error findById" + e.getMessage(), e);
        } finally
        {
            pm.close();
        }

    }
}
