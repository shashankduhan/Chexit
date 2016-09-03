package in.intrface.chexit;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Shashank Duhan on Abhi's iMac on 2/09/16.
 */
public class Add {

    public Add(){

    }

    public void newUser(String name, String photo){
        DatabaseReference newUserR = FireBaseStation.dbRef.child("users").push();
        String newUserId = newUserR.getKey();
        newUserR.setValue(new User(newUserId, name));
    }
}
