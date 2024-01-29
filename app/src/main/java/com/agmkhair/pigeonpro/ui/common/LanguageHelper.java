package com.agmkhair.pigeonpro.ui.common;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;


public class LanguageHelper {

    public static final String SELECTED_LANGUAGE = "Language.Helper.Selected.Language";

    public static Context setLanguage(Context context, String str) {
        if (str == null) {
            str = "en";
        }
        persist(context, str);
        return updateResourcesLegacy(context, str);
    }

 /*   public static void setLang(String lanCode,Context context)
    {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            conf.setLocale(new Locale(lanCode.toLowerCase()));
        }else {
            conf.locale = new Locale(lanCode.toLowerCase());
        }
        res.updateConfiguration(conf, dm);
    }
*/

    private static void persist(Context context, String str) {
        //SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        new SharedPref(context).saveString(SELECTED_LANGUAGE, str);
        // edit.putString(SELECTED_LANGUAGE, str);
        // edit.apply();
    }

    private static Context updateResourcesLegacy(Context context, String str) {
        Locale locale = new Locale(str);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}
