package com.geekband.moran.utils;

/**
 * ToastTool can let you  use Toast more easy ; 
 * 
 * @author ZHANGGeng
 * @version v1.0.1
 * @since JDK5.0
 *
 */
import android.content.Context;
import android.widget.Toast;

public class ToastTool {
	
	/**
	 * 
	 * @param context  The Class's context , where  want to use this tool  
	 * @param message  The message will be show
	 */
	public static void MyToast(Context context ,String message ){
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
		
	}
	
	/**
	 * 
	 * @param context  The class's context , where want to use this tool 
	 * @param position_id  where you click the item ,there must have a id or position , Yes , it is ;
	 */
	public static void MyToast(Context context , int position_id){
		Toast.makeText(context, position_id+"", Toast.LENGTH_SHORT).show();
	}

}
