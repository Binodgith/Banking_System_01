package DAOClasses;

import App.Main;
import Exceptions.*;
import Models.TransactionStatement;
import Models.UserAccount;
import Styles.ConsoleColors;
import Utility.*;
import org.json.JSONObject;
import Styles.ConsoleColors.*;

import java.net.CacheRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserPanel implements UserInterface{

    DBConnector connector= new DBConnector();
    PasswordEncrypter encrypter= new PasswordEncrypter();
    Connection connection;
    PreparedStatement pst;

    static long Temp_accountno;
    static String Temp_username;
    EmailConnector ec;
    JSONObject json=new JSONObject();

    @Override
    public boolean CreateAccount(UserAccount uc) throws CredentialException, AccountException {
        try{
            connection = connector.getConnection();
            pst= connection.prepareStatement("select * from account_requests cross join user_account where account_requests.username=? or account_requests.email=? or account_requests.mobile=? or account_requests.aadharno=? or user_account.username=? or user_account.email=? or user_account.mobile=? or user_account.aadharno=?");
            pst.setString(1,uc.getUsername());
            pst.setString(2, uc.getEmail());
            pst.setString(3, uc.getMobile());
            pst.setString(4, uc.getAadhar());
            pst.setString(5,uc.getUsername());
            pst.setString(6, uc.getEmail());
            pst.setString(7, uc.getMobile());
            pst.setString(8, uc.getAadhar());

            ResultSet res= pst.executeQuery();

            if(!res.next()){
                ec= new EmailConnector();
                String APIResponse= ec.SendOtp(uc.getEmail(),(uc.getName()).split("\\s+")[0]);
                json = new JSONObject(APIResponse);
                Scanner scanner = new Scanner(System.in);
                boolean OTP_Verified=false;

                if(json.getBoolean("sent_status")){
                     long otp_token= json.getLong("token_number");
                    System.out.println(ConsoleColors.GREEN_BRIGHT+json.getString("message")+ConsoleColors.RESET);
                    int entryCount=0;
                    while (!OTP_Verified){
                        System.out.println("Enter Email Otp (Valid till 5 minutes only)");
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
                    pst.setString(6, encrypter.hashPassword(uc.getPassword()));
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
            pst= connection.prepareStatement("select * from account_requests cross join user_account where account_requests.username=? or user_account.username=?");
            pst.setString(1,username);
            pst.setString(2,username);

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

//    -----------------------------------User login method-----------------------------------


    @Override
    public long UserLogin(String username, String password) throws UserException {

        try{
            connection = connector.getConnection();
            pst = connection.prepareStatement("select * from account_requests where username=? and password=?");
            pst.setString(1,username);
            pst.setString(2,encrypter.hashPassword(password));
            ResultSet res1= pst.executeQuery();

            if(!res1.next()){
                pst= connection.prepareStatement("select accountno,email,name from user_account where username=? and password=?");
                pst.setString(1,username);
                pst.setString(2,encrypter.hashPassword(password));

                ResultSet res2= pst.executeQuery();
                if (res2.next()){

// --------------------------------------Email OTP Sent----||--------------------------

                    try{
                        ec= new EmailConnector();
                        String APIResponse= ec.SendOtp(res2.getString("email"),(res2.getString("name")).split("\\s+")[0]);
                        json = new JSONObject(APIResponse);
                        Scanner scanner = new Scanner(System.in);
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
                                    Temp_username=username;
                                    Temp_accountno= res2.getLong("accountno");
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
                                return res2.getLong("accountno");
                            }
                            else throw new UserException("Email Otp not verified!");

                        }
                        else {
                            throw new UserException("Unable to sent OTP! Try Again");
                        }
                    }
                    catch (SQLException e){
                        throw new UserException(e.getMessage());
                    }

                }
                else {
                    throw new UserException("Invalid Credential");
                }
            }
            else{
                throw new UserException("Your Account is pending for approval ! Contact Bank");
            }


        }
        catch (Exception e){
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





//    ------------------------------Account balance Checking-------------------------------

    @Override
    public double CheckBalance(long accountno, String username) throws AccountException {
        try{
            connection=connector.getConnection();
            pst= connection.prepareStatement("select balance from user_account where accountno=? and username=?");
            pst.setLong(1,accountno);
            pst.setString(2,username);

            ResultSet res= pst.executeQuery();

            if(res.next()) return res.getDouble("balance");
            else throw new AccountException("No bank account found !");


        }
        catch (Exception e){
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
    public boolean Debit(double amount,long accountno,boolean isbank) throws TransactionException {

        try{
            connection= connector.getConnection();
            pst= connection.prepareStatement("select balance from user_account where accountno=?");
            pst.setLong(1,accountno);


            ResultSet res1= pst.executeQuery();
            if (res1.next()){
                if (res1.getDouble("balance")>=amount){
                    try{
                        pst= connection.prepareStatement("update user_account set balance=balance-? where accountno=?  and balance>=?");
                        pst.setLong(2,accountno);

                        pst.setDouble(1,amount);
                        pst.setDouble(3,amount);

                        int res2= pst.executeUpdate();
                        if(res2>0){
                            if (isbank){
                                String Remark= "Amount Withdrwal from AC No.: "+accountno+" via Bank.";
                                boolean ReportRes=AddTransaction(accountno,Remark,"DEBIT",amount);
                                if (ReportRes) return true;
                                else throw  new TransactionException("Unable to Add Report ! Try Again.");
                            }
                            else {
                                return true;
                            }



                        }
                        else{
                            throw new TransactionException("Unable to Debit amount ! Try Again.");
                        }
                    }
                    catch (SQLException e){
                        throw new TransactionException(e.getMessage());
                    }


                }
                else{
                    throw new TransactionException("Account balance is lower than Debit Amount.");

                }
            }
            else {
                throw new TransactionException("No account Found !");
            }
        }
        catch (Exception e){
            throw new TransactionException(e.getMessage());
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
                throw new TransactionException(e.getMessage());
            }
        }

    }




    @Override
    public boolean Credit(double amount, long accountno,boolean isbank) throws TransactionException {

        try{
            connection =connector.getConnection();
            pst= connection.prepareStatement("update user_account set balance=balance+? where accountno=?");
            pst.setDouble(1,amount);
            pst.setLong(2,accountno);


            int res= pst.executeUpdate();
            if(res>0){
                if (isbank){
                    String Remark= "Amount Deposited to AC No.: "+accountno+" via Bank.";
                    boolean ReportRes=AddTransaction(accountno,Remark,"CREDIT",amount);
                    if (ReportRes) return true;
                    else throw  new TransactionException("Unable to Add Report ! Try Again.");
                }
                else{
                    return true;
                }



            }
            else {
                throw new TransactionException("Unable to Credit amount ! Try Again.");

            }


        }
        catch (Exception e){
            throw new TransactionException(e.getMessage());
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
                throw new TransactionException(e.getMessage());
            }
        }

    }

    @Override
    public boolean TransferAmount(double amount,long fromaccount, long toaccountno) throws TransactionException {
        try{
            connection= connector.getConnection();
            pst= connection.prepareStatement("select name from user_account where accountno=?");
            pst.setLong(1,toaccountno);

            ResultSet res1= pst.executeQuery();

            if (res1.next()){
                pst= connection.prepareStatement("select balance from user_account where accountno=?");
                pst.setLong(1,fromaccount);
                ResultSet res2= pst.executeQuery();

                if (res2.next() && res2.getDouble("balance")>=amount){
                    boolean isDebit=Debit(amount,fromaccount,false);
                    if (isDebit) {
                        boolean isCredit= Credit(amount,toaccountno,false);

                        if (isCredit){
                            String fromRemark= "Amount Transfered from AC No.: "+fromaccount+" to "+"Ac No.: "+toaccountno+"("+res1.getString("name") +")"+" by User";
                            String toRemark= "Amount Receieved from AC No.: "+fromaccount+" to "+"Ac No.: "+toaccountno+ " by User";

                            boolean ReportRes=AddTransaction(fromaccount,fromRemark,"DEBIT",amount);
                            boolean RepotRes2= AddTransaction(toaccountno,toRemark,"CREDIT",amount);
                            if (ReportRes && RepotRes2) return true;
                            else throw  new TransactionException("Unable to Add Report ! Try Again.");
                        }
                        else {
                            throw  new TransactionException("Unable to Transfer !");
                        }
                    }
                    else {
                        throw  new TransactionException("Unable to Transfer !");
                    }

                }
                else {
                    throw new TransactionException("Your Account Balance is lower than Transfer Amount.");
                }



            }
            else{
                throw new TransactionException("Entered Account number doesn't Exist.");

            }
        }
        catch (SQLException e){
            throw new TransactionException(e.getMessage());
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
                throw new TransactionException(e.getMessage());
            }
        }

    }



    @Override
    public boolean ChangePassword(String username, long accountno,String oldpassword, String newpassword) throws UserException {

        try{

//            String EncryptPassword= encrypter.hashPassword(oldpassword);
            long resAccount= UserLogin(username,oldpassword);
            connection = connector.getConnection();
            if (resAccount==accountno){
                pst= connection.prepareStatement("update user_account set password=? where username=? and accountno=?");
                pst.setString(1,encrypter.hashPassword(newpassword));
                pst.setString(2,username);
                pst.setLong(3,accountno);

                int res= pst.executeUpdate();
                if (res>0) return true;
                else throw new UserException("Unable to Change password right now.");

            }
            else {
                throw new UserException("Old password is wrong!");
            }

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
    public boolean AddTransaction(long accountno, String remark, String tran_type,double amount) throws TransactionException {
        try{
            connection= connector.getConnection();
            pst=connection.prepareStatement("INSERT INTO transaction_statement (accountno, transactionType, remark, amount ) VALUES(?,?,?,?)");
            pst.setLong(1,accountno);
            pst.setString(2,tran_type);
            pst.setString(3,remark);
            pst.setDouble(4,amount);

            int res= pst.executeUpdate();
            if (res>0) return true;
            else throw new TransactionException("Unable to Add Transaction Report ! Try Again");

        }
        catch (SQLException e){
            throw new TransactionException(e.getMessage());
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
                throw new TransactionException(e.getMessage());
            }
        }

    }



//    -----------------------------------Getting Transaction Statement via map key value packed in ArrayList-----------------------------------
    @Override
    public List<Map> TransactionStatement(long accountno, String username) throws AccountException{

        try{
            String SQL_Query="SELECT " +
                    "    date AS Date," +
                    "    CONCAT(remark, ' || Transaction ID: ', transactionid) AS particulars," +
                    "    CASE " +
                    "        WHEN transactionType = 'DEBIT' THEN amount " +
                    "        ELSE NULL " +
                    "    END AS DEBIT," +
                    "    CASE " +
                    "        WHEN transactionType = 'CREDIT' THEN amount " +
                    "        ELSE NULL " +
                    "    END AS CREDIT," +
                    "    " +
                    "    SUM(" +
                    "        CASE " +
                    "            WHEN transactionType = 'CREDIT' THEN amount" +
                    "            WHEN transactionType = 'DEBIT' THEN -amount" +
                    "            ELSE 0" +
                    "        END" +
                    "    ) OVER (" +
                    "        ORDER BY date, transactionid " +
                    "        ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW" +
                    "    ) AS Balance " +
                    "" +
                    "FROM transaction_statement " +
                    "WHERE accountno =?" +
                    "ORDER BY date DESC, transactionid DESC";

            connection= connector.getConnection();
            pst= connection.prepareStatement(SQL_Query);
            pst.setDouble(1,accountno);

            ResultSet res= pst.executeQuery();


            List<Map> list = new ArrayList<>();
            while (res.next()){
                Map<String,String> map= new TreeMap<>();
                map.put("Date", String.valueOf(res.getDate("date")));
                map.put("Particular",String.valueOf(res.getString("particulars")));
                map.put("DEBIT",String.valueOf(res.getDouble("DEBIT")));
                map.put("CREDIT",String.valueOf(res.getDouble("CREDIT")));
                map.put("Balance",String.valueOf(res.getDouble("Balance")));

                list.add(map);
            }

            return list;


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
