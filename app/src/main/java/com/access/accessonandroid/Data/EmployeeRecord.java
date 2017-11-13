package com.access.accessonandroid.Data;

import com.access.accessonandroid.NFC.HCE.IHCEAccessCard;
import com.access.accessonandroid.Registration.UserAccess;


/**
 * The EmployeeRecord class is responsible for handling the local representation of the user's
 * Employee Record, which contains relevant information about the user.
 *
 * Created by Tyler Collison on 10/24/2017.
 */
public class EmployeeRecord implements IHCEAccessCard, UserAccess{

    //Stores the singleton instance
    private static EmployeeRecord instance = null;

    /**
     * Gets or creates the singleton instance
     * @return the singleton instance of this class
     */
    public static EmployeeRecord getInstance() {
        if(instance == null) {
            instance = new EmployeeRecord();
        }
        return instance;
    }

    @Override
    public String getAccessID() {
        //TODO: Implement this method such that it retrieves the user's employee ID from storage
        return "test1234";
    }

    public Boolean isUser(String username){
        return true;
    }

    public void updateUserPassword(String password){

    }

    public String getUserPassword(){
        return "";
    }
}
