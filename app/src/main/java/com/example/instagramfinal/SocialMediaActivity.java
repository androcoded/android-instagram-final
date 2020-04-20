package com.example.instagramfinal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.parse.ParseUser;

import java.net.SocketAddress;
import java.util.List;

public class SocialMediaActivity extends AppCompatActivity {

    private Toolbar myToolBar;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private Bitmap receivedImage;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        imageView = findViewById(R.id.imgUpdate);
        myToolBar = findViewById(R.id.myToolBar);
        myToolBar.setTitle("Instagram");
        setSupportActionBar(myToolBar);
        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.mnISharePicture:
                getImageResource();
                break;

            case R.id.mntLogOut:
                ParseUser.getCurrentUser().logOut();
                Intent intent = new Intent(SocialMediaActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;

        }
        return true;
    }

    private void getStoragePermission() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(SocialMediaActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(SocialMediaActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(SocialMediaActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();


    }

    private void getImageResource(){

        final CharSequence[] item = {"Choose from gallery","Choose from camera","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SocialMediaActivity.this);
        builder.setTitle("Choose image from..")
                .setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (item[which].equals("Choose from gallery")){
                            getStoragePermission();
                            Intent i = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(i, 1);

                        }else if(item[which].equals("Cancel")){
                            dialog.dismiss();
                        }

                    }
                });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if (resultCode == RESULT_OK && data != null){
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        receivedImage = BitmapFactory.decodeFile(picturePath);
                        imageView.setImageBitmap(receivedImage);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
        }

    }
}