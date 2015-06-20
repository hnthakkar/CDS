public class Client extends Thread {

	BankAccountTransaction bankAccountTransaction;
	private int num_threads = 0;
	private int num_accounts = 0;

	public Client(BankAccountTransaction pbankAccountTransaction) {
		bankAccountTransaction = pbankAccountTransaction;
	}

	@Override
	public void run() {

		int count = 0;
		if (num_threads > 0 && num_threads < num_accounts) {
			count = (num_accounts / num_threads) * 2;
		} else {
			count = 10;
		}

		// Each Clients performs 5 transactions
		for (int i = 0; i < count; i++) {
			bankAccountTransaction.transferAmount();
		}
	}

	/**
	 * @return the num_threads
	 */
	public int getNum_threads() {
		return num_threads;
	}

	/**
	 * @param num_threads
	 *            the num_threads to set
	 */
	public void setNum_threads(int num_threads) {
		this.num_threads = num_threads;
	}

	/**
	 * @return the num_accounts
	 */
	public int getNum_accounts() {
		return num_accounts;
	}

	/**
	 * @param num_accounts
	 *            the num_accounts to set
	 */
	public void setNum_accounts(int num_accounts) {
		this.num_accounts = num_accounts;
	}
}
