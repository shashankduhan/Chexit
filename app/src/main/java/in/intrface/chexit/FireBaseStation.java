package in.intrface.chexit;


import android.support.annotation.NonNull;
import android.util.Log;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Shashank Duhan on Abhi's iMAC on 1/09/16.
 */
public class FireBaseStation {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public static FirebaseUser user;
    public static DatabaseReference dbRef;
    DatabaseReference lastUserRef;
    private static final String TAG = "firebase_signin";

    public FireBaseStation(){

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // [START_EXCLUDE]
                //updateUI(user);
                // [END_EXCLUDE]
            }
        };

    }

    public void accountSetup(){


            user = FirebaseAuth.getInstance().getCurrentUser();
            String userid = user.getUid();

            dbRef = FirebaseDatabase.getInstance().getReference("users/"+userid);
            dbRef.child("loggedin").setValue("true");

    }






    //[SIGNIN FUNCTIONS end]

    public void signOut(){
        Auth.GoogleSignInApi.signOut(GoogleSignIn.mGoogleApiClient).setResultCallback(new ResultCallback<Status>(){

            @Override
            public void onResult(@NonNull Status status){
                //displayBoard.setText("Signed Out");
                System.out.println(Datable.usersBackup.size());

                dbRef.child("loggedin").setValue("false");
                FirebaseAuth.getInstance().signOut();

            }
        });
    }

    public FirebaseUser getUser(){
        return user;
    }
    public DatabaseReference getDbRef(){
        return dbRef;
    }
    public FirebaseAuth getAuth(){
        return mAuth;
    }
    public FirebaseAuth.AuthStateListener getAuthStateListener(){
        return mAuthListener;
    }
}
