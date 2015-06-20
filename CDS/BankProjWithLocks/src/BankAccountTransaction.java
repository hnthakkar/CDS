import java.util.Random;

public class BankAccountTransaction extends Thread{

	private static Random RND = new Random();
	public TimeSlicer timeSlicer;

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
				Thread.sleep(1);
				timeSlicer.checkTimeState();
				try {
					transferAmount_Internal();
					countSuccess();
					Thread.sleep(1);
					timeSlicer.checkTimeState();
					return;
				} catch(Exception e){
					countRestart();
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

	private synchronized void transferAmount_Internal() {
		Account acc1 = null;
		Account acc2 = null;
		try {
			accNo1 = RND.nextInt(RunMain.accounts.length);
			accNo2 = RND.nextInt(RunMain.accounts.length);
			
			acc1 = RunMain.accounts[accNo1];
			acc2 = RunMain.accounts[accNo2];
			//take locks in increasing order
			/*if( accNo1 < accNo2){
				acc1.lock();
				acc2.lock();
			} else if(accNo1 > accNo2) {
				acc2.lock();
				acc1.lock();
			} else {
				acc1.lock();
			}*/
			
			
			initialBalance1 = acc1.getBalance();
			acc1.withdraw(initialBalance1/2);
			acc1.setDebit(initialBalance1/2);

			afterWithdraw1 = acc1.getBalance();
		
						
			initialBalance2 = acc2.getBalance();
			acc2.deposit(initialBalance1/2);
			acc2.setCredit(initialBalance1/2);
			afterWithdraw2 = acc2.getBalance();
			Thread.sleep(1);
			timeSlicer.checkTimeState();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			
			/*if( accNo1 < accNo2){
				acc2.lock();
				acc1.lock();
			} else if(accNo1 > accNo2) {
				acc1.lock();
				acc2.lock();
			} else {
				acc1.lock();
			}*/
			
		}

	}
}