package com.webtech.homework.congress.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.webtech.homework.congress.R;
import com.webtech.homework.congress.model.Committee;

import org.apache.commons.lang3.text.WordUtils;

import java.util.List;

/**
 * Created by ferrari on 11/25/2016.
 */

public class CommitteesAdapter extends ArrayAdapter<Committee> {

    private List<Committee> committees;
    private LayoutInflater inflater;
    private int resource;
    private ViewHolder holder;

    public CommitteesAdapter(Context context, int resource, List<Committee> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        committees = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(resource, null);
            holder.committeeID = (TextView) view.findViewById(R.id.committeeID);
            holder.committeeName = (TextView) view.findViewById(R.id.committeeName);
            holder.committeeChamber = (TextView) view.findViewById(R.id.committeeChamber);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Committee committee = committees.get(position);

        holder.committeeID.setText(committee.getCommittee_id().toUpperCase());
        holder.committeeName.setText(committee.getName());
        String chamber = WordUtils.capitalize(committee.getChamber());
        holder.committeeChamber.setText(chamber);

        return view;
    }

    static class ViewHolder {
        public TextView committeeID;
        public TextView committeeName;
        public TextView committeeChamber;

    }

}


