package in.intrface.chexit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Datable extends AppCompatActivity {

    protected TextView tester;
    protected DatabaseReference dbref = FireBaseStation.dbRef;
    static ArrayList<User> users = new ArrayList<User>();
    final protected Add add = new Add();
    ChildEventListener userListner;
    EditText nameIn;
    Button addUserBut;
    private RecyclerView mUserListBox;
    private UserListAdapter userListAdapter;
    private int count;
    private  String afterid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_datable);

        tester = (TextView) findViewById(R.id.tester);
        nameIn = (EditText) findViewById(R.id.name_in);
        mUserListBox = (RecyclerView) findViewById(R.id.user_list_box) ;
        userListAdapter = new UserListAdapter(Datable.this, users);
        mUserListBox.setAdapter(userListAdapter);
        final LinearLayoutManager llm = new LinearLayoutManager(this);
        mUserListBox.setLayoutManager(llm);


        userListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User usr = dataSnapshot.getValue(User.class);

                users.add(usr);
                tester.setText(String.valueOf(users.size()));
                userListAdapter = new UserListAdapter(Datable.this, users);
                //mUserListBox.setAdapter(userListAdapter);
                userListAdapter.refresh();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };





        //add.newUser("KhunKhar Singh", "zero");



    }



    @Override
    protected void onStart(){
        super.onStart();

        dbref.child("users").orderByChild("name").addChildEventListener(userListner);

    }
    @Override
    public void onStop() {
        super.onStop();
        dbref.child("users").orderByChild("name").removeEventListener(userListner);

    }
    @Override
    public void onPause(){
        super.onPause();
        //dbref.child("users").orderByChild("name").removeEventListener(userListner);
    }

    @Override
    public void onResume(){
        super.onResume();
        //dbref.child("users").orderByChild("name").addChildEventListener(userListner);

    }
}
