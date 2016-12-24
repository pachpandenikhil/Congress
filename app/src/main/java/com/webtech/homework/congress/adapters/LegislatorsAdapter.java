package com.webtech.homework.congress.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.model.Legislator;

import java.util.List;

/**
 * Created by ferrari on 11/25/2016.
 */

public class LegislatorsAdapter extends ArrayAdapter<Legislator> {

    private List<Legislator> legislators;
    private LayoutInflater inflater;
    private int resource;
    private ViewHolder holder;

    public LegislatorsAdapter(Context context, int resource, List<Legislator> objects) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.resource = resource;
        legislators = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View view = convertView;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(resource, null);
            holder.legislatorImage = (ImageView) view.findViewById(R.id.legislatorImage);
            holder.legislatorName = (TextView) view.findViewById(R.id.legislatorName);
            holder.legislatorParty = (TextView) view.findViewById(R.id.legislatorParty);
            holder.legislatorState = (TextView) view.findViewById(R.id.legislatorState);
            holder.legislatorDistrict = (TextView) view.findViewById(R.id.legislatorDistrict);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        Legislator legislator = legislators.get(position);

        String imageURL = "https://theunitedstates.io/images/congress/original/" +legislator.getBioguide_id()+ ".jpg";
        Picasso.with(this.getContext()).load(imageURL)
                .error(R.mipmap.ic_launcher)
                .resize(150,170).centerCrop()
                .into(holder.legislatorImage);

        String name = legislator.getLast_name() + ", " + legislator.getFirst_name();
        holder.legislatorName.setText(name);
        holder.legislatorParty.setText("(" + legislator.getParty() + ")");
        holder.legislatorState.setText(legislator.getState_name());
        String district = Legislator.getNullableDistrictDisplayValue(legislator.getDistrict());
        holder.legislatorDistrict.setText(district);
        return view;
    }

    static class ViewHolder {
        public ImageView legislatorImage;
        public TextView legislatorName;
        public TextView legislatorParty;
        public TextView legislatorState;
        public TextView legislatorDistrict;

    }

}

