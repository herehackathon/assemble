package com.here.assemble;

import com.nokia.scbe.droid.LocalBinder;
import com.nokia.scbe.droid.ScbeService;
import com.nokia.scbe.droid.ScbeClient.ScbeEnvironment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	 private static final String TAG = "ASSEMBLE.MainActivity";
	 private ScbeService mScbeService = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        @SuppressWarnings("unchecked")
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mScbeService = ((LocalBinder<ScbeService>) service).getService();

            // do any extra setup that requires your Service
            mScbeService.setScbeEnvironment(ScbeEnvironment.QAEnvironment);
            mScbeService.setUserId("AssembleDemoUser");
        }

        @Override
        public void onServiceDisconnected(ComponentName className) {
            Log.d(TAG, "onServiceDisconnected");
        }
    };
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        // bind to our database using our Service Connection
        Intent serviceIntent = new Intent(this, ScbeService.class);
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new AssembleFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public ScbeService getScbeService() {
        if (mConnection != null) {
            return mScbeService;
        } else {
            return null;
        }
	}
}
