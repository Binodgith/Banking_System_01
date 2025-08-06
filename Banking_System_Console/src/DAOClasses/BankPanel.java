package DAOClasses;

import Models.BankStaff;
import Models.UserAccount;

import java.util.List;

public class BankPanel extends UserPanel implements BankInterface{

    @Override
    public boolean StaffLogin(String email, String password) {
        return false;
    }

    @Override
    public List<UserAccount> ListAccounts() {
        return List.of();
    }

    @Override
    public List<BankStaff> ListStaffDetails() {
        return List.of();
    }

    @Override
    public void ListAccountApproveRequest() {

    }

    @Override
    public String AccessAccountService(long accountno) {
        return "";
    }
}
