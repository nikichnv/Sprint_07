package client;

import dto.CourierCreateRequest;
import dto.CourierLoginRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class CourierClient extends RestClient {
    @Step("Создать курьера")
    public Response create(CourierCreateRequest courierCreateRequest) {
        return defaultRestSpecification()
                .body(courierCreateRequest)
                .when()
                .post("/api/v1/courier");
    }
    @Step("Авторизоваться курьером")
    public Response login(CourierLoginRequest courierLoginRequest){
        return defaultRestSpecification()
                .body(courierLoginRequest)
                .when()
                .post("api/v1/courier/login");
    }

    @Step("Удалить курьера")
    public Response deleteCourier(String courierId) {
        return defaultRestSpecification()
                .when()
                .delete("/api/v1/courier/{id}", courierId);
    }

}
