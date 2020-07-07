package com.wishster.model.login_and_signup_module;

import com.google.gson.annotations.SerializedName;

public class userData {

   @SerializedName("first_name")
   public Integer first_name;

   public Integer getFirst_name() {
       return first_name;
   }
}
