/*
 * Created on 20-May-2005
 *
 */
package uk.co.indigo.play.patterns.bridge;

/**
 * @author milbuw
 *
 */
public class AbstractionFactory {
	
	public static final int ABS_A = 0;
	
	private AbstractionFactory() {}
	
	public static Abstraction getAbstraction(int type) {
		Abstraction abstraction;
		Implementation implementation;
		switch (type) {
			case ABS_A :
				implementation =  ImplementationFactory.getImplementation(type);
				abstraction = new RefinedAbstraction(implementation);
				break;
			default : 
				throw new IllegalArgumentException(
						"A valid abstraction type must be specified.");
		}
		return abstraction;
	}

}
