package wattary.com.wattary;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    final static String FileName = "Statues";


    public static String readSharedSetting(Context context, String settingName, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context context, String settingName, String settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

}
