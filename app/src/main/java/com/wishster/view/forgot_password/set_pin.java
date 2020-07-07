package com.wishster.view.forgot_password;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.wishster.model.forgot_password.Forgot_pojo;
import com.wishster.model.forgot_password.forgotpassModel;
import com.wishster.model.forgot_password.pin_POJO;
import com.wishster.view.R;
import com.wishster.view.mylogindesign.ValidateString;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class set_pin extends Fragment {
    TextView re_send_email_btn,error_txt;
    PinEntryEditText pinEntry;
    TextView verify_btn;
    final Fragment change_pass = new change_pass();
    MainActivityViewModel mainActivityViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.set_pin_layout, container, false);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        init(view);
        action();
        return view;
    }

    private void action() {
        re_send_email_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try
                {
                    sendEmail();
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        });

        if (pinEntry != null) {
            pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener()
            {
                @Override
                public void onPinEntered(CharSequence str)
                {
                    forgotpassModel.pin=pinEntry.getText().toString().trim();
                    try {
                        setPin();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            verify_btn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    forgotpassModel.pin=pinEntry.getText().toString().trim();
                    try {
                        setPin();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    private void init(View view) {
        re_send_email_btn=(TextView)view.findViewById(R.id.re_send_email_btn);
        pinEntry = (PinEntryEditText) view.findViewById(R.id.txt_pin_entry);
        verify_btn = (TextView) view.findViewById(R.id.verify_btn);
        error_txt = (TextView) view.findViewById(R.id.error_txt);
    }
    private void pinCheck()
    {
        if ("1234".trim().equals(pinEntry.getText().toString().trim()))
        {

            try {
                setPin();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {
             Toast.makeText(getContext(), "OTP Not Match ", Toast.LENGTH_SHORT).show();

        }
    }

    private void setPin() throws JSONException
    {
        Log.d("Stttttt", "setpin aa");

        try {
            mainActivityViewModel.checkPin().observe(getActivity(), new Observer<pin_POJO>()
            {
                @Override
                public void onChanged(pin_POJO s)
                {
                    /*FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.cont1, change_pass);
                    fragmentTransaction.commit();*/

                    Toast.makeText(getActivity(), ""+s.getMessage(), Toast.LENGTH_LONG).show();
                    error_txt.setText(s.getMessage());
                    if (s.isStatus()) {
                        error_txt.setTextColor(getResources().getColor(R.color.green));
                        error_txt.setText(s.getMessage());
                        //fregment call
                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.cont1, change_pass);
                        fragmentTransaction.commit();
                    }
                    else {
                            error_txt.setTextColor(getResources().getColor(R.color.red));
                         }
                }
            });
        }catch (Exception e)
        {
            Log.d("Stttttt", "ERROR 2" + e.getMessage());
        }
    }
    private void sendEmail() throws JSONException
    {

        try {
            mainActivityViewModel.sendEmail().observe(getActivity(), new Observer<Forgot_pojo>()
            {
                @Override
                public void onChanged(Forgot_pojo s)
                {
                    Toast.makeText(getActivity(), ""+s.getMessage(), Toast.LENGTH_LONG).show();


                }
            });
        }catch (Exception e)
        {
            Log.d("Stttttt", "ERROR 1" + e.getMessage());
        }
    }
}
