package com.actionbazaar.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.actionbazaar.domain.Bid;
import com.actionbazaar.domain.BidRepository;
import com.actionbazaar.infrastructure.database.DefaultBidRepository;

@RunWith(Arquillian.class)
public class BidServiceTest {

    private static Long bidId;

    @Deployment
    public static WebArchive createDeployment() {
        return ShrinkWrap
                .create(WebArchive.class, "actionbazaar-service-test.war")
                .addClasses(BidService.class, DefaultBidService.class,
                        Profiled.class, ProfilingInterceptor.class,
                        BidRepository.class, DefaultBidRepository.class, Bid.class)
                .addAsWebInfResource("test-persistence-web.xml", "web.xml")
                .addAsWebInfResource("jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
                .addAsWebInfResource("test-persistence-beans.xml", "beans.xml")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml");
    }

    @EJB
    private BidService bidService;

    @Test
    @InSequence(1)
    public void testAddBid() {
        // Save a new bid.
        Bid bid = new Bid();
        bid.setBidder("rrahman");
        bid.setItem("Test item");
        bid.setAmount(100.50);

        bidService.addBid(bid);

        // Make sure it was correctly saved.
        bidId = bid.getId();

        bid = bidService.getBid(bidId);

        assertEquals("rrahman", bid.getBidder());
        assertEquals("Test item", bid.getItem());
        assertEquals(new Double(100.50), bid.getAmount());
    }

    @Test
    @InSequence(2)
    public void testUpdateBid() {
        // Update bid.
        Bid bid = bidService.getBid(bidId);
        bid.setAmount(101.50);
        bidService.updateBid(bid);

        // Make sure bid was updated.
        bid = bidService.getBid(bidId);

        assertEquals("rrahman", bid.getBidder());
        assertEquals("Test item", bid.getItem());
        assertEquals(new Double(101.50), bid.getAmount());
    }

    @Test
    @InSequence(3)
    public void testDeleteBid() {
        Bid bid = bidService.getBid(bidId);
        bidService.deleteBid(bid);

        assertNull(bidService.getBid(bidId));
    }
}
