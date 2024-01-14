import body.CourierBody;
import client.CourierClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreate {
    private static CourierBody courierBody;
    String login = RandomStringUtils.random(7);
    String password = RandomStringUtils.random(7);
    String firstname = RandomStringUtils.random(7);
    private static List<String> courierIds = new ArrayList<>();

    @Before
    public void setUp() {
        courierBody = new CourierBody(new CourierClient());
    }

    @Test
    public void canCreateCourier() {
        courierBody.create(login, password, firstname)
                .assertThat().statusCode(SC_CREATED).and().body("ok", equalTo(true));
    }

    @Test
    public void duplicateCourier() {
        courierBody.create("login", "password", "firstname");
        courierBody.create("login", "password", "firstname")
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
        courierBody.create(login, password, null)
                .statusCode(SC_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @AfterClass
    public static void tearDown() {
        // Удаление всех курьеров по их ID
        for (String courierId : courierIds) {
            courierBody.delete(courierId).log().all();
            System.out.println("Курьер с ID " + courierId + " удален.");
        }

    }
}