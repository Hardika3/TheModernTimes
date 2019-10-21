package com.mcoefoss.modernTimes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.ParseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParseHelp {
    static ParseObject currentClub;
    static ArrayList<Post> getPosts(ParseObject club) {
        ArrayList<Post> posts = new ArrayList<>();
        ParseQuery postQuery = ParseQuery.getQuery("Posts");
        postQuery.whereEqualTo("From_club", club);
        try {
            List<ParseObject> getPosts = postQuery.find();
            for(ParseObject post : getPosts){
                Bitmap image = toImg(post,"Image");
                Date createdAt = post.getUpdatedAt();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = dateFormat.format(createdAt);
                posts.add(new Post(post.getString("Title"),post.getString("text"),date,club.getString("Title"),image,post.getObjectId()));
            }
        }catch(ParseException e){
            Log.e("ParseError",e.toString());
        }
        return posts;
    }
    static ArrayList<Post> getPosts(){
        ArrayList<Post> posts = new ArrayList<>();
        ParseQuery postQuery = ParseQuery.getQuery("Posts");
        try {
            List<ParseObject> getPosts = postQuery.find();
            for(ParseObject post : getPosts){
                Bitmap image = toImg(post,"Image");
                Date createdAt = post.getUpdatedAt();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = dateFormat.format(createdAt);
                posts.add(new Post(post.getString("Title"),post.getString("text"),date,"",image,post.getObjectId()));
            }
        }catch(ParseException e){
            Log.e("ParseError",e.toString());
        }
        return posts;
    }
    static ArrayList<Post> getPostsBy(ParseUser currentUser){
        ArrayList<Post> posts = new ArrayList<>();
        ParseQuery postQuery = ParseQuery.getQuery("Posts");
        postQuery.whereEqualTo("Posted_by",currentUser);
        try {
            List<ParseObject> getPosts = postQuery.find();
            for(ParseObject post : getPosts){
                Bitmap image = toImg(post,"Image");
                Date createdAt = post.getUpdatedAt();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = dateFormat.format(createdAt);
                posts.add(new Post(post.getString("Title"),post.getString("text"),date,"",image,post.getObjectId()));
            }
        }catch(ParseException e){
            Log.e("ParseError",e.toString());
        }
        return posts;
    }
    static Bitmap toImg(ParseObject img,String ColumnName){
        ParseFile file = (ParseFile) img.get(ColumnName);

        Bitmap Image;
        try{
            byte[] data = file.getData();
            BitmapFactory.Options opts;
            Image = BitmapFactory.decodeByteArray(data, 0, data.length);
            return Image;
        }catch(ParseException e){
            Log.e("Parse Error:",e.toString());
        }catch(NullPointerException e){
            Log.e("Null pointer:",e.toString());
        }
        return null;
    }
    public static byte[] readBytesFromFile(String filePath) {

        FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File(filePath);
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        return bytesArray;

    }
    static void kill_if_not_logged_in(){
        if(ParseUser.getCurrentUser() == null){

        }
    }
}
