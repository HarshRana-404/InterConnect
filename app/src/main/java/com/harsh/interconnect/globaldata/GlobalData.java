package com.harsh.interconnect.globaldata;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GlobalData {
    public static FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    public static FirebaseFirestore fbStore = FirebaseFirestore.getInstance();
    public static String getDetailsForUID = "";
    public static ArrayList<String> alAddedDates = new ArrayList<>();
}
