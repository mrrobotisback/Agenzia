package model.mo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Order {

    private Long number;
    private String total;
    private String date;

    private Order[] order;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal() {
        this.total = total;
    }

    public java.sql.Date getDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date parsed = format.parse(date);
        return new java.sql.Date(parsed.getTime());
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Order[] getOrder() {
        return order;
    }

    public void setOrder(Order[] order) {
        this.order = order;
    }


}
