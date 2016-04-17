package io.keepcoding.fhonlop.fundandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by javi on 4/4/16.
 */
public class OrderListActivity extends SingleFragmentActivity {

    private static final String EXTRA_TABLE_ID = "io.keepcoding.fhonlop.fundandroid.table_id";

    public static Intent newIntent(Context packageContext, UUID tableID) {
        Intent intent = new Intent(packageContext, OrderListActivity.class);
        intent.putExtra(EXTRA_TABLE_ID, tableID);

        return intent;
    }

    @Override
    protected Fragment createFragment() {

        UUID tableId = (UUID)getIntent().getSerializableExtra(EXTRA_TABLE_ID);
        return OrderListFragment.newInstance(tableId);

    }

}
