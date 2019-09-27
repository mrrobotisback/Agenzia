package model.mo;

public class User {

  private Long userId;
  private String username;
  private String password;
  private String firstname;
  private String surname;
  private boolean deleted;
  
  /* 1:N */
  private Travel[] travels;

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

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }

  public Travel[] getContacts() {
    return travels;
  }

  public void setContacts(Travel[] contacts) {
    this.travels = contacts;
  }

  public Travel getContacts(int index) {
    return this.travels[index];
  }

  public void setContacts(int index, Travel contacts) {
    this.travels[index] = contacts;
  }

}
