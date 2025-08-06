package Models;

import java.util.Objects;

public class BankStaff {
    private String email;
    private String mob;
    private String aadharno;
    private String role;

    public BankStaff(String email, String mob, String aadharno, String role) {
        this.email = email;
        this.mob = mob;
        this.aadharno = aadharno;
        this.role = role;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankStaff bankStaff = (BankStaff) o;
        return Objects.equals(email, bankStaff.email) || Objects.equals(mob, bankStaff.mob) || Objects.equals(aadharno, bankStaff.aadharno);
    }


}
