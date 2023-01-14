package model;

import java.io.Serializable;

/**
 *
 * @author Ale
 */
public class Student implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final String number, name, surname, degreeCourse, careerState, email, password, activationCode;
    private final int registrationYear, authAttempts;
    private final boolean  profileActivated;
    
    public Student(String number, String name, String surname, String degreeCourse, String careerState, String email, String password, int registrationYear){
        this.number = number;
        this.name = name;
        this.surname = surname;
        this.degreeCourse = degreeCourse;
        this.careerState = careerState;
        this.email = email;
        this.password = password;
        this.registrationYear = registrationYear;
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
    
    public String getDegreeCourse(){
        return this.degreeCourse;
    }
    
    //******************************************************************************************************************************
    
    public String getCareerState(){
        return this.careerState;
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
    
    public int getRegistrationYear(){
        return this.registrationYear;
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
