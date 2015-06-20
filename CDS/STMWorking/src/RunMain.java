import java.util.Set;

import jvstm.Transaction;

public class RunMain {
	
	static int count = 1;
	
	
	public static void main(String[] args) {

		final int numAccounts = 2;
		final int numThreads = 2;

		TimeSlicer timeSlicer = new TimeSlicer();
		
		Transaction.begin();
		final BankAccountTransaction bankAccountTransaction = new BankAccountTransaction(numAccounts,timeSlicer);
		Transaction.commit();

		System.out
				.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			System.out.println("Initial Balance in a/c:" + i + "="
					+ bankAccountTransaction.accounts[i].getBalance());
		}
		System.out
				.println("=============================================================");

		Client clients[] = new Client[numThreads];

		// Creating the Clients(Threads)
		for (int i = 0; i < numThreads; i++) {
			clients[i] = new Client(bankAccountTransaction);

		}

		final long start = System.currentTimeMillis();

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
		System.out
				.println("=============================================================");
		for (int i = 0; i < numAccounts; i++) {
			System.out.println("Final Balance in a/c:" + i + "="
					+ bankAccountTransaction.accounts[i].getBalance());
		}
		System.out
				.println("=============================================================");

		// /////////////////////////////////////////////
		System.out.println("TestTransfers\t NumThreads:" + numThreads
				+ "\t NumAcconts:" + numAccounts + "\t TimeDiff:"
				+ (System.currentTimeMillis() - start));

		System.out.println("Restarts = " + bankAccountTransaction.restarts);

		// System.out.println("Counter = " +
		// bankAccountTransaction.counter.getCount());

	}
	
	/*public static synchronized void getThreadStates(TimeSlicer timeSlicer) {
		try {
			timeSlicer.freezeTime();
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
			StringBuffer buf = new StringBuffer();
			for (int j = 0; j < threadArray.length; j++) {
				if (j == 0){
					buf.setLength(0);
					buf.append("\n At t" + count + "| ");
				}	
				if (threadArray[j] instanceof Client) {
					Client obj = (Client) threadArray[j];
					if (obj.getName().startsWith("W")){
						if(obj.currentState != 0)
							buf.append(obj.getName() + ":"
									+ Client.states[obj.getCurrentState()] + ":"
									+ obj.beforeValue + ":" + obj.afterValue
									+ "| ");
						else
							buf.append(obj.getName() + ":"
									+ Client.states[obj.getCurrentState()] + "| ");
					}else{
						buf.append(obj.getName() + ":" + obj.afterValue + "| ");
					}	
				}
			}
			logger.info(buf.toString());
			//logger.info("_________________________________________________________________________________");
			count++;
			timeSlicer.unFreezeTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	

}
