package com.example.android.identify;

import java.util.ArrayList;

/**
 * Stores filters which are displayed in other classes
 * Created by bdphi on 9/4/2017.
 */

public class FilterClass {
    public ArrayList<String> filters = new ArrayList<>();

    public static final FilterClass filterClass = new FilterClass();
    public static FilterClass getFilterClass() {return filterClass;}
}
