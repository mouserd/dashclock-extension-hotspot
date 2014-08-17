package com.pixelus.dashclock.ext.hotspot;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

public class ToggleHotspotDialogActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DialogFragment dialog = new ToggleHotspotFragment();
        dialog.show(getSupportFragmentManager(), getString(R.string.toggle_hotspot_dialog_title));
    }
}