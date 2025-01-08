package model.service;

public class ExistingOrderException extends Exception {
	private static final long serialVersionUID = 1L;

	public ExistingOrderException() {
		super();
	}

	public ExistingOrderException(String arg0) {
		super(arg0);
	}
}
