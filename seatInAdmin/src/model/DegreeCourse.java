package model;

/**
 *
 * @author Ale
 */
public enum DegreeCourse {
    
    COMPUTERSCIENCE("Computer science"),
    MATHEMATICS("Mathematics"),
    PHYSICS("Physics"),
    ASTROPHYSICS("Astrophysics"),
    LAW("Law"),
    MEDICINE("Medicine"),
    CHEMISTRY("Chemistry");
    
    private String name;
    
    private DegreeCourse(String name){
        this.name = name;
    }
    
    public String toString(){
        return this.name;
    }
    
}
