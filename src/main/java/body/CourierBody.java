package body;

import client.CourierClient;
import dto.CourierCreateRequest;
import dto.CourierLoginRequest;
import dto.OrderCreateRequest;
import io.restassured.response.ValidatableResponse;



public class CourierBody {
    private final CourierClient courierClient;


    public CourierBody(CourierClient courierClient) {
        this.courierClient = courierClient;

    }

    public ValidatableResponse create(String login, String password, String firstname){

        CourierCreateRequest request = new CourierCreateRequest();
        request.setLogin(login);
        request.setPassword(password);
        request.setFirstName(firstname);
        return courierClient.create(request).then();
    }
     public ValidatableResponse login(String login, String password){
        CourierLoginRequest request = new CourierLoginRequest();
        request.setLogin(login);
        request.setPassword(password);
       return courierClient.login(request).then();
    }

    public ValidatableResponse delete(String courierId) {
        return courierClient.deleteCourier(courierId).then();
    }

}
