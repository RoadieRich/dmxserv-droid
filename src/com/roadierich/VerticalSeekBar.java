package com.roadierich;

/**
 * This code based on a post by Fatal1ty2787 on Stack Overflow:
 * http://stackoverflow.com/a/7957745
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.SeekBar.OnSeekBarChangeListener;

@SuppressWarnings("unused")
public class VerticalSeekBar extends android.widget.SeekBar
{

	private OnSeekBarChangeListener myListener;

	public VerticalSeekBar(Context context)
	{
		super(context);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	public VerticalSeekBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(h, w, oldh, oldw);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	@Override
	public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener)
	{
		this.myListener = onSeekBarChangeListener;
	}

	protected void onDraw(Canvas c)
	{
		c.rotate(-90);
		c.translate(-getHeight(), 0);

		super.onDraw(c);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		if (!isEnabled())
		{
			return false;
		}

		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			if (myListener != null)
				myListener.onStartTrackingTouch(this);
			break;
		case MotionEvent.ACTION_MOVE:
			int progress = Math.max(Math.min(getMax() - (int) (getMax() * event.getY() / getHeight()),255), 0);
			setProgress(progress);
			onSizeChanged(getWidth(), getHeight(), 0, 0);
			myListener.onProgressChanged(this,
					progress,
					true);
			break;
		case MotionEvent.ACTION_UP:
			myListener.onStopTrackingTouch(this);
			break;

		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}
}
