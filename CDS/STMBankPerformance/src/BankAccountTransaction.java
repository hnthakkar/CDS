import java.util.Random;

import jvstm.CommitException;
import jvstm.Transaction;

public class BankAccountTransaction extends Thread{

	private static Random RND = new Random();
	public TimeSlicer timeSlicer;

	final static String[] states = {"No-Trans","Trans-Started","Trans-Committed","Trans-Aborted"};
	final int state_not_started = 0;
	final int state_started = 1;
	final int state_committed = 2;
	final int state_aborted = 3;
	int currentState = 0;
	int accNo1,accNo2;
	long initialBalance1,initialBalance2,afterWithdraw1,afterWithdraw2;
	
	static int restarts = 0;
	static int success = 0;

	BankAccountTransaction(TimeSlicer ptimeSlicer) {
		timeSlicer = ptimeSlicer;
	}

	@Override
	public void run(){
		//each thread performs 10 transaction
		for(int i = 0; i < 10; i++){
			transferAmount();
		}
		
	}
	
	void transferAmount() {
		try {
			while (true) {
				//currentState = state_not_started;
				//Thread.sleep(1);
				//timeSlicer.checkTimeState();
				Transaction transaction = Transaction.begin();
				try {
					transferAmount_Internal();
					transaction.commit();
					countSuccess();
					//currentState = state_committed;
					//Thread.sleep(1);
					//timeSlicer.checkTimeState();
					transaction = null;
					return;
				} catch (CommitException ce) {
					//currentState = state_aborted;
					Transaction.abort();
					/*try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timeSlicer.checkTimeState();*/
					transaction = null;
					countRestart();
				} finally {
					if (transaction != null) {
						transaction.abort();
						transaction = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static synchronized void countRestart() {
		restarts++;
	}
	
	static synchronized void countSuccess() {
		success++;
	}

	private void transferAmount_Internal() {
		try {
			accNo1 = RND.nextInt(RunMain.accounts.length);
			
			//withdraw from ONE account
			Account acc1 = RunMain.accounts[accNo1];
			initialBalance1 = acc1.getBalance();
			acc1.withdraw(initialBalance1/2);
			acc1.setDebit(initialBalance1/2);

			afterWithdraw1 = acc1.getBalance();
		
			//deposit in another account.
			accNo2 = RND.nextInt(RunMain.accounts.length);
			Account acc2 = RunMain.accounts[accNo2];
			initialBalance2 = acc2.getBalance();
			acc2.deposit(initialBalance1/2);
			acc2.setCredit(initialBalance1/2);
			afterWithdraw2 = acc2.getBalance();
			currentState = state_started;
			Thread.sleep(1);
			timeSlicer.checkTimeState();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}