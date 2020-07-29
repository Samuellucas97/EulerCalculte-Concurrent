package br.ufrn.imd;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.concurrent.Callable;

public class EulerCalculatorTerm implements Callable<BigDecimal>{
	private Integer numberOfTerm;
	
	public EulerCalculatorTerm(String nameThread, Integer numberOfTerm) {
		this.numberOfTerm = numberOfTerm;
	}

	@Override
	public BigDecimal call() throws Exception {
		BigInteger factorialResult = factorial(BigInteger.valueOf(numberOfTerm));
	    System.out.println("Currently active threads are " + Thread.activeCount());
	    
	    return BigDecimal.ONE.divide(new BigDecimal(factorialResult), 50, RoundingMode.HALF_EVEN);
	}

	private BigInteger factorial(BigInteger number) {
		if (number == BigInteger.ZERO)
			return BigInteger.ONE;	
		return number.multiply(factorial(number.subtract(BigInteger.ONE)));
	}
}