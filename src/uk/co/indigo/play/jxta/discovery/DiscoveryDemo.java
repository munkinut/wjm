package uk.co.indigo.play.jxta.discovery;

import java.util.Enumeration;

import net.jxta.discovery.DiscoveryEvent;
import net.jxta.discovery.DiscoveryListener;
import net.jxta.discovery.DiscoveryService;
import net.jxta.exception.PeerGroupException;
import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;
import net.jxta.protocol.DiscoveryResponseMsg;
import net.jxta.protocol.PeerAdvertisement;

public class DiscoveryDemo implements Runnable, DiscoveryListener {

    static PeerGroup netPeerGroup  = null;
    private DiscoveryService discovery;

   //start the JXTA platform
    private void startJxta() {
        try {
            netPeerGroup = PeerGroupFactory.newNetPeerGroup();
        } catch ( PeerGroupException e) {
            // could not instantiate the group, print the stack and exit
            System.out.println("fatal error : group creation failure");
            e.printStackTrace();
            System.exit(1);
        }

        // Get the discovery service from our peer group
        discovery = netPeerGroup.getDiscoveryService();
    }

    /**
     * This thread loops forever discovering peers
     * every minute, and displaying the results.
     */

    public void run() {
        try {
            // Add ourselves as a DiscoveryListener for DiscoveryResponse events
            discovery.addDiscoveryListener(this);
            while (true) {
                System.out.println("Sending a Discovery Message");
                // look for any peer
                discovery.getRemoteAdvertisements(null, DiscoveryService.PEER,
                                                  null, null, 5);
                // wait a bit before sending next discovery message
                try {
                    Thread.sleep(60 * 1000);
                } catch(Exception e) {}

            } //end while
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * by implementing DiscoveryListener we must define this method
     * to deal to discovery responses
     */

    public void discoveryEvent(DiscoveryEvent ev) {

        DiscoveryResponseMsg res = ev.getResponse();
        String name = "unknown";

        // Get the responding peer's advertisement
        PeerAdvertisement peerAdv = res.getPeerAdvertisement();
        // some peers may not respond with their peerAdv
        if (peerAdv != null) { 
            name = peerAdv.getName();
        }

        System.out.println("Got a Discovery Response [" +
                           res.getResponseCount() + " elements] from peer: " +
                           name);
        //printout each discovered peer
        PeerAdvertisement adv = null;
        Enumeration en = res.getAdvertisements();
        if (en != null ) {
            while (en.hasMoreElements()) {
                adv = (PeerAdvertisement) en.nextElement();
                System.out.println (" Peer name = " + adv.getName());
            }
        }
    }

    static public void main(String args[]) {
        DiscoveryDemo myapp  = new DiscoveryDemo();
        myapp.startJxta();
        myapp.run();
    }
}
