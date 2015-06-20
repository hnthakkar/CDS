
public class RunMain {
	
	static int count = 1;
	
	public static Account accounts[];
	public static void main(String[] args) {

		final int numAccounts = 100;
		final int numThreads = 100;
		final int initailAmt = 10000;
		
		accounts = new Account[numAccounts];
		for (int i = 0; i < numAccounts; i++) {
			accounts[i] = new VAccount(initailAmt);
		}

		TimeSlicer timeSlicer = new TimeSlicer();
		
		System.out.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			System.out.println("Initial Balance in a/c:" + i + "=" + accounts[i].getBalance());
		}
		System.out.println("Sum of Total Amount in all the Accounts :" + (initailAmt * numAccounts));
		System.out.println("=============================================================");

		BankAccountTransaction bat[] = new BankAccountTransaction[numThreads];

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < numThreads; i++) {
			bat[i] = new BankAccountTransaction(timeSlicer);
			bat[i].setName("T" + i);
			bat[i].start();
		}

		
		// waiting for all the Threads to finish there work (can use cyclic barrier)
		for (int i = 0; i < numThreads; i++) {
			try {
				bat[i].join();
			} catch (Throwable t) {
				throw new Error("error:" + t.getMessage());
			}
		}

		// display a/c final status
		long finalTotalAmount = 0;
		System.out.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			System.out.println("Final Balance in a/c:" + i + "=" + accounts[i].getBalance());
			finalTotalAmount += accounts[i].getBalance();
		}
		System.out.println("=============================================================");

		System.out.println("Final Total balance : " + finalTotalAmount);
		System.out.println("Restarts = " + BankAccountTransaction.restarts);
		System.out.println("Success = " + BankAccountTransaction.success);
		System.out.println("Total Time Taken : " + (System.currentTimeMillis() - startTime));
	}
}
