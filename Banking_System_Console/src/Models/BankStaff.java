package Models;

import java.util.Objects;

public class BankStaff {
    private String name;
    private String email;
    private String mob;
    private String aadharno;
    private String role;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMob() {
        return mob;
    }

    public String getAadharno() {
        return aadharno;
    }

    public String getRole() {
        return role;
    }

    public BankStaff(String email, String mob, String aadharno, String role, String name) {
        this.email = email;
        this.mob = mob;
        this.aadharno = aadharno;
        this.role = role;
        this.name=name;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BankStaff bankStaff = (BankStaff) o;
        return Objects.equals(email, bankStaff.email) || Objects.equals(mob, bankStaff.mob) || Objects.equals(aadharno, bankStaff.aadharno);
    }


}
