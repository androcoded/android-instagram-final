package com.example.instagramfinal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;


public class UserFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView mListView;
    private ListAdapter mListAdapter;
    private ArrayList<String> mStrings;

    public UserFragment() {
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mListView = view.findViewById(R.id.listView);
        getAllUserName();
        mListAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,mStrings);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        return view;
    }

    private void getAllUserName(){
        mStrings =new ArrayList<>();
        ParseQuery<ParseUser> allUserQuery = ParseUser.getQuery();
        allUserQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        allUserQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (objects.size()>0 && e == null){
                    for (ParseUser user : objects){
                        mStrings.add(user.getUsername());
                    }
                }else{
                    Toast.makeText(getContext(),"", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),UserPost.class);
        intent.putExtra("name",mStrings.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        String name = mStrings.get(position);
        ParseQuery<ParseUser> query =ParseUser.getQuery();
        query.whereEqualTo("username",name);
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (object !=null && e == null){
                    final PrettyDialog prettyDialog = new PrettyDialog(getContext());
                    prettyDialog.setTitle(object.getUsername()+"'s profile")
                            .setMessage(object.get("favouriteSport")+"\n"+object.get("profession")+
                                    "\n"+object.get("hobbies"))
                            .addButton("OK",
                                    R.color.pdlg_color_white,
                                    R.color.pdlg_color_green,
                                    new PrettyDialogCallback() {
                                        @Override
                                        public void onClick() {
                                            prettyDialog.cancel();
                                        }
                                    })
                            .show();
                }

            }
        });
        return true;
    }
}
