package in.intrface.chexit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;


public class MainActivity extends AppCompatActivity{
    SignInButton googleSignIn;
    Button signOutButton;
    TextView displayBoard;
    TextView lastUserIndicator;
    Button cameraIgnitor;
    SocketSingh mrSocket;
    GoogleSignIn mrGoogle;
    FireBaseStation mrFirebase;


    public static boolean loggedin = false;

    //Google Signins


    //Firebase variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions googleOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mrFirebase = new FireBaseStation();
        mrGoogle = new GoogleSignIn(mrFirebase, this, googleOptions);
        mrSocket = new SocketSingh(this, mrGoogle, mrFirebase);

        googleSignIn = (SignInButton) findViewById(R.id.sign_in_google);
        googleSignIn.setOnClickListener(mrSocket);

        signOutButton = (Button) findViewById(R.id.sign_out);
        signOutButton.setOnClickListener(mrSocket);
        signOutButton.setVisibility(View.INVISIBLE);

        //cameraIgnitor = (Button) findViewById(R.id.cameraIgnitor);
        //cameraIgnitor.setOnClickListener(mrSocket);

        //lastUserIndicator = (TextView) findViewById(R.id.lastUserIndicator);

        displayBoard = (TextView) findViewById(R.id.displayPad);

        ShimmerFrameLayout container =
                (ShimmerFrameLayout) findViewById(R.id.shimmer_view_container);
        container.setDuration(2000);
        container.setAutoStart(true);




    }


    @Override
    protected void onStart(){
        super.onStart();
        mrFirebase.getAuth().addAuthStateListener(mrFirebase.getAuthStateListener());


    }
    @Override
    public void onStop() {
        super.onStop();




        if(loggedin){

        }
        if (mrFirebase.getAuthStateListener() != null) {
            mrFirebase.getAuth().removeAuthStateListener(mrFirebase.getAuthStateListener());
        }


    }
    @Override
    public void onPause(){
        super.onPause();
        if (mrFirebase.getAuthStateListener() != null) {
            mrFirebase.getAuth().removeAuthStateListener(mrFirebase.getAuthStateListener());
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        mrFirebase.getAuth().addAuthStateListener(mrFirebase.getAuthStateListener());

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);


        //Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(..);
        if(requestCode == mrGoogle.RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            mrGoogle.handleSignInResult(result);

        }
    }

    public void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        //Log.d("frB Auth:", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        //showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mrFirebase.getAuth().signInWithCredential(credential)
                .addOnCompleteListener( this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {

                            //Log.w("FrB Auth:", "signInWithCredential", task.getException());
                            Toast.makeText( MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                        }else {
                            //Let's setup account for this user.
                            mrFirebase.accountSetup();
                            googleSignIn.setVisibility(View.INVISIBLE);
                            signOutButton.setVisibility(View.VISIBLE);
                            Intent datable = new Intent(getApplicationContext(), Datable.class);
                            startActivity(datable);

                        }
                    }
                });
    }

    public void signOut(){

        loggedin = false;
        signOutButton.setVisibility(View.INVISIBLE);
        googleSignIn.setVisibility(View.VISIBLE);
        displayBoard.setText("Chexit");

    }

    //OUR BASIC FUNCTIONS.....
    //FUNCTION TO OPEN NEW CAMERA ACTIVITY
    public void newCameraActivity(){
        if(loggedin){
            Intent cameraPad = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(cameraPad);
            //Log.d("Camera", "Clicked");
        }
    }


    //Our Signin Functions.........................
    //Authentications...............
    //[SIGNIN FUNCTIONS start]





}
