package com.wishster.view.profile_regment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wishster.view.R;

import java.util.ArrayList;
import java.util.List;

public class AccountFregment extends Fragment
{
    View v;
    Spinner language_spinner;
    List<String> language;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        v= inflater.inflate(R.layout.user_accopunt,container,false);
        init();
        action();
        return v;
    }

    private void init()
    {
        language_spinner=(Spinner)v.findViewById(R.id.language_spinner);
        language=new ArrayList<String>();

    }
    private void action()
    {
        language.clear();
        language.add("English(US)");
            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_second, language);
            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            language_spinner.setAdapter(dataAdapter2);
    }
}
