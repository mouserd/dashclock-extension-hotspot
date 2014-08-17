package com.pixelus.dashclock.ext.hotspot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.pixelus.dashclock.ext.hotspot.HotspotExtension;

import java.util.Arrays;

import static com.pixelus.dashclock.ext.hotspot.HotspotExtension.UPDATE_REASON_CLIENT_SCAN;

public class WifiApClientsScanCompleteBroadcastReceiver extends BroadcastReceiver {

  private static final String TAG = WifiApClientsScanCompleteBroadcastReceiver.class.getSimpleName();

  private HotspotExtension extension;

  public WifiApClientsScanCompleteBroadcastReceiver(final HotspotExtension extension) {

    this.extension = extension;
  }

  @Override
  public synchronized void onReceive(Context context, Intent intent) {


    extension.onUpdateData(UPDATE_REASON_CLIENT_SCAN,
        Arrays.asList(intent.getCharSequenceArrayExtra(HotspotExtension.WIFI_AP_CLIENTS_SCANNED_EXTRA)));
  }
}
