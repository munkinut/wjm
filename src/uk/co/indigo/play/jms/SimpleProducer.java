package uk.co.indigo.play.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SimpleProducer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		int num_msgs = 3;
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
		MessageProducer messageProducer = null;
		try {
			messageProducer = session.createProducer(destination);
		}
		catch (JMSException jmse) {
			System.err.println("Failed to acquire message producer : " + jmse.toString());
			System.exit(4);
		}
		TextMessage textMessage = null;
		try {
			textMessage = session.createTextMessage();
		}
		catch (JMSException jmse) {
			System.err.println("Failed to acquire text message : " + jmse.toString());
			System.exit(5);
		}
		try {
			for (int i = 0; i < num_msgs; i++) {
				textMessage.setText("This is message " + (i+1));
				System.out.println("Sending message : " + textMessage.getText());
				messageProducer.send(textMessage);
			}
			messageProducer.send(session.createMessage());
		}
		catch (JMSException jmse) {
			System.err.println("Failed to send text message : " + jmse.toString());
			System.exit(6);
		}
		finally {
			if (connection != null) {
				try {
					connection.close();
				}
				catch (JMSException jmse) {}
			}
		}
	}

}
