package com.wishster.view.security_question;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.wishster.model.dialog_model.TransparentProgressDialog;
import com.wishster.model.internet_chacking.CheckInternet;
import com.wishster.model.securityModel.securityModel;
import com.wishster.view.R;
import com.wishster.view.mylogindesign.ActivityLogin;
import com.wishster.view.nointernet.ActivityNoActivity;
import com.wishster.viewmodel.MainActivityViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActivitySecurityQuestion extends AppCompatActivity {
    static  ActivitySecurityQuestion activity;
    ActivityLogin mActivity;
    MainActivityViewModel mainActivityViewModel;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    static int i=0;
    Button next_question,previous_question;
    ViewStub stub;
    TextView questions,login_btn;

    RadioButton q1,q2,q3,q4;
    LinearLayout radioContainer,radioContainer_bar,barLinear,radioContainer_input;
    SeekBar simpleSeekBar;
    JSONObject store_security_question;
    EditText user_input;
    static String regid="";
    private TransparentProgressDialog pd;
    static  int width;
    static  int height;
    static TextView error_msg;
    MyListAdapter_stape_adapter adapter;
    //Recycle view

    ArrayList<MyListData_stape_list> user_list;

    LinearLayoutManager manager;
    //end Recycle view

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_question);
        activity=this;
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        store_security_question=new JSONObject();
        pref = getApplicationContext().getSharedPreferences(getString(R.string.SESSION), MODE_PRIVATE);
        editor = pref.edit();

        pref = getApplicationContext().getSharedPreferences(getString(R.string.SESSION), MODE_PRIVATE);
        editor = pref.edit();

        if(!(new CheckInternet(this).getInternetStatus()))
        {

            startActivity(new Intent(this, ActivityNoActivity.class));
            finish();
        }
        pd = new TransparentProgressDialog(this, R.drawable.dialog_image);
        try {
            if (!(pref.getBoolean("isReg", false) == true))
            {
                Toast.makeText(activity, "Register First", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (!(pref.getBoolean("hasQuestion", false) == true))
            {
                Toast.makeText(activity, "No Question", Toast.LENGTH_SHORT).show();
                finish();
            }
            try
            {
                JSONArray jsonObject1 = new JSONArray(pref.getString("questionJsondata", ""));
                regid=pref.getString("regid","");
                store_security_question.put("user_id",regid);
                com.wishster.model.SecurityQuestionModel.questionJsondata = jsonObject1;
                com.wishster.model.SecurityQuestionModel.totalQuestion = pref.getInt("totalQuestion", 0);
            }catch (Exception e){

            }

        }catch (Exception e)
        {}
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
        {
            deviceSize();
        }
        radioButton();
        init();
        try
        {
            getSecurityQuestion();
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        action();
        setSteps(0);
}

    private void action() {
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putBoolean("isReg",false);
                editor.apply();
                startActivity(new Intent(activity, ActivityLogin.class).putExtra("userid","").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },1000);
            }
        });
}

    private void radioButton() {
        q1=(RadioButton)findViewById(R.id.q1);
        q2=(RadioButton)findViewById(R.id.q2);
        q3=(RadioButton)findViewById(R.id.q3);
        q4=(RadioButton)findViewById(R.id.q4);
        q1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1.setChecked(true);
                q2.setChecked(false);
                q3.setChecked(false);
                q4.setChecked(false);

            }
        });
        q2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1.setChecked(false);
                q2.setChecked(true);
                q3.setChecked(false);
                q4.setChecked(false);
            }
        });
        q3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1.setChecked(false);
                q2.setChecked(false);
                q3.setChecked(true);
                q4.setChecked(false);
            }
        });
        q4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                q1.setChecked(false);
                q2.setChecked(false);
                q3.setChecked(false);
                q4.setChecked(true);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    private void deviceSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        width = size.x;
        height = (size.y-100);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.q_container);
        rl.getLayoutParams().height = height;
        //rl.getLayoutParams().width = px;
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });
        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        simpleSeekBar.getLayoutParams().width=(width-100);
        LinearLayout barLinear=(LinearLayout)findViewById(R.id.barLinear);
        ViewGroup.LayoutParams params = barLinear.getLayoutParams();

        //params.height = 100;
        params.width = (width-100);
        barLinear.setLayoutParams(params);

        simpleSeekBar.getProgressDrawable().setColorFilter(
                R.color.text_color2, PorterDuff.Mode.MULTIPLY);


        //barLinear.getLayoutParams().width=width;
    }



    private void init()
    {
        login_btn=(TextView)findViewById(R.id.login_btn);
        error_msg=(TextView)findViewById(R.id.error_msg);

        next_question=(Button)findViewById(R.id.next_question);
        previous_question=(Button)findViewById(R.id.previous_question);
        questions=(TextView)findViewById(R.id.questions);
        radioContainer=(LinearLayout)findViewById(R.id.radioContainer);
        radioContainer_bar=(LinearLayout)findViewById(R.id.radioContainer_bar);
        radioContainer_input=(LinearLayout)findViewById(R.id.radioContainer_input);
        barLinear=(LinearLayout)findViewById(R.id.barLinear);

        simpleSeekBar=(SeekBar)findViewById(R.id.simpleSeekBar);
        user_input=(EditText)findViewById(R.id.user_input);
    }

    private void getSecurityQuestion() throws JSONException
    {

        if (com.wishster.model.SecurityQuestionModel.questionJsondata!=null)
        {
            if (com.wishster.model.SecurityQuestionModel.totalQuestion>0)
            {


                if (com.wishster.model.SecurityQuestionModel.questionJsondata.length()>0)
                {
                    i=0;
                    JSONObject jj = com.wishster.model.SecurityQuestionModel.questionJsondata.getJSONObject(0);
                    previous_question.setVisibility(View.GONE);
                    setQuestion(jj.getString("question"),(i+1),com.wishster.model.SecurityQuestionModel.questionJsondata.length());
                    store_security_question.put("security_qtn"+(i+1),jj.getString("question"));
                    callJobject(jj);
                    next_question.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            Log.d(com.wishster.model.SecurityQuestionModel.questionJsondata.length()+"\nfinaldataKKKT","B "+i);
                            if (i>=(com.wishster.model.SecurityQuestionModel.questionJsondata.length()-2))
                                next_question.setText("Submit");
                            else
                                next_question.setText("NEXT");
                            if (i!=com.wishster.model.SecurityQuestionModel.questionJsondata.length()) {
                                i++;
                                Log.d(com.wishster.model.SecurityQuestionModel.questionJsondata.length()+"\nfinaldataKKK","C "+i);
                                if (i==com.wishster.model.SecurityQuestionModel.questionJsondata.length())
                                {
                                    try
                                    {
                                        setStore_security_question(store_security_question);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    pd.show();

                                }
                            }
                            else {
                                i--;

                            }

                            if (i<com.wishster.model.SecurityQuestionModel.questionJsondata.length())
                            {
                                try
                                {

                                    if (i!=0){
                                        previous_question.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        previous_question.setVisibility(View.GONE);
                                    }

                                    JSONObject jj = com.wishster.model.SecurityQuestionModel.questionJsondata.getJSONObject(i);
                                    store_security_question.put("security_qtn"+(i+1),jj.getString("question"));
                                    setSteps(i);
                                    //if (jj.getString("type").trim().equals("question")) {
                                    if (!(q1.getText().toString().equals("") && q2.getText().toString().equals("") && q3.getText().toString().equals("") && q4.getText().toString().equals("") )) {
                                        if (q1.isChecked()) {
                                            store_security_question.put("security_ans" + (i), q1.getText().toString());
                                        } else if (q2.isChecked()) {
                                            store_security_question.put("security_ans" + (i), q2.getText().toString());
                                        }
                                        if (q3.isChecked()) {
                                            store_security_question.put("security_ans" + (i), q3.getText().toString());
                                        } else if (q4.isChecked()) {
                                            store_security_question.put("security_ans" + (i), q4.getText().toString());
                                        }
                                    }
                                     if (jj.getString("type").trim().equals("review")){
                                        store_security_question.put("security_ans" + (i+1),(simpleSeekBar.getProgress()+1));

                                        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                            @Override
                                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                try {
                                                    store_security_question.put("security_ans" + (i+1),(progress+1)+"");

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onStartTrackingTouch(SeekBar seekBar) {

                                            }

                                            @Override
                                            public void onStopTrackingTouch(SeekBar seekBar) {

                                            }
                                        });

                                    }
                                    else if (jj.getString("type").trim().equals("input")){
                                        if (user_input.getText().toString().length()==0)
                                            store_security_question.put("security_ans" + (i+1),"");
                                        else {
                                            store_security_question.put("security_ans" + (i+1), user_input.getText().toString());

                                        }
                                        user_input.addTextChangedListener(new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                                            }

                                            @Override
                                            public void afterTextChanged(Editable s)
                                            {
                                                try
                                                {
                                                    store_security_question.put("security_ans" + (i+1), s.toString());

                                                }
                                                catch (JSONException e)
                                                {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        user_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                            @Override
                                            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                                                next_question.performClick();
                                                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                                                    return true;
                                                }
                                                return true;
                                            }
                                        });
                                    }
                                    setQuestion(jj.getString("question"),(i+1),com.wishster.model.SecurityQuestionModel.questionJsondata.length());
                                    callJobject(jj);
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                    previous_question.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            next_question.setText("NEXT");
                            int j=0;
                            if (i==com.wishster.model.SecurityQuestionModel.questionJsondata.length()) {

                                i--;
                            }

                            if (i!=0) {
                                j = i;
                                i--;
                            }
                            if (i>0){
                                previous_question.setVisibility(View.VISIBLE);
                            }
                            else {
                                previous_question.setVisibility(View.GONE);
                            }
                            if (i>-1)
                            {
                                setSteps(i);
                                try
                                {
                                    JSONObject jj = com.wishster.model.SecurityQuestionModel.questionJsondata.getJSONObject(i);
                                    try{

                                        store_security_question.remove("security_qtn"+(j+1));
                                        store_security_question.remove("security_ans"+(j+1));

                                    }catch (Exception e){

                                    }
                                    setQuestion(jj.getString("question"),(i+1),com.wishster.model.SecurityQuestionModel.questionJsondata.length());
                                    callJobject(jj);
                                }
                                 catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        }

    }



    private void callJobject(JSONObject jj) {

        try {
            if (jj.getString("type").trim().equals("question"))
            {
                radioContainer.setVisibility(View.VISIBLE);
                radioContainer_bar.setVisibility(View.GONE);
                radioContainer_input.setVisibility(View.GONE);
                try
                {
                    if (jj.getString("option1")!=null)
                    {
                        setTextRadio(q1,jj.getString("option1") );
                        setTextRadio(q2,jj.getString("option2") );
                        setTextRadio(q3,jj.getString("option3") );
                        setTextRadio(q4,jj.getString("option4") );


                    }
                    else {
                        setTextRadio(q1,"");
                        setTextRadio(q2,"" );
                        setTextRadio(q3,"" );
                        setTextRadio(q4,"" );
                    }
                }
                catch (Exception e)
                {

                }
            }
            else if (jj.getString("type").trim().equals("review")){
                radioContainer.setVisibility(View.GONE);
                radioContainer_bar.setVisibility(View.VISIBLE);
                radioContainer_input.setVisibility(View.GONE);
                setTextRadio(q1,"");
                setTextRadio(q2,"" );
                setTextRadio(q3,"" );
                setTextRadio(q4,"" );

}
            else if (jj.getString("type").trim().equals("input")){
                radioContainer.setVisibility(View.GONE);
                radioContainer_bar.setVisibility(View.GONE);
                radioContainer_input.setVisibility(View.VISIBLE);
                setTextRadio(q1,"");
                setTextRadio(q2,"" );
                setTextRadio(q3,"" );
                setTextRadio(q4,"" );
                radioContainer_input.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideKeyboard();
                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setTextRadio(final RadioButton radio,final  String s)
    {
        radio.postDelayed(new Runnable() {
            @Override
            public void run() {
                radio.setText(s + "");
            }
        },100);
    }

    void setQuestion(final String qs, final int point, final int totalQuestion){
        final String [] totalArray=new String[totalQuestion];
        for (int j=0;j<totalQuestion;j++){
            totalArray[j]="\t\t"+j+"";
        }
        questions.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                questions.setText(qs);

            }
        },100);
}
    public static void hideKeyboard() {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void setStore_security_question(final JSONObject store_security_question) throws JSONException
    {

        try {
            mainActivityViewModel.setSecurity(store_security_question,pd,error_msg).observe(activity, new Observer<securityModel>()
            {
                @Override
                public void onChanged(securityModel response)
                {

                 //   Toast.makeText(mActivity, ""+securityQuestionSuccessMessage.s_boolo, Toast.LENGTH_LONG).show();


                    if (securityQuestionSuccessMessage.s_boolo==true)
                    {

                        editor.putBoolean("isReg",false);
                        editor.apply();
                        startActivity(new Intent(activity, ActivityLogin.class).putExtra("userid","").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

                    }
                    else {
                        if (pd.isShowing())
                        {
                            pd.dismiss();
                            Toast.makeText(mActivity, ""+securityQuestionSuccessMessage.msg, Toast.LENGTH_SHORT).show();

                        }
                    }


                }
            });

        }catch (Exception e)
        {
            Log.d("Stttttt", "ERROR " + e.getMessage());
        }
    }
    //stape start

    public class MyListData_complain_list{
        private String description;
        private int imgId;
        public MyListData_complain_list(String description, int imgId) {
            this.description = description;
            this.imgId = imgId;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public int getImgId() {
            return imgId;
        }
        public void setImgId(int imgId) {
            this.imgId = imgId;
        }
    }
    public class MyListAdapter_stape_adapter extends RecyclerView.Adapter<MyListAdapter_stape_adapter.ViewHolder>{
        private ArrayList<MyListData_stape_list> listdata;

        // RecyclerView recyclerView;
        public MyListAdapter_stape_adapter(ArrayList<MyListData_stape_list> listdata)
        {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.list_item_stape_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {
            if (position==listdata.get(position).getCurrent_position()){
                holder.textView.setTextColor(getResources().getColor(R.color.text_color2));
                holder.icon_id.setVisibility(View.VISIBLE);

            }
            else {
                holder.textView.setTextColor(getResources().getColor(R.color.deselectcolor));
                holder.icon_id.setVisibility(View.GONE);

            }
            holder.textView.setText(listdata.get(position).getCount()+"");
            holder.relativeLayout.getLayoutParams().width = ((width/(getItemCount()+3))-(holder.relativeLayout.getWidth()+holder.relativeLayout.getWidth()));
            /*holder.relativeLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    Toast.makeText(view.getContext(),width+"\nclick on item: \n"+holder.relativeLayout.getWidth(),Toast.LENGTH_LONG).show();
                }
            });*/
        }


        @Override
        public int getItemCount()
        {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder
        {
            public ImageView icon_id;
            public TextView textView;
            public RelativeLayout relativeLayout;
            public ViewHolder(View itemView)
            {
                super(itemView);

                this.textView = (TextView) itemView.findViewById(R.id.textView);
                this.icon_id = (ImageView) itemView.findViewById(R.id.icon_id);
                relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            }
        }
    }
    public class MyListData_stape_list{

        private int count;
        private int current_position;
        public MyListData_stape_list(int count,int current_position) {
            this.current_position=current_position;
            this.count = count;
        }

        public int getCurrent_position() {
            return current_position;
        }

        public int getCount() {
            return count;
        }
    }
    //stape end

    void setSteps(int c_position){
        //stape list

        user_list=new ArrayList<MyListData_stape_list>();
        user_list.clear();
        if (adapter!=null){
            adapter.notifyDataSetChanged();
        }
        if (com.wishster.model.SecurityQuestionModel.questionJsondata.length()>0) {
            for (int j=0;j<com.wishster.model.SecurityQuestionModel.questionJsondata.length();j++)
            {
                user_list.add(new MyListData_stape_list((j+1),c_position));
            }


            RecyclerView recyclerView_complant_list = (RecyclerView) findViewById(R.id.recyclerView_stape_list);
            manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

            adapter = new MyListAdapter_stape_adapter(user_list);
            recyclerView_complant_list.setHasFixedSize(true);
            recyclerView_complant_list.setLayoutManager(manager);
            recyclerView_complant_list.setAdapter(adapter);
            recyclerView_complant_list.scrollToPosition(c_position);
        }
        //end stape list
    }
}
