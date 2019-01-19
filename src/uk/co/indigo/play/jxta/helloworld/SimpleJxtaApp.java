package uk.co.indigo.play.jxta.helloworld;

import net.jxta.peergroup.PeerGroup;
import net.jxta.peergroup.PeerGroupFactory;
import net.jxta.exception.PeerGroupException;

/**
 *  This is a simple example of how an application would start jxta
 */

public class SimpleJxtaApp {

    static PeerGroup netPeerGroup = null;

    public static void main(String args[]) {

        System.out.println("Starting JXTA ....");
        SimpleJxtaApp myapp = new SimpleJxtaApp();
        myapp.startJxta();

        System.out.println("Hello from JXTA group " +
                           netPeerGroup.getPeerGroupName() );
        System.out.println("  Group ID = " +
                           netPeerGroup.getPeerGroupID().toString());
        System.out.println("  Peer name = " +
                           netPeerGroup.getPeerName());
        System.out.println("  Peer ID = " +
                           netPeerGroup.getPeerID().toString());
        System.out.println( "Good Bye ....");
        myapp.stopApp();
        System.exit(0);
    }
    
    private void stopApp() {
        if (netPeerGroup != null) {
            netPeerGroup.stopApp();
        }
        else {
            // could not stop the application
            System.out.println("fatal error : group was null while attempting to stop");
            System.exit(1);
        }
    }

    private void startJxta() {
        try {
            // create and start the default JXTA NetPeerGroup
            netPeerGroup = PeerGroupFactory.newNetPeerGroup();
        } catch (PeerGroupException e) {
            // could not instantiate the group, print the stack and exit
            System.out.println("fatal error : group creation failure");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
