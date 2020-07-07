package com.wishster.view.mylogindesign;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class JsonRetrive {

    public String getJsonfile(Context context)
    {
        String json = null;
        try {
            // Opening data.json file
            InputStream inputStream = context.getAssets().open("country_state_city.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return json;
        }

        return json;
    }
}
