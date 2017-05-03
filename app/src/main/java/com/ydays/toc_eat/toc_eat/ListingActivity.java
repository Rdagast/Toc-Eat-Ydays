package com.ydays.toc_eat.toc_eat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ListingActivity extends AppCompatActivity {

    private TextView textPrice;
    private TextView textRepas;
    private TextView textIngredients;
    private TextView textDescription;
    private TextView textTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle(Html.fromHtml("<font color='#ffffff'> " + getResources().getString(R.string.app_name) + "</font>"));

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String j = getIntent().getStringExtra("listing");
            try {
                JSONObject json = new JSONObject(j);
                Log.d("jsonListing",json.getString("title"));
                textTitle = (TextView) findViewById(R.id.title);
                textRepas = (TextView) findViewById(R.id.repas);
                textIngredients = (TextView) findViewById(R.id.ingredients);
                textPrice = (TextView) findViewById(R.id.prix);
                textDescription = (TextView) findViewById(R.id.description);

                textTitle.setText(json.getString("title"));
                textTitle.setTextColor(getResources().getColor(R.color.jaune));
                textRepas.setText(json.getString("repas"));
                textIngredients.setText(json.getString("ingredient"));
                textPrice.setText(json.getString("participation") + "€");
                textDescription.setText(json.getString("description"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        }
    }

