package model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourierModel {

    @JsonProperty("login")
    private String courierLogin;
    @JsonProperty("password")
    private String courierPassword;
    @JsonProperty("firstName")
    private String courierFirstName;

    }





