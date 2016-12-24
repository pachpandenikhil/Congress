package com.webtech.homework.congress.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.activities.CommitteeDetailsActivity;
import com.webtech.homework.congress.adapters.CommitteesAdapter;
import com.webtech.homework.congress.model.Committee;
import com.webtech.homework.congress.model.CommitteesComparator;
import com.webtech.homework.congress.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FavoriteCommittees extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static List<Committee> favoriteCommittees = new ArrayList<>();
    public static CommitteesAdapter adapter;

    public FavoriteCommittees() {
        // Required empty public constructor
    }

    public static FavoriteCommittees newInstance(String param1, String param2) {
        FavoriteCommittees fragment = new FavoriteCommittees();
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
        View rootView = inflater.inflate(R.layout.fragment_favorite_committees, container, false);
        ListView view = (ListView) rootView.findViewById(R.id.favoriteCommitteesList);
        Collections.sort(favoriteCommittees, new CommitteesComparator());
        adapter = new CommitteesAdapter(getActivity(), R.layout.committee_row, favoriteCommittees);

        //adding animations
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(view);

        view.setAdapter(animationAdapter);


        //attaching onClickListener
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CommitteeDetailsActivity.class);
                String selectedCommittee = new Gson().toJson(favoriteCommittees.get(position));
                intent.putExtra(Constants.SELECTED_COMMITTEE, selectedCommittee);
                startActivity(intent);
            }
        });

        return rootView;
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
