package com.webtech.homework.congress.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.activities.LegislatorDetailsActivity;
import com.webtech.homework.congress.adapters.LegislatorsAdapter;
import com.webtech.homework.congress.model.Legislator;
import com.webtech.homework.congress.model.LegislatorsComparator;
import com.webtech.homework.congress.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FavoriteLegislators extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static List<Legislator> favoriteLegislators = new ArrayList<>();
    public static LegislatorsAdapter adapter;

    private Map<String, Integer> mapIndex;
    private ListView view;

    public FavoriteLegislators() {
        // Required empty public constructor
    }

    public static FavoriteLegislators newInstance(String param1, String param2) {
        FavoriteLegislators fragment = new FavoriteLegislators();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorite_legislators, container, false);
        view = (ListView) rootView.findViewById(R.id.favoriteLegislatorsList);
        Collections.sort(favoriteLegislators, new LegislatorsComparator());
        adapter = new LegislatorsAdapter(getActivity(), R.layout.legislator_row, favoriteLegislators);

        //adding animations
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(view);

        view.setAdapter(animationAdapter);


        //attaching onClickListener
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), LegislatorDetailsActivity.class);
                String selectedLegislator = new Gson().toJson(favoriteLegislators.get(position));
                intent.putExtra(Constants.SELECTED_LEGISLATOR, selectedLegislator);
                startActivity(intent);
            }
        });

        populateIndexList();
        displayIndex(rootView, inflater);

        return rootView;
    }

    private void displayIndex(View rootView, LayoutInflater inflater) {

        LinearLayout indexLayout = (LinearLayout)rootView.findViewById(R.id.favoriteLegislatorsIndex);
        indexLayout.removeAllViews();
        List<String> indexList = new ArrayList<String>(mapIndex.keySet());
        Collections.sort(indexList);
        TextView textView;
        for(final String index : indexList)
        {
            textView = (TextView) inflater.inflate(R.layout.alphabet_indicator, null);
            textView.setText(index);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    view.setSelection(mapIndex.get(index));
                }
            });

            indexLayout.addView(textView);
        }
    }

    private void populateIndexList() {
        mapIndex = new LinkedHashMap<String, Integer>();
        for(int i = 0; i < favoriteLegislators.size(); i++)
        {
            String index = (favoriteLegislators.get(i).getLast_name().charAt(0) + "").toUpperCase();
            if(mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
