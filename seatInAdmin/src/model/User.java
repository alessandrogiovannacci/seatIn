package model;

import java.io.Serializable;

/**
 *
 * @author Ale
 */
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final String number, name, surname, role, department, email, password, activationCode;
    private final boolean profileActivated;
    private final int authAttempts;
    
    public User(String number, String name, String surname, String role, String department, String email){
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.department = department;
        this.email = email;
        this.password = "aaa";
        this.activationCode = "PROVA";
        this.profileActivated = false;
        this.authAttempts = 0;
    }
    
    //******************************************************************************************************************************
    
    public String getNumber(){
        return this.number;
    }
    
    //******************************************************************************************************************************
    
    public String getName(){
        return this.name;
    }
    
    //******************************************************************************************************************************
    
    public String getSurname(){
        return this.surname;
    }
    
    //******************************************************************************************************************************
    
    public String getRole(){
        return this.role;
    }
    
    //******************************************************************************************************************************
    
    public String getDepartment(){
        return this.department;
    }
    
    //******************************************************************************************************************************
    
    public String getEmail(){
        return this.email;
    }
    
    //******************************************************************************************************************************
    
    public String getPassword(){
        return this.password;
    }
    
    //******************************************************************************************************************************
    
    public String getActivationCode(){
        return this.activationCode;
    }
    
    //******************************************************************************************************************************
    
    public boolean getProfileActivated(){
        return this.profileActivated;
    }
    
    //******************************************************************************************************************************
    
    public int getAuthAttempts(){
        return this.authAttempts;
    }
    
}
