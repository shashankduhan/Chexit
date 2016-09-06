package in.intrface.chexit;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.*;


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
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
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
                for(User user: Datable.usersBackup){
                    dbRef.child("users/"+user.getId()+"/status").setValue(false);
                }
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
