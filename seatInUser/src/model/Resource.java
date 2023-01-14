package model;

import java.io.Serializable;

/**
 *
 * @author Ale
 */
public class Resource implements Serializable {
    
     private static final long serialVersionUID = 1L;
     private final String id, name, description, type, visibility, sectionId, folder;
     
     public Resource(String id, String name, String description, String type, String visibility, String sectionId, String folder){
         this.id = id;
         this.name = name;
         this.description = description;
         this.type = type;
         this.visibility = visibility;
         this.sectionId = sectionId;
         this.folder = folder;
     }
     
     public  String getId(){
         return this.id;
     }
     
     public String getName(){
         return this.name;
     }
     
     public String getDescription(){
         return this.description;
     }
     
     public String getType(){
         return this.type;
     }
     
     public String getVisibility(){
         return this.visibility;
     }
     
     public String getSectionId(){
         return this.sectionId;
     }
     
     public String getFolder(){
         return this.folder;
     }
     
}
