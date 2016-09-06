package in.intrface.chexit;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;
import in.intrface.chexit.Datable;
//import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Shashank Duhan on 3/09/16.
 */



public class UserBox extends RecyclerView.ViewHolder{

    //protected NetworkImageView userimage;
    private TextView username;
    private RelativeLayout userbox;
    private ArrayList<User> userList;

    public UserBox(View itemView, final ArrayList<User> usrList) {
        super(itemView);

        userList = usrList;
        username = (TextView) itemView.findViewById(R.id.user_fullname);
        userbox = (RelativeLayout) itemView.findViewById(R.id.user_box);
        itemView.setClickable(true);
    }

    public void setName(String name){
        username.setText(name);
    }
    public void setBackgroundColor(String color){
        userbox.setBackgroundColor(Color.parseColor("#"+color));
        username.setBackgroundColor(Color.parseColor("#"+color));
    }
    public RelativeLayout getUserBox(){
        return userbox;
    }




}
