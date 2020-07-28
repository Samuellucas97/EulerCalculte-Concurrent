package br.ufrn.imd;

import java.util.concurrent.Callable;

public class EulerCalculatorTerm implements Callable<Double>{
	private Integer numberOfTerm;
	
	public EulerCalculatorTerm(String nameThread, Integer numberOfTerm) {
		this.numberOfTerm = numberOfTerm;
	}
	
	@Override
	public Double call() throws Exception {
		Integer factorialResult = factorial(this.numberOfTerm);
	    System.out.println("Currently active threads are " + Thread.activeCount());
		
		return (double)1/factorialResult;
	}
	
	private Integer factorial(int number) {
		if (number == 1)
			return 1;	
		return number * factorial(number-1);
	}
}