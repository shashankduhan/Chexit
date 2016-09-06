package in.intrface.chexit;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
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

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Datable extends AppCompatActivity {

    protected Button addVisitor;
    protected DatabaseReference dbref = FireBaseStation.dbRef;
    static ArrayList<User> users = new ArrayList<User>();
    static ArrayList<User> usersBackup = new ArrayList<User>();
    final static Add add = new Add();
    protected  ChildEventListener newUserListner;
    protected ValueEventListener valueUpdateListner;
    static View.OnClickListener userBoxClickListener;
    private RecyclerView mUserListBox;
    private UserListAdapter userListAdapter;
    private int mStackLevel = 0;
    public static User selectedUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_datable);
        Firebase.setAndroidContext(this);

        addVisitor = (Button) findViewById(R.id.add_visitor);
        //nameIn = (EditText) findViewById(R.id.name_in);
        mUserListBox = (RecyclerView) findViewById(R.id.user_list_box) ;
        mUserListBox.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setReverseLayout(true);
        mUserListBox.setLayoutManager(llm);
        userListAdapter = new UserListAdapter(users, this);
        mUserListBox.setAdapter(userListAdapter);
        addVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("add_user");
            }
        });


        newUserListner = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User usr = dataSnapshot.getValue(User.class);

                users.add(usr);
                userListAdapter.refresh();

            }

            @Override
            public void onChildChanged(DataSnapshot data, String s) {
                User updatedUserData = data.getValue(User.class);
                System.out.println(updatedUserData.getName());
                int i = 0;
                for(User usr: users){
                    if(usr.getId().matches(updatedUserData.getId())){
                        users.set(i, updatedUserData);
                        userListAdapter.refresh();
                    }
                    i++;
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot data) {
                User updatedUserData = data.getValue(User.class);
                System.out.println(updatedUserData.getName());
                int i = 0;
                for(User usr: users){
                    if(usr.getId().matches(updatedUserData.getId())){
                        users.remove(i);
                        userListAdapter.refresh();
                    }
                    i++;
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };






    }



    @Override
    protected void onStart(){
        super.onStart();

        dbref.child("users").orderByChild("key").addChildEventListener(newUserListner);



    }
    @Override
    public void onStop() {
        super.onStop();
        dbref.child("users").orderByChild("name").removeEventListener(newUserListner);
        usersBackup = (ArrayList<User>)users.clone();
        users.clear();


    }
    @Override
    public void onPause(){
        super.onPause();

    }

    @Override
    public void onResume(){
        super.onResume();
        //dbref.child("users").orderByChild("name").addChildEventListener(userListner);

    }

    public void showDialog(String what) {


        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);



        switch (what){
            case "add_user":
                DialogFragment newFragment = AddUserDialog.newInstance(7);
                newFragment.show(ft, "dialog");
                break;
            case "checkin":
                DialogFragment newAlert = new CheckinDialog();
                newAlert.show(ft, "dialog");
                break;
        }
    }

    public void checkoutAll(){
        for(User user: users){
            dbref.child("users/"+user.getId()+"/status").setValue(false);
        }
    }


}
