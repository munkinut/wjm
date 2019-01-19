/*
 * Created on 19-May-2005
 *
 */
package uk.co.indigo.play.patterns.bridge;

/**
 * @author milbuw
 *
 */
public abstract class Abstraction {
	
	protected Implementation implementation;

	public Abstraction() {
		implementation = null;
	}
	
	public Abstraction(Implementation i) {
		implementation = i;
	}
	
	public Object operation() throws NoImplementationException {
		if (implementation == null) throw new NoImplementationException();
		return implementation.operationImpl();
	}
	
	public Object operation(Object o) throws NoImplementationException {
		if (implementation == null) throw new NoImplementationException();
		return implementation.operationImpl(o);
	}
	
}
