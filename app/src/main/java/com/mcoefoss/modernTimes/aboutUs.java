package com.mcoefoss.modernTimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class aboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        String about = "1.The Modern Times is a free platform designed specifically for the P.E.S's Modern College of Engineering which allows the users to upload posts about the events happening in the college.\n" +
                "2.This app brings together all the students and the faculty irrespective of the department they belong to.\n" +
                "\n" +
                "FEATURES\n" +
                "1.This app features various tabs to choose from, including the Home Page, Clubs and Departments to enable an easy view for the posts made. \n" +
                "2.Home Page features the latest posts. Clubs and departments section shows the posts sorted as per their source.\n" +
                "3.Upload Posts helps to upload posts.\n" +
                "4.Delete Post helps the user to delete the posts made by him/her.\n" +
                "\n" +
                "GET TO KNOW YOUR COLLEGE MORE\n" +
                "1.This app makes it easy for the users to know about the events in the college as well as makes complete use of the information in line with their personal interest.\n" +
                "\n" +
                "\n" +
                "DEVELOPERS\n" +
                "1.Hardika Doshi\n" +
                "2.Priya Ghayal\n" +
                "3.Atharva Borekar\n" +
                "4.Sushant Sangle\n";
        TextView textView = findViewById(R.id.about_body);
        textView.setText(about);


    }
}
