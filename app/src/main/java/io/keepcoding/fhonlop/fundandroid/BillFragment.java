package io.keepcoding.fhonlop.fundandroid;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by javi on 6/4/16.
 */
public class BillFragment extends DialogFragment {

    private static final String ARG_BILL = "bill";

    private TextView mBill;

    public static BillFragment newInstance(String bill) {
        Bundle args = new Bundle();
        args.putString(ARG_BILL, bill);

        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String msg = getArguments().getString(ARG_BILL);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.bill_fragment, null);

        TextView mMsgBill = (TextView)v.findViewById(R.id.total_bill_text_view);
        mMsgBill.setText(msg);

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.calculate_bill_title).setPositiveButton(android.R.string.ok, null).create();
    }
}
