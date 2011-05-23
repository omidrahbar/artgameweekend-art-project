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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author pierre
 */
public class HttpUtils
{

    public static String getUrl(String url)
    {
        String result = "";
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget = new HttpGet(url);
        HttpResponse response;

        try
        {
            response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();

            if (entity != null)
            {
                InputStream instream = entity.getContent();
                result = convertStreamToString(instream);
            }
        } catch (IOException ex)
        {
            Log.e(HttpUtils.class.getName(), ex.getMessage());
        }
        return result;
    }

    private static String convertStreamToString(InputStream is)
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line).append("\n");
            }
        } catch (IOException e)
        {
            Log.e(HttpUtils.class.getName(), e.getMessage());
        } finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                Log.e(HttpUtils.class.getName(), e.getMessage());
            }
        }
        return sb.toString();
    }
    
    
    public static Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        try
        {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            final BufferedInputStream bis = new BufferedInputStream(is);
            opts.inSampleSize = 1;
            bm = BitmapFactory.decodeStream(bis, null, opts);
            bis.close();
            is.close();
        } catch (IOException ex)
        {
            Log.e("ARTags Widget", "Error  : " + ex.getMessage() + ex.getClass().getName());
        }

        return bm;
    }


}
