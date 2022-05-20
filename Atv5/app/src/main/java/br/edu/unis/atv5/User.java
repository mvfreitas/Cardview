package br.edu.unis.atv5;

public class User {

    private long id;
    private String user;
    private String password;

    public User(long id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
