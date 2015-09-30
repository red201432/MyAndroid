package com.example.draganddraw;

import java.text.AttributedCharacterIterator.Attribute;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class BoxDrawingView extends View {
	public static final String TAG="BoxDrawingView";
	private Box mCurrentBox;
	private Paint mBoxPaint;
	private Paint mBackgroundPaint;
	
	private ArrayList<Box> mBoxs=new ArrayList<Box>();
	
	
	public BoxDrawingView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	public BoxDrawingView(Context context, AttributeSet attr) {
		// TODO Auto-generated constructor stub
		super(context,attr);
		
		mBoxPaint=new Paint();
		mBoxPaint.setColor(0x22ff0000);
		
		mBackgroundPaint=new Paint();
		mBackgroundPaint.setColor(0xfff8efe0);
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		canvas.drawPaint(mBackgroundPaint);
		for(Box box:mBoxs){
			float left=Math.min(box.getmOrigin().x, box.getmCurrent().x);
			float right=Math.max(box.getmOrigin().x, box.getmCurrent().x);
			float top=Math.min(box.getmCurrent().y, box.getmCurrent().y);
			float down=Math.max(box.getmCurrent().y, box.getmCurrent().y);
			canvas.drawRect(left, top,right,down,mBoxPaint);
		}
	}
	public boolean onTouchEvent(MotionEvent event){
		PointF curr=new PointF(event.getX(),event.getY());
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, " ACTION_DOWN");
			mCurrentBox=new Box(curr);
			mBoxs.add(mCurrentBox);
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i(TAG, " ACTION_MOVE");
			if(mCurrentBox!=null)
			{
				mCurrentBox.setmCurrent(curr);
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			mCurrentBox=null;
			break;
		case MotionEvent.ACTION_CANCEL:
			mCurrentBox=null;
			break;
		}
		
		return true;
	}
}
