package Models;

import java.util.Date;

public class TransactionStatement {
    private long accountno;
    private int transactionID;
    private String Remark;
    private Date date;

    public TransactionStatement(long accountno, int transactionID, String remark, Date date) {
        this.accountno = accountno;
        this.transactionID = transactionID;
        Remark = remark;
        this.date = date;
    }

    public long getAccountno() {
        return accountno;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getRemark() {
        return Remark;
    }

    public Date getDate() {
        return date;
    }
}
