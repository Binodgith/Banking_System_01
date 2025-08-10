package App;

import DAOClasses.BankPanel;
import Exceptions.AccountException;
import Exceptions.BankException;
import Exceptions.CredentialException;
import Exceptions.TransactionException;
import Models.BankStaff;
import Styles.ConsoleColors;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bank_Dashboard {
    public static void main(String[] args) {
//        BankDash();
    }


    protected static void BankDash(){
        Scanner sc= new Scanner(System.in);
        User_Dashboard user= new User_Dashboard();
        BankPanel bankPanel= new BankPanel();

        long TEMP_ACCOUNT=0;
        String TEMP_USERNAME=null;
        Pattern pattern;
        Matcher matcher;


        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);

        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.BLUE_BACKGROUND+"                                  STRAIGHT BANK OF INDIA                             "+ConsoleColors.RESET);
        System.out.println(ConsoleColors.BLUE+"======================================================================================"+ConsoleColors.RESET);



        while (true){

            System.out.println("       "+ConsoleColors.BLACK_BOLD+ConsoleColors.PURPLE_BACKGROUND+"                     Welcome to Bank Dashboard                        "+ConsoleColors.RESET);

            System.out.println();
            System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Staff Login"+ConsoleColors.RESET);
            System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 0 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Exit(0)"+ConsoleColors.RESET);

            System.out.println();
            System.out.println(ConsoleColors.PURPLE+"        ==================================================================="+ConsoleColors.RESET);


            System.out.println("Enter to any option.");
            String option= sc.next();

            if(option.equals("1")){

                System.out.println("Enter Email ID.");
                String e_email= sc.next();

                System.out.println("Enter Password");
                String e_password= sc.next();

                try{
                    try {
                        boolean resStaffLogin= bankPanel.StaffLogin(e_email,e_password);

                        if (resStaffLogin){

                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Staff Login Succeful."+ConsoleColors.RESET);

                            while (true){

                                System.out.println("       "+ConsoleColors.BLACK_BOLD+ConsoleColors.PURPLE_BACKGROUND+"                     Welcome to Bank Dashboard                        "+ConsoleColors.RESET);

                                System.out.println();
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Services On User Accounts"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 2 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Print Active User Accounts"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 3 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Approve Account opening Requets"+ConsoleColors.RESET);
                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 0 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Exit(0)"+ConsoleColors.RESET);


                                System.out.println();
                                System.out.println(ConsoleColors.PURPLE+"        ==================================================================="+ConsoleColors.RESET);


                                System.out.println("Enter any Option.");
                                option = sc.next();

                                if (option.equals("1")){

                                    System.out.println(ConsoleColors.ORANGE+"Login to User Account"+ConsoleColors.RESET);

                                    System.out.println("Enter Account Number");
                                    long e_acountno= sc.nextLong();

                                    try{
                                        String resUsername= bankPanel.AccessAccountService(e_acountno);

                                        if (resUsername!=null){
                                            TEMP_USERNAME=resUsername;
                                            TEMP_ACCOUNT=e_acountno;

                                            while (true){
                                                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Entered to user A/C no.:("+e_acountno+")"+ConsoleColors.RESET);

                                                System.out.println();
                                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 1 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Check Balance"+ConsoleColors.RESET);
                                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 2 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Account Debit"+ConsoleColors.RESET);
                                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 3 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Account Credit"+ConsoleColors.RESET);
                                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 4 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Transfer Amount"+ConsoleColors.RESET);
                                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 5 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Print Transaction Statement"+ConsoleColors.RESET);
                                                System.out.println(ConsoleColors.ORANGE+"        "+ConsoleColors.RESET+"  Press 0 for"+ConsoleColors.GREEN_BOLD_BRIGHT+" Exit(0)"+ConsoleColors.RESET);


                                                System.out.println("Choose any Option");
                                                option= sc.next();

                                                if (option.equals("1")){
                                                    try{

                                                        double resBalance= bankPanel.CheckBalance(TEMP_ACCOUNT,TEMP_USERNAME);
                                                        System.out.println(ConsoleColors.BOXING+"Current Balance is :"+resBalance+ConsoleColors.RESET);
                                                    }
                                                    catch (AccountException e){
                                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                                    }


                                                }
                                                else if (option.equals("2")){
                                                    System.out.println("Enter Amount want to Withdraw.");
                                                    double e_amount= sc.nextDouble();

                                                    try{
                                                        boolean resDebit= bankPanel.Debit(e_amount,TEMP_ACCOUNT,true);
                                                        if (resDebit){
                                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Amount Withdraw successfully from A/C no.:("+TEMP_ACCOUNT+")"+ConsoleColors.RESET);

                                                        }

                                                    } catch (TransactionException e) {
                                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);
                                                    }
                                                    finally {
                                                        ;
                                                    }
                                                }
                                                else if (option.equals("3")) {
                                                    System.out.println("Enter Amount want to Deposit.");
                                                    double e_amount= sc.nextDouble();

                                                    try{
                                                        boolean resCebit= bankPanel.Credit(e_amount,TEMP_ACCOUNT,true);
                                                        if (resCebit){
                                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Amount Deposited successfully to your A/C no.:("+TEMP_ACCOUNT+")"+ConsoleColors.RESET);

                                                        }

                                                    } catch (TransactionException e) {
                                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);
                                                    }
                                                    finally {

                                                    }

                                                }
                                                else if (option.equals("4")) {
                                                    System.out.println("Enter Account Number to whom want to Transfer.");
                                                    long toAccountno= sc.nextLong();

                                                    System.out.println("Enter Amount want to Transfer.");
                                                    double amount= sc.nextDouble();


                                                    try {
                                                        boolean resTransfer = bankPanel.TransferAmount(amount, TEMP_ACCOUNT, toAccountno);

                                                        if (resTransfer){
                                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Amount Succesfully Transfered from A/C No:"+TEMP_ACCOUNT+" to A/C No.:"+toAccountno+ConsoleColors.RESET);

                                                        }

                                                    } catch (TransactionException e) {
                                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                                    }
                                                    finally {

                                                    }

                                                }

                                                else if (option.equals("5")) {
                                                    System.out.println("Printing Trasaction Statement.............");
                                                    try{
                                                        List<Map> list= bankPanel.TransactionStatement(TEMP_ACCOUNT,TEMP_USERNAME);

                                                        System.out.println(ConsoleColors.BOXING+ConsoleColors.BROWN_BACKGROUND+"              Table of Account Request List            "+ConsoleColors.RESET);

                                                        System.out.println("---------------------------------------------------------------------------------");

                                                        System.out.printf("%-12s %-30s %15s %15s %15s%n ",ConsoleColors.BROWN_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT  + "Date", "Particulars","Debit", "Credit", "Balance" + ConsoleColors.RESET);
                                                        System.out.println();
                                                        System.out.println("---------------------------------------------------------------------------------");

                                                        for (Map map:list){
                                                            System.out.printf("%-12s %-30s %15s %15s %15s%n", map.get("Date"), map.get("Particular"),map.get("DEBIT"), map.get("CREDIT"), map.get("Balance"));
                                                            System.out.println();

                                                        }


                                                        System.out.println("---------------------------------------------------------------------------------");



                                                    } catch (AccountException e) {
                                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                                    }
                                                    finally {

                                                    }

                                                }
                                                else if (option.equals("0")) {

                                                    break;
                                                }
                                                else {
                                                    System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Input ! Enter Between 0 to 5"+ConsoleColors.RESET);

                                                }
                                            }


                                        }
                                        else {
                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+ "Unable to Enter in user account."+ConsoleColors.RESET);

                                        }
                                    } catch (RuntimeException e) {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);
                                    }
                                    catch (AccountException e){
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                    }
                                    finally {

                                    }




                                }
                                else if(option.equals("2")){
                                    try{
                                        List<BankStaff> list = bankPanel.ListStaffDetails();

                                        System.out.println(ConsoleColors.BOXING+ConsoleColors.BROWN_BACKGROUND+"              List of Bank Staffs           "+ConsoleColors.RESET);

                                        System.out.println("---------------------------------------------------------------------------------");

                                        System.out.printf("%7s %7.5s %10s %15s %18s ",ConsoleColors.BROWN_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT  + "Name", "Email Id","Mobile No.", "Aadhar No.", "Role" + ConsoleColors.RESET);
                                        System.out.println();
                                        System.out.print("---------------------------------------------------------------------------------");


                                        for (BankStaff bs:list){
                                            System.out.printf("%7s %7.5s %10s %15s %18s ",ConsoleColors.BROWN_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT  + bs.getName(), bs.getEmail(),bs.getMob(), bs.getAadharno(), bs.getRole() + ConsoleColors.RESET);
                                            System.out.println();

                                        }

                                        System.out.print("---------------------------------------------------------------------------------");

                                    } catch (BankException e) {
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);


                                    }
                                    finally {

                                    }
                                }
                                else if(option.equals("3")){
                                    try{
                                        bankPanel.ListAccountApproveRequest();

                                        System.out.println("Enter Username that you want to Approve.");
                                        String e_username= sc.next();

                                        boolean resApprove= bankPanel.ApproveAccountRequest(e_username);

                                        if (resApprove) {
                                            System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.GREEN_BACKGROUND+"Approved !Account Information sent to by mail."+ e_username+ConsoleColors.RESET);

                                        }


                                    }
                                    catch (AccountException e){
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                    }
                                    catch (Exception e){
                                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);

                                    }
                                    finally {

                                    }
                                }
                                else if (option.equals("0")) {
                                    break;

                                }
                                else {
                                    System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Input ! Enter Between 0 to 3"+ConsoleColors.RESET);

                                }
                            }


                        }
                    } catch (CredentialException e) {
                        System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);
                        BankDash();
                    }


                }   catch (Exception e) {
                    System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+e.getMessage()+ConsoleColors.RESET);
                }
                finally {

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

                System.out.println(ConsoleColors.WHITE_BOLD_BRIGHT+ConsoleColors.DARK_RED_BACKGROUND+"Invalid Input ! Enter 1 or 0"+ConsoleColors.RESET);
            }


        }
    }
}
