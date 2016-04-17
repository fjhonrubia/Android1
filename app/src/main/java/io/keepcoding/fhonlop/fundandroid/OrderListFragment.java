package io.keepcoding.fhonlop.fundandroid;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by javi on 4/4/16.
 */
public class OrderListFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";
    private static final String SAVED_TABLE_ID = "table";
    private static final String DIALOG_BILL = "DialogBill";

    private RecyclerView mTableRecyclerView;
    private OrderAdapter mAdapter;

    private Table mTable;
    private Double mBill = 0.0;

    public static OrderListFragment newInstance(UUID tableID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TABLE_ID, tableID);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID tableId = (UUID)getArguments().getSerializable(ARG_TABLE_ID);
        mTable = Orders.get(getActivity()).getTable(tableId);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mTableRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mTableRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            UUID tableUUID = UUID.fromString(savedInstanceState.getString(SAVED_TABLE_ID));
            mTable = Orders.get(getActivity()).getTable(tableUUID);
        }

        mTable = Orders.get(getActivity()).getTable(Orders.get(getActivity()).getTableSelected());

        updateUI();

        return view;

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putString(SAVED_TABLE_ID, mTable.getID().toString());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_order_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_order:
                Intent intent = PlateListActivity.newIntent(getActivity(), mTable.getID());
                startActivity(intent);
                return true;
            case R.id.menu_calculate_bill:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                BillFragment dialog  = BillFragment.newInstance("La cuenta de la mesa: " + mTable.getName() + " es de: " + mBill.toString());
                dialog.show(fm, DIALOG_BILL);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {

        Orders orders = Orders.get(getActivity());
        List<Order> ordersTable = orders.getOrdersByTable(mTable.getID());

        for (Order order: ordersTable) {
            mBill += order.getPlate().getPrice();
        }

        if (mAdapter == null) {
            mAdapter = new OrderAdapter(ordersTable);
            mTableRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setOrders(ordersTable);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class OrderHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Order mOrder;
        private ImageView mPlateImageView;
        private TextView mPlateNameTextView;

        public OrderHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPlateImageView = (ImageView)itemView.findViewById(R.id.list_item_order_plate_image_view);
            mPlateNameTextView = (TextView)itemView.findViewById(R.id.list_item_order_plate_name_text_view);
        }

        public void bindOrder(Order order) {

            mOrder = order;
            mPlateNameTextView.setText(mOrder.getPlate().getName());
            mPlateImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), mOrder.getPlate().returnIDImage()));

        }

        @Override
        public void onClick(View v) {

            Intent intent = PlatePagerActivity.newIntent(getActivity(), mTable.getID());
            startActivity(intent);

        }

    }

    private class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {

        private List<Order> mOrders;

        public OrderAdapter(List<Order> orders) {
            mOrders = orders;
        }

        @Override
        public OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_order, parent, false);
            return new OrderHolder(view);

        }

        @Override
        public void onBindViewHolder(OrderHolder holder, int position) {

            Order order = mOrders.get(position);
            holder.bindOrder(order);
        }

        @Override
        public int getItemCount() {

            return mOrders.size();

        }

        public void setOrders(List<Order> orders) {

            mOrders = orders;

        }

    }
}
