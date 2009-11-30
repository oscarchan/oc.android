package oc.android.sandbox.revolve.widget.reminder;

import java.util.Arrays;
import java.util.List;

import android.app.TabActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabContentFactory;

/**
 * testing the tab widget
 * @author ochan
 */
public class ToDoTabActivity extends TabActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		TabHost tabHost = getTabHost();
		
		tabHost.addTab(tabHost.newTabSpec("Grocery")
				.setIndicator("Grocery")
				.setContent(new TabContentFactory()
				{
					public View createTabContent(String tag)
					{
						TextView text = new TextView(ToDoTabActivity.this);
						text.setText("Grocery List");
						return text;
					}
				}));
		
		tabHost.addTab(tabHost.newTabSpec("ToDo")
				.setIndicator("ToBuyList")
				.setContent(new TabContentFactory()
				{
					public View createTabContent(String tag)
					{
						ListView listView = new ListView(ToDoTabActivity.this);
						
						List<String> values = Arrays.asList("Stamps", "Printer", "Laptop");
						listView.setAdapter(new ArrayAdapter<String>(ToDoTabActivity.this, android.R.layout.simple_list_item_1, values));
						
						return listView;
					}
				}));
		
		
	}
}
