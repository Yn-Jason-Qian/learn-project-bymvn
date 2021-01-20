package effctiveJava.section11;

import java.io.Serializable;

public class Entity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8393357978442429190L;

	private int field1;
	
	private int field2;

	@Override
	public String toString() {
		return String.format("Entity [field1=%s, field2=%s]", field1, field2);
	}
	
}
