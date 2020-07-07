package com.wishster.model.forgot_password;

import com.google.gson.annotations.SerializedName;

public class change_passF_POJO {
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
