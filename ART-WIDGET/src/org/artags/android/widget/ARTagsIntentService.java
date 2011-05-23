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

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author Pierre Levy
 */
public class ARTagsIntentService extends IntentService
{

    private static final String URL_LATEST = "http://artags-site.appspot.com/json?gallery=latest";
    private long mStartTime;
    private int mRefreshDelay = 10;
    private static Tag mCurrentTag;
    private static List<Tag> mTagList;
    private static int mCurrentTagIndex;
    private static boolean mRunning;

    public ARTagsIntentService()
    {
        super("ARTagsIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.d("ARTags Widget", "Handle Intent");

        if (mTagList == null)
        {
            mTagList = getTagList();
        }

        if (!mRunning)
        {
            mUpdateTimeTask.run();
            mRunning = true;
        }
    }
    private Handler mHandler = new Handler();
    private Runnable mUpdateTimeTask = new Runnable()
    {

        public void run()
        {
            Log.d("ARTags Widget", "Run update thread");

            final long start = mStartTime;
            long millis = SystemClock.uptimeMillis() - start;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            nextTag();
            mHandler.postAtTime(this, start + (((minutes * 60) + seconds + mRefreshDelay) * 1000));
        }

        private void nextTag()
        {
            mCurrentTag = mTagList.get(mCurrentTagIndex);

            if (mCurrentTag.getBitmap() == null)
            {
                mCurrentTag.setBitmap(HttpUtils.loadBitmap( mCurrentTag.getThumbnailUrl()));
            }
            ARTagsWidget.updateTag(mCurrentTag);
            mCurrentTagIndex++;
            if (mCurrentTagIndex >= mTagList.size())
            {
                mCurrentTagIndex = 0;
            }

        }
    };

    private List<Tag> getTagList()
    {
        List<Tag> list = new ArrayList<Tag>();
//        list.add(new Tag(2496053, "cure menetic"));
//        list.add(new Tag(726001, "Cygne"));
//        list.add(new Tag(1011001, "twerp"));
        String jsonflow = HttpUtils.getUrl(URL_LATEST);

        try
        {

            JSONTokener tokener = new JSONTokener(jsonflow);
            JSONObject json = (JSONObject) tokener.nextValue();
            JSONArray jsonTags = json.getJSONArray("tags");
            for (int i = 0; i < jsonTags.length(); i++)
            {
                JSONObject jsonTag = jsonTags.getJSONObject(i);
                Tag tag = new Tag();
                tag.setText(jsonTag.getString("title"));
                tag.setThumbnailUrl(jsonTag.getString("imageUrl"));
                list.add(tag);
            }
        } catch (JSONException e)
        {
            throw new RuntimeException("JSON Parsing Error : " + e.getMessage(), e);
        }
        return list;
    }
}
