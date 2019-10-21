package com.mcoefoss.modernTimes;

import android.graphics.Bitmap;


public class Post {
    private String heading;
    private String date;
    private String text;
    private String club;
    private Bitmap img;
    public String Obj_id;
    Post(String Heading,String Text,String Date,String Club,Bitmap Img,String Id){
        heading = Heading;
        text = Text;
        date = Date;
        club = Club;
        if(img == null){
            img = Img;
        }

        Obj_id = Id;
    }
    public String getHeading() {
        return heading;
    }
    public String getClub(){
        return club;
    }
    public String getDate() {
        return date;
    }
    public String getText(){
        return text;
    }
    public Bitmap getImage(){
        return img;
    }
}
