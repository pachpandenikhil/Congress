package com.webtech.homework.congress.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webtech.homework.congress.R;
import com.webtech.homework.congress.model.Bill;
import com.webtech.homework.congress.utils.Utility;

import java.util.List;

/**
 * Created by ferrari on 11/25/2016.
 */

public class BillsAdapter extends ArrayAdapter<Bill> {

    private List<Bill> bills;
    private LayoutInflater inflater;
    private int resource;
    private ViewHolder holder;

    public BillsAdapter(Context context, int resource, List<Bill> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        bills = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(resource, null);
            holder.billID = (TextView) view.findViewById(R.id.billID);
            holder.billTitle = (TextView) view.findViewById(R.id.billTitle);
            holder.billDate = (TextView) view.findViewById(R.id.billDate);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Bill bill = bills.get(position);

        holder.billID.setText(bill.getBill_id().toUpperCase());
        String title = bill.getShort_title();
        if (title == null) {
            title = bill.getOfficial_title();
        }

        holder.billTitle.setText(title);
        holder.billDate.setText(Utility.convertToDateFormat(bill.getIntroduced_on(), "MMM dd, yyyy"));

        return view;
    }

    static class ViewHolder {
        public TextView billID;
        public TextView billTitle;
        public TextView billDate;

    }

}


