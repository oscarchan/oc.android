package oc.android.util;

public class StringUtils
{
	public static String trimToEmpty(String original)
	{
		if(original==null)
			return "";
		else
			return original.trim();
	}
	
	public static boolean isEmpty(String s)
	{
		if(s==null)
			return true;
		
		return s.length()==0;
	}
	
	public static boolean isBlank(String s)
	{
		if(s==null)
			return true;
		
		return s.trim().length()==0;
	}
	
	
}
