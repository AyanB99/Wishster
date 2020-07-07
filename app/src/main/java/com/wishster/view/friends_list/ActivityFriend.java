package com.wishster.view.friends_list;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.wishster.model.internet_chacking.CheckInternet;
import com.wishster.view.R;
import com.wishster.view.nointernet.ActivityNoActivity;
import com.wishster.view.test.ActivityTest;
import com.wishster.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityFriend extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActivityFriend a;
    MainActivityViewModel mainActivityViewModel;
    ViewStub stub;
    Spinner spinner;
    LinearLayout menu_container;
    CircleImageView profile_image;
    //Scrolling
    boolean isScrolling=false;
    ArrayList<MyListData_complain_list> user_list;
    int currentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public static boolean isOpen=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);
        mainActivityViewModel= ViewModelProviders.of(this).get(MainActivityViewModel.class);
        a=ActivityFriend.this;
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        //toolbar.setTitle("Testing");
        if(!(new CheckInternet(this).getInternetStatus()))
        {

            startActivity(new Intent(this, ActivityNoActivity.class));
            finish();
        }
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
        {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        menu_container=(LinearLayout)findViewById(R.id.menu_container);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        spinner = (Spinner) findViewById(R.id.spinner);
        FragmentManager fm = getSupportFragmentManager();
        Menu menu=mainActivityViewModel.callDrawer(a,toolbar,spinner,profile_image,fm);
        //Menu menu=mainActivityViewModel.callDrawer(a,toolbar);
        mainActivityViewModel.callLanguage(menu);
        /*stub= (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.activity_friend);
        View inflated = stub.inflate();*/


        //recycleView
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //int height = (size.y);
        user_list=new ArrayList<MyListData_complain_list>();
        user_list.add(new MyListData_complain_list("Add New", R.drawable.add));
        user_list.add(new MyListData_complain_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_complain_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_complain_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_complain_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_complain_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_complain_list("Andey", R.drawable.profile_image_default));

       /* MyListData_user_list[] myListData_Money = new MyListData_user_list[]
                {
                        user_list.add(new MyListData_user_list("Email", android.R.drawable.ic_dialog_email));
                    new MyListData_user_list("Email", android.R.drawable.ic_dialog_email),
                    new MyListData_user_list("Info", android.R.drawable.ic_dialog_info)

      };*/
        manager=new LinearLayoutManager(this);
        RecyclerView recyclerView_complant_list = (RecyclerView) findViewById(R.id.recyclerView_complant_list);
        MyListAdapter_complain_adapter adapter_bed = new MyListAdapter_complain_adapter(user_list);
        recyclerView_complant_list.setHasFixedSize(true);
        int c_no=3;
        c_no=(width/100);
        if (c_no>3)
            c_no=4;
        else if (c_no<3)
            c_no=3;

        recyclerView_complant_list.setLayoutManager(new GridLayoutManager(this, c_no));
        //recyclerView_complant_list.setLayoutManager(manager);
        recyclerView_complant_list.setAdapter(adapter_bed);
        recyclerView_complant_list.scrollToPosition(0);
        //loading
        recyclerView_complant_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrollOutItems = manager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems))
                {
                    isScrolling = false;
                    //fetchdatagetData();
                }
            }
        });
        //end recycle view
        menu_container=(LinearLayout)findViewById(R.id.menu_container);
        menu_container.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    isOpen=false;
                    menu_container.setVisibility(View.GONE);
                }
            });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        mainActivityViewModel.CallGoMenu(item, a);
        DrawerLayout drawer = a.findViewById(R.id.drawer_id);
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nv);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

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
    public class MyListAdapter_complain_adapter extends RecyclerView.Adapter<MyListAdapter_complain_adapter.ViewHolder>{
        private ArrayList<MyListData_complain_list> listdata;

        // RecyclerView recyclerView;
        public MyListAdapter_complain_adapter(ArrayList<MyListData_complain_list> listdata)
        {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.friend_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position)
        {

            holder.friend_name.setText(listdata.get(position).getDescription());
            holder.imageView.setImageResource(listdata.get(position).getImgId());
            holder.relativeLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (isOpen==false) {
                        menu_container.setVisibility(View.VISIBLE);
                        isOpen=true;
                    }
                }
            });
            /*holder.menu_container.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    isOpen=false;
                    holder.menu_container.setVisibility(View.GONE);
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
            //public LinearLayout menu_container;
            public CircleImageView imageView;
            public TextView friend_name;
            public RelativeLayout relativeLayout;
            public ViewHolder(View itemView)
            {
                super(itemView);
                this.imageView = (CircleImageView) itemView.findViewById(R.id.profile_icon_friend);
                this.friend_name = (TextView) itemView.findViewById(R.id.friend_name);
               // this.menu_container = (LinearLayout) itemView.findViewById(R.id.menu_container);
                relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
            }
        }
    }

}
