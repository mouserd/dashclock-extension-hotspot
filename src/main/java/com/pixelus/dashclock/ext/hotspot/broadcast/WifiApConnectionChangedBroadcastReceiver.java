package com.pixelus.dashclock.ext.hotspot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pixelus.dashclock.ext.hotspot.HotspotExtension;
import com.whitebyte.wifihotspotutils.WifiApManager;

import static com.pixelus.dashclock.ext.hotspot.HotspotExtension.UPDATE_REASON_WIFI_AP_CONNECTED;
import static com.pixelus.dashclock.ext.hotspot.HotspotExtension.UPDATE_REASON_WIFI_AP_DISCONNECTED;
import static com.whitebyte.wifihotspotutils.WIFI_AP_STATE.WIFI_AP_STATE_ENABLED;
import static com.whitebyte.wifihotspotutils.WIFI_AP_STATE.WIFI_AP_STATE_ENABLING;

public class WifiApConnectionChangedBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = WifiApConnectionChangedBroadcastReceiver.class.getSimpleName();

  private HotspotExtension extension;

  public WifiApConnectionChangedBroadcastReceiver(final HotspotExtension extension) {

    this.extension = extension;
  }

  @Override
  public synchronized void onReceive(Context context, Intent intent) {

    int updateReason = UPDATE_REASON_WIFI_AP_DISCONNECTED;
    Log.i(TAG, "Broadcast received: " + intent.getAction() + " state: " + intent.getIntExtra("wifi_state", 0));

    try {

      // If this intent has resulted in the WIFI_AP being enabled - then trigger clients to be scanned.
      final WifiApManager wifiApManager = new WifiApManager(extension);
      if (wifiApManager.getWifiApState().equals(WIFI_AP_STATE_ENABLED)
          || wifiApManager.getWifiApState().equals(WIFI_AP_STATE_ENABLING)) {

        updateReason = UPDATE_REASON_WIFI_AP_CONNECTED;
      }


      extension.onUpdateData(updateReason);
    } catch (NullPointerException e) {
      // Every so often an exception seems to be thrown by the DashClock api.
      // It seems that this exception is timing related.
    }
  }
}