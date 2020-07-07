package com.wishster.model.securityModel;

import com.google.gson.annotations.SerializedName;
import com.wishster.model.login_and_signup_module.UserDetailsPojo;

public class securityModel {
    @SerializedName("status")
    public boolean status;
    @SerializedName("message")
    public String message;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}



