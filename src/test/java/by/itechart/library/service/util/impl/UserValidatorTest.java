
package by.itechart.library.service.util.impl;

import by.itechart.library.dao.api.UserDAO;
import by.itechart.library.dao.exception.DAOException;
import by.itechart.library.entity.User;
import by.itechart.library.service.exception.ValidatorException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.MockitoAnnotations.initMocks;

public class UserValidatorTest {
    @InjectMocks
    UserValidatorImpl userValidator;

    User user;

    @Mock
    UserDAO userDAO;

    @Before
    public void init() {
        initMocks(this);
        user = new User();
    }

    @Test
    public void validateAdd() throws ValidatorException {
        user.setFirstName("Egor");
        user.setLastName("Avilov");
        user.setUsername("username");
        user.setPassword("password1");
        user.setEmail("egoravilov99@gmail.com");
        user.setPhoneNumber("+375 (044) 792-0178");
        userValidator.validateAdd(user);
    }

    @Test
    public void validateUpdate() throws ValidatorException {
        user.setFirstName("Egor");
        user.setLastName("Avilov");
        user.setPassword("password1");
        user.setPhoneNumber("+375 (044) 792-0178");
        userValidator.validateUpdate(user);
    }







    @Test(expected = ValidatorException.class)
    public void validatePhoneNumber_isNull() throws ValidatorException {
        userValidator.validatePhoneNumber(user.getPhoneNumber());
    }

    @Test(expected = ValidatorException.class)
    public void validatePhoneNumber_isEmpty() throws ValidatorException {
        user.setPhoneNumber("");
        userValidator.validatePhoneNumber(user.getPhoneNumber());
    }

    @Test(expected = ValidatorException.class)
    public void validatePhoneNumber_invalidData() throws ValidatorException {
        user.setPhoneNumber("bdghfh1234567890");
        userValidator.validatePhoneNumber(user.getPhoneNumber());
    }

    @Test
    public void validatePhoneNumber_validData() throws ValidatorException {
        user.setPhoneNumber("+111 (202) 555-0125");
        userValidator.validatePhoneNumber(user.getPhoneNumber());
    }


    @Test(expected = ValidatorException.class)
    public void validateUsername_isNull() throws ValidatorException {
        userValidator.validateUsername(user.getUsername());
    }

    @Test(expected = ValidatorException.class)
    public void validateUsername_isEmpty() throws ValidatorException {
        user.setUsername("");
        userValidator.validateUsername(user.getUsername());
    }

    @Test(expected = ValidatorException.class)
    public void validateUsername_invalidData() throws ValidatorException {
        user.setUsername("1ad");
        userValidator.validateUsername(user.getUsername());
    }

    @Test(expected = ValidatorException.class)
    public void validateUsername_validData_UserDAO_CheckUsername() throws ValidatorException, DAOException {
        user.setUsername("egor-avilov");
        Mockito.doThrow(DAOException.class)
               .when(userDAO)
               .checkUsername(user.getUsername());
        userValidator.validateUsername(user.getUsername());
    }

    @Test
    public void validateUsername_validData() throws ValidatorException, DAOException {
        user.setUsername("egor-avilov");
        Mockito.doReturn(true)
               .when(userDAO)
               .checkUsername(user.getUsername());
        userValidator.validateUsername(user.getUsername());
    }


    @Test(expected = ValidatorException.class)
    public void validateEmail_isNull() throws ValidatorException {
        userValidator.validateEmail(user.getEmail());
    }

    @Test(expected = ValidatorException.class)
    public void validateEmail_isEmpty() throws ValidatorException {
        user.setEmail("");
        userValidator.validateEmail(user.getEmail());
    }

    @Test(expected = ValidatorException.class)
    public void validateEmail_invalidDataWithoutMail() throws ValidatorException {
        user.setEmail("efrgtmail.ru");
        userValidator.validateEmail(user.getEmail());
    }

    @Test(expected = ValidatorException.class)
    public void validateEmail_invalidDataWithoutDot() throws ValidatorException {
        user.setEmail("efrgt@mailru");
        userValidator.validateEmail(user.getEmail());
    }

    @Test(expected = ValidatorException.class)
    public void validateEmail_validData_UserDAO_CheckUsername() throws ValidatorException, DAOException {
        user.setEmail("efrrgdgt@mail.ru");
        Mockito.doThrow(DAOException.class)
               .when(userDAO)
               .checkEmail(user.getEmail());
        userValidator.validateEmail(user.getEmail());
    }

    @Test
    public void validateEmail_validData() throws ValidatorException, DAOException {
        user.setEmail("efrrgdgt@mail.ru");
        Mockito.doReturn(true)
               .when(userDAO)
               .checkEmail(user.getEmail());
        userValidator.validateEmail(user.getEmail());
    }


    @Test(expected = ValidatorException.class)
    public void validateFirstName_isNull() throws ValidatorException {
        userValidator.validateFirstName(user.getFirstName());
    }

    @Test(expected = ValidatorException.class)
    public void validateFirstName_isEmpty() throws ValidatorException {
        user.setFirstName("");
        userValidator.validateFirstName(user.getFirstName());
    }

    @Test(expected = ValidatorException.class)
    public void validateFirstName_isShort() throws ValidatorException {
        user.setFirstName("a");
        userValidator.validateFirstName(user.getFirstName());
    }

    @Test(expected = ValidatorException.class)
    public void validateFirstName_isLong() throws ValidatorException {
        user.setFirstName("fjerqgkbeugherogjerojgnejgnerjogre;ljgehljsarhuoehrljgouehrfehrgliaejglehrgulehrgklc");
        userValidator.validateFirstName(user.getFirstName());
    }

    @Test
    public void validateFirstName_validData() throws ValidatorException {
        user.setFirstName("Egor-aka-Avilov");
        userValidator.validateFirstName(user.getFirstName());
    }

    @Test(expected = ValidatorException.class)
    public void validateLastName_isNull() throws ValidatorException {
        userValidator.validateLastName(user.getFirstName());
    }

    @Test(expected = ValidatorException.class)
    public void validateLastName_isEmpty() throws ValidatorException {
        user.setLastName("");
        userValidator.validateLastName(user.getLastName());
    }

    @Test(expected = ValidatorException.class)
    public void validateLastName_isShort() throws ValidatorException {
        user.setLastName("a");
        userValidator.validateLastName(user.getLastName());
    }

    @Test(expected = ValidatorException.class)
    public void validateLastName_isLong() throws ValidatorException {
        user.setLastName("fjerqgkbeugherogjerojgnejgnerjogre;ljgehljsarhuoehrljgouehrfehrgliaejglehrgulehrgklc");
        userValidator.validateLastName(user.getLastName());
    }

    @Test
    public void validateLastName_validData() throws ValidatorException {
        user.setLastName("Egor-aka-Avilov");
        userValidator.validateLastName(user.getLastName());
    }

    @Test(expected = ValidatorException.class)
    public void validatePassword_isNull() throws ValidatorException {
        userValidator.validatePassword(user.getPassword());
    }

    @Test(expected = ValidatorException.class)
    public void validatePassword_isEmpty() throws ValidatorException {
        user.setPassword("");
        userValidator.validatePassword(user.getPassword());
    }

    @Test(expected = ValidatorException.class)
    public void validatePassword_isShort() throws ValidatorException {
        user.setPassword("12wqr");
        userValidator.validatePassword(user.getPassword());
    }

    @Test(expected = ValidatorException.class)
    public void validatePassword_isLong() throws ValidatorException {
        user.setPassword("12wfgthyjuilikujytgrfedrgthjukiqr");
        userValidator.validatePassword(user.getPassword());
    }

    @Test(expected = ValidatorException.class)
    public void validatePassword_WitoutNumbers() throws ValidatorException {
        user.setPassword("egoRAvilov");
        userValidator.validatePassword(user.getPassword());
    }

    @Test
    public void validatePassword_validData() throws ValidatorException {
        user.setPassword("egoRAvilov1");
        userValidator.validatePassword(user.getPassword());
    }
}
