package iiit.stm.test;

public class TwoThreads extends Thread {
	static int i = 0;

	public void run() {
		increment();
	}
	
	void increment() {
		try {
			for (int j = 0; j < 200; j++) {
				//int temp = i;
				//Thread.sleep(1000);
				i++;
				//System.out.println("i Value : " + i);
				//Thread.sleep(1000);
				//i = temp + 1;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		TwoThreads t1 = new TwoThreads();
		TwoThreads t2 = new TwoThreads();
		TwoThreads t3 = new TwoThreads();
		TwoThreads t4 = new TwoThreads();
		TwoThreads t5 = new TwoThreads();
		t1.start();
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