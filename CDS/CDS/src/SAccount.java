class SAccount implements Account {
    long balance;
    
    SAccount(long balance) {
        this.balance = balance;
    }

    public synchronized long getBalance() {
        return balance;
    }

    synchronized void setBalance(long newBalance) {
        this.balance = newBalance;
    }

    synchronized public void withdraw(long amount) {
        setBalance(getBalance() - amount);
    }

    synchronized public void deposit(long amount) {
        setBalance(getBalance() + amount);
    }

    public boolean canWithdraw(long amount) {
        return amount < getBalance();
    }

    @Override
    public void setCredit(Long credit)
    {
     // TODO Auto-generated method stub
     
    }

    @Override
    public Long getCredit()
    {
     // TODO Auto-generated method stub
     return null;
    }

    @Override
    public void setDebit(Long dedit)
    {
     // TODO Auto-generated method stub
     
    }

    @Override
    public Long getDebit()
    {
     // TODO Auto-generated method stub
     return null;
    }
}
