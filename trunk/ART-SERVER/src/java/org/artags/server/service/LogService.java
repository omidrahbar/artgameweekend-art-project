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

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pierre
 */
public class LogService
{
    private static final Logger logger = Logger.getLogger( "ARTags Server ");
    private static final LogService logService = new LogService();
    
    public static LogService getLogger()
    {
        return logService;
    }
    
    public void log( String message )
    {
        System.out.println( message );
        logger.log(Level.INFO, message );
    }
    
    public void log( String message , Object... params )
    {
        System.out.println( MessageFormat.format(message, params) );
        logger.log(Level.INFO, message , params );
    }
    
}
