package com.iksydk.baseparseloginnotrequired.Utils;

import android.content.Context;
import android.util.Log;

/**
 * Created by Billy on 4/5/2015.
 * This class is used to allow the use of api or other key values to exist within a protectedStrings.xml file which is not checked into git
 * it's main purpose is to prevent crashing during debug and can be overwritten to return non production api keys that are not sensitive (aka ones on free trials)
 *
 * It also allows the use of keys that are not compiled into R to be used
 */
public class GitProtectedStringManager
{
    public static String TAG = GitProtectedStringManager.class.getSimpleName();

    public static String getString(Context context, String keyname)
    {
        if(context.getResources().getIdentifier(keyname, "string", context.getPackageName()) == 0)
        {
            Log.e(TAG, "Secure string " + keyname + " not found, using insecure one. You probably forgot protectedStrings.xml.");
            return ""; //replace with insecure debug key if needed
        }
        else
        {
            Log.v(TAG, "Secure string " + keyname + " found, using it");
            return context.getString(context.getResources().getIdentifier(keyname, "string", context.getPackageName()));
        }
    }
}
