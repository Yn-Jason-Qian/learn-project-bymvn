package algorithm;

public class IntegerFactory extends ObjectFactory<Integer> {

	@Override
	public Integer createObj() {
		return Util.nextInt();
	}

}
