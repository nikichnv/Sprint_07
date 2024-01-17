import client.OrderClient;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;


public class ListOrderTest {

    private static OrderClient orderClient;
    @Before
    public void setUp(){orderClient = new OrderClient();}
    @Test
    public void getOrders(){
        orderClient.getOrders()
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue())
                .body("orders", hasSize(greaterThan(0)));
    }

}
