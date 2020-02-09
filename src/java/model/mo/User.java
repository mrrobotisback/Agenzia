package model.mo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User {

  private Long userId;
  private String username;
  private String password;
  private String firstname;
  private String surname;
  private String birthday;
  private String sex;
  private String via;
  private String numero;
  private String citta;
  private String provincia;
  private String cap;
  private String phone;
  private String email;
  private String work;
  private String cf;
  
  /* 1:N */
  private User[] user;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getBirthday() throws ParseException {
    return new SimpleDateFormat("yyyy/MM/dd").parse(birthday);
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getCitta() {
    return citta;
  }

  public void setCitta(String citta) {
    this.citta = citta;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getCap() {
    return cap;
  }

  public void setCap(String cap) {
    this.cap = cap;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getWork() {
    return work;
  }

  public void setWork(String work) {
    this.work = work;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public User[] getUser() {
    return user;
  }

  public void setUser(User[] user) {
    this.user = user;
  }

  public User getUser(int index) {
    return this.user[index];
  }

  public void setUser(int index, User user) {
    this.user[index] = user;
  }

}
