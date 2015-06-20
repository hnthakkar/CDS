import java.util.concurrent.locks.ReentrantLock;


class VAccount implements Account {

	private Long balance = 0l;

	private Long credit = 0l;
	private Long debit = 0l;
	
	private ReentrantLock lock = new ReentrantLock();
	
	public void lock(){
		lock.lock();
	}
	
	public void unlock(){
		lock.unlock();
	}
	
	VAccount(long balance) {
		setBalance(balance);
	}

	public long getBalance() {
		return balance;
	}

	void setBalance(long newBalance) {
		this.balance = newBalance;
	}

	/**
	 * @return the credit
	 */
	public Long getCredit() {
		return this.credit;
	}

	/**
	 * @param credit
	 *            the credit to set
	 */
	public void setCredit(Long credit) {
		this.credit = credit;
	}

	/**
	 * @return the debit
	 */
	public Long getDebit() {
		return this.debit;
	}

	/**
	 * @param debit
	 *            the debit to set
	 */
	public void setDebit(Long debit) {
		this.debit = debit;
	}

	public void withdraw(long amount) {
		setBalance(getBalance() - amount);
	}

	public void deposit(long amount) {
		setBalance(getBalance() + amount);
	}

	public boolean canWithdraw(long amount) {
		return amount < getBalance();
	}

}
