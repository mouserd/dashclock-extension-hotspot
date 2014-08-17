package com.pixelus.dashclock.ext.hotspot;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.crashlytics.android.Crashlytics;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import com.pixelus.dashclock.ext.hotspot.broadcast.WifiApClientsScanCompleteBroadcastReceiver;
import com.pixelus.dashclock.ext.hotspot.broadcast.WifiApConnectedClientsBroadcastReceiver;
import com.pixelus.dashclock.ext.hotspot.broadcast.WifiApConnectionChangedBroadcastReceiver;
import com.pixelus.dashclock.ext.hotspot.builder.HotspotMessageBuilder;
import com.whitebyte.wifihotspotutils.WifiApManager;

import java.util.ArrayList;
import java.util.List;

public class HotspotExtension extends DashClockExtension {

  public static final String TAG = HotspotExtension.class.getSimpleName();

  public static final String WIFI_AP_ENABLED = "com.pixelus.dashclock.ext.hotspot.HOTSPOT_ENABLED";
  public static final String WIFI_AP_CLIENTS_SCANNED = "com.pixelus.dashclock.ext.hotspot.WIFI_AP_CLIENTS_SCANNED";
  public static final String WIFI_AP_CLIENTS_SCANNED_EXTRA = "com.pixelus.dashclock.ext.hotspot.WIFI_AP_CLIENTS_SCANNED_EXTRA";
  public static final String WIFI_AP_STATE_CHANGED = "android.net.wifi.WIFI_AP_STATE_CHANGED";
  public static final String WIFI_AP_CLIENT_STATUS_CHANGED = "android.net.wifi.WIFI_AP_STA_STATUS_CHANGED";

  public static final int UPDATE_REASON_FORCED = 99;
  public static final int UPDATE_REASON_CLIENT_SCAN = 100;
  public static final int UPDATE_REASON_WIFI_AP_CONNECTED = 101;
  public static final int UPDATE_REASON_WIFI_AP_DISCONNECTED = 102;

  private WifiApManager wifiApManager;

  @Override
  public void onCreate() {
    super.onCreate();

    Crashlytics.start(this);

    wifiApManager = new WifiApManager(this);

    // On create, register to receive any changes to the wifi settings.  This ensures that we can
    // update our extension status based on us toggling the state or something externally.

    registerReceiver(new WifiApConnectionChangedBroadcastReceiver(this), new IntentFilter(WIFI_AP_STATE_CHANGED));
    registerReceiver(new WifiApConnectedClientsBroadcastReceiver(this), new IntentFilter(WIFI_AP_CLIENT_STATUS_CHANGED));
    registerReceiver(new WifiApClientsScanCompleteBroadcastReceiver(this), new IntentFilter(WIFI_AP_CLIENTS_SCANNED));
  }

  @Override
  public void onUpdateData(int updateReason) {

    onUpdateData(updateReason, null);
  }

  public void onUpdateData(int updateReason, List<CharSequence> clients) {

    if (updateReason == UPDATE_REASON_WIFI_AP_CONNECTED) {
      clients = new ArrayList<CharSequence>();
    } else if (updateReason == UPDATE_REASON_WIFI_AP_DISCONNECTED) {
      clients = null;
    }

    final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    final HotspotMessageBuilder builder = new HotspotMessageBuilder()
        .withContext(this)
        .withWifiApManager(wifiApManager)
        .withWifiApClients(clients);

    final String status = builder.buildStatusMessage();
    final int icon = getIcon(wifiApManager);

    final ExtensionData extensionData = new ExtensionData()
        .visible(true)
        .icon(icon)
        .status(status)
        .expandedTitle(builder.buildExpandedTitleMessage())
        .expandedBody(builder.buildExpandedBodyMessage())
        .clickIntent(new Intent(this, ToggleHotspotDialogActivity.class)
            .putExtra(WIFI_AP_ENABLED, wifiApManager.isWifiApEnabled()));

    publishUpdate(extensionData);
  }

  private int getIcon(WifiApManager wifiApManager) {

    // Is wifi disabled?
    if (!wifiApManager.isWifiApEnabled()) {
      return R.drawable.ic_hotspot_off;
    }

    return R.drawable.ic_launcher;
  }
}