package br.ufrn.imd;

import java.math.BigDecimal;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
	private static void checkArgument(String modeThreadPool) {			
		if (	(!modeThreadPool.equals("F") && !modeThreadPool.equals("C") && !modeThreadPool.equals("W")) ) {
			throw new IllegalArgumentException("O segundo parâmetro refere-se ao tipo de thread pool. "
					+ "As opcoes são 'F' para a fixed, C para  cached e 'W' para a work stealing.");
		}		
	}
	
	public static void main(String[] args) {

		Integer numberTerms = 0;
		String modeThreadPool = "";
		Integer numberThreads = 0;
		
		if (args.length != 2 && args.length != 3) {
			throw new IllegalArgumentException("Há dois parâmetros obrigatórios. O primeiro parâmetro é a quantidade de "
					+ "termos. O segundo parâmetro a forma de thread pool: fixed (F), cached (C) e work stealing (W)."
					+ " Caso seja escolhida a forma de thread pool fixed deve acrescentar um "
					+ "terceiro parâmetro, a quantidade de threads ");	
		}
		else {
			numberTerms = Integer.parseInt(args[0]);
			modeThreadPool = args[1];
			
			if (args.length == 3) 
				numberThreads = Integer.parseInt(args[2]);
			
			checkArgument(modeThreadPool);
		}
		
		if ( modeThreadPool.equals("F") ) {
			ExecutorService executor = Executors.newFixedThreadPool(numberThreads);
			BigDecimal euler =  BigDecimal.ONE;
		      
			for (int i = 1; i< numberTerms+1; ++i) {
			 	Callable<BigDecimal> termEulerCalculator = new EulerCalculatorTerm("Thread-" + i, i); 
				Future<BigDecimal> termEuler = executor.submit(termEulerCalculator);
				try {
					//euler += termEuler.get();
					euler = euler.add(termEuler.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}	
			}
			
			System.out.println("(FIXED) - Result is " + euler);
			executor.shutdown();
		}
		else if (modeThreadPool.equals("C") ) {
			ExecutorService executor = Executors.newCachedThreadPool();
			BigDecimal euler =  BigDecimal.ONE;
		      
			for (int i = 1; i< numberTerms+1; ++i) {
			 	Callable<BigDecimal> termEulerCalculator = new EulerCalculatorTerm("Thread-" + i, i); 
				Future<BigDecimal> termEuler = executor.submit(termEulerCalculator);
				try {
					//euler += termEuler.get();
					euler = euler.add(termEuler.get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("(CACHED) - Result is " + euler);
			executor.shutdown();
		}
		else {
			ExecutorService executor = Executors.newWorkStealingPool();
			BigDecimal euler =  BigDecimal.ONE;
		      
			for (int i = 1; i< numberTerms+1; ++i) {
			 	Callable<BigDecimal> termEulerCalculator = new EulerCalculatorTerm("Thread-" + i, i); 
				Future<BigDecimal> termEuler = executor.submit(termEulerCalculator);
				try {
					//euler += termEuler.get();
					euler = euler.add(termEuler.get());
			    } catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			
			System.out.println("(CACHED) - Result is " + euler);
			executor.shutdown();
		}	
	}
}