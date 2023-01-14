package seatinserver;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import model.EmailSender;
import model.PasswordGenerator;
import sql.Department;

/**
 *
 * @author Ale
 */
public class AdminRegister {
    
    //fields
    private final String number;
    private final String name;
    private final String surname;
    private final String role;
    private final String department;
    private final String email;
    private final String password;
    private final String activationCode;
    private final boolean profileActivated;
    private final int authAttempts;
    
    //-------------------------------------------------------------------
    
    public AdminRegister(){
        Scanner in = new Scanner(System.in);
        System.out.print("Insert number:");
        this.number = in.nextLine();
        System.out.print("Insert name: ");
        this.name = in.nextLine();
        System.out.print("Insert surname: ");
        this.surname = in.nextLine();
        this.role = "Administrator";
        boolean invalidChoice;
        String dep = null;
        do{
            invalidChoice = false;
            System.out.println("Select department:");
            System.out.println("1- " + Department.CHEMICAL_SCIENCES.toString());
            System.out.println("2- " + Department.ECONOMY.toString());
            System.out.println("3- " + Department.ENGINEERING.toString());
            System.out.println("4- " + Department.MEDICAL_SCIENCES.toString());
            System.out.println("5- " + Department.THEORICAL_SCIENCES.toString());
            System.out.print("Insert number: ");
            byte choice = in.nextByte();
            switch(choice){
                case 1:
                    dep = Department.CHEMICAL_SCIENCES.toString();
                    break;
                case 2:
                    dep = Department.ECONOMY.toString();
                    break;
                case 3:
                    dep = Department.ENGINEERING.toString();
                    break;
                case 4:
                    dep = Department.MEDICAL_SCIENCES.toString();
                    break;
                case 5:
                    dep = Department.THEORICAL_SCIENCES.toString();
                    break;
                default:
                    System.out.println("Invalid choice. Repeat.");
                    invalidChoice = true;
                    break;
            }  
        }while(invalidChoice);
        this.department = dep;
        in = new Scanner(System.in);
        System.out.print("Insert email: ");
        this.email = in.nextLine();
        
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
        .useDigits(true)
        .useLower(true)
        .useUpper(true)
        .build();
        String password = passwordGenerator.generate(8);
        String activationCode = passwordGenerator.generate(8);
        
        this.password = password;
        this.activationCode = activationCode;
        try {
            new EmailSender(email, password, activationCode).sendRegEmail();
        } catch (MessagingException ex) {
            Logger.getLogger(AdminRegister.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.profileActivated = false;
        this.authAttempts = 0;
    }
    
    //-------------------------------------------------------------------
    
    public String getNumber(){
        return this.number;
    }
    
    //-------------------------------------------------------------------
    
    public String getName(){
        return this.name;
    }
    
    //-------------------------------------------------------------------
    
    public String getSurname(){
        return this.surname;
    }
    
    //-------------------------------------------------------------------
    
    public String getRole(){
        return this.role;
    }
    
    //-------------------------------------------------------------------
    
    public String getDepartment(){
        return this.department;
    }
    
    //-------------------------------------------------------------------
    
    public String getEmail(){
        return this.email;
    }
    
    //-------------------------------------------------------------------
    
    public String getPassword(){
        return this.password;
    }
    
    //-------------------------------------------------------------------
    
    public String getActivationCode(){
        return this.activationCode;
    }
    
    //-------------------------------------------------------------------
    
    public boolean getProfileActivated(){
        return this.profileActivated;
    }
    
    //-------------------------------------------------------------------
    
    public int getAuthAttempts(){
        return this.authAttempts;
    }
}
