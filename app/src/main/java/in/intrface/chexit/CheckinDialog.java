package in.intrface.chexit;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;


/**
 * Created by Shashank Duhan on 6/09/16.
 */
public class CheckinDialog extends DialogFragment {

    private User usr = Datable.selectedUser;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder((Datable)getActivity());


        String action;
        if(usr.getStatus()){
            action = "Check out";
        }else{
            action = "Checkin";
        }

        builder.setMessage(action+" as "+usr.getName())
            .setPositiveButton(action, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    System.out.println(usr.getName()+" "+usr.getStatus());
                    if(usr.getStatus()){
                        FireBaseStation.dbRef.child("users/"+usr.getId().toString()+"/status").setValue(false);
                    }
                    if(!usr.getStatus()){
                        FireBaseStation.dbRef.child("users/"+usr.getId().toString()+"/status").setValue(true);
                    }


                }
            })
            .setNegativeButton("", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

        return builder.create();
    }
}
