package io.keepcoding.fhonlop.fundandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by javi on 4/4/16.
 */
public class TableListFragment extends Fragment {

    private RecyclerView mTableRecyclerView;
    private TableAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        mTableRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        mTableRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        updateUI();

        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.download_plates_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_download_plates:
                AsyncTask<String, Integer, List<Plate>> downloadTask = new PlatesDownloader(this);
                downloadTask.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void updateUI() {

        Orders orders = Orders.get(getActivity());
        List<Table> tables = orders.getTables();

        if (mAdapter == null) {
            mAdapter = new TableAdapter(tables);
            mTableRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setTables(tables);
            mAdapter.notifyDataSetChanged();
        }

    }

    private class TableHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Table mTable;
        private TextView mTitleTextView;

        public TableHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)itemView.findViewById(R.id.list_item_table_button);
        }

        public void bindTable(Table table) {

            mTable = table;
            mTitleTextView.setText(mTable.getName());

        }

        @Override
        public void onClick(View v) {

            Intent intent = OrderListActivity.newIntent(getActivity(), mTable.getID());
            Orders.get(getActivity()).setTableSelected(mTable.getID());
            startActivity(intent);

        }

    }

    private class TableAdapter extends RecyclerView.Adapter<TableHolder> {

        private List<Table> mTables;

        public TableAdapter(List<Table> tables) {
            mTables = tables;
        }

        @Override
        public TableHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            View view = layoutInflater.inflate(R.layout.list_item_table, parent, false);
            return new TableHolder(view);

        }

        @Override
        public void onBindViewHolder(TableHolder holder, int position) {

            Table table = mTables.get(position);
            holder.bindTable(table);
        }

        @Override
        public int getItemCount() {

            return mTables.size();

        }

        public void setTables(List<Table> tables) {

            mTables = tables;

        }

    }

    private class PlatesDownloader extends AsyncTask<String, Integer, List<Plate>> {
        private WeakReference<ProgressBar> mProgressBar;
        private WeakReference<TableListFragment> mFragment;

        public PlatesDownloader(TableListFragment fragment) {
            mFragment = new WeakReference<>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mFragment.get().getView() != null) {
                mProgressBar = new WeakReference<>((ProgressBar) mFragment.get().getView().findViewById(R.id.progress));
            }
            getView().findViewById(R.id.loading).setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Plate> doInBackground(String... params) {
            LinkedList<Plate> platesList = new LinkedList<>();
            URL url;
            InputStream input = null;
            try {
                url = new URL(String.format("http://www.mocky.io/v2/570dfd41120000211812e610"));
                HttpURLConnection con = (HttpURLConnection) (url.openConnection());
                con.setConnectTimeout(5000);
                con.connect();
                int responseLength = con.getContentLength();
                byte data[] = new byte[1024];
                long currentBytes  = 0;
                int downloadedBytes;
                input = con.getInputStream();
                StringBuilder sb = new StringBuilder();
                while ((downloadedBytes = input.read(data)) != -1) {
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }

                    sb.append(new String(data, 0, downloadedBytes));

                    if (responseLength > 0) {
                        currentBytes += downloadedBytes;
                        publishProgress((int) ((currentBytes * 100) / responseLength));
                    }
                    else {
                        currentBytes++;
                        publishProgress((int)currentBytes * 10);
                    }

                }

                JSONObject jsonRoot = new JSONObject(sb.toString());
                JSONArray plates = jsonRoot.getJSONArray("plates");

                for (int i = 0; i < plates.length(); i++) {
                    JSONObject plate = plates.getJSONObject(i);

                    String name = plate.getString("name");
                    String description = plate.getString("description");
                    String image = plate.getString("photo");
                    double price = plate.getDouble("price");

                    Plate newPlate = new Plate(name, description, price, image);

                    JSONArray allergens = plate.getJSONArray("allergens");

                    for (int j = 0; j < allergens.length(); j++) {
                        JSONObject a = allergens.getJSONObject(j);
                        String str = a.keys().next().toString();
                        newPlate.setAllergen(str);
                    }

                    newPlate.setPhoto(image);

                    platesList.add(newPlate);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return platesList;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (mProgressBar.get() != null) {
                if (mProgressBar.get().isIndeterminate()) {
                    mProgressBar.get().setIndeterminate(false);
                }
                mProgressBar.get().setProgress(values[0]);
            }
        }

        @Override
        protected void onPostExecute(List<Plate> plates) {
            super.onPostExecute(plates);
            if (mFragment.get() != null) {
                if (plates != null && plates.size() > 0) {
                    mFragment.get().setPlates(plates);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setNegativeButton(android.R.string.ok, null);
                    alert.setMessage(R.string.error_download_plates);
                    alert.show();

                }
            }
            getView().findViewById(R.id.loading).setVisibility(View.INVISIBLE);
        }
    }

    public void setPlates(List<Plate> plates) {
        Orders.get(getActivity()).setPlates(plates);
    }

}
