package org.aliveclean;

import android.os.Bundle;

/**
 * Entry page reached from 系统个性化. Translucent window so the theme manager keeps rendering
 * underneath; the transition itself is the platform's, driven by the same anim resources that app
 * uses for its own pages (declared in the theme and applied here explicitly).
 */
public final class HomeEntryActivity extends HomeActivity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        overridePendingTransition(R.anim.hla_open_enter, R.anim.hla_open_exit);
    }

    @Override public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hla_close_enter, R.anim.hla_close_exit);
    }
}
