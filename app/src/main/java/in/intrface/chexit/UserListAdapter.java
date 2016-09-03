package in.intrface.chexit;

import android.content.Context;
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
    private Context mContext;
    private int mFocusedItem = 0;

    public UserListAdapter(Context context,ArrayList<User> userlist){
        mContext = context;
        mUserList = userlist;
    }

    @Override
    public UserBox onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_box, null);
        UserBox box = new UserBox(v);
        box.userbox.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                System.out.println("YOYOYO");
            }
        });

        return box;
    }

    @Override
    public void onBindViewHolder(UserBox holder, int position) {
        User user = mUserList.get(position);
        holder.getLayoutPosition();
        holder.username.setText(user.getName());

    }


    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public void refresh(){
        notifyDataSetChanged();
    }
}
