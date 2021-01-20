package jmx.service;

public class SimpleServie implements SimpleServiceMBean{

	@Override
	public String getMessage() {
		return "hello world";
	}

}
