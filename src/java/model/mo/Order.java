package model.mo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Order {

    private Long number;
    private Double total;
    private String date;
    private Long userId;
    private String with;

    private Order[] order;

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
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

    public Long getUserId() {
        return userId;
    }

    public Long getOrderNumber() {
        return number;
    }

    public void setOrderNumber(Long number) {
        this.number = number;
    }

    public void setUserId (Long userId) {
        this.userId = userId;
    }


}
