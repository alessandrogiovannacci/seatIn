package model;

import java.io.Serializable;

/**
 *
 * @author Ale
 */
public class Course implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final String id, name, degreeCourse, description;
    private final int activationYear;
    
    public Course(String id, String name, int activationYear, String degreeCourse, String description){
        this.id = id;
        this.name = name;
        this.activationYear = activationYear;
        this.degreeCourse = degreeCourse;
        this.description = description;
    }
    
    //******************************************************************************************************************************
    
    public String getID(){
        return this.id;
    }
    
    //******************************************************************************************************************************
    
    public String getName(){
        return this.name;
    }
    
    //******************************************************************************************************************************
    
    public int getActivationYear(){
        return this.activationYear;
    }
    
    //******************************************************************************************************************************
    
    public String getDegreeCourse(){
        return this.degreeCourse;
    }
    
    //******************************************************************************************************************************
    
    public String getDescription(){
        return this.description;
    }
    
}
