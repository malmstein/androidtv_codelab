package com.android.example.leanback.fastlane;

import android.app.Activity;
import android.os.Bundle;

import com.android.example.leanback.R;

public class LeanbackActivity extends Activity {

    public static final String LEANBACK_FRAGMENT_TAG = "leanback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leanback);

        if (getFragmentManager().findFragmentByTag(LEANBACK_FRAGMENT_TAG) == null) {
            getFragmentManager().beginTransaction().add(R.id.leanback_root, new LeanbackBrowseFragment(), LEANBACK_FRAGMENT_TAG).commit();
        }

    }
}
