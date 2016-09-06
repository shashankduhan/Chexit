package in.intrface.chexit;

import android.provider.Settings;
import android.view.View;

/**
 * Created by Abhi on 6/09/16.
 */
public class UserBoxClickListener implements View.OnClickListener {

    private User user;
    Datable datable;

    public UserBoxClickListener(User usr, Datable dtb){
        user = usr;
        datable = dtb;
    }

    @Override
    public void onClick(View view) {
        Datable.selectedUser = user;
        System.out.println(Datable.selectedUser.getName());
        datable.showDialog("checkin");

    }


}
