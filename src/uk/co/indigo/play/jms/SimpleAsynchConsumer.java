package uk.co.indigo.play.jms;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleAsynchConsumer {

	public static void main(String[] args) {

		// Use a topic - publish and subscribe model
		String dest = "jms/Topic";
		Context jndiContext = null;
		try {
			jndiContext = new InitialContext();
			
		}
		catch (NamingException ne) {
			System.err.println("Could not create JNDI context : " + ne.toString());
			System.exit(1);
		}
		ConnectionFactory connectionFactory = null;
		Destination destination = null;
		try {
			connectionFactory = (ConnectionFactory)jndiContext.lookup("jms/ConnectionFactory");
			destination = (Destination)jndiContext.lookup(dest);
		}
		catch (NamingException ne) {
			System.err.println("JNDI lookup failed : " + ne.toString());
			System.exit(2);
		}
		Connection connection = null;
		Session session = null;
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		}
		catch (JMSException jmse) {
			System.err.println("JMS connection failed : " + jmse.toString());
			System.exit(3);
		}
		MessageConsumer messageConsumer = null;
		try {
			messageConsumer = session.createConsumer(destination);
		}
		catch (JMSException jmse) {
			System.err.println("Failed to acquire message consumer : " + jmse.toString());
			System.exit(4);
		}
		try {
			connection.start();
		}
		catch (JMSException jmse) {
			System.err.println("Failed to start connection : " + jmse.toString());
			System.exit(5);
		}
		try {
		MessageListener messageListener = new TextListener();
		messageConsumer.setMessageListener(messageListener);
		}
		catch (JMSException jmse) {
			System.err.println("Failed to register listener : " + jmse.toString());
			System.exit(6);
		}
		System.out.println("Type 'Q' or 'q' to quit.");
		InputStreamReader isr = new InputStreamReader(System.in);
		char answer = ' ';
		while ((answer != 'Q') && (answer != 'q')) {
			try {
				answer = (char)isr.read();
			}
			catch (IOException ioe) {
				System.err.println("Failed to read input : " + ioe.toString());
			}
		}
		try {
			connection.close();
		}
		catch (JMSException jmse) {
			System.err.println("Failed to close connection : " + jmse.toString());
			System.exit(7);
		}
		finally {
			System.exit(0);
		}
	}

}
