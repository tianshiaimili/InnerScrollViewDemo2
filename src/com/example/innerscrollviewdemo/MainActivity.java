package com.example.innerscrollviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
///////test
public class MainActivity extends Activity {

	InnerScrollView innerScrollView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		innerScrollView = (InnerScrollView) findViewById(R.id.scroll_2);
		final Button button = (Button) innerScrollView
				.findViewById(R.id.scroll_button2);
		final View content = findViewById(R.id.scroll_content);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (content.getVisibility() == View.VISIBLE) {
					System.out.println("第2次点击");
					innerScrollView.resume();
					content.setVisibility(View.GONE);
				} else {
					System.out.println("第1次点击");
					innerScrollView.scrollTo(v);
					content.setVisibility(View.VISIBLE);
				}
			}
		});
		
		
	}


}
