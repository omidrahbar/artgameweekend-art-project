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

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *
 * @author Pierre LEVY
 */
public abstract class TagsWidgetProvider extends AppWidgetProvider
{
    static final String ACTION_SHOW_TAG = "org.artags.android.widget.SHOW_TAG";
    
    abstract Tag getCurrentTag();
    abstract Class getIntentServiceClass();
    abstract void setStatics(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds);
  
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        setStatics(context, appWidgetManager, appWidgetIds);
        Intent intent = new Intent(context, getIntentServiceClass());
        context.startService(intent);
    }
    
    @Override
    public void onReceive(Context context, Intent intent)
    {
        super.onReceive(context, intent);

        if (ACTION_SHOW_TAG.equals(intent.getAction()))
        {
            showTag(context);
        }
    }

    private void showTag(Context context)
    {
        String url = Constants.URL_JSP_TAG + getCurrentTag().getId();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
