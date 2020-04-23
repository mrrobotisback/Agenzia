package services.password;

import services.password.BCrypt;

public class Password {


    public String hashPassword(String password_plaintext) {
        // Define the BCrypt workload to use when generating password hashes. 10-31 is a valid value.
        int workload = 12;
        String salt = BCrypt.gensalt(workload);

        return(BCrypt.hashpw(password_plaintext, salt));
    }

    public boolean checkPassword(String password_plaintext, String stored_hash) {
        boolean password_verified = false;

        if(null == stored_hash || !stored_hash.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        password_verified = BCrypt.checkpw(password_plaintext, stored_hash);

        return(password_verified);
    }

}