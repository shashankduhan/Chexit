package in.intrface.chexit;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Shashank Duhan on 6/09/16.
 */
public class AddUserDialog extends DialogFragment {
    int mNum;
    EditText nameIn;
    TextView label;
    /**
     * Create a new instance of AddUserDialog, providing "num"
     * as an argument.
     */
    static AddUserDialog newInstance(int num) {
        AddUserDialog f = new AddUserDialog();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments().getInt("num");

        // Pick a style based on the num.
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        switch ((mNum-1)%6) {
            case 1: style = DialogFragment.STYLE_NO_TITLE; break;
            case 2: style = DialogFragment.STYLE_NO_FRAME; break;
            case 3: style = DialogFragment.STYLE_NO_INPUT; break;
            case 4: style = DialogFragment.STYLE_NORMAL; break;
            case 5: style = DialogFragment.STYLE_NORMAL; break;
            case 6: style = DialogFragment.STYLE_NO_TITLE; break;
            case 7: style = DialogFragment.STYLE_NO_FRAME; break;
            case 8: style = DialogFragment.STYLE_NORMAL; break;
        }
        switch ((mNum-1)%6) {
            case 4: theme = android.R.style.Theme_Holo; break;
            case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
            case 6: theme = android.R.style.Theme_Holo_Light; break;
            case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
            case 8: theme = android.R.style.Theme_Holo_Light; break;
        }
        setStyle(style, theme);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_user_form, container, false);
        label = (TextView) v.findViewById(R.id.add_user_title);
        label.setText("Please Type in your Name");
        nameIn = (EditText) v.findViewById(R.id.name_in);

        // Watch for button clicks.
        Button button = (Button)v.findViewById(R.id.add_user_final);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

               if(nameIn.getText().toString().matches("")){
                    label.setText("Please Enter your Name");
                    Toast.makeText((Datable)getActivity(), "You did not enter a username", Toast.LENGTH_SHORT).show();

               }else{

                   Datable.add.newUser(nameIn.getText().toString(), "");
                   dismiss();
               }
            }
        });

        return v;
    }




}
