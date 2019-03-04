package com.example.android.identify;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

//TODO: Comment the shit out of everything
public class MainActivity extends AppCompatActivity {

    public ArrayList<String> filterList;
    Resources res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        res = getResources();

//        filterList.add("Flat leaves");
//        filterList.add("Compound");

        setUpSpinner();
        updateFilters();
    }

    /**
     * Fills spinner with contents of array
     */
    private void setUpSpinner(){
        Spinner spinner = (Spinner) findViewById(R.id.item_type_spinner);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.item_type, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    public void needleButtonPressed(View view){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.needle_panel);
        liftPanel(linearLayout);

        String title = res.getString(R.string.needles);
        int[] images = {R.drawable.e6a1, R.drawable.e7d2};
        String[] headers = {res.getString(R.string.clustered_needles),
                res.getString(R.string.arranged_needles)};
        String[] descriptions = {res.getString(R.string.clustered_needles_description),
                res.getString(R.string.arranged_needles_description)};

        Intent needlesIntent = new Intent(MainActivity.this, SubPageActivity.class);
        needlesIntent.putExtra("title", title);
        needlesIntent.putExtra("images", images);
        needlesIntent.putExtra("headers", headers);
        needlesIntent.putExtra("descriptions", descriptions);
        startActivity(needlesIntent);
    }

    public void scaleButtonPressed(View view){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.scale_panel);
        liftPanel(linearLayout);

        String title = res.getString(R.string.scales_awls);
        int[] images = {R.drawable.e17d, R.drawable.e18d, R.drawable.e19a };
        String[] headers = {res.getString(R.string.northern_whitecedar),
                res.getString(R.string.southern_whitecedar), res.getString(R.string.eastern_redcedar)};
        String[] descriptions = {res.getString(R.string.northern_whitecedar_description),
                res.getString(R.string.southern_whitecedar_description),
                res.getString(R.string.eastern_redcedar_description)};

        Intent scaleIntent = new Intent(MainActivity.this, SubPageActivity.class);
        scaleIntent.putExtra("title", title);
        scaleIntent.putExtra("images", images);
        scaleIntent.putExtra("headers", headers);
        scaleIntent.putExtra("descriptions", descriptions);
        startActivity(scaleIntent);
    }

    public void flatButtonPressed(View view){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.flat_panel);
        liftPanel(linearLayout);

        String title = res.getString(R.string.flat);
        int[] images = {R.drawable.e6a3, R.drawable.e7a2, R.drawable.e7a3 };
        String[] headers = {res.getString(R.string.simple),
                res.getString(R.string.compound), res.getString(R.string.fan)};
        String[] descriptions = {res.getString(R.string.simple_description),
                res.getString(R.string.compound_description), res.getString(R.string.fan_description)};

        Intent flatIntent = new Intent(MainActivity.this, SubPageActivity.class);
        flatIntent.putExtra("title", title);
        flatIntent.putExtra("images", images);
        flatIntent.putExtra("headers", headers);
        flatIntent.putExtra("descriptions", descriptions);
        startActivity(flatIntent);
    }

    /**
     * Increases elevation of input to 8 when touched
     * @param linearLayout layout of which to change
     */
    private void liftPanel(LinearLayout linearLayout){
        ViewCompat.setElevation(linearLayout, 8);
    }

    private void updateFilters(){
        LinearLayout filterView = (LinearLayout) findViewById(R.id.filter_view);

        FilterClass filterClass = FilterClass.getFilterClass();
        filterList = filterClass.filters;

        if (filterList.size() > 0){
            for (String filter: filterList){
                filterView.addView(makeSelectedFiltersButton(filter));
            }
        }
    }

    /**
     * Creates the little buttons which indicate which filters have been selected and gives the option
     * to close them
     * @param filter the name that will be displayed on the filter
     * @return LinearLayout containing all the filter icons
     */
    private LinearLayout makeSelectedFiltersButton(final String filter){
        LinearLayout filterButton = createFilterButton();

        final TextView filterText = createFilterText(filter);
        filterButton.addView(filterText);

        ImageView filterCancel = createFilterCancelButton(filterText.getText());
        filterButton.addView(filterCancel);

        return filterButton;
    }

    private LinearLayout createFilterButton(){
        LinearLayout filterButton = new LinearLayout(MainActivity.this);
        filterButton.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, convertDpToPixels(2), 0);
        filterButton.setLayoutParams(params);
        filterButton.setBackground(getResources().getDrawable(R.drawable.filter_item));

        int buttonPadding = convertDpToPixels(8);
        filterButton.setPadding(buttonPadding, buttonPadding, buttonPadding, buttonPadding);
        return filterButton;
    }

    private TextView createFilterText(String filter){
        TextView filterText = new TextView(MainActivity.this);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        filterText.setLayoutParams(textParams);

        filterText.setPadding(0, 0, convertDpToPixels(8), 0);
        filterText.setText(filter);
        filterText.setGravity(Gravity.CENTER_VERTICAL);
        return filterText;
    }

    private ImageView createFilterCancelButton(final CharSequence text){
        ImageView filterCancel = new ImageView(MainActivity.this);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                convertDpToPixels(16), convertDpToPixels(16));
        filterCancel.setLayoutParams(imageParams);
        filterCancel.setImageResource(R.drawable.close_filter);
        filterCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilterClass filterClass = FilterClass.getFilterClass();
                ArrayList<String> filters = filterClass.filters;
                filters.remove(text);
                //TODO: make the filterButton disappear without resetting the Activity
                Intent mainActivity = new Intent(MainActivity.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });
        return filterCancel;
    }

    private int convertDpToPixels(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }

}