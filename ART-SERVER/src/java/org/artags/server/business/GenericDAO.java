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

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

/**
 *
 * @author Pierre Levy
 */
public class GenericDAO<E>
{

    private static Logger _logger = Logger.getLogger(TagDAO.class.getName());
    private Class<E> _entityClass;

    /**
     * Constructor
     */
    public GenericDAO()
    {
        _entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public E findById(long id)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        E entity = null;
        try
        {
            entity = pm.getObjectById(_entityClass, id);
            entity = pm.detachCopy(entity);
        } catch (JDOObjectNotFoundException e)
        {
            _logger.log(Level.SEVERE, "Error findById" + e.getMessage(), e);
        }
        return entity;
    }

    public List<E> findAll()
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("SELECT FROM " + _entityClass.getName());
        List<E> list = (List<E>) query.execute();
        E[] array = (E[]) pm.detachCopyAll( list.toArray());
        List<E> listDetached = new ArrayList<E>();
        listDetached.addAll(Arrays.asList(array));

        return  listDetached;

    }

    public List<E> findLast( long lastUpdate )
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("SELECT FROM " + _entityClass.getName()  + " WHERE dateUpdate > " + lastUpdate );
        List<E> list = (List<E>) query.execute();
        E[] array = (E[]) pm.detachCopyAll( list.toArray());
        List<E> listDetached = new ArrayList<E>();
        listDetached.addAll(Arrays.asList(array));

        return  listDetached;

    }

    public <E> E create(E entity)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        E persisted = null;
        try
        {
            persisted = pm.makePersistent(entity);
        } finally
        {
            pm.close();
        }
        return persisted;

    }

    public void remove(long id)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();
        E entity = null;
        try
        {
            entity = pm.getObjectById(_entityClass, id);
            pm.deletePersistent(entity);

        } catch (JDOObjectNotFoundException e)
        {
            _logger.log(Level.SEVERE, "Error findById" + e.getMessage(), e);
        } finally
        {
            pm.close();
        }

    }

    public void update(E entity)
    {
        PersistenceManager pm = PMF.get().getPersistenceManager();

        try
        {
            pm.makePersistent(entity);
        } finally
        {
            pm.close();
        }

    }


}
