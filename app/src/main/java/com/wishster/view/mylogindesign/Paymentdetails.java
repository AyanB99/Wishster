package com.wishster.view.mylogindesign;

import com.google.gson.annotations.SerializedName;

public class Paymentdetails {

    @SerializedName("id")
    public Integer id;
    @SerializedName("name")
    public String name;
    @SerializedName("year")
    public Integer year;
    @SerializedName("pantone_value")
    public String pantoneValue;



    @SerializedName("doc")
    String doc;
    @SerializedName("credit")
    String credit;
    @SerializedName("status")
    String status;
    @SerializedName("booking_id")
    String booking_id_n;
    @SerializedName("tenant_id")
    String tenant_id_n;
    @SerializedName("tenant_name")
    String tenant_name;
    @SerializedName("t_id")
    String t_id;
    @SerializedName("debit")
    String debit;
    @SerializedName("mode")
    String mode;
    @SerializedName("purpose")
    String purpose;
    @SerializedName("to_person")
    String to_person;
    @SerializedName("screen_short")
    String screen_short;
    @SerializedName("transaction_id")
    String transaction_id;


    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getPantoneValue() {
        return pantoneValue;
    }

    public String getDoc() {
        return doc;
    }

    public String getCredit() {
        return credit;
    }

    public String getStatus() {
        return status;
    }

    public String getBooking_id_n() {
        return booking_id_n;
    }

    public String getTenant_id_n() {
        return tenant_id_n;
    }

    public String getTenant_name() {
        return tenant_name;
    }

    public String getT_id() {
        return t_id;
    }

    public String getDebit() {
        return debit;
    }

    public String getMode() {
        return mode;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getTo_person() {
        return to_person;
    }

    public String getScreen_short() {
        return screen_short;
    }

    public String getTransaction_id() {
        return transaction_id;
    }
}
