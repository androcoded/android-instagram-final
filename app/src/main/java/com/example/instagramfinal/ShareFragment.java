package com.example.instagramfinal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.util.List;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment implements View.OnClickListener{

    private ImageView imageUpdate;
    private EditText edtImageDescription;
    private Button btnImageDesUpdate;
    private Bitmap receivedImage;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        imageUpdate = view.findViewById(R.id.imgUpdate);
        edtImageDescription = view.findViewById(R.id.edtImageDescription);
        btnImageDesUpdate = view.findViewById(R.id.btnImageDesUpdate);
        imageUpdate.setOnClickListener(this);
        btnImageDesUpdate.setOnClickListener(this);
        return view;
    }


    private void getStoragePermission(){

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };

        TedPermission.with(getContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnImageDesUpdate:
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                receivedImage.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                ParseFile imageFile = new ParseFile("image.png",byteArray);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture",imageFile);
                parseObject.put("imageDescription",edtImageDescription.getText().toString());
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Toast.makeText(getContext(), "Image and Image des successfully updated", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Not updated try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case  R.id.imgUpdate:
                getImageResource();
                break;
        }

    }

    private void getImageResource(){

        final CharSequence[] item = {"Choose from gallery","Choose from camera","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if (resultCode == RESULT_OK && data != null){
                    try {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContext().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        receivedImage = BitmapFactory.decodeFile(picturePath);
                        imageUpdate.setImageBitmap(receivedImage);
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
        }

        }
}

