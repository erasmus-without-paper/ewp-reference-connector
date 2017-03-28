package eu.erasmuswithoutpaper.common.control;

import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestClientTest {
    
    RestClient restClient;
    
    @Before
    public void setUp() {
        EwpKeyStore keystoreController = mock(EwpKeyStore.class);
        restClient = new RestClient();
        restClient.keystoreController = keystoreController;
    }
    
    @After
    public void tearDown() {
    }

    @Test
    @Ignore
    public void testClient() {
        when(restClient.keystoreController.isSuccessfullyInitiated()).thenReturn(false);
        restClient.createClient();

        String url = "https://ewp-test.its.umu.se/ewp-reference-connector/rest/echo?echo=test";
        Response response = restClient.client().target(url).request().get();
        String answer = response.readEntity(String.class);
        System.out.println(answer);
    }

}
