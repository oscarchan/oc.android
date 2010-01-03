package oc.android.widget;

import oc.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

/**
 * Test how to LinearLayout weight is configured
 * @author ochan
 *
 */
public class LinearLayoutWeightTest extends Activity 
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.linear_layout_weight);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) 
	{
		super.onWindowFocusChanged(hasFocus);
		
		updateTextWithDimension(R.id.ll_top);
		updateTextWithDimension(R.id.ll_middle);
		updateTextWithDimension(R.id.ll_bottom);
	}
	
	private void updateTextWithDimension(int id) 
	{
		TextView topView = (TextView) findViewById(id);

		topView.setText(topView.getText() + ": " + topView.getMeasuredHeight() + "H x " + topView.getMeasuredWidth() + " W");
	}
}
