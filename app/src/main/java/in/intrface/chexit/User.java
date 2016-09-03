package in.intrface.chexit;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Shashank Duhan on Abhi's iMAC on 2/09/16.
 */
public class User {
    private String id;
    private String name;
    private String photo;

    public User(String id, String name, String photo){
        this.id = id;
        this.name = name;
        this.photo = photo;
    }
    public User(String id, String name){
        this.id = id;
        this.name = name;
        this.photo = "https://lh6.googleusercontent.com/-pNp3CpBi5V4/AAAAAAAAAAI/AAAAAAAAAAA/8hSKcdxIr5o/W96-H96/photo.jpg?sz=64";
    }
    public User(){

    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getPhoto(){
        return photo;
    }

}


