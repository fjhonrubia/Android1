package io.keepcoding.fhonlop.fundandroid;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

/**
 * Created by javi on 5/4/16.
 */
public class PlateListFragment extends Fragment {

    private static final String ARG_TABLE_ID = "table_id";

    private RecyclerView mPlateRecyclerView;
    private PlateAdapter mAdapter;

    private Table mTable;

    public static PlateListFragment newInstance(UUID tableID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TABLE_ID, tableID);
        PlateListFragment fragment = new PlateListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID tableId = (UUID)getArguments().getSerializable(ARG_TABLE_ID);
        mTable = Orders.get(getActivity()).getTable(tableId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mPlateRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mPlateRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTable = Orders.get(getActivity()).getTable(Orders.get(getActivity()).getTableSelected());

        updateUI();

        return view;

    }

    public void updateUI() {

        Orders orders = Orders.get(getActivity());
        List<Plate> platesTable = orders.getPlates();

        if (mAdapter == null) {
            mAdapter = new PlateAdapter(platesTable);
            mPlateRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setPlates(platesTable);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class PlateHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Plate mPlate;
        private ImageView mPlateImageView;
        private TextView mPlateNameTextView;
        private TextView mPlatePriceTextView;

        public PlateHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mPlateImageView = (ImageView)itemView.findViewById(R.id.list_item_plate_image_view);
            mPlateNameTextView = (TextView)itemView.findViewById(R.id.list_item_plate_name_text_view);
            mPlatePriceTextView = (TextView)itemView.findViewById(R.id.list_item_plate_price_text_view);

        }

        public void bindPlate(Plate plate) {

            mPlate = plate;
            mPlateNameTextView.setText(mPlate.getName());
            mPlatePriceTextView.setText(mPlate.getPrice().toString());
            mPlateImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), mPlate.returnIDImage()));


        }

        @Override
        public void onClick(View v) {

            Intent intent = PlateActivity.newIntent(getActivity(), mTable.getID(), mPlate.getID());
            startActivity(intent);

        }

    }

    private class PlateAdapter extends RecyclerView.Adapter<PlateHolder> {

        private List<Plate> mPlates;

        public PlateAdapter(List<Plate> plates) {
            mPlates = plates;
        }

        @Override
        public PlateHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_plate, parent, false);
            return new PlateHolder(view);

        }

        @Override
        public void onBindViewHolder(PlateHolder holder, int position) {

            Plate plate = mPlates.get(position);
            holder.bindPlate(plate);
        }

        @Override
        public int getItemCount() {

            return mPlates.size();

        }

        public void setPlates(List<Plate> plates) {

            mPlates = plates;

        }


    }
}
