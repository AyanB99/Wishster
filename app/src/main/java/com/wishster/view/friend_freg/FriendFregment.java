package com.wishster.view.friend_freg;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wishster.view.R;
import com.wishster.view.friends_list.ActivityFriend;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendFregment extends Fragment {
    //Scrolling
    boolean isScrolling=false;
    ArrayList<MyListData_friend_list> user_list;
    int currentItems,totalItems,scrollOutItems;
    LinearLayoutManager manager;
    public static boolean isOpen=false;
    View v;
    EditText search_btn;
    static boolean bool=false;
    LinearLayout menu_container;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.activity_friend,container,false);
        init();
        action();
        recycleviewCall();
        return v;
    }

    private void action() {
        if (bool==true) {
            search_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bool=false;
                    Log.d("testsearch","A");
                }
            });
        }
        search_btn.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {

                if (!hasFocus) {
                    bool=true;
                    Log.d("testsearch","B");
                }
            }
        });
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

    private void init() {
        search_btn=(EditText)v.findViewById(R.id.search_btn);
        menu_container=(LinearLayout)v.findViewById(R.id.menu_container);
    }

    void recycleviewCall(){
        //recycleView
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //int height = (size.y);
        user_list=new ArrayList<MyListData_friend_list>();

        user_list.add(new MyListData_friend_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_friend_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_friend_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_friend_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Andey", R.drawable.profile_image_default));



        user_list.add(new MyListData_friend_list("Julia", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Lea", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Hugo", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Ethan", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Rossi", R.drawable.profile_image_default));
        user_list.add(new MyListData_friend_list("Andey", R.drawable.profile_image_default));

       /* MyListData_user_list[] myListData_Money = new MyListData_user_list[]
                {
                        user_list.add(new MyListData_user_list("Email", android.R.drawable.ic_dialog_email));
                    new MyListData_user_list("Email", android.R.drawable.ic_dialog_email),
                    new MyListData_user_list("Info", android.R.drawable.ic_dialog_info)

      };*/
        manager=new LinearLayoutManager(getActivity());
        RecyclerView recyclerView_complant_list = (RecyclerView) v.findViewById(R.id.recyclerView_complant_list);
        MyListAdapter_complain_adapter adapter_bed = new MyListAdapter_complain_adapter(user_list);
        recyclerView_complant_list.setHasFixedSize(true);
        int c_no=3;
        c_no=(width/100);

        if (c_no>4)
            c_no=5;
        else if (c_no<4)
            c_no=4;

        recyclerView_complant_list.setLayoutManager(new GridLayoutManager(getActivity(), c_no));
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
    }

    public class MyListAdapter_complain_adapter extends RecyclerView.Adapter<MyListAdapter_complain_adapter.ViewHolder>{
        private ArrayList<MyListData_friend_list> listdata;

        // RecyclerView recyclerView;
        public MyListAdapter_complain_adapter(ArrayList<MyListData_friend_list> listdata)
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
    public class MyListData_friend_list{
        private String description;
        private int imgId;
        public MyListData_friend_list(String description, int imgId) {
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
}
