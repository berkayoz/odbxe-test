package com.trlogic.odxetest;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String exception) {
		super(exception);
	}
}
