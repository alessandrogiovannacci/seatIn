package model;

/**
 *
 * @author Ale
 */
public enum Department {
    
    THEORICAL_SCIENCES("Theorical sciences"),
    ENGINEERING("Engineering"),
    CHEMICAL_SCIENCES("Chemical sciences"),
    MEDICAL_SCIENCES("Medical sciences"),
    ECONOMY("Economy");
    
    private String name;
    
    private Department(String name){
        this.name = name;
    }
    
    public String toString(){
        return this.name;
    }
    
}
