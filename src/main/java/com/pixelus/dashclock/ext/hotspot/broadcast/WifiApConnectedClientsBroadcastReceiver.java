package com.pixelus.dashclock.ext.hotspot.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.pixelus.dashclock.ext.hotspot.HotspotExtension;
import com.pixelus.dashclock.ext.hotspot.WifiApClientListener;
import com.whitebyte.wifihotspotutils.WifiApManager;

public class WifiApConnectedClientsBroadcastReceiver extends BroadcastReceiver {

  private HotspotExtension hotspotExtension;

  public WifiApConnectedClientsBroadcastReceiver(final HotspotExtension hotspotExtension) {
    super();
    this.hotspotExtension = hotspotExtension;
  }

  @Override
  public void onReceive(final Context context, final Intent intent) {

    final WifiApManager wifiApManager = new WifiApManager(hotspotExtension);
    wifiApManager.getClientList(true, 1000, new WifiApClientListener(context));
  }
}
