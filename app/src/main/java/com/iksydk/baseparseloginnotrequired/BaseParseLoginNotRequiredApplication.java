package com.iksydk.baseparseloginnotrequired;

import android.app.Application;
import android.util.Log;

import com.iksydk.baseparseloginnotrequired.Utils.GitProtectedStringManager;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.PushService;
import com.parse.SaveCallback;

/**
 * Created by Billy on 4/5/2015.
 */
public class BaseParseLoginNotRequiredApplication extends Application
{
    private static final String TAG = BaseParseLoginNotRequiredApplication.class.getSimpleName();

    @Override public void onCreate()
    {
        super.onCreate();

        Parse.initialize(this, GitProtectedStringManager.getString(this, "applicationId"), GitProtectedStringManager.getString(this, "clientKey"));

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        installation.saveInBackground(new SaveCallback()
        {
            @Override
            public void done(ParseException e)
            {
                if(e == null)
                {
                    PushService.setDefaultPushCallback(BaseParseLoginNotRequiredApplication.this, MainActivity.class);
                    Log.e(TAG, "Parse registration success (launch)");
                }
                else
                {
                    Log.e(TAG, "Parse registration failed (launch): " + e.getMessage());
                }
            }
        });
    }
}
