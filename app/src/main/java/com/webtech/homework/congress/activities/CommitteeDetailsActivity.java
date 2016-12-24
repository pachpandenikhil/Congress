package com.webtech.homework.congress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.fragments.FavoriteCommittees;
import com.webtech.homework.congress.model.Committee;
import com.webtech.homework.congress.model.CommitteesComparator;
import com.webtech.homework.congress.utils.Constants;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collections;
import java.util.List;

import static com.webtech.homework.congress.utils.Utility.getNullableDisplayValue;

public class CommitteeDetailsActivity extends AppCompatActivity {

    private boolean isFavorite = false;
    private Committee committee;
    private Button favoritesButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_details);
        setTitle("Committee Info");

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String selectedCommittee = intent.getStringExtra(Constants.SELECTED_COMMITTEE);
        committee = new Gson().fromJson(selectedCommittee, Committee.class);
        favoritesButton = (Button) findViewById(R.id.committees_favorites_button);
        addImageToFavoritesButton();
        populateCommitteeDetails();
        addListenerOnFavoritesButton();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }
    
    private void addImageToFavoritesButton() {
        if(favoritesButton != null) {
            int position = getFavoritesPosition();
            if(position != -1) {
                isFavorite = true;
            }
            else {
                isFavorite = false;
            }

            favoritesButton.setBackgroundResource(isFavorite ? R.drawable.ic_details_favorites_checked : R.drawable.ic_details_favorites_unchecked);
        }
    }

    private int getFavoritesPosition() {
        int position = -1;
        if(committee != null) {
            List<Committee> favoriteCommittees = FavoriteCommittees.favoriteCommittees;
            String committeeID = committee.getCommittee_id();
            for(int idx = 0; idx < favoriteCommittees.size(); idx++) {
                Committee currCommittee = favoriteCommittees.get(idx);
                if(committeeID.equalsIgnoreCase(currCommittee.getCommittee_id())) {
                    position = idx;
                    break;
                }
            }
        }
        return position;
    }


    private void populateCommitteeDetails() {
        if(committee != null) {

            //committee id
            TextView id = (TextView) findViewById(R.id.detailsCommitteeID);
            id.setText(committee.getCommittee_id().toUpperCase());

            //name
            TextView name = (TextView) findViewById(R.id.detailsCommitteeName);
            name.setText(committee.getName());

            //chamber logo
            ImageView chamberLogo = (ImageView) findViewById(R.id.detailsCommitteeChamberLogo);
            int logoID = getLogoID(committee.getChamber());
            //Picasso.with(getApplicationContext()).load(logoID).into(partyLogo);
            //Picasso.with(getApplicationContext()).load(logoID).fit().centerCrop().into(partyLogo);
            Picasso.with(getApplicationContext()).load(logoID).into(chamberLogo);


            //chamber
            TextView chamber = (TextView) findViewById(R.id.detailsCommitteeChamber);
            chamber.setText(WordUtils.capitalize(committee.getChamber()));

            //parent committee
            TextView parentID = (TextView) findViewById(R.id.detailsParentCommittee);
            String parentCommitteeID = committee.getParent_committee_id();
            if(!getNullableDisplayValue(parentCommitteeID).equalsIgnoreCase(Constants.NULL)) {
                parentID.setText(parentCommitteeID.toUpperCase());
            }
            else {
                parentID.setText(Constants.NULL);
            }

            //chamber
            TextView contact = (TextView) findViewById(R.id.detailsCommitteeContact);
            contact.setText(getNullableDisplayValue(committee.getPhone()));

            //chamber
            TextView office = (TextView) findViewById(R.id.detailsCommitteeOffice);
            office.setText(getNullableDisplayValue(committee.getOffice()));
        }
    }

    private int getLogoID(String chamber) {
        int logoID = R.mipmap.ic_launcher;
        if(chamber != null) {
            switch (chamber) {
                case "house" :
                    logoID = R.drawable.ic_house;
                    break;
                case "senate" :
                    logoID = R.drawable.ic_senate;
                    break;
                case "joint" :
                    logoID = R.drawable.ic_senate;
                    break;
                default:
                    logoID = R.mipmap.ic_launcher;
            }
        }
        return logoID;
    }

    private void addListenerOnFavoritesButton() {

        favoritesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if(isFavorite) {
                    FavoriteCommittees.favoriteCommittees.add(committee);
                }
                else {
                    int position = getFavoritesPosition();
                    if(position != -1) {
                        FavoriteCommittees.favoriteCommittees.remove(position);
                    }
                }

                if(FavoriteCommittees.adapter != null) {
                    Collections.sort(FavoriteCommittees.favoriteCommittees, new CommitteesComparator());
                    FavoriteCommittees.adapter.notifyDataSetChanged();
                }

                v.setBackgroundResource(isFavorite ? R.drawable.ic_details_favorites_checked : R.drawable.ic_details_favorites_unchecked);
            }
        });
    }
}
