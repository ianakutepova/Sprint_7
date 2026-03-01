package model;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CourierModel {
    private String login;
    private String password;
    private String firstName;
}


