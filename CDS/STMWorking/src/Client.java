public class Client extends Thread {

	BankAccountTransaction bankAccountTransaction;

	public Client(BankAccountTransaction pbankAccountTransaction) {
		bankAccountTransaction = pbankAccountTransaction;
	}

	@Override
	public void run() {

		// Each Clients performs 5 transactions
		for (int i = 0; i < 5; i++) {
			bankAccountTransaction.transferAmount();
		}
	}
}
