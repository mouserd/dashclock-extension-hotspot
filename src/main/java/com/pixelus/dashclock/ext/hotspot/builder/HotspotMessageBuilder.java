package com.pixelus.dashclock.ext.hotspot.builder;

import android.content.Context;
import com.pixelus.dashclock.ext.hotspot.R;
import com.whitebyte.wifihotspotutils.WifiApManager;

import java.util.List;

public class HotspotMessageBuilder {

  private static final String TAG = HotspotMessageBuilder.class.getSimpleName();

  private WifiApManager wifiApManager;
  private Context context;
  private List<CharSequence> wifiApClients;

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

  public String buildStatusMessage() {
    return getWifiApStatus();
  }

  public String buildExpandedTitleMessage() {

    if (wifiApManager == null) {

      return context.getString(R.string.extension_expanded_title_not_connected,
          context.getString(R.string.hotspot_status_unknown));
    }

    // TODO Sort out whether 2nd message param is required.
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

//      String connectedClientsMessage = "";
//      final Iterator<CharSequence> wifiApClientIterator = wifiApClients.iterator();
//      while (wifiApClientIterator.hasNext()) {
//        connectedClientsMessage += " > " + wifiApClientIterator.next();
//        if (wifiApClientIterator.hasNext()) {
//          connectedClientsMessage += "\n";
//        }
//      }

      return context.getString(R.string.extension_expanded_body_connected_clients,
          (wifiApClients == null ? "n/a" : wifiApClients.size()));
    }
    return null;
  }
}
