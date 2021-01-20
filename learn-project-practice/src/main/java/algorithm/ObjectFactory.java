package algorithm;

public abstract class ObjectFactory<T extends Comparable<? super T>> {

	public abstract T createObj();
	
}
