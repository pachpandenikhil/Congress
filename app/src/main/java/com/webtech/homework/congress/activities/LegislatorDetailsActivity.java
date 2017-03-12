package com.webtech.homework.congress.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.fragments.FavoriteLegislators;
import com.webtech.homework.congress.model.Legislator;
import com.webtech.homework.congress.model.LegislatorsComparator;
import com.webtech.homework.congress.utils.Constants;
import com.webtech.homework.congress.utils.Utility;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import uk.co.senab.photoview.PhotoView;

import static com.webtech.homework.congress.utils.Utility.getNullableDisplayValue;

public class LegislatorDetailsActivity extends AppCompatActivity {

    private boolean isFavorite = false;
    private Legislator legislator;
    private Button favoritesButton;

    private Button facebookButton;
    private Button twitterButton;
    private Button websiteButton;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legislator_details);

        setTitle("Legislator Info");

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String selectedLegislator = intent.getStringExtra(Constants.SELECTED_LEGISLATOR);
        legislator = new Gson().fromJson(selectedLegislator, Legislator.class);
        favoritesButton = (Button) findViewById(R.id.legislator_favorites_button);
        addImageToFavoritesButton();
        populateLegislatorDetails();
        addListenerOnFavoritesButton();
        addListenerOnFacebookButton();
        addListenerOnTwitterButton();
        addListenerOnWebsiteButton();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onBackPressed();
        return true;
    }

    private void addListenerOnFacebookButton() {
        facebookButton = (Button) findViewById(R.id.facebook_button);
        facebookButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String facebookID = legislator.getFacebook_id();
                if(!getNullableDisplayValue(facebookID).equalsIgnoreCase(Constants.NULL)) {
                    String facebookURL = "https://www.facebook.com/" + facebookID;
                    Uri uri = Uri.parse(facebookURL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Facebook ID found", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void addListenerOnTwitterButton() {
        twitterButton = (Button) findViewById(R.id.twitter_button);
        twitterButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String twitterID = legislator.getTwitter_id();
                if(!getNullableDisplayValue(twitterID).equalsIgnoreCase(Constants.NULL)) {
                    String twitterURL = "https://twitter.com/" + twitterID;
                    Uri uri = Uri.parse(twitterURL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No Twitter ID found", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void addListenerOnWebsiteButton() {
        websiteButton = (Button) findViewById(R.id.website_button);
        websiteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String websiteURL = legislator.getWebsite();
                if(!getNullableDisplayValue(websiteURL).equalsIgnoreCase(Constants.NULL)) {
                    Uri uri = Uri.parse(websiteURL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "No website found", Toast.LENGTH_LONG).show();
                }
            }

        });
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
        if(legislator != null) {
            List<Legislator> favoriteLegislators = FavoriteLegislators.favoriteLegislators;
            String bioguideID = legislator.getBioguide_id();
            for(int idx = 0; idx < favoriteLegislators.size(); idx++) {
                Legislator currLegislator = favoriteLegislators.get(idx);
                if(bioguideID.equalsIgnoreCase(currLegislator.getBioguide_id())) {
                    position = idx;
                    break;
                }
            }
        }
        return position;
    }

    private void populateLegislatorDetails() {
        if(legislator != null) {

            //legislator image
            PhotoView image = (PhotoView) findViewById(R.id.detailsLegislatorImage);
            String imageURL = "https://theunitedstates.io/images/congress/original/" +legislator.getBioguide_id()+ ".jpg";
            Picasso.with(getApplicationContext()).load(imageURL)
                    .error(R.mipmap.ic_launcher)
                    .resize(200,240).centerCrop()
                    .into(image);

            //party logo
            ImageView partyLogo = (ImageView) findViewById(R.id.detailsLegislatorPartyLogo);
            int logoID = getLogoID(legislator.getParty());
            //Picasso.with(getApplicationContext()).load(logoID).into(partyLogo);
            //Picasso.with(getApplicationContext()).load(logoID).fit().centerCrop().into(partyLogo);
            Picasso.with(getApplicationContext()).load(logoID).into(partyLogo);

            //party name
            TextView partyName = (TextView) findViewById(R.id.detailsLegislatorPartyName);
            partyName.setText(getPartyName(legislator.getParty()));

            //name
            TextView name = (TextView) findViewById(R.id.detailsLegislatorName);
            String strName = legislator.getTitle() + ". " + legislator.getLast_name() + ", " + legislator.getFirst_name();
            name.setText(strName);

            //email
            TextView email = (TextView) findViewById(R.id.detailsLegislatorEmail);
            email.setText(getNullableDisplayValue(legislator.getOc_email()));

            //chamber
            TextView chamber = (TextView) findViewById(R.id.detailsLegislatorChamber);
            chamber.setText(WordUtils.capitalize(legislator.getChamber()));

            //contact
            TextView contact = (TextView) findViewById(R.id.detailsLegislatorContact);
            contact.setText(getNullableDisplayValue(legislator.getPhone()));

            //start term
            TextView startTerm = (TextView) findViewById(R.id.detailsLegislatorStartTerm);
            String strStartTerm = Utility.convertToDateFormat(legislator.getTerm_start(), "MMM dd, yyyy");
            startTerm.setText(strStartTerm);

            //end term
            TextView endTerm = (TextView) findViewById(R.id.detailsLegislatorEndTerm);
            String strEndTerm = Utility.convertToDateFormat(legislator.getTerm_end(), "MMM dd, yyyy");
            endTerm.setText(strEndTerm);

            //term
            Date today = new Date();
            Date termStartDate = Utility.getDate(legislator.getTerm_start());
            Date termEndDate = Utility.getDate(legislator.getTerm_end());
            int num = Utility.daysBetween(termStartDate, today);
            double numerator =(double)num;
            int denominator =  Utility.daysBetween(termStartDate, termEndDate);
            double percentage = (numerator/denominator)*100;
            long roundedProgress = Math.round(percentage);
            int progress = (int)roundedProgress;
            progress = Math.min(100, progress);
            ProgressBar term = (ProgressBar) findViewById(R.id.detailsLegislatorTerm);
            term.setProgress(progress);

            //setting progress bar label
            TextView progressLabel = (TextView) findViewById(R.id.detailsLegislatorTermLabel);
            String strProgressLabel = String.valueOf(progress) + "%";
            progressLabel.setText(strProgressLabel);


            //office
            TextView office = (TextView) findViewById(R.id.detailsLegislatorOffice);
            office.setText(getNullableDisplayValue(legislator.getOffice()));

            //state
            TextView state = (TextView) findViewById(R.id.detailsLegislatorState);
            state.setText(legislator.getState());

            //fax
            TextView fax = (TextView) findViewById(R.id.detailsLegislatorFax);
            fax.setText(getNullableDisplayValue(legislator.getFax()));

            //birthday
            TextView birthday = (TextView) findViewById(R.id.detailsLegislatorBirthday);
            String strBirthday = Utility.convertToDateFormat(legislator.getBirthday(), "MMM dd, yyyy");
            birthday.setText(strBirthday);
        }
    }

    private String getPartyName(String party) {
        String name = Constants.NULL;
        if(party != null) {
            switch (party) {
                case "D" :
                    name = "Democrat";
                    break;
                case "R" :
                    name = "Republican";
                    break;
                case "I" :
                    name = "Independent";
                    break;
                default:
                    name = Constants.NULL;
            }
        }
        return name;
    }

    private int getLogoID(String party) {
        int logoID = R.mipmap.ic_launcher;
        if(party != null) {
            switch (party) {
                case "D" :
                    logoID = R.drawable.ic_democrat;
                    break;
                case "R" :
                    logoID = R.drawable.ic_republican;
                    break;
                case "I" :
                    logoID = R.drawable.ic_independent;
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
                    FavoriteLegislators.favoriteLegislators.add(legislator);
                }
                else {
                    int position = getFavoritesPosition();
                    if(position != -1) {
                        FavoriteLegislators.favoriteLegislators.remove(position);
                    }

                }

                //FavoriteLegislators.adapter == null if Favorites fragment is never opened by the user
                //check required if user Favorites (especially, un-favorites) a legislator before opening the
                //Favorites fragment
                if(FavoriteLegislators.adapter != null) {
                    Collections.sort(FavoriteLegislators.favoriteLegislators, new LegislatorsComparator());
                    FavoriteLegislators.adapter.notifyDataSetChanged();
                }

                v.setBackgroundResource(isFavorite ? R.drawable.ic_details_favorites_checked : R.drawable.ic_details_favorites_unchecked);
            }

        });

    }
}
