package oc.android.widget;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * this activity explores various layouts in android.R.layout
 * @author ochan
 *
 */
public class SimpleAdapterActivity extends ListActivity
{
	public static final String LAYOUT_ID = "oc.layout.id";
	private static String TITLE_1 = "title1";
	private static String TITLE_2 = "title2";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		int layoutID = intent.getIntExtra(LAYOUT_ID, -1);
		
		setListAdapter(newSimpleAdapter(layoutID));
		
		String title = "SimpleAdapter Test";
		if(layoutID!=-1)
			title = title + ": " + getLayoutName(layoutID);
		
		setTitle(title);
	}
	
	@SuppressWarnings("unchecked")
  @Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Map<String, Object> rowMap = (Map<String, Object>) l.getItemAtPosition(position);
		Integer layoutID = (Integer) rowMap.get(LAYOUT_ID);

		if(layoutID==null) {
			Toast.makeText(this, "unable to recognize layout id: " + layoutID, Toast.LENGTH_SHORT);
		} else {
			Intent intent = new Intent();
			intent.setClass(this, SimpleAdapterActivity.class);

			intent.putExtra(LAYOUT_ID, layoutID);
			startActivity(intent);
		}
	}
	
	private SimpleAdapter newSimpleAdapter(int id)
	{
		List<Map<String, Object>> data = getLayoutIDs();
		
		switch(id) 
		{
			default:
			case android.R.layout.simple_list_item_1:
			{
				String[] keys = new String[] { TITLE_1 };
				int[] ids = new int[] {android.R.id.text1};
				
				return new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, keys, ids);
			}
			case android.R.layout.simple_list_item_2:
			case android.R.layout.two_line_list_item:
			{
				String[] keys = new String[] { TITLE_1, TITLE_2};
				int[] ids = new int[] {android.R.id.text1, android.R.id.text2};
				
				return new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, keys, ids);
			}
			case android.R.layout.simple_list_item_checked:
			case android.R.layout.simple_list_item_multiple_choice:
			case android.R.layout.simple_list_item_single_choice:
			{
				SimpleAdapter.ViewBinder binder = new SimpleAdapter.ViewBinder()
				{
					public boolean setViewValue(View view, Object data, String textRepresentation)
					{
						if(view instanceof TextView)
						{
							TextView textView = (TextView) view;
							if(data!=null)
								textView.setText(data.toString());
							
							return true;
						}
						return false;
					}
				};
				String[] keys = new String[] { TITLE_1};
				int[] ids = new int[] {android.R.id.text1};
				
				SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, id, keys, ids);
				simpleAdapter.setViewBinder(binder);
				return simpleAdapter;
			}
		}
	}

	private static List<Field> getLayoutFields()
	{
		List<Field> filteredFields = new ArrayList<Field>();
		Field[] fields = android.R.layout.class.getFields();
		
		for (Field field : fields) {
			int modifiers = field.getModifiers();
			
			if (Modifier.isStatic(modifiers) 
					&& Modifier.isPublic(modifiers) 
					&& Integer.TYPE.equals(field.getType())) {
				filteredFields.add(field);
			}
		}
		
		return filteredFields;
	}

	private static String getLayoutName(int layout)
	{
		List<Field> fields = getLayoutFields();
		
		for (Field field : fields) {
			try {
				int id = field.getInt(null);
				if(id==layout)
					return field.getName();
			} catch (Exception e) {
			}
		}
		
		return "unknown:" + layout;
	}
	private static List<Map<String, Object>> getLayoutIDs()
	{
		List<Map<String, Object>> layouts = new ArrayList<Map<String, Object>>();
	
		for (Field field : getLayoutFields()) {
			try {
				int layoutID = field.getInt(null);
				String layoutName = field.getName();
				
				Map<String, Object> layout = new HashMap<String, Object>();
				layout.put(TITLE_1, layoutName);
				layout.put(TITLE_2, layoutName + "2");
				layout.put(LAYOUT_ID, layoutID);
				
				layouts.add(layout);
			} catch (Exception e) {
				// ignore
			}
		}
			
		return layouts; 
	}
}
