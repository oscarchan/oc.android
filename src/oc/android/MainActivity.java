package oc.android;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oc.android.util.StringUtils;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends ListActivity
{
    private static final String INTENT = "intent";
		private static final String TITLE = "title";
		private static final String OC_ANDROID_PATH = "oc.android.path";

		/** Called when the activity is first created. */

		@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();
        String path = StringUtils.trimToEmpty(intent.getStringExtra(OC_ANDROID_PATH));
        
        List<Map<String, Object>> data = getData(path, null);
        
        setListAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_1, new String[] { TITLE }, new int[] {android.R.id.text1}));
    }
    
		private List<Map<String, Object>> getData(String path, List<Map<String, Object>> current)
		{
			if(path==null) {
				Toast.makeText(this, "missing path", Toast.LENGTH_SHORT);
				return Collections.emptyList();
			} else if(current==null) {
				return getData(path, getData(path));
			} else if (current.size()==1) {
      	Map<String, Object> map = current.get(0);
      	
      	Intent intent = (Intent) map.get(INTENT);
      	if(MainActivity.class.getName().equals(intent.getComponent().getClassName())) {
      		String newPath = (String) intent.getStringExtra(OC_ANDROID_PATH);
      		
      		return getData(newPath, getData(path));
      	} else {
      		return current;
      	}
			} else {
				return current;
			}
				
		}
		protected List<Map<String, Object>> getData(String prefix) 
    {
      List<Map<String, Object>> listViewData = new ArrayList<Map<String, Object>>();

      Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
      mainIntent.addCategory(Intent.CATEGORY_SAMPLE_CODE);

      PackageManager pm = getPackageManager();

      List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

      if (null == list)
          return listViewData;

      Set<String> labels = new HashSet<String>();
      
      for (ResolveInfo info : list) {
        String label = info.activityInfo.name;
                
        if (StringUtils.isBlank(prefix) || label.startsWith(prefix)) {
        	
        	if(prefix.length()==label.length()) { // match the item label
        		listViewData.add(activityIntent(info));
        	} else {
        		int nextDelimiter = label.indexOf(".", prefix.length() + 1);
        		if(nextDelimiter==-1) {
        			listViewData.add(activityIntent(info));
        		} else {
	        		String nextLabel = label.substring(prefix.length(), nextDelimiter);
	        		String path = label.substring(0, nextDelimiter);
	
	        		if(labels.contains(nextLabel)==false) {
	        			listViewData.add(browseIntent(path, nextLabel));
	        			labels.add(nextLabel);
	        		}
        		}
        	}
        }
			}

      Collections.sort(listViewData, sDisplayNameComparator);
      
      return listViewData;
  }

	protected Map<String, Object> browseIntent(String path, String name)
	{
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		intent.putExtra(OC_ANDROID_PATH, path);

		return addItem(name, intent);
	}
  
  protected Map<String, Object> addItem(String name, Intent intent) {
      Map<String, Object> temp = new HashMap<String, Object>();
      temp.put(TITLE, name);
      temp.put(INTENT, intent);
      return temp;
  }

  protected Map<String, Object> activityIntent(ResolveInfo info) 
  {
  	String pkg = info.activityInfo.applicationInfo.packageName;
  	String componentName = info.activityInfo.name;

  	String name = componentName;
  	int lastDot = componentName.lastIndexOf('.');
  	if(lastDot>=0)
  		name = componentName.substring(lastDot, componentName.length()); 
  	
  	Intent intent = new Intent();
    intent.setClassName(pkg, componentName);
    
    return addItem(name, intent);
  }
 
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		Map<String, Object> rowMap = (Map<String, Object>) l.getItemAtPosition(position);
		Intent intent = (Intent) rowMap.get(INTENT);
		startActivity(intent);
	}

  @SuppressWarnings("unchecked")
	private final static Comparator<Map> sDisplayNameComparator = new Comparator<Map>() {
    private final Collator  collator = Collator.getInstance();

    public int compare(Map map1, Map map2) {
        return collator.compare(map1.get(TITLE), map2.get(TITLE));
    }
};


}