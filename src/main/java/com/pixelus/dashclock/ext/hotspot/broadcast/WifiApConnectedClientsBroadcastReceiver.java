package com.pixelus.dashclock.ext.hotspot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pixelus.dashclock.ext.hotspot.HotspotExtension;
import com.pixelus.dashclock.ext.hotspot.WifiApClientListener;
import com.whitebyte.wifihotspotutils.WifiApManager;

public class WifiApConnectedClientsBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = WifiApConnectedClientsBroadcastReceiver.class.getSimpleName();

  private HotspotExtension hotspotExtension;

  public WifiApConnectedClientsBroadcastReceiver(final HotspotExtension hotspotExtension) {
    super();
    this.hotspotExtension = hotspotExtension;
  }

  @Override
  public void onReceive(final Context context, final Intent intent) {

    Log.d(TAG, "Received " + intent.getAction() + " action");

    new Thread(new Runnable() {

      public void run() {

        try {
          Thread.sleep(1000);  // Short sleep here ensures a recently connected/disconnected client has had time to
                              // complete since the AP_STATE_CHANGED broadcast was received.

          final WifiApManager wifiApManager = new WifiApManager(hotspotExtension);
          wifiApManager.getClientList(true, 500, new WifiApClientListener(context));
        } catch (InterruptedException e) {
          Log.e(TAG, "Interrupted while runnable was sleeping.");
        }
      }
    }).start();


  }
}
