package com.wishster.model.forgot_password;

import com.google.gson.annotations.SerializedName;

public class Forgot_pojo {
    @SerializedName("message")
    public String message;
    @SerializedName("status")
    public boolean status;

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
