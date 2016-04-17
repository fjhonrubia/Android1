package io.keepcoding.fhonlop.fundandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by javi on 6/4/16.
 */
public class PlatePagerActivity extends AppCompatActivity {

    private static final String EXTRA_TABLE_ID = "io.keepcoding.fhonlop.fundandroid.table_id";

    private ViewPager mViewPager;
    private List<Order> mOrderTable;

    public static Intent newIntent(Context packageContext, UUID tableID) {
        Intent intent = new Intent(packageContext, PlatePagerActivity.class);
        intent.putExtra(EXTRA_TABLE_ID, tableID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_plate_pager);

        UUID tableId = (UUID) getIntent().getSerializableExtra(EXTRA_TABLE_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_plate_pager_view_pager);
        mOrderTable = Orders.get(this).getOrdersByTable(tableId);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                Order order = mOrderTable.get(position);
                return PlatePagerFragment.newInstance(order.getTable().getID(), order.getPlate().getID());
            }

            @Override
            public int getCount() {
                return mOrderTable.size();
            }
        });

    }
}
