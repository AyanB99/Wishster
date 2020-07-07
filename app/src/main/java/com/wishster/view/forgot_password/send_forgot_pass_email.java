package com.wishster.view.forgot_password;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
import com.wishster.model.forgot_password.forgotpassModel;
import com.wishster.view.R;
import com.wishster.view.mylogindesign.ValidateString;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class send_forgot_pass_email extends Fragment {
EditText forgot_email;
static TextView send_email_btn,errortext;
    final Fragment set_pin = new set_pin();
    MainActivityViewModel mainActivityViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_email_freg, container, false);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        init(view);
        action();

        return view;
    }

    private void action()
    {
        send_email_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Validate()){
                    forgotpassModel.email=forgot_email.getText().toString().trim();
                    try {
                        sendEmail();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        forgot_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        errortext.setText("");
                    }
                },100);

            }
        });
/*
        forgot_email.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.) {
                    */
/* do something *//*

                }

                return  true;
            };
        });
*/
        forgot_email.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                send_email_btn.performLongClick();
                if (actionId == EditorInfo.IME_ACTION_SEND)
                {

                    return true;
                }
                return false;
            }
        });
    }
boolean Validate(){

    if (!(forgot_email.getText().toString().trim().length()==0) ){
        if (!((forgot_email.getText().toString().trim().matches(ValidateString.Expn)))) {
            forgot_email.setError("Invalid");
            setTextErrorMessage("Invalid Email");
            return false;
        }
        else {
            setTextErrorMessage("");
        }
    }
    else {
        forgot_email.setError("Email required");
        setTextErrorMessage("Email required");
        return  false;
    }
  return true;
}
    private void init(View view) {
        forgot_email=(EditText)view.findViewById(R.id.forgot_email1);
        send_email_btn=(TextView)view.findViewById(R.id.send_email_btn1);
        errortext=(TextView)view.findViewById(R.id.errortext);
    }
    void setTextErrorMessage(final  String message){
        errortext.postDelayed(new Runnable() {
            @Override
            public void run() {
                errortext.setText(message);
            }
        },100);

    }
    private void sendEmail() throws JSONException
    {

        try {
            mainActivityViewModel.sendEmail().observe(getActivity(), new Observer<Forgot_pojo>()
            {
                @Override
                public void onChanged( Forgot_pojo s)
                {
                    Toast.makeText(getActivity(), ""+s.getMessage(), Toast.LENGTH_LONG).show();
                    errortext.setText(s.getMessage());
                     if (s.isStatus())
                     {
                         errortext.setTextColor(getResources().getColor(R.color.green));
                         errortext.setText(s.getMessage());
                         //fregment call
                         FragmentManager fm = getActivity().getSupportFragmentManager();
                         FragmentTransaction fragmentTransaction = fm.beginTransaction();
                         fragmentTransaction.replace(R.id.cont1, set_pin);
                         fragmentTransaction.commit();
                     }
                     else
                         {
                            errortext.setTextColor(getResources().getColor(R.color.red));
                         }

                }
            });
        }
        catch (Exception e)
        {
            Log.d("Stttttt", "ERROR 1" + e.getMessage());
        }
    }
}
