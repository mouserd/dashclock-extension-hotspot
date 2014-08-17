package com.pixelus.dashclock.ext.hotspot;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import com.whitebyte.wifihotspotutils.WifiApManager;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.pixelus.dashclock.ext.hotspot.HotspotExtension.WIFI_AP_ENABLED;

public class ToggleHotspotFragment extends DialogFragment {

  private static final String TAG = ToggleHotspotFragment.class.getSimpleName();

  private boolean wifiApEnabled;
  private WifiApManager wifiApManager;
  private WifiManager wifiManager;

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {

    wifiApManager = new WifiApManager(getActivity());
    wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);

    final Activity activity = getActivity();
    final Intent intent = getActivity().getIntent();
    final Bundle bundle = intent.getExtras();
    wifiApEnabled = bundle.getBoolean(WIFI_AP_ENABLED);

    int title = R.string.toggle_hotspot_to_enabled_title;
    int message = R.string.toggle_hotspot_to_enabled_message;
    if (wifiApEnabled) {
      title = R.string.toggle_hotspot_to_disabled_title;
      message = R.string.toggle_hotspot_to_disabled_message;
    }

    return new AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.toggle_hotspot_affirmative_button, new DialogClickListener())
        .setNegativeButton(R.string.toggle_hotspot_negative_button, new DialogClickListener())
        .create();

  }

  private class DialogClickListener implements DialogInterface.OnClickListener {

    @Override
    public void onClick(final DialogInterface dialog, final int id) {

      Log.d(TAG, "Handling " + (BUTTON_POSITIVE == id ? "affirmative" : "negative") + " click event");
      final Activity activity = getActivity();

      if (BUTTON_POSITIVE == id) {

        wifiApManager = new WifiApManager(activity);
        wifiApManager.setWifiApEnabled(wifiApManager.getWifiApConfiguration(), !wifiApEnabled);

        wifiManager.setWifiEnabled(wifiApEnabled);
      }

      activity.finish();
    }
  }
}