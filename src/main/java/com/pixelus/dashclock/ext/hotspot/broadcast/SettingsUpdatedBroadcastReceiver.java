package com.pixelus.dashclock.ext.hotspot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.pixelus.dashclock.ext.hotspot.HotspotExtension;

public class SettingsUpdatedBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = SettingsUpdatedBroadcastReceiver.class.getSimpleName();

  private HotspotExtension extension;

  public SettingsUpdatedBroadcastReceiver(final HotspotExtension extension) {

    this.extension = extension;
  }

  @Override
  public synchronized void onReceive(Context context, Intent intent) {

    Log.d(TAG, "User updated extension settings...");
    try {
      extension.onUpdateData(HotspotExtension.UPDATE_REASON_FORCED);
    } catch (NullPointerException e) {
      // Every so often an exception seems to be thrown by the DashClock api.
      // It seems that this exception is timing related.
    }
  }
}