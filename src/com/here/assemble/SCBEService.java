package com.here.assemble;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.android.gms.location.LocationClient;
import com.nokia.scbe.droid.ScbeClient;
import com.nokia.scbe.droid.ScbeClient.OperationScope;
import com.nokia.scbe.droid.ScbeResponseBase.ScbeResponseStatus;
import com.nokia.scbe.droid.ScbeResponseT;
import com.nokia.scbe.droid.ScbeService;
import com.nokia.scbe.droid.datamodel.GeoCoordinate;
import com.nokia.scbe.droid.datamodel.Location;
import com.nokia.scbe.droid.datamodel.favoritePlace;


public class SCBEService extends Service{

	LocationClient mLocationClient = AssembleFragment.mLocationClient;
    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder {
        SCBEService getService() {
            return SCBEService.this;
        }
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("Lili's Test", "Received start id " + startId + ": " + intent);
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        double latitude;
        double longitude;
         
        final favoritePlace favPlace = new favoritePlace();
        /* Test code below! Should probably 1. thread, 2. create a new object, and 3 error validate it. But positive case works!*/
        if (mLocationClient.isConnected() && mLocationClient.getLastLocation() != null){
        	//Log.d("ASSEMBLE!","The message will look like: ASSEMBLE! http://here.com/?plcsDl=search&q=" + mLocationClient.getLastLocation().getLatitude() + "," + mLocationClient.getLastLocation().getLongitude());
            latitude = 	mLocationClient.getLastLocation().getLatitude();
            longitude = mLocationClient.getLastLocation().getLongitude();   
            favPlace.name = "Assemble";
            favPlace.attribution = "lili.shi@here.com";
            favPlace.description = "Something Awesome Is Happening HERE!!!";
            if (favPlace.location == null)
                favPlace.location = new Location();
            if (favPlace.location.position == null)
                favPlace.location.position = new GeoCoordinate();
            favPlace.location.position.latitude = latitude;
            favPlace.location.position.longitude = longitude;
        }
        
        // finished populating fields.. Update or Register
        ScbeResponseT<favoritePlace> scbeResp = null;
        
        if(favPlace != null){
	        
	            // registering

            new Thread(new Runnable() {
                public void run() {
                	favoritePlace reg;
                	ScbeClient scbeClient = MainActivity.mScbeService.getScbeClient();
                	ScbeResponseT<favoritePlace> respFav1 = scbeClient.register(favPlace, OperationScope.RemoteScope);
                	if (respFav1.Status == ScbeResponseStatus.Completed)
               		 reg = respFav1.Data;
                }
            }).start();
        	

		
	   }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.  See
    // RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

}
