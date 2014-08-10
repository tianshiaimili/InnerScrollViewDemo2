package com.example.innerscrollviewdemo;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class InnerScrollView extends ScrollView{

	  Handler handler;

	    /**
	     */
	    public ScrollView parentScrollView;
	    private int lastScrollDelta = 0;
	    int mTop = 10;
	    /**
	     * 表示上一个触摸的点 (刚点击进去时的坐标点)而不是说手指离开时的坐标点
	     */
	    int currentY;
	
	
	    public InnerScrollView(Context context, AttributeSet attrs) {
	        super(context, attrs);
	        System.out.println("InnerScrollView(Context context, AttributeSet attrs)");
	        handler = new Handler();

	    }
	    
	    
	    @Override
	    public boolean onInterceptTouchEvent(MotionEvent ev) {
	    	// TODO Auto-generated method stub
	    	
	    	int code = ev.getAction();
	    	switch (code) {
			case MotionEvent.ACTION_DOWN:
				getParent().requestDisallowInterceptTouchEvent(true);
				/**
				 * ev.getY() 是针对屏幕左上角（0，0）而计算得
				 */
				currentY = (int) ev.getY();
				LogUtils2.i("onInterceptTouchEvent---currentY=="+currentY);
				break;
				
			case MotionEvent.ACTION_MOVE:
				
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				getParent().requestDisallowInterceptTouchEvent(false);
				break;
			default:
				break;
			}
	    	return super.onInterceptTouchEvent(ev);
	    }
	    
	    @Override
	    public boolean onTouchEvent(MotionEvent ev) {
	    	// TODO Auto-generated method stub
	    	int code = ev.getAction();
	    	
	    	switch (code) {
			case MotionEvent.ACTION_DOWN:
				currentY = (int) ev.getY();
				LogUtils2.i("onTouchEvent——ACTION_DOWN---currentY=="+currentY);
				break;
				
			case MotionEvent.ACTION_MOVE:
				
				View child = getChildAt(0);
				
				/**
				 * 得到neibuScrollView的第一个子View LinerLayout的高度
				 * 用来计算 滑动了多少距离，然后判断是否子ScrollView滑动到底部
				 */
				int childHeight = child.getMeasuredHeight();
				 /**
                 * getMeasuredHeight() 得到的应该是当前InnerScrollView的高度 
                 * 所以获取得子View LinerLayout的高度减去当前scrollView的高度 就代表滑到底部了
                 */
				int currentScrollViewHeiht = getMeasuredHeight();
				int currentShowHeight = childHeight - currentScrollViewHeiht;
				
				LogUtils2.i("childHeight——="+childHeight +"   currentScrollViewHeiht="+currentScrollViewHeiht);
				LogUtils2.i("currentShowHeight——="+currentShowHeight);
				
				 int y = (int) ev.getY();
				 int scrollY = getScrollY();
				 LogUtils2.i("y——="+y +"    scrollY=="+scrollY+"   currentY=="+currentY);
				 
				 if(currentY > y){
					 LogUtils2.i("向上........");
					 if(scrollY >= currentShowHeight){
						 
						 getParent().requestDisallowInterceptTouchEvent(false);
						 return false;
						 
					 }else {
						
						 getParent().requestDisallowInterceptTouchEvent(true); 
						 
					}
					 
				 }else  if(currentY < y){
					 LogUtils2.i("向下*****************");
					 if(scrollY <=0){
						 
						 getParent().requestDisallowInterceptTouchEvent(false);
						 return false;
					 }else {
						
						 getParent().requestDisallowInterceptTouchEvent(true);
						 
					}
					 
				}
				 
				 LogUtils2.i("y——="+y+"   currentY=="+currentY +"    scrollY=="+scrollY);
				 currentY = y;
				 
				break;

			default:
				break;
			}
	    	
	    	return super.onTouchEvent(ev);
	    }

	    private int lastDelta;
	    private int marginTop = 5;
	    private int tempTop ;
		public void scrollTo(final View childView) {
			// TODO Auto-generated method stub
			handler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					int oldScrollY = getScrollY();
					int top = childView.getTop() - marginTop;
					
					lastDelta = top - oldScrollY;
					smoothScrollTo(0, lastDelta);
					
				}
			}, 50);
		}


		public void resume() {
			// TODO Auto-generated method stub
			 overScrollBy(0, -lastDelta, 0, getScrollY(), 0, getScrollRange(), 0, 0, true);
			 lastDelta = 0;
		}
		
	    private int getScrollRange() {
	        int scrollRange = 0;
	        if (getChildCount() > 0) {
	        	
	            View child = getChildAt(0);
	            LogUtils2.d("child.getHeight() - (getHeight())==="+child.getHeight() +" __"+ (getHeight()));
	            scrollRange = Math.max(0, child.getHeight() - (getHeight()));
	            LogUtils2.i("scrollRange==="+scrollRange +"    child.getHeight()=="+child.getHeight());
	        }
	        return scrollRange;
	    }
	    

}
