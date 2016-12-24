package com.webtech.homework.congress.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.activities.CommitteeDetailsActivity;
import com.webtech.homework.congress.adapters.CommitteesAdapter;
import com.webtech.homework.congress.model.Committee;
import com.webtech.homework.congress.model.CommitteeResults;
import com.webtech.homework.congress.model.CommitteesComparator;
import com.webtech.homework.congress.utils.Constants;
import com.webtech.homework.congress.utils.Utility;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SenateCommittees extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<Committee> senateCommittees;
    private CommitteesAdapter adapter;

    private OnFragmentInteractionListener mListener;

    public SenateCommittees() {
        // Required empty public constructor
    }

    public static SenateCommittees newInstance(String param1, String param2) {
        SenateCommittees fragment = new SenateCommittees();
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
        View rootView = inflater.inflate(R.layout.fragment_senate_committees, container, false);
        senateCommittees = getSenateCommittees();
        ListView view = (ListView) rootView.findViewById(R.id.senateCommitteesList);
        adapter = new CommitteesAdapter(getActivity(), R.layout.committee_row, senateCommittees);

        //adding animations
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(view);

        view.setAdapter(animationAdapter);


        //attaching onClickListener
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CommitteeDetailsActivity.class);
                String selectedCommittee = new Gson().toJson(senateCommittees.get(position));
                intent.putExtra(Constants.SELECTED_COMMITTEE, selectedCommittee);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private List<Committee> getSenateCommittees() {
        List<Committee> committees = null;
        //first invocation of the method
        if (senateCommittees == null) {
            String strSenateCommittees = Utility.readFromPreferences(getContext(), Constants.SENATE_COMMITTEES, null);
            //value not found in SharedPreferences
            if (strSenateCommittees == null) {
                committees = new ArrayList<>();
                new SenateCommitteesAsyncTask().execute(Constants.QUERY_SENATE_COMMITTEES);
            } else {
                committees = Utility.getCommitteesFromJSON(strSenateCommittees);
                Collections.sort(committees, new CommitteesComparator());
            }
        }
        else {
            committees = senateCommittees;
        }
        return committees;
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

    private class SenateCommitteesAsyncTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet httpGet = new HttpGet(urls[0]);
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httpGet);

                int status = response.getStatusLine().getStatusCode();

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String strData = EntityUtils.toString(entity);

                    //writing the data to SharedPreferences
                    Utility.writeToPreferences(getContext(), Constants.SENATE_COMMITTEES, strData);

                    Gson gson = new Gson();
                    CommitteeResults results = gson.fromJson(strData, CommitteeResults.class);
                    senateCommittees = Arrays.asList(results.getResults());
                    return true;
                }

            } catch (ParseException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            Collections.sort(senateCommittees, new CommitteesComparator());
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }
}
