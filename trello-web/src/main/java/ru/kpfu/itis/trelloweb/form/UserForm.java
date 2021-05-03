package ru.kpfu.itis.trelloweb.form;

import lombok.Data;
import ru.kpfu.itis.trelloimpl.validation.ValidPassword;
import ru.kpfu.itis.trelloimpl.validation.ValidPasswords;
import ru.kpfu.itis.trelloimpl.validation.ValidUniqEmail;

import javax.validation.constraints.Email;

/**
 * @author Roman Leontev
 * 17:16 02.04.2021
 * group 11-905
 */

@Data
@ValidPasswords(
        message = "{errors.invalid.passwords}",
        password = "password",
        confirmPassword = "confirmPassword"
)
public class UserForm {

    private String firstName;
    private String secondName;

    @Email(message = "{errors.incorrect.email}")
    @ValidUniqEmail(message = "{errors.repeated.email}")
    private String email;

    @ValidPassword(message = "{errors.invalid.password}")
    private String password;

    private String confirmPassword;
}
