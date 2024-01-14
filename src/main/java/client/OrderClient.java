package client;

import dto.OrderCreateRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class OrderClient extends RestClient {

    private OrderCreateRequest orderCreateRequest;

    public void setOrder(OrderCreateRequest orderCreateRequest) {
        this.orderCreateRequest = orderCreateRequest;
    }

    @Step("Создать заказ")
    public Response create() {
        return defaultRestSpecification()
                .body(orderCreateRequest)
                .when()
                .post("/api/v1/orders");
    }
    @Step("Получить список заказов")
    public  Response getOrders(){
        return defaultRestSpecification()
                .get("/api/v1/orders");
    }

}
