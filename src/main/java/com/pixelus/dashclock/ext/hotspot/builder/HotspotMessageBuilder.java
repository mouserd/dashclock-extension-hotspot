package com.pixelus.dashclock.ext.hotspot.builder;

import android.content.Context;
import android.util.Log;
import com.pixelus.dashclock.ext.hotspot.HotspotExtension;
import com.pixelus.dashclock.ext.hotspot.R;
import com.whitebyte.wifihotspotutils.WifiApManager;

import java.util.Arrays;
import java.util.List;

public class HotspotMessageBuilder {

  private static final String TAG = HotspotMessageBuilder.class.getSimpleName();

  private WifiApManager wifiApManager;
  private Context context;
  private List<CharSequence> wifiApClients;
  private int updateReason;

  public HotspotMessageBuilder withContext(final Context context) {
    this.context = context;
    return this;
  }

  public HotspotMessageBuilder withWifiApManager(final WifiApManager wifiManager) {
    this.wifiApManager = wifiManager;
    return this;
  }

  public HotspotMessageBuilder withWifiApClients(final List<CharSequence> wifiApClients) {
    if (wifiApClients != null) {
      this.wifiApClients = wifiApClients;
    }
    return this;
  }

  public HotspotMessageBuilder withUpdateReason(int updateReason) {
    this.updateReason = updateReason;

    if (updateReason == HotspotExtension.UPDATE_REASON_WIFI_AP_CONNECTED) {
      Log.d(TAG, "Update reason is connecting - initialising the wifiApClient list to 0.");
      wifiApClients = Arrays.asList();
    } else if (updateReason == HotspotExtension.UPDATE_REASON_WIFI_AP_DISCONNECTED) {

      Log.d(TAG, "Update reason is disconnecting - resetting the wifiApClient list.");
      wifiApClients = null;
    }

    return this;
  }

  public String buildStatusMessage() {
    return getWifiApStatus();
  }

  public String buildExpandedTitleMessage() {
    return context.getString(R.string.extension_expanded_title_connected, getWifiApStatus());
  }

  private String getWifiApStatus() {

    if (wifiApManager == null) {
      return context.getString(R.string.hotspot_status_unknown);
    }

    if (wifiApManager.isWifiApEnabled()) {
      return context.getString(R.string.hotspot_status_enabled);
    }

    return context.getString(R.string.hotspot_status_disabled);
  }

  public String buildExpandedBodyMessage() {
    if (wifiApManager != null && wifiApManager.isWifiApEnabled()) {

      return context.getString(R.string.extension_expanded_body_connected_clients,
          (wifiApClients == null ? "n/a" : wifiApClients.size()));
    }
    return null;
  }
}
