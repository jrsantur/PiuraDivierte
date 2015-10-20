package com.project.workgroup.piuradivierte.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.project.workgroup.piuradivierte.Config;

import static com.project.workgroup.piuradivierte.util.LogUtils.LOGD;
import static com.project.workgroup.piuradivierte.util.LogUtils.makeLogTag;

/**
 * Created by Junior on 06/10/2015.
 */
public class PrefUtils  {

    private static final String TAG = makeLogTag("PrefUtils");

    /** Boolean indicating whether ToS has been accepted */
    public static final String PREF_TOS_ACCEPTED = "pref_tos_accepted";

    /**
     * Boolean indicating whether the debug build warning was already shown.
     */
    public static final String PREF_DEBUG_BUILD_WARNING_SHOWN = "pref_debug_build_warning_shown";


    public static boolean isTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_TOS_ACCEPTED, false);
    }
    public static void markTosAccepted(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_TOS_ACCEPTED, true).commit();
    }

    public static boolean wasDebugWarningShown(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(PREF_DEBUG_BUILD_WARNING_SHOWN, false);
    }

    public static void markDebugWarningShown(final Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        sp.edit().putBoolean(PREF_DEBUG_BUILD_WARNING_SHOWN, true).commit();
    }

}
