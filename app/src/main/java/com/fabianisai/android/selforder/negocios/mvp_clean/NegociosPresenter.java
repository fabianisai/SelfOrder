package com.fabianisai.android.selforder.negocios.mvp_clean;

import com.fabianisai.android.selforder.negocios.events.NegociosEvent;

/**
 * Created by fabianisai on 7/10/16.
 */

public interface NegociosPresenter {
    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
    void getNegocios();
    void onEventMainThread(NegociosEvent event);
}
