package com.ashraf.rokomariassignment.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {

    public static final String STATUS_OPEN="Open" ;
    public static final String STATUS_IN_PROGRESS="In-progress" ;
    public static final String STATUS_TEST="Test" ;
    public static final String STATUS_DONE="Done" ;

    public static SimpleDateFormat DATE_FORMAT_DD_MM_YYYY= new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
}
