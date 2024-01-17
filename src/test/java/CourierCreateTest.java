import courierApi.CourierBody;
import client.CourierClient;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {
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
    public void canCreateCourier() {
        ValidatableResponse response = courierBody.create(login, password, firstname);
                response.assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
        courierBody.getId(login, password);
        couriersData.add(response);
    }

    @Test
    public void duplicateCourier() {
        ValidatableResponse response = courierBody.create("login", "password", "firstname")
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    public void courierWithoutLogin() {
        courierBody.create(null, "password", "firstname")
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void courierWithoutPassword() {
        courierBody.create(login, null, "firstname")
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void courierWithoutFirstname() {
        ValidatableResponse response = courierBody.create(login, password, null)
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
        courierBody.getId(login, password);
        couriersData.add(response);
    }

    @AfterClass
    public static void tearDown() {

        for (ValidatableResponse response : couriersData) {
            courierBody.delete(response);
        }
        couriersData.clear();
    }
}