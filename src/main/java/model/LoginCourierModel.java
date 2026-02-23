package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
//@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)

public class LoginCourierModel {
    private String courierLogin;
    private String courierPassword;


    public LoginCourierModel(String courierLogin, String courierPassword) {
        this.courierLogin = courierLogin;
        this.courierPassword = courierPassword;
    }


}
