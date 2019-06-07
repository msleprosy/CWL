package com.epam.cwlhub.entities.user;

import java.util.Objects;

public class UserEntity {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserType userType;
    private boolean banned;

    public UserEntity() {
    }

    public UserEntity(Builder builder) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.banned = banned;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id
                && banned == that.banned
                && firstName.equals(that.firstName)
                && lastName.equals(that.lastName)
                && email.equals(that.email)
                && password.equals(that.password)
                && userType == that.userType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, userType, banned);
    }

    @Override
    public String toString() {
        return "UserEntity{"
                + "id=" + id
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", password='" + password + '\''
                + ", userType=" + userType
                + ", banned=" + banned
                + '}';
    }

    public static class Builder {

        private long id;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private UserType userType;
        private boolean banned;

        public Builder(String email, String password) {
            if (email == null || password == null) {
                throw new IllegalArgumentException("mail and passw can not be null");
            }
            this.email = email;
            this.password = password;
        }

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withUserType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public Builder withBanned(boolean banned) {
            this.banned = banned;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}





