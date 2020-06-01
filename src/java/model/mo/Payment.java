package model.mo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Payment {
    private Long number;
    private String with;
    private String date;
    private String amount;

    private model.mo.Payment[] payment;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount() {
        this.amount = amount;
    }

    public String getWith() {
        return with;
    }

    public void setWith() {
        this.with = with;
    }

    public java.sql.Date getDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date parsed = format.parse(date);
        return new java.sql.Date(parsed.getTime());
    }

    public void setDate(String date) {
        this.date = date;
    }

    public model.mo.Payment[] getPayment() {
        return payment;
    }

    public void setPayment(model.mo.Payment[] payment) {
        this.payment = payment;
    }
}
