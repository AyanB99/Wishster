package com.wishster.model.login_and_signup_module;

import com.google.gson.annotations.SerializedName;



import java.util.ArrayList;
import java.util.List;

public class SignUpPojo {
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public boolean status;


    public boolean isStatus() {
        return status;
    }

 

    public String getMessage() {
        return message;
    }
}


