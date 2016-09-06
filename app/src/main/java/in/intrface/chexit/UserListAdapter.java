package in.intrface.chexit;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Shashank Duhan on 3/09/16.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserBox>{

    private ArrayList<User> mUserList;
    private int mFocusedItem = 0;
    protected User user;
    protected Datable activity;
    protected View.OnClickListener clickListener;

    public UserListAdapter(ArrayList<User> userlist, Datable datable){
        mUserList = userlist;
        activity = datable;
    }

    @Override
    public UserBox onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_box, parent, false);
        UserBox box = new UserBox(v, mUserList);

        return box;
    }

    @Override
    public void onBindViewHolder(UserBox holder, int position) {

        user = mUserList.get(position);
        clickListener = new UserBoxClickListener(mUserList.get(position), activity);
        holder.getLayoutPosition();
        holder.setName(user.getName());
        holder.getUserBox().setOnClickListener(clickListener);
        String color;
        System.out.println(mUserList.get(position).getStatus() + " " + mUserList.get(position).getName());
        if(mUserList.get(position).getStatus()){color = "5b7433";}
        else{color = "3b445e";}

        holder.setBackgroundColor(color);

        System.out.println(user.getId());

    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void refresh(){
        notifyDataSetChanged();
    }


}
