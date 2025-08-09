package DAOClasses;

import Exceptions.*;
import Models.BankStaff;
import Models.UserAccount;

import java.util.List;

public interface BankInterface {
    boolean StaffLogin(String email, String password) throws CredentialException;

    List<UserAccount> ListAccounts() throws UserException;

    List<BankStaff> ListStaffDetails() throws BankException;

    void ListAccountApproveRequest() throws AccountException;

    String AccessAccountService(long accountno) throws AccountException;

    boolean ApproveAccountRequest(String username) throws AccountException;
}
