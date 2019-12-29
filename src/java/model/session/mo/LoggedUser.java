package model.session.mo;

/**
 *
 * @author Mr. Robot
 */
public class LoggedUser {

    private Long userId;
    private String firstname;
    private String surname;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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


}
