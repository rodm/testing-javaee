package com.actionbazaar.interfaces.web;

import com.actionbazaar.application.BidService;
import com.actionbazaar.application.DefaultBidService;
import com.actionbazaar.domain.Bid;
import com.actionbazaar.domain.BidRepository;
import com.actionbazaar.infrastructure.database.DefaultBidRepository;
import java.net.URL;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

@RunWith(Arquillian.class)
public class AddBidTest {

    @Drone
    WebDriver browser;

    @ArquillianResource
    URL contextPath;

    @Deployment(testable = false)
    public static Archive<?> createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "actionbazaar-jsf-test.war")
                .addClasses(BidService.class, DefaultBidService.class,
                        BidRepository.class, DefaultBidRepository.class, Bid.class,
                        AddBid.class)
                .addAsWebInfResource("test-web.xml", "web.xml")
                .addAsWebInfResource("jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
                .addAsWebResource("add_bid.xhtml", "add_bid.xhtml")
                .addAsWebResource("confirm_add_bid.xhtml", "confirm_add_bid.xhtml")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml",
                        "META-INF/persistence.xml");
    }

    @Test
    public void testAddBid() {
        browser.navigate().to(contextPath + "add_bid.jsf");

        browser.findElement(By.id("bidForm:itemTextField")).sendKeys("Test item");
        browser.findElement(By.id("bidForm:bidderTextField")).sendKeys("rrahman");
        browser.findElement(By.id("bidForm:amountTextField")).sendKeys("50.25");

        browser.findElement(By.id("bidForm:addBidButton")).click();

        assertEquals("Test item", browser.findElement(By.id("itemField")).getText());
        assertEquals("rrahman", browser.findElement(By.id("bidderField")).getText());
        assertEquals("50.25", browser.findElement(By.id("amountField")).getText());
    }
}
