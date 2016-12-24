package com.webtech.homework.congress.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.activities.LegislatorDetailsActivity;
import com.webtech.homework.congress.adapters.LegislatorsAdapter;
import com.webtech.homework.congress.model.Legislator;
import com.webtech.homework.congress.model.LegislatorResults;
import com.webtech.homework.congress.model.LegislatorsComparator;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class HouseLegislators extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<Legislator> houseLegislators;
    private LegislatorsAdapter adapter;

    private OnFragmentInteractionListener mListener;

    private Map<String, Integer> mapIndex;
    private ListView view;

    public HouseLegislators() {
        // Required empty public constructor
    }

    public static HouseLegislators newInstance(String param1, String param2) {
        HouseLegislators fragment = new HouseLegislators();
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
        View rootView = inflater.inflate(R.layout.fragment_house_legislators, container, false);
        houseLegislators = getHouseLegislators();
        view = (ListView)rootView.findViewById(R.id.legislatorsByHouseList);
        adapter = new LegislatorsAdapter(getActivity(), R.layout.legislator_row, houseLegislators);

        //adding animations
        AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
        animationAdapter.setAbsListView(view);

        view.setAdapter(animationAdapter);

        //attaching onClickListener
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), LegislatorDetailsActivity.class);
                String selectedLegislator = new Gson().toJson(houseLegislators.get(position));
                intent.putExtra(Constants.SELECTED_LEGISLATOR, selectedLegislator);
                startActivity(intent);
            }
        });

        populateIndexList();
        displayIndex(rootView, inflater);


        return rootView;
    }

    private void displayIndex(View rootView, LayoutInflater inflater) {

        LinearLayout indexLayout = (LinearLayout)rootView.findViewById(R.id.houseLegislatorsIndex);
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
        for(int i = 0; i < houseLegislators.size(); i++)
        {
            String index = (houseLegislators.get(i).getLast_name().charAt(0) + "").toUpperCase();
            if(mapIndex.get(index) == null)
                mapIndex.put(index, i);
        }
    }

    private List<Legislator> getHouseLegislators() {
        List<Legislator> legislators = null;
        //first invocation of the method
        if (houseLegislators == null) {
            String strHouseLegislators = Utility.readFromPreferences(getContext(), Constants.HOUSE_LEGISLATORS, null);
            //value not found in SharedPreferences
            if (strHouseLegislators == null) {
                legislators = new ArrayList<>();
                new HouseLegislatorsAsyncTask().execute(Constants.QUERY_HOUSE_LEGISLATORS);
            } else {
                legislators =Utility.getLegislatorsFromJSON(strHouseLegislators);
                Collections.sort(legislators, new LegislatorsComparator());
            }
        }
        else {
            legislators = houseLegislators;
        }
        return legislators;
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


    private class HouseLegislatorsAsyncTask extends AsyncTask<String, Void, Boolean> {

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
                    Utility.writeToPreferences(getContext(), Constants.HOUSE_LEGISLATORS, strData);

                    Gson gson = new Gson();
                    LegislatorResults results = gson.fromJson(strData, LegislatorResults.class);
                    houseLegislators = Arrays.asList(results.getResults());
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
            Collections.sort(houseLegislators, new LegislatorsComparator());
            adapter.notifyDataSetChanged();
            if (result == false)
                Toast.makeText(getContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

        }
    }

}
