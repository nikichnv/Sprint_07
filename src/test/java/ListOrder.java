import client.OrderClient;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;


public class ListOrder {

    private static OrderClient orderClient;
    @Before
    public void setUp(){orderClient = new OrderClient();}
    @Test
    public void getOrders(){
        orderClient.getOrders()
                .then()
                .statusCode(SC_OK)
                .and()
                .assertThat().body("orders", notNullValue());
    }

}
