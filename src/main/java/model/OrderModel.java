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
public class OrderModel {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("address")
    private String address;
    @JsonProperty("metroStation")
    private String metroStation;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("rentTime")
    private Number rentTime;
    @JsonProperty("deliveryDate")
    private String deliveryDate;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("color")
    private String[] color;

    public OrderModel(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
    }
}