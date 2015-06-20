
public class RunMain {
	
	static int count = 1;
	
	
	public static void main(String[] args) {

		final int numAccounts = 100;
		final int numThreads = 10;

		TimeSlicer timeSlicer = new TimeSlicer();
		
		final BankAccountTransaction bankAccountTransaction = new BankAccountTransaction(numAccounts,timeSlicer);
		
		System.out.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			System.out.println("Initial Balance in a/c:" + i + "=" + bankAccountTransaction.accounts[i].getBalance());
		}
		System.out.println("=============================================================");

		Client clients[] = new Client[numThreads];

		// Creating the Clients(Threads)
		for (int i = 0; i < numThreads; i++) {
			clients[i] = new Client(bankAccountTransaction);

		}

		long startTime = System.currentTimeMillis();

		// start all the Threads
		for (int i = 0; i < numThreads; i++) {
			clients[i].start();
		}

		// waiting for all the to finish there work (can use cyclic barrier)
		for (int i = 0; i < numThreads; i++) {
			try {
				clients[i].join();
			} catch (Throwable t) {
				throw new Error("error:" + t.getMessage());
			}
		}

		// display a/c final status
		long finalTotalAmount = 0;
		System.out.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			System.out.println("Final Balance in a/c:" + i + "=" + bankAccountTransaction.accounts[i].getBalance());
			finalTotalAmount += bankAccountTransaction.accounts[i].getBalance();
		}
		System.out.println("=============================================================");

		// /////////////////////////////////////////////
		/*System.out.println("TestTransfers\t NumThreads:" + numThreads
				+ "\t NumAcconts:" + numAccounts + "\t TimeDiff:"
				+ (System.currentTimeMillis() - start));*/

		System.out.println("Final Total balance : " + finalTotalAmount);
		System.out.println("Restarts = " + BankAccountTransaction.restarts);
		System.out.println("Success = " + BankAccountTransaction.success);
		System.out.println("Total Time Taken : " + (System.currentTimeMillis() - startTime));

	}
}
