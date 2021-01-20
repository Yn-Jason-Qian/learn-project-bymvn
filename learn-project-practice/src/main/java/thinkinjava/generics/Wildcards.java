package thinkinjava.generics;

/*
 * 通配符
 * 
 */
public class Wildcards {

	static void rawArgs(Holder holder, Object arg) {
		//warning: Type safety: The method set(Object) belongs to the raw type Holder. References to generic type Holder<T> should be parameterized
		holder.set(arg);
		holder.set(new Wildcards());
		
		Object obj = holder.get();
	}
	
	static void unboundedArgs(Holder<?> holder, Object arg) {
		//error : The method set(capture#1-of ?) in the type Holder<capture#1-of ?> is not applicable for the arguments (Object)
		//holder.set(arg);
		
		Object obj = holder.get();
	}
	
	static <T> T exact1(Holder<T> holder) {
		T t = holder.get();
		return t;
	}
	
	static <T> T exact2(Holder<T> holder, T arg) {
		holder.set(arg);
		T t = holder.get();
		return t;
	}
	
	static <T> T wildSubType(Holder<? extends T> holder, T arg) {
		//error : The method set(capture#2-of ? extends T) in the type Holder<capture#2-of ? extends T> is not applicable for the arguments (T)
		//holder.set(arg);
		
		T t = holder.get();
		return t;
	}
	
	static <T> T wildSuperType(Holder<? super T> holder, T arg) {
		holder.set(arg);
		
		//error : Type mismatch: cannot convert from capture#4-of ? super T to T
		//T t = holder.get();
		Object obj = holder.get();
		return (T) obj;
	}
	
	public static void main(String[] args) {
		Holder raw = new Holder();
		Holder<?> unbound = new Holder<Long>();
		Holder<Long> exact = new Holder<Long>();
		Holder<? extends Long> sub = new Holder<Long>();
		Long lng = 1L;

		rawArgs(raw, lng);
		rawArgs(unbound, lng);
		rawArgs(exact, lng);
		rawArgs(sub, lng);
		
		unboundedArgs(raw, lng);
		unboundedArgs(unbound, lng);
		unboundedArgs(exact, lng);
		unboundedArgs(sub, lng);
		
		//warning : Type safety: Unchecked invocation exact1(Holder) of the generic method exact1(Holder<T>) of type Wildcards
		Object r1 = exact1(raw);
		Object r2 = exact1(unbound);
		Long r3 = exact1(exact);
		Long r4 = exact1(sub);
		
		Object r5 = exact2(raw, lng);
//		Object r6 = exact2(unbound, lng);
		Long r7 = exact2(exact, lng);
//		Long r8 = exact2(sub, lng);
		
		Object r9 = wildSubType(raw, lng);
		Object r10 = wildSubType(unbound, lng);
		Long r11 = wildSubType(exact, lng);
		Long r12 = wildSubType(sub, lng);
		
		Object r13 = wildSuperType(raw, lng);
//		Object r14 = wildSuperType(unbound, lng);
		Long r15 = wildSuperType(exact, lng);
//		Long r16 = wildSuperType(sub, lng);
	}

}

class Holder<T> {
	T t;
	void set(T arg) {
		this.t = arg;
	}
	
	T get() {
		return t;
	}
}
