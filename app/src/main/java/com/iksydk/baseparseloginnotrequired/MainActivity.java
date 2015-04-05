package com.iksydk.baseparseloginnotrequired;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity
{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment
    {

        private ParseUser mCurrentUser;

        public PlaceholderFragment()
        {
            //Check to see whether we already have a current user
            mCurrentUser = ParseUser.getCurrentUser();
            if(mCurrentUser == null)
            {
                //if we do not have a current user then we create a temporary one
                mCurrentUser = createTempUser();
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            //Textview where we will greet the user by their temporary name or otherwise show there was an error
            TextView welcomeUserTextView = (TextView) rootView.findViewById(R.id.welcomeUserTextView);

            //if the current user is null, and as it should be set in the create method, we know it failed to create successfully
            if(mCurrentUser == null)
            {
                welcomeUserTextView.setText("Welcome. Unable to create user");
            }
            else
            {
                welcomeUserTextView.setText("Welcome: " + mCurrentUser.getUsername());
            }


            return rootView;
        }

        private ParseUser createTempUser()
        {
            ParseUser parseUser = new ParseUser();
            parseUser.setUsername("satoshi" + Math.round(Math.random() * 10000000)); //create the user with a constant and a random number
            parseUser.setPassword(Math.round(Math.random() * 10000000) + ""); //the user is created with a random password as it is required but not needed at this time
                                                                              //it can later be changed to a user provided value if needed through this same method
            try
            {
                parseUser.signUp();//we use the non background signup here as we want the activity to wait until it completes to present the user with a greeting
                                   //in a more full feature app the user would be presented with a refreshing animation or indication while it is done in the background
                Log.v(TAG, "Created user: " + parseUser.getUsername());
            }
            catch(ParseException e)
            {
                e.printStackTrace();
                Log.e(TAG, "Creating user failed: " + parseUser.getUsername());
            }

            return parseUser;
        }
    }
}
