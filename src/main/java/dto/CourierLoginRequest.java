package dto;

import lombok.Data;

@Data
public class CourierLoginRequest {
    private String login;
    private String password;
    private int id;

}