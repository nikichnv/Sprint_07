import body.CourierBody;
import client.CourierClient;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;


public class LoginTest {
    private static CourierBody courierBody;
    String login = RandomStringUtils.random(7);
    String password = RandomStringUtils.random(7);
    String firstname = RandomStringUtils.random(7);
    @Before
    public void setUp() {
        courierBody = new CourierBody(new CourierClient());
    }
    @Test
    public void canLoginCourier(){
        courierBody.create(login, password, firstname);
        courierBody.login(login, password)
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
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
