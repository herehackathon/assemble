package com.here.assemble;

import android.app.Activity;
import android.app.Dialog;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;

public class LocationProvider implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener {
	
	
    static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    LocationClient mLocationClient;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	Activity activity;
	static LocationProvider locationProvider = null;
	
	public static LocationProvider getInstance(Activity activity){
		if (activity == null){
			return null;
		}
		
		if (locationProvider == null){
			locationProvider = new LocationProvider(activity);
		}
		return locationProvider;
	}
	public LocationProvider(Activity activity){
		this.activity = activity;
	}

	private boolean servicesConnected() {
		// Check that Google Play services is available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(activity);
		// If Google Play services is available
		if (ConnectionResult.SUCCESS == resultCode) {
			// In debug mode, log the status
			Log.d("Location Updates", "Google Play services is available.");
			// Continue
			return true;
			// Google Play services was not available for some reason
		} else {
			Log.d("ASSEMBLE!", "Could not connect to location services");
			// Get the error dialog from Google Play services
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					resultCode, activity,
					CONNECTION_FAILURE_RESOLUTION_REQUEST);

			// If Google Play services can provide an error dialog
			if (errorDialog != null) {
				// Create a new DialogFragment for the error dialog
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				// Set the dialog in the DialogFragment
				errorFragment.setDialog(errorDialog);
				// Show the error dialog in the DialogFragment
				errorFragment.show(activity.getFragmentManager(), "Location Updates");
			}
		}
		return false;
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(activity,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			/*
			 * If no resolution is available, display a dialog to the user with
			 * the error.
			 */
			showErrorDialog(connectionResult.getErrorCode());
		}

	}

	@Override
	public void onConnected(Bundle bundle) {
		Log.d("ASSEMBLE!", "Services have been connected");
		Button assembleButton = (Button) activity.findViewById(
				R.id.assembleButton);
		assembleButton.setEnabled(true);
		assembleButton.setOnClickListener(new AssembleFragment());
	}

	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(activity, "Disconnected. Please re-connect.",
				Toast.LENGTH_SHORT).show();
	}

	void showErrorDialog(int code) {
		GooglePlayServicesUtil.getErrorDialog(code, activity,
				REQUEST_CODE_RECOVER_PLAY_SERVICES).show();
	}
}
