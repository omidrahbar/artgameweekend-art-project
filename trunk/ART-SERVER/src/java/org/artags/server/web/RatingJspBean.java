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

import org.artags.server.business.Tag;
import org.artags.server.business.TagDAO;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Pierre Levy
 */
public class RatingJspBean implements Serializable
{
    private static Logger logger = Logger.getLogger(RatingJspBean.class.getName());

    public String doRating( HttpServletRequest request )
    {
        String msg = "Your rating has been successfully registered";
        try
        {
        int rating = Integer.parseInt( request.getParameter("rating"));
        long id = Long.parseLong( request.getParameter("id"));

        TagDAO dao = new TagDAO();
        Tag tag = dao.findById( id );
        int ratingSum = tag.getRatingSum();
        int ratingCount = tag.getRatingCount();
        tag.setRatingSum( ratingSum + rating );
        tag.setRatingCount( ratingCount + 1 );
        tag.setDateUpdate( new Date().getTime());
        dao.update(tag);
        }
        catch( NumberFormatException e )
        {
            msg = "Sorry, You haven't select any star !";
        }
        catch( Exception e )
        {
            msg = "Sorry, an error occured. Your rating hasn't been correctly saved !";
            logger.log(Level.SEVERE, "Error saving rating", e);
        }
        return msg;
   }

    public String doFlag( HttpServletRequest request )
    {
        String msg = "Your notification has been successfully registered";
        try
        {
        int flag = Integer.parseInt( request.getParameter("flag"));
        long id = Long.parseLong( request.getParameter("id"));

        TagDAO dao = new TagDAO();
        Tag tag = dao.findById( id );
        tag.setInappropriate(flag);
        tag.setDateUpdate( new Date().getTime());
        dao.update(tag);
        }
        catch( Exception e )
        {
            msg = "Sorry, an error occured. Your notification hasn't been correctly saved !";
            logger.log(Level.SEVERE, "Error saving flag", e);

        }
        return msg;
   }

}
