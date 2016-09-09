package in.intrface.chexit;

import android.content.Intent;
import android.support.annotation.NonNull;





import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


/**
 * Created by Shashank Duhan on 1/09/16.
 */
public class GoogleSignIn implements GoogleApiClient.OnConnectionFailedListener{

    SignInButton signInButton;
    public static GoogleApiClient mGoogleApiClient;
    private static final String TAG = "G_SignInActivity";
    public static final int RC_SIGN_IN = 9001;
    GoogleSignInAccount acct;
    //protected boolean loggedIn = false;
    FireBaseStation FireBaseGuy;
    MainActivity missActivity;

    public GoogleSignIn(FireBaseStation fbguy, MainActivity acti, GoogleSignInOptions gso){



        mGoogleApiClient = new GoogleApiClient.Builder(acti)
                .enableAutoManage(acti /*FragmentActivity*/, this /* OnConnectionFailedListener*/)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        FireBaseGuy = fbguy;
        missActivity = acti;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //If some error occured from google
        //Log.d(TAG, "onConnectioFailed:" + connectionResult);
    }

    public void googleSignIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        missActivity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void handleSignInResult(GoogleSignInResult result){
        //Log.d(TAG, "handleSignInResult:"+result.isSuccess());

        if(result.isSuccess()){
            //Signed in successfully, show authenticated UI.
            acct = result.getSignInAccount();
            missActivity.displayBoard.setText("Hello, " + acct.getDisplayName());
            missActivity.firebaseAuthWithGoogle(acct);

            MainActivity.loggedin = true;
        }
        else{
            //If error occurs.
            //displayBoard.setText("Some Error occured");
        }
    }

}
