package App;

import DAOClasses.UserPanel;
import Exceptions.AccountException;
import Exceptions.CredentialException;
import Exceptions.TransactionException;
import Exceptions.UserException;
import Models.UserAccount;
import Styles.ConsoleColors;
import com.sun.source.tree.WhileLoopTree;


import javax.xml.validation.Schema;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.*;


public class User_Dashboard {
    public static void main(String[] args) {
//        UserDash();
//        long TEMP_ACCOUN = 0;
//        System.out.println(TEMP_ACCOUN);
    }

    protected static void UserDash(){
        Scanner sc= new Scanner(System.in);
        Bank_Dashboard bank= new Bank_Dashboard();
        User_Dashboard user= new User_Dashboard();
        UserPanel userPanel= new UserPanel();

        long TEMP_ACCOUNT=0;
        String TEMP_USERNAME=null;
        Pattern pattern;
        Matcher matcher;


        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.BLUE_BACKGROUND+"                                  STRAIGHT BANK OF INDIA                             "+ConsoleColors.RESET);

        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);
        System.out.println("       "+ConsoleColors.BLACK_BOLD+ConsoleColors.ORANGE_BACKGROUND+"                     Welcome to User Dashboard                        "+ConsoleColors.RESET);

        System.out.println();
        System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Create Account"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 2 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" User Login"+ConsoleColors.RESET);
        System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 0 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Exit(0)"+ConsoleColors.RESET);


        System.out.println();
        System.out.println(ConsoleColors.ORANGE+"        ==================================================================="+ConsoleColors.RESET);


        while (true){
            String option= sc.next();

            if (option.equals("1")){
                while (true){
                    System.out.println(ConsoleColors.BLACK_BOLD+ConsoleColors.ORANGE_BACKGROUND+"  Fill User Registration Form.                        "+ConsoleColors.RESET);
                    System.out.println("Enter Username");
                    String e_username= sc.next();

                    pattern= Pattern.compile("^[a-zA-Z0-9._]{3,16}$");

                    if (pattern.matcher(e_username).matches()){
                        try {
                            boolean rescheck= userPanel.CheckUsername(e_username);
                            if (rescheck){
                                String e_name; String e_email; String e_mobile; String e_aadharno; String e_password; int e_pin;
                                System.out.println(ConsoleColors.GREEN_BRIGHT+"Username Available"+ConsoleColors.RESET);

                                sc.nextLine();
                                System.out.println("Enter Your Name.");
                                e_name= sc.nextLine();

                                while (true){
                                    System.out.println("Enter Email ID.");
                                    e_email= sc.next();

                                    pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
                                    if (pattern.matcher(e_email).matches()){
                                        break;
                                    }
                                    else {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Email Id Type."+ConsoleColors.RESET);

                                    }

                                }


                                while (true){
                                    System.out.println("Enter Mobile number.");
                                    e_mobile= sc.next();

                                    pattern = Pattern.compile("^[6-9]\\d{9}$");
                                    if (pattern.matcher(e_mobile).matches()){
                                        break;
                                    }
                                    else {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Mobile number ! Enter 10 digit number. "+ConsoleColors.RESET);

                                    }
                                }

                                while (true){
                                    System.out.println("Enter Aadhar card number.");
                                    e_aadharno= sc.next();

                                    pattern = Pattern.compile("^[2-9]\\d{11}$");
                                    if (pattern.matcher(e_aadharno).matches()){
                                        break;
                                    }
                                    else {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Aadhar number ! Enter 12 digit number. "+ConsoleColors.RESET);

                                    }
                                }

                                while (true){
                                    System.out.println("Enter Strong Password");
                                    System.out.println(ConsoleColors.RED_ITALIC+"Minimum 6 characters,At least 1 uppercase letter,At least 1 lowercase letter,At least 1 digit,At least 1 special character (@#$%^&+=!)"+ConsoleColors.RESET);
                                    e_password= sc.next();

                                    pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");
                                    if (pattern.matcher(e_password).matches()){
                                        break;
                                    }
                                    else {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Wrong Pattern! Minimum 6 characters,At least 1 uppercase letter,At least 1 lowercase letter,At least 1 digit,At least 1 special character (@#$%^&+=!)  "+ConsoleColors.RESET);

                                    }
                                }

                                while (true){
                                    System.out.println("Enter Transaction Pin");
                                    System.out.println(ConsoleColors.RED_ITALIC+"4 Transaction Security Pin"+ConsoleColors.RESET);
                                    e_pin= sc.nextInt();

                                    pattern = Pattern.compile("^\\d{4}$");
                                    if (pattern.matcher(String.valueOf(e_pin)).matches()){
                                        break;
                                    }
                                    else {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Enter four digit pin only !"+ConsoleColors.RESET);

                                    }
                                }

                                boolean accountres = userPanel.CreateAccount(new UserAccount(e_username,e_email,e_mobile,e_aadharno,e_name,e_password,e_pin));

                                if (accountres){
                                    System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Account Created Succesfully . Your account is pending for Approval."+ConsoleColors.RESET);
                                    UserDash();
                                }





                            }
                        } catch (CredentialException e) {
                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                        } catch (RuntimeException e) {
                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                        }
                        catch (AccountException e) {
                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                        }
                        catch (Exception e){
                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                        }
                    }
                    else {
                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalide Username type! Must be Character , Digit & _ or . (3-16 Character)"+ConsoleColors.RESET);

                    }


                }





            }
            else if (option.equals("2")) {
                System.out.println(ConsoleColors.BLACK_BOLD+ConsoleColors.ORANGE_BACKGROUND+"  Login to Net Banking                       "+ConsoleColors.RESET);

                while (true){
                    System.out.println("Enter Username");
                    String e_username= sc.next();

                    System.out.println("Enter Password");
                    String e_password= sc.next();

                    try {
                        TEMP_ACCOUNT = userPanel.UserLogin(e_username,e_password);

                        if (TEMP_ACCOUNT!=0){
                            TEMP_USERNAME= e_username;

                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Logged In Succesfully . "+ConsoleColors.RESET);
                            System.out.println();


                            while (true){
                                System.out.println("       "+ConsoleColors.BLACK_BOLD+ConsoleColors.ORANGE_BACKGROUND+"                     Hello, A/C :("+TEMP_ACCOUNT+") Holder                        "+ConsoleColors.RESET) ;

                                System.out.println();
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Check Balance"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 2 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Transfer Amount"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 3 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Change Password"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 4 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Print Transaction Statemant"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 0 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Exit(0)"+ConsoleColors.RESET);


                                System.out.println();
                                System.out.println(ConsoleColors.ORANGE+"        ==================================================================="+ConsoleColors.RESET);

                                System.out.println("Choose Any option.");
                                option= sc.next();

                                if (option.equals("1")){

                                    try{
                                        double balance = userPanel.CheckBalance(TEMP_ACCOUNT,TEMP_USERNAME);

                                        System.out.println(ConsoleColors.BOXING+"Current Balance is :"+balance+ConsoleColors.RESET);


                                    }
                                    catch (AccountException e){
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                    }
                                    catch (Exception e){
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);


                                    }


                                } else if (option.equals("2")) {

                                    System.out.println("Enter Account Number to whom want to Transfer.");
                                    long toAccountno= sc.nextLong();

                                    System.out.println("Enter Amount want to Transfer.");
                                    double amount= sc.nextDouble();


                                    try {
                                        boolean resTransfer = userPanel.TransferAmount(amount, TEMP_ACCOUNT, toAccountno);

                                        if (resTransfer){
                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Amount Succesfully Transfered from A/C No:"+TEMP_ACCOUNT+" to A/C No.:"+toAccountno+ConsoleColors.RESET);

                                        }

                                    } catch (TransactionException e) {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                    }
                                }
                                else if (option.equals("3")){
                                    System.out.println("Enter Old Password");
                                    String oldpw= sc.next();

                                    String newpw1; String newpw2;

                                    while (true){
                                        System.out.println("Enter New Password");
                                        System.out.println(ConsoleColors.RED_ITALIC+"Minimum 6 characters,At least 1 uppercase letter,At least 1 lowercase letter,At least 1 digit,At least 1 special character (@#$%^&+=!)"+ConsoleColors.RESET);
                                        newpw1= sc.next();

                                        pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");
                                        if (pattern.matcher(e_password).matches()){
                                            break;
                                        }
                                        else {
                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Wrong Pattern! Minimum 6 characters,At least 1 uppercase letter,At least 1 lowercase letter,At least 1 digit,At least 1 special character (@#$%^&+=!)  "+ConsoleColors.RESET);

                                        }
                                    }

                                    while (true){
                                        System.out.println("Enter New Password ,Again");
                                        System.out.println(ConsoleColors.RED_ITALIC+"Minimum 6 characters,At least 1 uppercase letter,At least 1 lowercase letter,At least 1 digit,At least 1 special character (@#$%^&+=!)"+ConsoleColors.RESET);
                                        newpw2= sc.next();

                                        pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{6,}$");
                                        if (pattern.matcher(e_password).matches()){
                                            break;
                                        }
                                        else {
                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Wrong Pattern! Minimum 6 characters,At least 1 uppercase letter,At least 1 lowercase letter,At least 1 digit,At least 1 special character (@#$%^&+=!)  "+ConsoleColors.RESET);

                                        }
                                    }

                                    if (newpw1.equals(newpw2)){

                                        try{
                                            boolean resChangepw= userPanel.ChangePassword(TEMP_USERNAME,TEMP_ACCOUNT,oldpw,newpw1);
                                            if (resChangepw){
                                                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Password Changed Succesfully."+ConsoleColors.RESET);

                                            }

                                        }
                                        catch (UserException e){
                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                        }


                                    }
                                    else {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Both New password section must be same! Try Again "+ConsoleColors.RESET);

                                    }


                                }
                                else if (option.equals("4")) {
                                    System.out.println("Printing Trasaction Statement.............");
                                    try{
                                        List<Map> list= userPanel.TransactionStatement(TEMP_ACCOUNT,TEMP_USERNAME);

                                        System.out.println(ConsoleColors.BOXING+ConsoleColors.BROWN_BACKGROUND+"                                 Account Statement                        "+ConsoleColors.RESET);

                                        System.out.println("---------------------------------------------------------------------------------");

                                        System.out.printf("%12s %18s %45s %50s %55s%n",ConsoleColors.BROWN_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT  + "Date", "Particulars","Debit", "Credit", "Balance" + ConsoleColors.RESET);
//                                        System.out.println();
                                        System.out.println("---------------------------------------------------------------------------------");

                                        for (Map map:list){
                                            System.out.printf("%-12s %-40s %18s %13s %17s%n1", map.get("Date"), map.get("Particular"),map.get("DEBIT"), map.get("CREDIT"), map.get("Balance"));
                                            System.out.println();
                                        }


                                        System.out.println("---------------------------------------------------------------------------------");



                                    } catch (AccountException e) {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                    }


                                }
                                else if (option.equals("0")) {

                                    UserDash();
                                    break;
                                }
                                else {
                                    System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Input ! Enter 0 to 4 only."+ConsoleColors.RESET);

                                }

                            }


                        }

                    } catch (UserException e) {
                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                    }
                    catch (Exception e){
                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                    }
                }


            }
            else if (option.equals("0")) {
                try {
                    Main.HomeDash();
                    break;
                } catch (IOException e) {
                    System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                }

            }
            else {
                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid input ! Enter 0 to 2 only."+ConsoleColors.RESET);

            }
        }

    }
}
