package com.here.assemble;

import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;


public class AssembleLocationProvider implements GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{
	
	private Context context;

    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    public LocationClient mLocationClient;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	public AssembleLocationProvider(Context context){
		this.context = context;
		init();
	}

	
	private void init(){

        // Try to establish Location Services
        if (servicesConnected()) {
            mLocationClient = new LocationClient(context, this, this);

            // TODO: Create a loader to let the user know that we're still
            // determining location
            new Thread(new Runnable() {
                public void run() {
                    mLocationClient.connect();
                }
            }).start();

        }
	}
	private boolean servicesConnected() {
        // Check that Google Play services is available
        int resultCode =
                GooglePlayServicesUtil.
                        isGooglePlayServicesAvailable(context);
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates",
                    "Google Play services is available.");
            // Continue
            return true;
        // Google Play services was not available for some reason
        } else {
            // Get the error code
//            int errorCode = connectionResult.getErrorCode();
//            // Get the error dialog from Google Play services
//            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
//                    errorCode,
//                    this,
//                    CONNECTION_FAILURE_RESOLUTION_REQUEST);
//
//            // If Google Play services can provide an error dialog
//            if (errorDialog != null) {
//                // Create a new DialogFragment for the error dialog
//                ErrorDialogFragment errorFragment =
//                        new ErrorDialogFragment();
//                // Set the dialog in the DialogFragment
//                errorFragment.setDialog(errorDialog);
//                // Show the error dialog in the DialogFragment
//                errorFragment.show(getSupportFragmentManager(),
//                        "Location Updates");
//            }
        	return false;
        }
    }

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
}
