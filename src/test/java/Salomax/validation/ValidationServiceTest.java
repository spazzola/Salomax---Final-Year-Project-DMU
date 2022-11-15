package Salomax.validation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ValidationServiceTest {

    private ValidationService validationService;

    @Before
    public void setUp() {
        validationService = new ValidationService();
    }

    @Test
    public void validateStringValidDataShouldPass() {
        //given
        String name = "Ab";

        //when
        boolean isValid = validationService.validateString(name);

        //then
        assertTrue(isValid);
    }

    @Test
    public void validateStringDataAsNullShouldReturnFalse() {
        //given
        String name = null;

        //when
        boolean isValid = validationService.validateString(name);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validateStringDataAsEmptyStringShouldReturnFalse() {
        //given
        String name = "";

        //when
        boolean isValid = validationService.validateString(name);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validateStringDataAsEmptyStringWithWhiteSpaceShouldReturnFalse() {
        //given
        String name = " ";

        //when
        boolean isValid = validationService.validateString(name);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePhoneNumberValidDataShouldPass() {
        //given
        String phoneNumber = "1234567890";

        //when
        boolean isValid = validationService.validatePhoneNumber(phoneNumber);

        //then
        assertTrue(isValid);
    }

    @Test
    public void validatePhoneNumberDataAsNullShouldReturnFalse() {
        //given
        String phoneNumber = null;

        //when
        boolean isValid = validationService.validatePhoneNumber(phoneNumber);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePhoneNumberDataTooShortReturnFalse() {
        //given
        String phoneNumber = "12345678";

        //when
        boolean isValid = validationService.validatePhoneNumber(phoneNumber);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePhoneNumberDataTooLongReturnFalse() {
        //given
        String phoneNumber = "1234567890123";

        //when
        boolean isValid = validationService.validatePhoneNumber(phoneNumber);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validateEmailValidDataShouldPass() {
        //given
        String email = "m@x.c";

        //when
        boolean isValid = validationService.validateEmail(email);

        //then
        assertTrue(isValid);
    }

    @Test
    public void validateEmailDataAsNullShouldReturnFalse() {
        //given
        String email = null;

        //when
        boolean isValid = validationService.validateEmail(email);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validateEmailDataEmptyStringShouldReturnFalse() {
        //given
        String email = "";

        //when
        boolean isValid = validationService.validateEmail(email);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validateEmailDataEmptyStringWhiteSpaceShouldReturnFalse() {
        //given
        String email = " ";

        //when
        boolean isValid = validationService.validateEmail(email);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validateEmailDataWithIncorrectFormatShouldReturnFalse() {
        //given
        String email = "m@l";

        //when
        boolean isValid = validationService.validateEmail(email);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePasswordValidDataShouldPass() {
        //given
        String password = "Ab!1c";

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertTrue(isValid);
    }

    @Test
    public void validatePasswordDataAsNullShouldReturnFalse() {
        //given
        String password = null;

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePasswordDataTooShortShouldReturnFalse() {
        //given
        String password = "A!1c";

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePasswordDataWithoutDigitShouldReturnFalse() {
        //given
        String password = "A!bcD";

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePasswordDataWithoutSpecialCharacterShouldReturnFalse() {
        //given
        String password = "A1bcD";

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePasswordDataWithoutUpperCaseShouldReturnFalse() {
        //given
        String password = "a!1bc";

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertFalse(isValid);
    }

    @Test
    public void validatePasswordDataWithoutLowerCaseShouldReturnFalse() {
        //given
        String password = "A!1BC";

        //when
        boolean isValid = validationService.validatePassword(password);

        //then
        assertFalse(isValid);
    }

}