/* Copyright (c) 2010-2011 ARTags Project owners (see http://www.artags.org)
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

package org.artags.android.widget;

/**
 *
 * @author pierre
 */
public class LatestTagsIntentService extends AbstractTagsIntentService
{
    private static final String NAME = "LatestTagsIntentService";
    private static final String URL = "http://artags-site.appspot.com/json?gallery=latest";


   
    public LatestTagsIntentService()
    {
        super( NAME );
    }

    @Override
    String getTagListUrl()
    {
        return URL;
    }
    
    @Override
    void updateTag(Tag tag)
    {
        LatestTagsWidget.updateTag(tag);
    }


}
