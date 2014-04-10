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
		  Log.d("ASSEMBLE!","CLICK");
		     requestLocation(context);
	  }
	  Log.d("ASSEMBLE!","Action: "+intent.getAction());
  }

	private void requestLocation(Context context) {
		if (AssembleFragment.mLocationClient == null){
			Log.e("ASSEMBLE!", "User has not started the activity yet");
			Toast.makeText(context, "YOU HAVE NOT LAUNCHED THE ACTIVITY YET", Toast.LENGTH_LONG).show();
		} else {
			m_location = AssembleFragment.mLocationClient.getLastLocation();
		    Toast.makeText(context, m_location.getLatitude() + ","+m_location.getLongitude(), Toast.LENGTH_SHORT).show();
		}
	}
} 