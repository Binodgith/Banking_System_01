package DAOClasses;

import Exceptions.AccountException;
import Exceptions.CredentialException;
import Exceptions.TransactionException;
import Exceptions.UserException;
import Models.TransactionStatement;
import Models.UserAccount;

import java.util.List;
import java.util.Map;

public interface UserInterface {
    boolean CreateAccount(UserAccount uc) throws AccountException, CredentialException;

    boolean CheckUsername(String username) throws CredentialException;

    long UserLogin(String username,String password) throws UserException,CredentialException;

    double CheckBalance(long accountno,String username) throws AccountException;

    boolean Debit(double amount,long accountno,boolean isbank) throws TransactionException;

    boolean Credit(double amount,long accountno,boolean isbank) throws TransactionException;

    boolean TransferAmount(double amount, long fromaccount, long toaccountno) throws TransactionException;

    boolean ChangePassword(String username, long accountno,String oldpassword, String newpassword) throws UserException;

    boolean AddTransaction(long accountno,String remark,String tran_type, double amount) throws TransactionException;

    List<Map> TransactionStatement(long accountno, String username) throws AccountException;




}
