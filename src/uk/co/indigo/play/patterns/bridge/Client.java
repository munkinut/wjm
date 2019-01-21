/*
 * Created on 19-May-2005
 *
 */
package uk.co.indigo.play.patterns.bridge;

/**
 * @author milbuw
 *
 */
public class Client {

	public Client() {
		super();
	}

	public static void main(String[] args) {
		Abstraction a = AbstractionFactory.getAbstraction(AbstractionFactory.ABS_A);
		try {
			a.operation();
		}
		catch (NoImplementationException nie) {
			System.err.println("There was no implementation specified for this abstraction.");
		}
	}
}
