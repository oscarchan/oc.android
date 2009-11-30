package oc.android.sandbox.revolve.widget.reminder;

import java.util.Arrays;
import java.util.List;

import oc.android.R;
import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ToDoPopUpActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.to_do_gallery);
		
		Gallery tagViews = (Gallery) findViewById(R.id.to_do_tag_list);
		
		tagViews.setAdapter(new ImageAdapter());

	}
	
	class ImageAdapter extends BaseAdapter
	{
		private int mBackgroundId;
		public ImageAdapter()
		{
			TypedArray attrs = obtainStyledAttributes(R.styleable.Gallery1);
			mBackgroundId = attrs.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
			
			attrs.recycle();
		}
		
		public int getCount()
		{
			return mImageIds.size();
		}

		public Object getItem(int position)
		{
			return mImageIds.get(position);
		}

		public long getItemId(int position)
		{
			return mImageIds.get(position);
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			ImageView imageView;
			if(convertView==null) {
				imageView = new ImageView(ToDoPopUpActivity.this);
			} else {
				imageView = (ImageView) convertView;
			} 

			imageView.setImageResource(mImageIds.get(position));
			imageView.setScaleType(ImageView.ScaleType.CENTER);
//			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//			imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));
			
			imageView.setBackgroundResource(mBackgroundId);
			
			return imageView;
		}
		
	}

	private static List<Integer> mImageIds = 
		Arrays.asList(R.drawable.calendar, R.drawable.calculator);
}
