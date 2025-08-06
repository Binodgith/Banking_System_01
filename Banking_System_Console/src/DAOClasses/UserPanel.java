package DAOClasses;

import Exceptions.*;
import Models.TransactionStatement;
import Models.UserAccount;
import Styles.ConsoleColors;
import Utility.DBConnector;
import Utility.EmailConnector;
import org.json.JSONObject;
import Styles.ConsoleColors.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserPanel implements UserInterface{

    DBConnector connector= new DBConnector();
    Connection connection;
    PreparedStatement pst;

    long Temp_accountno;
    String Temp_username;
    EmailConnector ec;
    JSONObject json;

    @Override
    public boolean CreateAccount(UserAccount uc) throws CredentialException, AccountException {
        try{
            connection = connector.getConnection();
            pst= connection.prepareStatement("select * from account_requests cross join user_account where username=? or email=? or mobile=? or aadharno=?");
            pst.setString(1,uc.getUsername());
            pst.setString(2, uc.getEmail());
            pst.setString(3, uc.getMobile());
            pst.setString(4, uc.getAadhar());

            ResultSet res= pst.executeQuery();

            if(!res.next()){
                ec= new EmailConnector();
                String APIResponse= ec.SendOtp(uc.getEmail(),uc.getName());
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
                        String OTPResponse= ec.verifyOTp(otp, uc.getEmail(), otp_token);

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

                }


                if (OTP_Verified){
                    pst=connection.prepareStatement("insert into account_requests(username,email,mobile,aadharno,name,password,transaction_pin) values (?,?,?,?,?,?,?)");
                    pst.setString(1,uc.getUsername());
                    pst.setString(2, uc.getEmail());
                    pst.setString(3, uc.getMobile());
                    pst.setString(4, uc.getAadhar());
                    pst.setString(5, uc.getName());
                    pst.setString(6, uc.getPassword());
                    pst.setInt(7,uc.getTransaction_pin());

                    int response= pst.executeUpdate();
                    if (response>0) return true;
                    else return false;
                }
                else {
                    throw new CredentialException("Email OTP Not Verified!");
                }


            }
            else{
                throw new AccountException("Account Already Exists !");
            }


        }
        catch(Exception e){
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
                if (ec!=null){
                    json.clear();
                }
            }
            catch (SQLException e){
                throw new AccountException(e.getMessage());
            }
        }


    }




    @Override
    public boolean CheckUsername(String username) throws CredentialException{

        try{
            connection= connector.getConnection();
            pst= connection.prepareStatement("select * from account_requests cross join user_account where username=?");
            pst.setString(1,username);

            ResultSet res= pst.executeQuery();
            if(!res.next()) return true;
            else return false;

        }
        catch (Exception e){
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
    public long UserLogin(String username, String password) {
        return 0;
    }

    @Override
    public double CheckBalance(long accountno, String username) {
        return 0;
    }

    @Override
    public boolean Debit(double amount) {
        return false;
    }

    @Override
    public boolean Credit(double amount) {
        return false;
    }

    @Override
    public boolean TransferAmount(double amount, long accountno) {
        return false;
    }

    @Override
    public boolean ChangePassword(String oldpassword, String newpassword) {
        return false;
    }

    @Override
    public boolean AddTransaction(long accountno, String remark, String tran_type) {
        return false;
    }

    @Override
    public List<TransactionStatement> TransactionStatement(long accountno, String username) {
        return List.of();
    }
}
