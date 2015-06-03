package com.pixelus.dashclock.ext.hotspot;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.whitebyte.wifihotspotutils.ClientScanResult;
import com.whitebyte.wifihotspotutils.FinishScanListener;

import java.util.ArrayList;
import java.util.List;

public class WifiApClientListener implements FinishScanListener {

  private static final String TAG = WifiApClientListener.class.getSimpleName();

  private Context context;

  public WifiApClientListener(Context context) {

    this.context = context;
  }

  public void onFinishScan(ArrayList<ClientScanResult> clients) {

    Log.d(TAG, "Finished scanning for clients: " + clients.size());
    List<CharSequence> clientDevices = new ArrayList<CharSequence>();
    for (ClientScanResult client : clients) {
      clientDevices.add(client.getIpAddr());
    }

    final Intent intent = new Intent(HotspotExtension.WIFI_AP_CLIENTS_SCANNED);
    intent.putExtra(HotspotExtension.WIFI_AP_CLIENTS_SCANNED_EXTRA, clientDevices.toArray(new CharSequence[clients.size()]));

    context.sendBroadcast(intent);
  }
}
