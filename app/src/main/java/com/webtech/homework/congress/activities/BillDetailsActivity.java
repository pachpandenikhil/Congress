package com.webtech.homework.congress.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.webtech.homework.congress.R;
import com.webtech.homework.congress.fragments.FavoriteBills;
import com.webtech.homework.congress.model.Bill;
import com.webtech.homework.congress.model.BillHistory;
import com.webtech.homework.congress.model.BillLastVersion;
import com.webtech.homework.congress.model.BillLastVersionUrls;
import com.webtech.homework.congress.model.BillSponsor;
import com.webtech.homework.congress.model.BillUrls;
import com.webtech.homework.congress.model.BillsComparator;
import com.webtech.homework.congress.utils.Constants;
import com.webtech.homework.congress.utils.Utility;

import org.apache.commons.lang3.text.WordUtils;

import java.util.Collections;
import java.util.List;

import static com.webtech.homework.congress.utils.Utility.getNullableDisplayValue;

public class BillDetailsActivity extends AppCompatActivity {

    private boolean isFavorite = false;
    private Bill bill;
    private Button favoritesButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);
        setTitle("Bill Info");

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        String selectedBill = intent.getStringExtra(Constants.SELECTED_BILL);
        bill = new Gson().fromJson(selectedBill, Bill.class);
        favoritesButton = (Button) findViewById(R.id.bill_favorites_button);
        addImageToFavoritesButton();
        populateBillDetails();
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
        if(bill != null) {
            List<Bill> favoriteBills = FavoriteBills.favoriteBills;
            String billID = bill.getBill_id();
            for(int idx = 0; idx < favoriteBills.size(); idx++) {
                Bill currBill = favoriteBills.get(idx);
                if(billID.equalsIgnoreCase(currBill.getBill_id())) {
                    position = idx;
                    break;
                }
            }
        }
        return position;
    }

    private void populateBillDetails() {
        if(bill != null) {

            //bill id
            TextView id = (TextView) findViewById(R.id.detailsBillID);
            id.setText(bill.getBill_id().toUpperCase());

            //title
            TextView title = (TextView) findViewById(R.id.detailsBillTitle);
            String strTitle = bill.getShort_title();
            if (getNullableDisplayValue(strTitle).equalsIgnoreCase(Constants.NULL)) {
                strTitle = bill.getOfficial_title();
            }
            title.setText(strTitle);

            //type
            TextView type = (TextView) findViewById(R.id.detailsBillType);
            String strType = bill.getBill_type();
            strType = getNullableDisplayValue(strType);
            if(strType.equalsIgnoreCase(Constants.NULL)) {
                type.setText(strType);
            }
            else {
                type.setText(strType.toUpperCase());
            }


            //sponsor
            TextView sponsor = (TextView) findViewById(R.id.detailsBillSponsor);
            BillSponsor billSponsor = bill.getSponsor();
            String strSponsor = billSponsor.getTitle() + ". " + billSponsor.getLast_name() + ", " + billSponsor.getFirst_name();
            sponsor.setText(strSponsor);

            //chamber
            TextView chamber = (TextView) findViewById(R.id.detailsBillChamber);
            chamber.setText(WordUtils.capitalize(bill.getChamber()));

            //status
            TextView status = (TextView) findViewById(R.id.detailsBillStatus);
            BillHistory history = bill.getHistory();
            boolean isActive = Boolean.valueOf(history.getActive());
            String strStatus = "New";
            if(isActive) {
                strStatus = "Active";
            }
            status.setText(strStatus);

            //introduced on
            TextView introducedOn = (TextView) findViewById(R.id.detailsBillIntroducedOn);
            String strIntroducedOn = Utility.convertToDateFormat(bill.getIntroduced_on(), "MMM dd, yyyy");
            introducedOn.setText(strIntroducedOn);

            //congress URL
            TextView congressURL = (TextView) findViewById(R.id.detailsBillCongressURL);
            BillUrls urls = bill.getUrls();
            congressURL.setText(getNullableDisplayValue(urls.getCongress()));

            //version status
            TextView versionStatus = (TextView) findViewById(R.id.detailsBillVersionStatus);
            BillLastVersion lastVersion = bill.getLast_version();
            if(lastVersion != null) {
                versionStatus.setText(getNullableDisplayValue(lastVersion.getVersion_name()));
            }
            else {
                versionStatus.setText(Constants.NULL);
            }


            //bill url
            TextView billURL = (TextView) findViewById(R.id.detailsBillURL);
            BillLastVersionUrls  billUrls = lastVersion.getUrls();
            if(billUrls != null) {
                billURL.setText(getNullableDisplayValue(billUrls.getPdf()));
            }
            else {
                billURL.setText(Constants.NULL);
            }
        }
    }


    private void addListenerOnFavoritesButton() {

        favoritesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                isFavorite = !isFavorite;
                if(isFavorite) {
                    FavoriteBills.favoriteBills.add(bill);
                }
                else {
                    int position = getFavoritesPosition();
                    if(position != -1) {
                        FavoriteBills.favoriteBills.remove(position);
                    }

                }

                if(FavoriteBills.adapter != null) {
                    Collections.sort(FavoriteBills.favoriteBills, new BillsComparator());
                    FavoriteBills.adapter.notifyDataSetChanged();
                }

                v.setBackgroundResource(isFavorite ? R.drawable.ic_details_favorites_checked : R.drawable.ic_details_favorites_unchecked);
            }
        });
    }
}
