package com.mcoefoss.modernTimes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Log;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseException;
import com.parse.ParseACL;
import com.parse.ParseQuery;

import com.parse.ParseUser;

import java.io.File;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class postUpload extends AppCompatActivity {

    ImageView mImageView;
    Button mChooseButton;
    Button submit;
    String uri;
    volatile boolean upload;


    private static final int IMAGE_PICK_CODE=0;
    private static final int PERMISSION_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_upload);

        mImageView=findViewById(R.id.post_image);
     //   mChooseButton=findViewById(R.id.upload_image);
        submit = findViewById(R.id.submit);
        final EditText Title = findViewById(R.id.inputTitle);
        final EditText club  = findViewById(R.id.inputClub);
        final EditText info = findViewById(R.id.inputDescription);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                        //permission denied
                        String[] permissions={Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions,PERMISSION_CODE);
                    }
                    else {
                        //permission granted
                        pickImageFromGallery();
                    }

                }

                else {
                    pickImageFromGallery();
                }
        }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                post(Title.getText().toString(),club.getText().toString(),info.getText().toString());
            }
        });

    }

    private void pickImageFromGallery() {
        //intent to pick image
        Intent pick_image_intent = new Intent(Intent.ACTION_PICK);
        pick_image_intent.setType("image/*");
        startActivityForResult(pick_image_intent,IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else{
                    Toast.makeText(this,"Permisssion Denied!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            try {
                setImage(data.getData().toString());
                upload = false;
            }catch(NullPointerException e){
                Log.e("NULL",e.toString());
            }
            mImageView.setImageURI(data.getData());
        }
    }
    void post(String title,String club,String text) {
        ParseObject newPost = new ParseObject("Posts");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Clubs");
        query.whereContains("Title",club);
        try{
           ParseObject Club  = query.getFirst();
            newPost.put("Title", title);
            newPost.put("text", text);
            newPost = AddImage(newPost);
           newPost.put("From_club",Club);
           newPost.put("Posted_by",ParseUser.getCurrentUser());
           ParseACL acl = new ParseACL();
           acl.setPublicReadAccess(true);
           acl.setWriteAccess(ParseUser.getCurrentUser(),true);
           newPost.setACL(acl);
           newPost.saveEventually();
           Toast.makeText(getApplicationContext(),"Your post will be uploaded as soon as possible if You are eligible to post",Toast.LENGTH_SHORT).show();
           actionAfterUpload();
           Intent home = new Intent(postUpload.this,Main2Activity.class);
           finish();
           startActivity(home);
        }catch(ParseException e){
            Toast.makeText(getApplicationContext(), "No Club found with that name or Internet error ", Toast.LENGTH_SHORT).show();
        }
    }

    void setImage(String URI){
        uri = URI;
    }
    ParseObject AddImage(ParseObject currObj){
        if(uri!=null){

            File file = new File(uri);
            ParseFile image = new ParseFile(file);
            try {
                image.save();
                currObj.put("Image", image);
            }catch(ParseException e){
                Log.e("Parse Exception::",e.toString());
            }
        }else{
            upload = true;
        }
        return currObj;
    }

    void actionAfterUpload(){

    }
}


