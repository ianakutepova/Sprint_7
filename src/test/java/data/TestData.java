package data;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class TestData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    static Faker user = new Faker();
    public static final String LOGIN = user.name().lastName() + user.regexify("[0-9]{4}");
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String FIRSTNAME = user.name().firstName();

    public static String generateRandomLogin() {
        return "user" + System.currentTimeMillis();
    }

    public static String generateRandomPassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }

    public static final String KNOWN_USER = "knownUser";
    public static final String KNOWN_PASSWORD = "knownPassword";
    public static final String INCORRECT_LOGIN = "wrong_user";
    public static final String INCORRECT_PASSWORD = "wrong_password";
    public static final String NON_EXISTING_LOGIN = generateRandomLogin();
    public static final String NON_EXISTING_PASSWORD = generateRandomPassword();



}
