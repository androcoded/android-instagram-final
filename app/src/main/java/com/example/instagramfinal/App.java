package com.example.instagramfinal;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("NUIT4JtUNGSH7EFQyoQwSWcuioIUDSfWEsakK9II")
                .clientKey("tNP33FxxDSzbC2wtjK30Hw3jUnZZYlV2orzeiNYN")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
