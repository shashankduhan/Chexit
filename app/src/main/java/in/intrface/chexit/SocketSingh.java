package in.intrface.chexit;

import android.content.Context;
import android.util.Log;
import android.view.View;

/**
 * Created by Shashank Duhan on 1/09/16.
 */
public class SocketSingh implements View.OnClickListener {
    GoogleSignIn googleGuy;
    MainActivity mainActivityGuy;
    FireBaseStation fireBaseGuy;
    String TAG = "SocketSingh";

    public SocketSingh(MainActivity mainSingh, GoogleSignIn mrGoogle, FireBaseStation mrFirebase){

            googleGuy = mrGoogle;
            mainActivityGuy = mainSingh;
            fireBaseGuy = mrFirebase;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.sign_in_google:
                //googleGuy.googleSignIn();
                Log.d(TAG, "onClick: ");
                googleGuy.googleSignIn();
                break;
            case R.id.sign_out:
                fireBaseGuy.signOut();
                mainActivityGuy.signOut();
                break;

        }
    }
}
