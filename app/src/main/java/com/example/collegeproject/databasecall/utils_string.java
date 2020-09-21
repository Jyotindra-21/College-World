package com.example.collegeproject.databasecall;

public class utils_string {

    public static String BASE_URL = "https://icegamer.000webhostapp.com/theme/light/";
    //public static String BASE_URL = "http://192.168.43.36/theme/light/";
    public static String IMAGE_URL_BASE = "upload/";
    public static String IMAGE_URL_BASE1 = "UserProfile/";


    public interface IMAGE_URL {
        String COLLEGE_GALLERY = IMAGE_URL_BASE;
        String USER_PROFILE = IMAGE_URL_BASE1 ;

        /*String VENDOR_PROFILE = IMAGE_URL_BASE + "VendorProfile/";
        String VENDOR_FOODITEMS = IMAGE_URL_BASE + "VendorFoodItems/";
        String VENDOR_PACKAGES = IMAGE_URL_BASE + "VendorPackages/";*/
    }

    /*public interface DB_TABLES {
        String FOODITEMS = "demo";
    }*/

}
