package com.here.assemble;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MyWidgetProvider extends AppWidgetProvider {

  private static final String ACTION_CLICK = "ACTION_CLICK";
  private LocationManager m_locationManager;
  private Location m_location;
  private AssembleLocationProvider m_locationProvider;
  
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager,
      int[] appWidgetIds) {


    // Get all ids
    ComponentName thisWidget = new ComponentName(context,
        MyWidgetProvider.class);
    int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
    for (int widgetId : allWidgetIds) {
      // create some random data
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
          R.layout.assemble_appwidget);

      // Register an onClickListener
      Intent intent = new Intent(context, this.getClass());
      intent.setAction(ACTION_CLICK);

      PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
          0, intent, 0);
      remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
      appWidgetManager.updateAppWidget(widgetId, remoteViews);
    }
  }
  
  @Override
  public void onReceive(Context context, Intent intent) {
	  super.onReceive(context, intent);
	  if(ACTION_CLICK.equals(intent.getAction()))
	  {
		     requestLocation(context);
		     Toast.makeText(context, m_location.getLatitude() + ","+m_location.getLongitude(), Toast.LENGTH_SHORT);
	  }
	  Log.d("ASSEMBLE!","Action: "+intent.getAction());
  }

	private void requestLocation(Context context) {
		  if(m_locationProvider ==null){
				 m_locationProvider= new AssembleLocationProvider(context);
			  }
		m_location = m_locationProvider.mLocationClient.getLastLocation();
		// Acquire a reference to the system Location Manager
//		 m_locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//
//		// Define a listener that responds to location updates
//		LocationListener locationListener = new LocationListener() {
//		    public void onLocationChanged(Location location) {
//		    	m_location = location;
//		    }
//
//		    public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//		    public void onProviderEnabled(String provider) {}
//
//		    public void onProviderDisabled(String provider) {}
//		  };
//
//		// Register the listener with the Location Manager to receive location updates
//		m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);		
	}
} 