package uk.co.indigo.play.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleConsumer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String dest = "jms/Queue";
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
			TextMessage textMessage = null;
			while (true) {
				//System.out.println("Listening...");
				Message genericMessage = messageConsumer.receive(1);
				if (genericMessage != null) {
					if (genericMessage instanceof TextMessage) {
						textMessage = (TextMessage)genericMessage;
						System.out.println("BING BONG Receiving message : " + textMessage.getText());
					}
					else {
						//System.out.println("Not a TextMessage");
						//break;
						connection.close();
						break;
						//System.exit(0);
					}
				}
			}
		}
		catch (JMSException jmse) {
			System.err.println("Failed to consume message : " + jmse.toString());
			System.exit(6);
		}
	}

}
