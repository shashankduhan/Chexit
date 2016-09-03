package in.intrface.chexit;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Shashank Duhan on 3/09/16.
 */



public class UserBox extends RecyclerView.ViewHolder{

    //protected NetworkImageView userimage;
    public TextView username;
    public RelativeLayout userbox;

    public UserBox(View itemView) {
        super(itemView);

        username = (TextView) itemView.findViewById(R.id.user_fullname);
        userbox = (RelativeLayout) itemView.findViewById(R.id.user_box);

        itemView.setClickable(true);
    }
}
