package com.miguel_santos.notinstagram.common.model;

public class UserAuth {

    private String email;
    private String password;

    public UserAuth() {}

    public UserAuth(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Validar se o usuário é único nesse banco.
    public String getUUID() {
        return String.valueOf(hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAuth userAuth = (UserAuth) o;

        if (email != null ? !email.equals(userAuth.email) : userAuth.email != null) return false;
        return password != null ? password.equals(userAuth.password) : userAuth.password == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

}
