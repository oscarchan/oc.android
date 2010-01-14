package oc.android.sandbox.revolve.widget.note;

import oc.android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;

public class AddNoteActivity extends Activity 
{
	private static final String DEFAULT_TITLE = "Add Note Title";
	private EditText mTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_note);
		
		mTitle = (EditText) findViewById(R.id.an_subject);
		
		mTitle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				onFocusTitleChange(true);
			}
		});
		
		mTitle.setOnKeyListener(new OnKeyListener() {
			
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				onFocusTitleChange(true);
				return false;
			}
		});
		
	}
	
	private void onFocusTitleChange(boolean focus) {
		if(focus && DEFAULT_TITLE.equals(mTitle.getText())) { // if focused and title is still default
			mTitle.setText("");
			mTitle.setTextColor(R.color.black);
		} else if (focus==false && mTitle.getText().toString().trim().length()==0) {
			mTitle.setText(DEFAULT_TITLE);
			mTitle.setTextColor(R.color.beige);
		}
	}
	

}
