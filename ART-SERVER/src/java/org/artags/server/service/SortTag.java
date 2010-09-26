/* Copyright (c) 2010 ARTags project owners (see http://artags.org)
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

import org.artags.server.business.Tag;

/**
 *
 * @author pierre@artags.org
 */
public class SortTag implements Comparable
{
    Tag tag;
    long dist;

    SortTag( Tag tag , double lat , double lon )
    {
        this.tag = tag;
        long lat10e6 = (long) (lat * 1000000.0);
        long lon10e6 = (long) (lon * 1000000.0);
        dist = (tag.getLat10e6() - lat10e6)*(tag.getLat10e6() - lat10e6) + (tag.getLon10e6() - lon10e6)*(tag.getLon10e6() - lon10e6);
        dist = dist / 1000000000;

    }

    public int compareTo(Object t)
    {
        return (int) ( this.dist - ((SortTag) t).dist );
    }

}
