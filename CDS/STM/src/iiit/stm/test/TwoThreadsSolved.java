package iiit.stm.test;

import jvstm.Atomic;
import jvstm.Transaction;

public class TwoThreadsSolved extends Thread {
	static int i = 0;
	
	@Override
	public void run() {
		increment();
	}
	
	@Atomic void increment(){
	//void increment(){
		try {
			for (int j = 0; j < 100; j++) {
				//System.out.println(getName() + ": " + i);
				//int temp = i;
				//i = temp + 1;
				//i++;
				/*int temp = i;
				Thread.sleep(1000);
				i = temp + 1;*/
				Transaction.begin();
				i++;
				
				Transaction.commit();
				//System.out.println(getName() + " i Value : " + i);
				//Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			// Ignore exception
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TwoThreadsSolved t1 = new TwoThreadsSolved();
		TwoThreadsSolved t2 = new TwoThreadsSolved();
		TwoThreadsSolved t3 = new TwoThreadsSolved();
		TwoThreadsSolved t4 = new TwoThreadsSolved();
		TwoThreadsSolved t5 = new TwoThreadsSolved();
		t1.start();
		
		/*try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Value of i :" + i );
		
	}
}