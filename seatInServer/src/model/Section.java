package model;

import java.io.Serializable;

/**
 *
 * @author Ale
 */
public class Section implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private final String id, name, description, visibility, upsection;
    
    public Section(String id, String name, String description, String visibility, String upsection){
        this.id = id;
        this.name = name;
        this.description = description;
        this.visibility = visibility;
        this.upsection = upsection;
    }
    
    public String getId(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public String getVisibility(){
        return this.visibility;
    }
    
    public String getUpsection(){
        return this.upsection;
    }
    
}