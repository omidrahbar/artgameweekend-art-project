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
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

/**
 *
 * @author Pierre Levy
 */
public class ARTagsWidget extends AppWidgetProvider
{

    private static AppWidgetManager mAppWidgetManager;
    private static int[] mAppWidgetIds;
    private static Context mContext;
    private static Tag mCurrentTag;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        mContext = context;
        mAppWidgetManager = appWidgetManager;
        mAppWidgetIds = appWidgetIds;
        
        Intent intent = new Intent( context , ARTagsIntentService.class );
        context.startService(intent);
    }

    public static void updateTag(Tag tag)
    {
        mCurrentTag = tag;
        updateWidget();
    }

    private static void updateWidget()
    {
        RemoteViews remoteViews = new RemoteViews( mContext.getPackageName(), R.layout.main);
        remoteViews.setViewVisibility(R.id.progressBar, View.GONE);
        remoteViews.setImageViewBitmap(R.id.thumbnail, mCurrentTag.getBitmap());
        remoteViews.setViewVisibility(R.id.thumbnail, View.VISIBLE);
        remoteViews.setTextViewText(R.id.text, mCurrentTag.getText());
        Log.d("ARTags Widget", mAppWidgetIds.toString() + " - " + remoteViews.toString());

        mAppWidgetManager.updateAppWidget(mAppWidgetIds, remoteViews);
        Log.d("ARTags Widget", "Widget updated");
    }

}
