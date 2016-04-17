package io.keepcoding.fhonlop.fundandroid;

import android.support.v4.app.Fragment;

/**
 * Created by javi on 4/4/16.
 */
public class TableListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {

        return new TableListFragment();
    }


}
