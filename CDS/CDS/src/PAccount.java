
class PAccount implements Account {
    long balance;
    
    PAccount(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    void setBalance(long newBalance) {
        this.balance = newBalance;
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
