package com.example.instagramfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class UserPost extends AppCompatActivity {

    private Toolbar mToolbar;
    private String name;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);
        mToolbar = findViewById(R.id.toolbarUserPost);
        mLinearLayout = findViewById(R.id.linearLayout);
        name = getIntent().getStringExtra("name");
        setTitle(name+"'s post");
        setSupportActionBar(mToolbar);
        gettingAllPost();
    }

    private void gettingAllPost(){
//        ParseQuery<ParseObject> userPost = ParseQuery.getQuery("Photo");
        ParseQuery<ParseObject> userPost = new ParseQuery<ParseObject>("Photo");
        userPost.whereEqualTo("username",name);
        userPost.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size()>0 && e == null){
                   for (ParseObject user: objects){
                    try {
                        String imageDes =user.getString("imageDescription");
                        final TextView textDescription = new TextView(UserPost.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(5,5,5,15);
                        textDescription.setLayoutParams(layoutParams);
                        textDescription.setGravity(Gravity.CENTER);
                        textDescription.setBackgroundColor(Color.BLACK);
                        textDescription.setTextColor(Color.WHITE);
                        textDescription.setTextSize(30f);
                        textDescription.setText(imageDes+"");
                        ParseFile parseFile = (ParseFile) user.get("picture");
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if (e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams.setMargins(5,5,5,5);
                                    ImageView imageView = new ImageView(UserPost.this);
                                    imageView.setLayoutParams(layoutParams);
                                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    imageView.setImageBitmap(bitmap);
                                    mLinearLayout.addView(imageView);
                                    mLinearLayout.addView(textDescription);
                                }
                            }
                        });
                   }catch (Exception i){
                    i.printStackTrace();
                    }
                   }
                }else{
                    Toast.makeText(UserPost.this, "Does not work", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}