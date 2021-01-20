package net.stealthcat.test.jvm;

public class SwitchChoose {

	/**
	 * public static void chooseTableSwitch();
	    Code:
	       0: iconst_0
	       1: istore_0
	       2: iload_0
	       3: tableswitch   { // 0 to 2
	                     0: 28
	                     1: 29
	                     2: 30
	               default: 31
	          }
	      28: return
	      29: return
	      30: return
	      31: return
	 */
	public static void chooseTableSwitch() {
		int i = 0;
		switch(i) {
		case 0:
			return;
		case 1:
			return;
		case 2:
			return;
		default:
			break;
		}
	}
	
	/**
	 * public static void chooseLookupSwitch();
	    Code:
	       0: iconst_0
	       1: istore_0
	       2: iload_0
	       3: lookupswitch  { // 3
	                     0: 36
	                   100: 37
	                   200: 38
	               default: 39
	          }
	      36: return
	      37: return
	      38: return
	      39: return
	 */
	public static void chooseLookupSwitch() {
		int i = 0;
		switch (i) {
		case 0:
			return;
		case 100:
			return;
		case 200:
			return;
		default:
			break;
		}
	}
	
	public static void chooseEnum() {
		ChooseEnum form = ChooseEnum.A;
		switch (form) {
		case A:
			System.out.println("a");
			break;
		case B:
			System.out.println("b");
			break;
		case C:
			System.out.println("c");
		default:
			break;
		}
	}
	
}
