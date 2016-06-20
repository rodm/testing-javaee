package com.actionbazaar.interfaces.web;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;

// Ran into weird bugs trying to run this on the client.
// Probably another Maven dependency melee.
@RunWith(Arquillian.class)
public class AlertServletTest {

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "actionbazaar-servlet-test.war")
                .addClass(AlertServlet.class);
    }

    @Test
    public void testGetAlerts() {
        Client client = ClientBuilder.newClient();

        // Get account balance
        JsonObject response = client
                .target("http://localhost:8080/actionbazaar-servlet-test/alerts")
                .queryParam("user_id", "1111").request("application/json")
                .get(JsonObject.class);
        // TODO Assert more of the content.
        assertEquals(5, response.getJsonArray("alerts").size());
    }
}
