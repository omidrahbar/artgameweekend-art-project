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
package org.artags.android.app.draw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * 
 * @author pierre
 */
public class PictureLayout extends ViewGroup
{

    private final Picture mPicture = new Picture();

    /**
     * 
     * @param context
     */
    public PictureLayout(Context context)
    {
        super(context);
    }

    /**
     * 
     * @param context
     * @param attrs
     */
    public PictureLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * 
     * @param child
     */
    @Override
    public void addView(View child)
    {
        if (getChildCount() > 1)
        {
            throw new IllegalStateException("PictureLayout can host only one direct child");
        }

        super.addView(child);
    }

    /**
     * 
     * @param child
     * @param index
     */
    @Override
    public void addView(View child, int index)
    {
        if (getChildCount() > 1)
        {
            throw new IllegalStateException("PictureLayout can host only one direct child");
        }

        super.addView(child, index);
    }

    /**
     * 
     * @param child
     * @param params
     */
    @Override
    public void addView(View child, LayoutParams params)
    {
        if (getChildCount() > 1)
        {
            throw new IllegalStateException("PictureLayout can host only one direct child");
        }

        super.addView(child, params);
    }

    /**
     * 
     * @param child
     * @param index
     * @param params
     */
    @Override
    public void addView(View child, int index, LayoutParams params)
    {
        if (getChildCount() > 1)
        {
            throw new IllegalStateException("PictureLayout can host only one direct child");
        }

        super.addView(child, index, params);
    }

    /**
     * 
     * @return
     */
    @Override
    protected LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
    }

    /**
     * 
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        final int count = getChildCount();

        int maxHeight = 0;
        int maxWidth = 0;

        for (int i = 0; i < count; i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE)
            {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }

        maxWidth += getPaddingLeft() + getPaddingRight();
        maxHeight += getPaddingTop() + getPaddingBottom();

        Drawable drawable = getBackground();
        if (drawable != null)
        {
            maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
            maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
        }

        setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec),
                resolveSize(maxHeight, heightMeasureSpec));
    }

    private void drawPict(Canvas canvas, int x, int y, int w, int h,
            float sx, float sy)
    {
        canvas.save();
        canvas.translate(x, y);
        canvas.clipRect(0, 0, w, h);
        canvas.scale(0.5f, 0.5f);
        canvas.scale(sx, sy, w, h);
        canvas.drawPicture(mPicture);
        canvas.restore();
    }

    /**
     * 
     * @param canvas
     */
    @Override
    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(mPicture.beginRecording(getWidth(), getHeight()));
        mPicture.endRecording();

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        if (false)
        {
            canvas.drawPicture(mPicture);
        } else
        {
            drawPict(canvas, 0, 0, x, y, 1, 1);
            drawPict(canvas, x, 0, x, y, -1, 1);
            drawPict(canvas, 0, y, x, y, 1, -1);
            drawPict(canvas, x, y, x, y, -1, -1);
        }
    }

    /**
     * 
     * @param location
     * @param dirty
     * @return
     */
    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty)
    {
        location[0] = getLeft();
        location[1] = getTop();
        dirty.set(0, 0, getWidth(), getHeight());
        return getParent();
    }

    /**
     * 
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        final int count = super.getChildCount();

        for (int i = 0; i < count; i++)
        {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE)
            {
                final int childLeft = getPaddingLeft();
                final int childTop = getPaddingTop();
                child.layout(childLeft, childTop,
                        childLeft + child.getMeasuredWidth(),
                        childTop + child.getMeasuredHeight());

            }
        }
    }
}
