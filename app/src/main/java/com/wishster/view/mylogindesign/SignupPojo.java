package com.wishster.view.mylogindesign;


import com.google.gson.annotations.SerializedName;
import com.wishster.model.login_and_signup_module.UserDetailsPojo;

import java.util.ArrayList;

public class SignupPojo {

    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;

        @SerializedName("data")
        public UserDetailsPojo allUserData = new UserDetailsPojo();


    public String getMessage() {
        return message;
    }

    public boolean getStatus()
    {
        return status;
    }

    public UserDetailsPojo getAllUserData() {
        return allUserData;
    }
}













