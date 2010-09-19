/* Copyright (c) 2010 ARtags project owners (see http://artags.org)
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

import java.util.ArrayList;
import java.util.List;
import org.artags.site.business.Tag;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author pierre@artags.org@androidsoft.org
 */
public class TagParser extends DefaultHandler
{

    private static final String TAG = "tag";
    private static final String URL = Constants.SERVER + "/display?id=";
    private static final String URL_ICON = Constants.SERVER + "/thumbnail?id=";
    private static final String ID = "id";
    private static final String TITLE = "name";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "lon";
    private static final String IMAGE = "image-id";
    private static final String THUMBNAIL = "thumbnail-id";
    private static final String DATE = "date";
    private static final String DATE_VALUE = "date-value";
    private static final String RATING = "rating";
    private static final String RATING_VALUE = "rating-value";
    private static final String RATING_COUNT = "rating-count";
    private List<Tag> tags;
    private Tag currentTag;
    private StringBuilder builder;

    public List<Tag> getTags()
    {
        return this.tags;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name) throws SAXException
    {
        super.endElement(uri, localName, name);
        if (this.currentTag != null)
        {
            if (name.equalsIgnoreCase(ID))
            {
                currentTag.setId(builder.toString());
            } else if (name.equalsIgnoreCase(TITLE))
            {
                currentTag.setName(builder.toString());
            } else if (name.equalsIgnoreCase(DATE))
            {
                currentTag.setDate(builder.toString());
            } else if (name.equalsIgnoreCase(DATE_VALUE))
            {
                currentTag.setDateValue( Long.parseLong(builder.toString()) );
            } else if (name.equalsIgnoreCase(RATING))
            {
                currentTag.setRating(builder.toString());
            } else if (name.equalsIgnoreCase(RATING_VALUE))
            {
                currentTag.setRatingValue( Integer.parseInt(builder.toString()));
            } else if (name.equalsIgnoreCase(RATING_COUNT))
            {
                currentTag.setRatingCount( Integer.parseInt(builder.toString()));
            } else if (name.equalsIgnoreCase(LATITUDE))
            {
                currentTag.setLatitude(builder.toString());
            } else if (name.equalsIgnoreCase(LONGITUDE))
            {
                currentTag.setLongitude(builder.toString());
            } else if (name.equalsIgnoreCase(IMAGE))
            {
                currentTag.setUrl(URL + builder.toString());
            } else if (name.equalsIgnoreCase(THUMBNAIL))
            {
                currentTag.setIconUrl(URL_ICON + builder.toString());
                currentTag.setThumbnailId( builder.toString() );
            } else if (name.equalsIgnoreCase(TAG))
            {
                tags.add(currentTag);
            }
            builder.setLength(0);
        }
    }

    @Override
    public void startDocument() throws SAXException
    {
        super.startDocument();
        tags = new ArrayList<Tag>();
        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attributes) throws SAXException
    {
        super.startElement(uri, localName, name, attributes);
        if (name.equalsIgnoreCase(TAG))
        {
            this.currentTag = new Tag();
        }
    }

}

