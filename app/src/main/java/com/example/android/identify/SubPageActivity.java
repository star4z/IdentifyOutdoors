package com.example.android.identify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SubPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_page);

        //Gets info packaged with Intent
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        int[] images = bundle.getIntArray("images");
        String[] headers = bundle.getStringArray("headers");
        String[] descriptions = bundle.getStringArray("descriptions");

        createSubPage(title, images, headers, descriptions);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_filter:
                FilterClass filterClass = FilterClass.getFilterClass();
                filterClass.filters.add(getIntent().getExtras().getString("title"));
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void back(View view){
        Intent mainIntent = new Intent(this, MainActivity.class);
        startActivity(mainIntent);
    }

    private void createSubPage(String title, int[] images, String[] headers, String[] descriptions){

        TextView header = (TextView) findViewById(R.id.header);
        header.setText(title);

        LinearLayout body = (LinearLayout) findViewById(R.id.body);

        for (int i = 0; i < headers.length; i++){
            RelativeLayout nextPanel = createRelativeLayoutPanel();

            //Creates panel image
            ImageView nextPanelImage = createPanelImage(images[i]);
            nextPanel.addView(nextPanelImage);

            int padding4 = convertDpToPixels(4);
            //Create panel title TextView and add to nextPanel
            TextView nextPanelHeader = createPanelHeader(padding4 ,nextPanelImage.getId(), headers[i]);
            nextPanel.addView(nextPanelHeader);

            //Create panel description TextView and add to nextPanel
            TextView nextPanelDescription = new TextView(this);
            RelativeLayout.LayoutParams descriptionParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            descriptionParams.addRule(RelativeLayout.BELOW, nextPanelHeader.getId());
            descriptionParams.addRule(RelativeLayout.END_OF, nextPanelImage.getId());
            nextPanelDescription.setLayoutParams(descriptionParams);
            nextPanelDescription.setText(descriptions[i]);
            nextPanelDescription.setPadding(padding4, padding4, padding4, padding4);
            nextPanel.addView(nextPanelDescription);

            body.addView(nextPanel);
        }
    }

    /**
     * Creates RelativeLayout to build contents in
     * @return RelativeLayout for content panel
     */
    private RelativeLayout createRelativeLayoutPanel(){
        RelativeLayout nextPanel = new RelativeLayout(this);
        LinearLayout.LayoutParams panelParams =  new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, convertDpToPixels(128));
        int padding8 = convertDpToPixels(8);
        panelParams.setMargins(padding8, padding8, padding8, padding8);
        nextPanel.setLayoutParams(panelParams);
        nextPanel.setElevation(convertDpToPixels(2));
        nextPanel.setBackground(getResources().getDrawable(R.drawable.selector_grey));
        return nextPanel;
    }

    /**
     * Creates ImageView for panel with constant parameters
     * @param imageResourceID image to use in ImageView
     * @return ImageView for content Panel
     */
    private ImageView createPanelImage(int imageResourceID){
        int padding128 = convertDpToPixels(128);
        ImageView nextPanelImage = new ImageView(this);
        RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(
                padding128, padding128);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        imageParams.addRule(RelativeLayout.ALIGN_PARENT_START);
        nextPanelImage.setLayoutParams(imageParams);
        nextPanelImage.setImageResource(imageResourceID);
        nextPanelImage.setBackground(getResources().getDrawable(R.drawable.white_square, null));
        nextPanelImage.setId(View.generateViewId());
        return nextPanelImage;
    }

    /**
     * Creates header for panel
     * @param padding4 constant for quick reference; 4dp in px
     * @param nextPanelImageID panel image ID (int)
     * @param text String that is displayed
     * @return TextView header
     */
    private TextView createPanelHeader(int padding4, int nextPanelImageID, String text){
        TextView nextPanelTitle = new TextView(this);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.END_OF, nextPanelImageID);
        titleParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        nextPanelTitle.setLayoutParams(titleParams);
        int titleViewID = View.generateViewId();
        nextPanelTitle.setId(titleViewID);
        nextPanelTitle.setText(text);
        nextPanelTitle.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        nextPanelTitle.setPadding(padding4, padding4, padding4, padding4);
        return nextPanelTitle;
    }

    public void addFilter(View view){

    }

    /**
     * Returns appropriate px value, given desired dp value
     * @param dp
     * @return
     */
    private int convertDpToPixels(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dp*scale + 0.5f);
    }


}
