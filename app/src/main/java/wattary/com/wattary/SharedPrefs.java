package wattary.com.wattary;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    final static String FileName = "Statues";
    final static String FileNameuser="UserName";

  //Check
    public static String readSharedSetting(Context context, String settingName, String defaultValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    //Username
    public static String readSharedSettingUsername(Context context, String settingName, String defaultValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(FileNameuser, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    //Save Check
    public static void saveSharedSetting(Context context, String settingName, String settingValue) {

        SharedPreferences sharedPref = context.getSharedPreferences(FileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    //save user name
    public static void saveSharedSettingUserName(Context context, String settingName, String settingValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(FileNameuser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

}
