package DAOClasses;

import Exceptions.*;
import Models.BankStaff;
import Models.UserAccount;
import Styles.ConsoleColors;
import Utility.*;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankPanel extends UserPanel implements BankInterface{

    DBConnector connector= new DBConnector();
    PasswordEncrypter encrypter= new PasswordEncrypter();
    UserAccount user;
    Connection connection;
    PreparedStatement pst;

//    static long Temp_accountno;
//    static String Temp_username;
    EmailConnector ec;
    JSONObject json;




//    -----------------------------------------Bank Staff Login Function returniing True or False------------------------


    @Override
    public boolean StaffLogin(String email, String password) throws CredentialException {

        try{
            connection=connector.getConnection();
            pst= connection.prepareStatement("select email,name from bank_staff where email=? and password=?");
            pst.setString(1,email);
            pst.setString(2,password);

            ResultSet res2= pst.executeQuery();
            if (res2.next()){

// --------------------------------------Email OTP Sent----||--------------------------

                try{
                    ec= new EmailConnector();
                    String APIResponse= ec.SendOtp(res2.getString("email"),res2.getString("name"));
                    json = new JSONObject(APIResponse);
                    Scanner scanner = null;
                    boolean OTP_Verified=false;

                    if(json.getBoolean("sent_status")){
                        long otp_token= json.getLong("token_number");
                        System.out.println(ConsoleColors.GREEN_BRIGHT+json.getString("message")+ConsoleColors.RESET);
                        int entryCount=0;
                        while (!OTP_Verified){
                            System.out.println("Enter Email Otp (Valid till 5 minutes only");
                            int otp= scanner.nextInt();
                            String OTPResponse= ec.verifyOTp(otp, res2.getString("email"), otp_token);

                            json = new JSONObject(OTPResponse);
                            if(json.getBoolean("verify_status")) {
                                OTP_Verified=true;
                                System.out.println(json.getString("message"));
                                break;
                            }
                            else {
                                entryCount++;
                                if (entryCount>=2){
                                    break;
                                }
                                System.out.println(ConsoleColors.RED_BACKGROUND_BRIGHT+json.getString("message")+ConsoleColors.RESET);
                            }

                        }
                        //--------------------------------------------Email OTP Verified -------------------------------

                        if (OTP_Verified){
                            return true;
                        }
                        else throw new CredentialException("Email Otp not verified!");

                    }
                    else {
                        throw new CredentialException("Unable to sent OTP! Try Again");
                    }
                }
                catch (SQLException e){
                    throw new CredentialException(e.getMessage());
                }

            }
            else {
                throw new CredentialException("Invalid Credential");
            }
        }
        catch (SQLException e){
            throw new CredentialException(e.getMessage());
        }
        finally {
            try{
                if(connection!=null){
                    connection.close();
                }
                if(pst!=null){
                    pst.close();
                }

            }
            catch (SQLException e){
                throw new CredentialException(e.getMessage());
            }
        }

    }



    @Override
    public List<UserAccount> ListAccounts() throws UserException{

        try {
            List<UserAccount>  list= new ArrayList<>();

            connection= connector.getConnection();
            pst=connection.prepareStatement("select username,accountno,name,email,mobile,aadharno,balance,account_active from user_account");

            ResultSet res = pst.executeQuery();
            while (res.next()){
                list.add(new UserAccount(res.getString("username"),res.getString("email"),res.getString("mobile"),res.getString("aadharno"),res.getString("name"),res.getLong("accountno"),res.getDouble("balance"),res.getBoolean("account_active")));
            }

            return list;


        }
        catch (SQLException e){
            throw new UserException(e.getMessage());
        }
        finally {
            try{
                if(connection!=null){
                    connection.close();
                }
                if(pst!=null){
                    pst.close();
                }

            }
            catch (SQLException e){
                throw new UserException(e.getMessage());
            }
        }


    }

    @Override
    public List<BankStaff> ListStaffDetails() throws BankException {
        try {
            List<BankStaff>  list= new ArrayList<>();

            connection= connector.getConnection();
            pst=connection.prepareStatement("select staffId,accountno,name,email,mobile,aadharno,role from bank_staff");

            ResultSet res = pst.executeQuery();
            while (res.next()){
                list.add(new BankStaff(res.getString("email"),res.getString("mobile"),res.getString("aadharno"),res.getString("role"),res.getString("name")));
            }

            return list;


        }
        catch (SQLException e){
            throw new BankException(e.getMessage());
        }
        finally {
            try{
                if(connection!=null){
                    connection.close();
                }
                if(pst!=null){
                    pst.close();
                }

            }
            catch (SQLException e){
                throw new BankException(e.getMessage());
            }
        }
    }

    @Override
    public void ListAccountApproveRequest() throws AccountException {
        try{
            connection = connector.getConnection();
            pst= connection.prepareStatement("select * from  account_requests");

                ResultSet res= pst.executeQuery();

                System.out.println(ConsoleColors.BOXING+ConsoleColors.BROWN_BACKGROUND+"              Table of Account Request List            "+ConsoleColors.RESET);

                System.out.println("---------------------------------------------------------------------------------");

                System.out.printf("%7s %7.5s %8.5s %10s %15s %17s %16s",ConsoleColors.BROWN_BACKGROUND+ConsoleColors.WHITE_BOLD_BRIGHT  + "User Name", "Name","Email Id", "Mobile no.", "Aadhar No.", "Account Status", "Account Number" + ConsoleColors.RESET);
                System.out.println();
			    System.out.print("---------------------------------------------------------------------------------");
                while (res.next()){
                    System.out.printf("%7s %7.5s %8.5s %10s %15s %17s %16s", res.getString("username"), res.getString("name"),res.getString("email"), res.getString("mobile"), res.getString("aadharno"), res.getString("account_active"), "Not Generated");

                }
                System.out.println("---------------------------------------------------------------------------------");


        }
        catch (SQLException e){
            throw new AccountException(e.getMessage());
        }
        finally {
            try{
                if(connection!=null){
                    connection.close();
                }
                if(pst!=null){
                    pst.close();
                }

            }
            catch (SQLException e){
                throw new AccountException(e.getMessage());
            }
        }
    }



    @Override
    public boolean ApproveAccountRequest(String username) throws AccountException {
        try{
            connection=connector.getConnection();
            pst= connection.prepareStatement("select name from account_requests");

            ResultSet res1= pst.executeQuery();

            if(res1.next()){
                pst= connection.prepareStatement("update account_requests set accountno=COALESCE((SELECT MAX(accountno) FROM user_account), 1000100)+1 , account_active=true,balance=0 where username=? ");
                pst.setString(1,username);

                int res2= pst.executeUpdate();
                if (res2>0){
                    try{
                        pst= connection.prepareStatement("insert into user_account select * from account_requests");
                        pst.executeUpdate();

                        pst= connection.prepareStatement("delete from account_requests where username=? ");
                        pst.setString(1,username);
                        int res3= pst.executeUpdate();

                        if (res3>0){
                            return true;
                        }
                        else{
                            throw new AccountException("Unable to insert approve request! Try Again");
                        }

                    }
                    catch (SQLException e){
                        throw new AccountException(e.getMessage());
                    }


                }
                else{
                    throw new AccountException("Unable to Update approve request! Try Again");
                }

            }
            else {
                throw new AccountException("No Request found for this User name");
            }
        }
        catch (SQLException e){
            throw new AccountException(e.getMessage());

        }
        finally {
            try{
                if(connection!=null){
                    connection.close();
                }
                if(pst!=null){
                    pst.close();
                }

            }
            catch (SQLException e){
                throw new AccountException(e.getMessage());
            }
        }


    }



    @Override
    public String AccessAccountService(long accountno) throws AccountException {
        try{
            connection= connector.getConnection();
            pst=connection.prepareStatement("select username from user_account where accountno=?");
            pst.setLong(1,accountno);

            ResultSet res= pst.executeQuery();
            if(res.next()){
                return res.getString("username");
            }
            else {
                throw new AccountException("No Bank Account found for this account number ");
            }
        }
        catch (SQLException e){
            throw new AccountException(e.getMessage());
        }
        finally {
            try{
                if(connection!=null){
                    connection.close();
                }
                if(pst!=null){
                    pst.close();
                }

            }
            catch (SQLException e){
                throw new AccountException(e.getMessage());
            }
        }
    }

}
