package com.webtech.homework.congress.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.webtech.homework.congress.R;


public class CommitteesFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FragmentTabHost mTabHost;

    public CommitteesFragment() {
        // Required empty public constructor
    }

    public static CommitteesFragment newInstance(String param1, String param2) {
        CommitteesFragment fragment = new CommitteesFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_bills,container, false);


        mTabHost = (FragmentTabHost)rootView.findViewById(R.id.billsTabHost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.billsTabContent);

        mTabHost.addTab(mTabHost.newTabSpec("house_committees").setIndicator("House"),
                HouseCommittees.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("senate_committees").setIndicator("Senate"),
                SenateCommittees.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("joint_committees").setIndicator("Joint"),
                JointCommittees.class, null);

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
