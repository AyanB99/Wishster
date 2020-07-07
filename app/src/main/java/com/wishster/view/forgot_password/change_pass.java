package com.wishster.view.forgot_password;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.wishster.model.forgot_password.Forgot_pojo;
import com.wishster.model.forgot_password.change_passF_POJO;
import com.wishster.model.forgot_password.forgotpassModel;
import com.wishster.view.R;
import com.wishster.view.mylogindesign.ValidateString;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class change_pass extends Fragment {
EditText forgot_email;
static TextView send_setpass_btn1,errortext;
static String[] err_msg={"","","","","",""};
static  EditText newpass,cnewpass;
MainActivityViewModel mainActivityViewModel;
static boolean aBoolean=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_change, container, false);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        init(view);
        action();

        return view;
    }

    private void init(View view) {
        newpass=(EditText)view.findViewById(R.id.newpass);
        cnewpass=(EditText)view.findViewById(R.id.cnewpass);
        send_setpass_btn1=(TextView) view.findViewById(R.id.send_setpass_btn1);
        errortext=(TextView) view.findViewById(R.id.errortext);
    }

    private void action()
    {
        cnewpass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                send_setpass_btn1.performClick();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    return true;
                }
                return false;
            }
        });
        send_setpass_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VAlidation()){
                    forgotpassModel.pass=newpass.getText().toString().trim();
                    try {
                        changePass();
                        errortext.setText("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    String err_msg_s="";
                    for (int err_count=0;err_count<err_msg.length;err_count++){

                        if (!(err_msg[err_count].trim().equals(""))){
                            err_msg_s+="\n"+err_msg[err_count].trim();
                        }
                    }
                    // err_msg
                    errortext.setText(err_msg_s);
                }
            }
        });

    }

    private boolean VAlidation() {
        Boolean BOOL_NP=false,BOOL_CNP=false,BOOL_NCNP=false,BOOL_FCNP=false,BOOL_LCNP=false;
        if (newpass.getText().toString().trim().length()==0){
            err_msg[0]="Password field must not be blank";
            BOOL_NP=false;
        }
        else {
            err_msg[0]="";
            BOOL_NP=true;
        }
        if (cnewpass.getText().toString().trim().length()==0){
            err_msg[1]="Confirm Password field must not be blank";
            BOOL_CNP=false;
        }
        else {
            err_msg[1]="";
            BOOL_CNP=true;
        }
        if (!(newpass.getText().toString().trim().equals(cnewpass.getText().toString().trim()))){
            err_msg[2]="Password and confirm password not match";
            BOOL_NCNP=false;
        }
        else {
            err_msg[2]="";
            BOOL_NCNP=true;
        }
        if (!(newpass.getText().toString().trim().matches(ValidateString.pattern_pass))){
            err_msg[3]="Password must contain at least one letter and one number and a special character from !@#$%^&*()_";
            BOOL_FCNP=false;
        }
        else
            {
            err_msg[3]="";
            BOOL_FCNP=true;
        }
        if(!((newpass.getText().toString().trim().length()>7) && (newpass.getText().toString().trim().length()<21))){
            err_msg[4]="The password Must be 8 to 20 characters in length";
            BOOL_LCNP=false;
        }
        else
            {
            err_msg[4]="";
            BOOL_LCNP=true;
        }
        if (!(BOOL_NP==true && BOOL_CNP==true && BOOL_NCNP==true && BOOL_FCNP==true && BOOL_LCNP==true)){
            return false;
        }


        return true;
    }
    private void changePass() throws JSONException
    {

        try {
            mainActivityViewModel.changePasswordF().observe(getActivity(), new Observer<change_passF_POJO>()
            {
                @Override
                public void onChanged(change_passF_POJO s)
                {
                    Toast.makeText(getActivity(), ""+s.getMessage(), Toast.LENGTH_LONG).show();
                    errortext.setText(s.getMessage());
                    if (s.isStatus()) {
                        errortext.setTextColor(getResources().getColor(R.color.green));
                        errortext.setText(s.getMessage());
                        getActivity().finish();
                    }
                    else {
                        errortext.setTextColor(getResources().getColor(R.color.red));
                    }
                }
            });
        }catch (Exception e)
        {
            Log.d("Stttttt", "ERROR 3" + e.getMessage());
        }
    }

}
