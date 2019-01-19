/*
 * Created on 19-May-2005
 *
 */
package uk.co.indigo.play.patterns.bridge;

/**
 * @author milbuw
 *
 */
public class ConcreteImplementationA implements Implementation {

	public ConcreteImplementationA() {
		super();
	}

	public Object operationImpl(Object o) {
		System.out.println("ConcreteImplementationA.operationImpl(o)");
		return o;
	}

	public Object operationImpl() {
		System.out.println("ConcreteImplementationA.operationImpl()");
		return null;
	}

}
