import java.util.Random;

public class BankAccountTransaction {

	private static Random RND = new Random();
	public TimeSlicer timeSlicer;

	Account accounts[];
	// Counter counter;
	// final Counter counter = new CFOCounter();
	static int restarts = 0;
	static int success = 0;

	BankAccountTransaction(int numAccounts, TimeSlicer ptimeSlicer) {
		// counter = new Counter();
		timeSlicer = ptimeSlicer;
		accounts = new Account[numAccounts];

		for (int i = 0; i < numAccounts; i++) {
			accounts[i] = new VAccount(10000);
		}
	}

	synchronized void transferAmount() {
		while (true) {
			// + ":Transaction#"+tx.getNumber()
			//System.out.println("\n" + Thread.currentThread() + ":Begin: New Transaction");
			try {
				transferAmount_Internal();
				countSuccess();
				//System.out.println(Thread.currentThread() + ":Commit *****\n");
				return;
			} catch (Exception ce) {
				//System.out.println(Thread.currentThread() + ":Abort *****\n");
				countRestart();
			}
			//System.out.println(Thread.currentThread() + ":Retry: as transaction was failed due to conflict *****\n");
		}

	}

	static synchronized void countRestart() {
		restarts++;
	}
	
	static synchronized void countSuccess() {
		success++;
	}

	private void transferAmount_Internal() {
		int acctNo = RND.nextInt(accounts.length);
		// Account acc1 = ac[RND.nextInt(ac.length)];

		//withdraw from ONE account
		Account acc1 = accounts[acctNo];
		long value = acc1.getBalance() / 2;
		acc1.withdraw(value);
		acc1.setDebit(value);

/*		System.out.println(Thread.currentThread() + ":Withdrawn from a/c:"
				+ acctNo + ", Balance after withDrawn=" + acc1.getBalance()
				+ ", Debitted=" + acc1.getDebit());
*/
		//deposit in another account.
		acctNo = RND.nextInt(accounts.length);
		Account acc2 = accounts[acctNo];
		acc2.deposit(value);
		acc2.setCredit(value);

/*		System.out.println(Thread.currentThread() + ":Deposit to a/c:" + acctNo
				+ ", Balance after Deposit=" + acc2.getBalance()
				+ ", Credited=" + acc2.getCredit());
*/
		// counter.inc();

		//mySleep(1000, 10);
	}
}