package com.wishster.view.mylogindesign;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Testingdata {


    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private int status;

    @SerializedName("api_key")
    private String api_key;
    @SerializedName("booking_id")
    private String booking_id;
    @SerializedName("total_due_amount")
    private String total_due_amount;

    @SerializedName("total_paid_amount")
    private String total_paid_amount;
    @SerializedName("remain_amount")
    private String remain_amount;
    @SerializedName("data")
    public List<Paymentdetails> data = null;

    public List<Paymentdetails> getData() {
        return data;
    }

    public int getStatus()
    {
        return status;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public String getTotal_due_amount() {
        return total_due_amount;
    }

    public String getTotal_paid_amount() {
        return total_paid_amount;
    }

    public String getRemain_amount() {
        return remain_amount;
    }

    public String getMessage() {
        return message;
    }

}
