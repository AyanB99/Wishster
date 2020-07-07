package com.wishster.view.profile_regment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wishster.view.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);
        pref = getActivity().getSharedPreferences(getString(R.string.SESSION), 0);
        editor = pref.edit();
        String uName="";
        if (!(pref.getString("first_name","").equals(""))){
            uName=""+pref.getString("first_name","");
        }
        if (!(pref.getString("last_name","").equals(""))){
            uName+=" "+pref.getString("last_name","");
        }
        if (!(pref.getString("country","").equals(""))){
            ((TextView)view.findViewById(R.id.user_user_country)).setText(""+pref.getString("country",""));

        }
        ((TextView)view.findViewById(R.id.user_name)).setText(""+uName);
        if (!(pref.getString("profile_img","").equals("")))
        {


            CircleImageView profa=(CircleImageView)view.findViewById(R.id.profile_image_user);
            Picasso.with(getActivity()).load(pref.getString("profile_img", ""))
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.profile_image_default)
                    .placeholder(R.drawable.profile_image_default).into(profa);

        }
        return view;
    }
}
