package uk.co.indigo.play.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class NonBlockingTimeServer {
	
	public static final int DEFAULT_PORT = 8900;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		NonBlockingTimeServer nbts = new NonBlockingTimeServer();
		nbts.acceptConnections(NonBlockingTimeServer.DEFAULT_PORT);
	}
	
	public void acceptConnections(int port) {
		try {
			
			// set up server socket channel
			
			ServerSocketChannel channel = ServerSocketChannel.open();
			channel.configureBlocking(false);
			
			// set up socket address and bind
			
			InetSocketAddress socketAddress = new InetSocketAddress(port);
			channel.socket().bind(socketAddress);
			
			// set up selector and register with channel
			
			Selector selector = SelectorProvider.provider().openSelector();
			channel.register(selector, SelectionKey.OP_ACCEPT);
			
			// wait for an event
			System.out.println("Waiting for connections on port " + port + " ...");
			while ((selector.select()) > 0) {
				// get a set of event keys and iterate
				Set readyKeys = selector.selectedKeys();
				Iterator i = readyKeys.iterator();
				while (i.hasNext()) {
					SelectionKey key = (SelectionKey)i.next();
					if (key.isAcceptable()) {
						i.remove();
						ServerSocketChannel nextChannel = (ServerSocketChannel)key.channel();
						Socket s = nextChannel.accept().socket();
						String remoteHost = s.getInetAddress().getHostName();
						int remotePort = s.getPort();
						System.out.println("Connection from " + remoteHost + ":" + remotePort);
						PrintWriter out = new PrintWriter(s.getOutputStream(), true);
						Date date = new Date();
						out.println(date);
						out.close();
					}
				}
				System.out.println("Waiting for connections on port " + port + " ...");
			}
		}
		catch (IOException ioe) {
			System.err.println(ioe);
		}
	}

}
