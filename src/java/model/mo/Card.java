package model.mo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Card {
    private Long number;
    private String holder;
    private int cvv;
    private String expiration;
    private Long userId;
    private Card[] card;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public java.sql.Date getExpiration() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsed = format.parse(expiration);
        return new java.sql.Date(parsed.getTime());
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Card[] getCard() {
        return card;
    }

    public void setCard(Card[] card) {
        this.card = card;
    }
}
