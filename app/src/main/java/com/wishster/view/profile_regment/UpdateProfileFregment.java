package com.wishster.view.profile_regment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wishster.view.R;

public class UpdateProfileFregment extends Fragment {
    View v;
    EditText etUname, etFname, etLname, etLoc, etSelf;
    TextView btnSave;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.profile_update,container,false);
       // View Rendering
        etFname = v.findViewById(R.id.et_fname);
        etLname = v.findViewById(R.id.et_lname);
        etUname = v.findViewById(R.id.et_uname);
        etLoc = v.findViewById(R.id.et_loc);
        etSelf = v.findViewById(R.id.et_self);
        btnSave = v.findViewById(R.id.tv_save);
        pref = getActivity().getSharedPreferences(getString(R.string.SESSION), 0);
        editor = pref.edit();

        etFname.setText(pref.getString("first_name", ""));
        etLname.setText(pref.getString("last_name", ""));
        etUname.setText(pref.getString("username", ""));
        etLoc.setText(pref.getString("city", ""));
        etSelf.setText(pref.getString("about_me", ""));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFname.getText().toString();
                String lName = etLname.getText().toString();
                String uName = etUname.getText().toString();
                String location = etLoc.getText().toString();
                String sd = etSelf.getText().toString();
                if(!fName.equals("")){
                    Toast.makeText(getActivity(), "Please Enter Your First Name", Toast.LENGTH_SHORT).show();

                } else if (!lName.equals("")){
                    Toast.makeText(getActivity(), "Please Enter Your Last Name", Toast.LENGTH_SHORT).show();
                }else if (!uName.equals("")){
                    Toast.makeText(getActivity(), "Please Enter Your User Name", Toast.LENGTH_SHORT).show();

                }else{
                   // updateProfileData(fName, lName, uName, location, sd);
                }
            }
        });


        return  v;
    }

    /*private void updateProfileData(String fName, String lName, String uName, String location, String sd) {

    }*/


}
