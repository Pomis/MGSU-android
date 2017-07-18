package ru.lodmisis.mgsu.api;

/**
 * Created by romanismagilov on 12.07.17.
 */

public class AuthBody {

    public AuthBody(String email, String password) {
        Email = email;
        Password = password;
    }

    private String Email;
    private String Password;
}
