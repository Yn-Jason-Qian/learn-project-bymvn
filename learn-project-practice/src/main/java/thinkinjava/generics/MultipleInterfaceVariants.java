package thinkinjava.generics;

interface Payable<T>{}

class Employee implements Payable<Employee>{
}

/*
 * 基类劫持了接口
 */
//error : The interface Payable cannot be implemented more than once with different arguments: Payable<Employee> and Payable<Hourly>
//class Hourly extends Employee implements Payable<Hourly>{
//}

public class MultipleInterfaceVariants {

}
