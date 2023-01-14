package model;

/**
 *
 * @author Ale
 */
public enum CourseName {
    
    PROGRAMMING("Programming"),
    ANALYSIS_I("Analysis I"),
    ANALYSIS_II("Analysis II"),
    OPERATING_SYSTEMS("Operating systems"),
    DATABASES("Databases"),
    COMPUTER_NETWORKS("Computer networks"),
    ALGEBRA_AND_GEOMETRY("Algebra and geometry"),
    PHYSICS_I("Physics I"),
    PHYSICS_II("Physics II"),
    NATURAL_SCIENCES("Natural sciences"),
    ANALYSIS("Analysis"),
    DATA_ANALYSIS("Data analysis"),
    COSMOLOGY("Cosmology"),
    PARTICLE_PHYSICS("Particle physics"),
    CIVIL_LAW("Civil law"),
    CRIMINAL_LAW("Criminal law"),
    ADMINISTRATIVE_LAW("Administrative law"),
    HISTORY("History"),
    INTERNATIONAL_LAW("International law"),
    HUMAN_ANATOMY("Human anatomy"),
    MEDICINE_HISTORY("Medicine history"),
    LEGAL_MEDICINE("Legal medicine"),
    MICROBIOLOGY("Microbiology"),
    PSICOLOGY("Psicology"),
    BIOCHEMISTRY("Biochemistry"),
    ANALYTICAL_CHEMISTRY("Analytical chemistry");
    
    private String name;
    
    private CourseName(String name){
        this.name = name;
    }
    
    public String toString(){
        return this.name;
    }
    
}
