package io.keepcoding.fhonlop.fundandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class PlateActivity extends SingleFragmentActivity {

    private static final String EXTRA_TABLE_ID = "io.keepcoding.fhonlop.fundandroid.table_id";
    private static final String EXTRA_PLATE_ID = "io.keepcoding.fhonlop.fundandroid.plate_id";

    public static Intent newIntent(Context packageContext, UUID tableID, UUID plateID) {
        Intent intent = new Intent(packageContext, PlateActivity.class);
        intent.putExtra(EXTRA_TABLE_ID, tableID);
        intent.putExtra(EXTRA_PLATE_ID, plateID);

        return intent;
    }

    @Override
    protected Fragment createFragment() {

        UUID tableId = (UUID)getIntent().getSerializableExtra(EXTRA_TABLE_ID);
        UUID plateId = (UUID)getIntent().getSerializableExtra(EXTRA_PLATE_ID);
        return PlateFragment.newInstance(tableId, plateId);

    }
}
