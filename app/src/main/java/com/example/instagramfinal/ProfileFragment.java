package com.example.instagramfinal;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileFragment extends Fragment implements View.OnClickListener{

    private EditText edtName,edtProfession,edtHobbies,edtFavSport;
    private Button btnUpdate;
    private ProgressBar mProgressBar;
    private ConstraintLayout mConstraintLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        edtName = view.findViewById(R.id.edtName);
        edtProfession = view.findViewById(R.id.edtProfession);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        edtFavSport = view.findViewById(R.id.edtFavSport);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        mProgressBar = view.findViewById(R.id.progressBar);
        mConstraintLayout = view.findViewById(R.id.constraintLayout);
        updateProfileFields();
        btnUpdate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        mConstraintLayout.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        ParseUser user = ParseUser.getCurrentUser();
        user.put("name",edtName.getText().toString());
        user.put("profession",edtProfession.getText().toString());
        user.put("hobbies",edtHobbies.getText().toString());
        user.put("favouriteSport",edtFavSport.getText().toString());
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    FancyToast.makeText(getContext(),"Successfully saved data in the server"
                    , Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                }else {
                    FancyToast.makeText(getContext(),e.getMessage()
                            , Toast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
               }
                mProgressBar.setVisibility(View.INVISIBLE);
                mConstraintLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    //Updating the profile information in different field

    private void updateProfileFields(){
        ParseUser user = ParseUser.getCurrentUser();
        if (user.get("name")==null) {
            edtName.getText().clear();
        }else {
            edtName.setText(user.get("name")+"");
        }
        if (user.get("profession")== null) {
            edtProfession.getText().clear();
        }else {
            edtProfession.setText(user.get("profession")+"");
        }
        if (user.get("hobbies")== null) {
                edtHobbies.getText().clear();
        }else {
            edtHobbies.setText(user.get("hobbies")+"");
        }

        if (user.get("favouriteSport") == null) {
            edtFavSport.getText().clear();
        }else {
            edtFavSport.setText(user.get("favouriteSport")+"");
        }

    }

}
