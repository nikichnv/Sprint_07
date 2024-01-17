import courierApi.CourierBody;
import client.CourierClient;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;


public class LoginTestTest {
    private static CourierBody courierBody;
    String login = RandomStringUtils.random(7);
    String password = RandomStringUtils.random(7);
    String firstname = RandomStringUtils.random(7);
    static List<ValidatableResponse> couriersData = new ArrayList<>();
    @Before
    public void setUp() {
        courierBody = new CourierBody(new CourierClient());
    }
    @Test
    public void canLoginCourier(){
        ValidatableResponse response = courierBody.create(login, password, firstname);
        courierBody.login(login, password)
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
        courierBody.getId(login, password);
        couriersData.add(response);
        courierBody.delete(response);
    }
    @Test
    public void loginWithoutLogin(){
        courierBody.login("", password)
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message",equalTo("Недостаточно данных для входа"));

    }
    @Test
    public void loginWithoutPassword(){
        courierBody.login(login, "")
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    public void loginWrongLogin(){
        courierBody.login(login, password)
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

}
