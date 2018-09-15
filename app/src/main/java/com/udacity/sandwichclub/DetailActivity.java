package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    Sandwich sandwich;

    //fields for View
    //private ImageView mImageView;
    private TextView mOriginTextView;
    private TextView mDescriptionTextView;
    private TextView mIngredientsTextView;
    private TextView mAlsoKnownTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        //initialisation of views
        //mImageView =findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mDescriptionTextView =findViewById(R.id.description_tv);
        mIngredientsTextView =findViewById(R.id.ingredients_tv);
        mAlsoKnownTextView =findViewById(R.id.also_known_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        //Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        //we need first to crete a String from a List
        mAlsoKnownTextView.setText(sandwich.getAlsoKnownAs().toString());

        //We also need to create a String from a list for displaying Ingredient
        mIngredientsTextView.setText(sandwich.getIngredients().toString());

        //display the description
        mDescriptionTextView.setText(sandwich.getDescription());

        Toast.makeText(this, "fonction populate", Toast.LENGTH_SHORT).show();
    }
}
