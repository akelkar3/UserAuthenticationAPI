package inclass1.group3.group3_inclass01;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Aliandro on 4/2/2018.
 */

public class ResponseApi implements Serializable {
    // any
    String status;
    String token;



    String _id;
    String email;
    String name;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String age;
    String weight, address;
    String message="";
    String userId;

//ArrayList<Thread> threads,messages;
//Thread message;
public String get_id() {
    return _id;
}

    public void set_id(String _id) {
        this._id = _id;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return userId;
    }

    public void setUser_id(String user_id) {
        this.userId = user_id;
    }

    public String getUser_email() {
        return email;
    }

    public void setUser_email(String user_email) {
        this.email = user_email;
    }

    public String getUser_fname() {
        return name;
    }

    public void setUser_fname(String user_fname) {
        this.name = user_fname;
    }


}
