import java.util.Random;

import jvstm.CommitException;
import jvstm.Transaction;

public class BankAccountTransaction {

	private static Random RND = new Random();
	public TimeSlicer timeSlicer;

	Account accounts[];
	// Counter counter;
	// final Counter counter = new CFOCounter();
	int restarts = 0;

	BankAccountTransaction(int numAccounts, TimeSlicer ptimeSlicer) {
		// counter = new Counter();
		timeSlicer = ptimeSlicer;
		accounts = new Account[numAccounts];

		for (int i = 0; i < numAccounts; i++) {
			accounts[i] = new VAccount(10000);
		}
	}

	void transferAmount() {
		while (true) {
			Transaction transaction = Transaction.begin();
			// + ":Transaction#"+tx.getNumber()
			System.out.println("\n" + Thread.currentThread()
					+ ":Begin: New Transaction");
			try {
				transferAmount_Internal();
				transaction.commit();
				System.out.println(Thread.currentThread() + ":Commit *****\n");
				transaction = null;
				return;
			} catch (CommitException ce) {
				System.out.println(Thread.currentThread() + ":Abort *****\n");

				transaction.abort();
				transaction = null;
				countRestart();
			} finally {
				if (transaction != null) {
					transaction.abort();
				}
			}
			System.out
					.println(Thread.currentThread()
							+ ":Retry: as transaction was failed due to conflict *****\n");
		}

	}

	synchronized void countRestart() {
		restarts++;
	}

	private void transferAmount_Internal() {
		int acctNo = RND.nextInt(accounts.length);
		// Account acc1 = ac[RND.nextInt(ac.length)];

		//withdraw from ONE account
		Account acc1 = accounts[acctNo];
		long value = acc1.getBalance() / 2;
		acc1.withdraw(value);
		acc1.setDebit(value);

		System.out.println(Thread.currentThread() + ":Withdrawn from a/c:"
				+ acctNo + ", Balance after withDrawn=" + acc1.getBalance()
				+ ", Debitted=" + acc1.getDebit());

		//deposit in another account.
		acctNo = RND.nextInt(accounts.length);
		Account acc2 = accounts[acctNo];
		acc2.deposit(value);
		acc2.setCredit(value);

		System.out.println(Thread.currentThread() + ":Deposit to a/c:" + acctNo
				+ ", Balance after Deposit=" + acc2.getBalance()
				+ ", Credited=" + acc2.getCredit());

		// counter.inc();

		mySleep(1000, 10);
	}

	static void mySleep(long millis, int nanos) {
		try {
			Thread.sleep(millis, nanos);
		} catch (InterruptedException ie) {
		}
	}
}