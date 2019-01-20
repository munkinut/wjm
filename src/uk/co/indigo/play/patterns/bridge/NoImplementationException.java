/*
 * Created on 20-May-2005
 *
 */
package uk.co.indigo.play.patterns.bridge;

/**
 * @author milbuw
 *
 */
public class NoImplementationException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoImplementationException() {
		super();
	}

	public NoImplementationException(String arg0) {
		super(arg0);
	}

	public NoImplementationException(Throwable arg0) {
		super(arg0);
	}

	public NoImplementationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
