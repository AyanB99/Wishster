package com.wishster.view.mylogindesign;


import com.google.gson.annotations.SerializedName;

public class UploadObject {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
/*
     @SerializedName("data")
    public List<bookingpaymentDetails> data = new ArrayList();
*/

    public String getMessage() {
        return message;
    }

    public int getStatus()
    {
        return status;
    }
}


class bookingpaymentDetails {

    @SerializedName("id")
    public Integer id;
    @SerializedName("first_name")
    public String first_name;
    @SerializedName("last_name")
    public String last_name;
    @SerializedName("avatar")
    public String avatar;

}