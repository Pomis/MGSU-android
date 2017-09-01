package ru.lodmisis.mgsu.api;

import com.rengwuxian.materialedittext.MaterialEditText;

import lombok.Builder;

public class RegBody {
    transient private static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";

    public String firstName;

    public String lastName;

    public String middleName;

    public String email;

    public String password;

    public String phone;

    public boolean preValidate(MaterialEditText email,
                               MaterialEditText password,
                               MaterialEditText phone,
                               MaterialEditText dateOfBirth,
                               MaterialEditText yearOfGraduation) {
        boolean result = true;
        if (!email.getText().toString().matches(EMAIL_PATTERN)) {
            email.setError("Нужно ввести email");
            result = false;
        }
        if (password.getText().length() < 6 || password.getText().length() > 100) {
            password.setError("Пароль неверной длины");
            result = false;
        }
        if (phone.getText().length() < 11 || phone.getText().length() > 12) {
            phone.setError("Введите корректный номер телефона");
            result = false;
        }
        if (dateOfBirth.getText().toString().split(".").length != 3 &&
                dateOfBirth.getText().toString().split("/").length != 3) {
            result = false;
        }
        if (yearOfGraduation.getText().toString().length() != 4) {
            result = false;
        }

        if (result) {
            this.email = email.getText().toString();
            this.password = password.getText().toString();
            this.phone = phone.getText().toString();
        }
        return result;
    }
}
