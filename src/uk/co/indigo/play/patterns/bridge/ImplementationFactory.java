/*
 * Created on 20-May-2005
 *
 */
package uk.co.indigo.play.patterns.bridge;

/**
 * @author milbuw
 *
 */
public class ImplementationFactory {

	public static final int IMPL_A = 0;
	
	private ImplementationFactory() {}
	
	public static Implementation getImplementation(int type) {
		Implementation implementation;
		switch (type) {
			case IMPL_A :
				implementation = new ConcreteImplementationA();
				break;
			default : 
				throw new IllegalArgumentException(
						"A valid implementation type must be specified.");
		}
		return implementation;
	}

}
