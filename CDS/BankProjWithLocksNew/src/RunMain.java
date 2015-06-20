public class RunMain {

	static int count = 1;
	public final static int numAccounts = 10000;
	public final static int numThreads = 1000;

	/**
	 * Starting point to start Coarse-Grain Application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();

		TimeSlicer timeSlicer = new TimeSlicer();

		final BankAccountTransaction bankAccountTransaction = new BankAccountTransaction(
				numAccounts, timeSlicer);

		long initialTotalAmount = 0;
		System.out
				.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			//System.out.println("Initial Balance in a/c:" + i + "=" + bankAccountTransaction.accounts[i].getBalance());
			initialTotalAmount += bankAccountTransaction.accounts[i].getBalance();
		}
		System.out.println("Sum of Initial Balance in All Accounts : " + initialTotalAmount );
		System.out
				.println("=============================================================");

		Client clients[] = new Client[numThreads];

		// Creating the Clients(Threads)
		for (int i = 0; i < numThreads; i++) {
			clients[i] = new Client(bankAccountTransaction);
			clients[i].setNum_accounts(numAccounts);
			clients[i].setNum_threads(numThreads);

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
		//System.out.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			//System.out.println("Final Balance in a/c:" + i + "=" + bankAccountTransaction.accounts[i].getBalance());
			finalTotalAmount += bankAccountTransaction.accounts[i].getBalance();
		}
		//System.out.println("=============================================================");

		// /////////////////////////////////////////////
		/*
		 * System.out.println("TestTransfers\t NumThreads:" + numThreads +
		 * "\t NumAcconts:" + numAccounts + "\t TimeDiff:" +
		 * (System.currentTimeMillis() - start));
		 */

		System.out.println("Final Total balance : " + finalTotalAmount);
		System.out.println("Restarts = " + BankAccountTransaction.restarts);
		System.out.println("Success = " + BankAccountTransaction.success);
		System.out.println("Total Time Taken : "
				+ (System.currentTimeMillis() - startTime) + " milliseconds");

		long memory = runtime.totalMemory() - runtime.freeMemory();
		//System.out.println("Used memory is bytes: " + memory);
		System.out.println("Used memory is megabytes: " + memory
				/ (1024L * 1024L));

	}
}
