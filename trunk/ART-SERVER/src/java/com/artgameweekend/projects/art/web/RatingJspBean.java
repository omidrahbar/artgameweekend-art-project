/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.artgameweekend.projects.art.web;

import com.artgameweekend.projects.art.business.Tag;
import com.artgameweekend.projects.art.business.TagDAO;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author pierre
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
