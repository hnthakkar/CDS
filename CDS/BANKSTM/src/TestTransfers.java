
import java.util.Random;

import jvstm.*;

public class TestTransfers {

private static Random RND = new Random();

Account accounts[];
//Counter counter;
//final Counter counter = new CFOCounter();
int restarts = 0;

TestTransfers(int numAccounts)
{
  //counter = new Counter();
 accounts = new Account[numAccounts];
  
  for (int i = 0; i < numAccounts; i++) 
  {
   accounts[i] = new VAccount(10);
  }
}
    
/**
 * 
 */
void transferAmount() 
{
  while (true) 
  {
    Transaction transaction = Transaction.begin();
    // + ":Transaction#"+tx.getNumber()
    System.out.println("\n"+ Thread.currentThread()  + ":Begin: New Transaction");
    try 
    {
        transferAmount_Internal();
        transaction.commit();
        System.out.println(Thread.currentThread()  +":Commit *****\n");
        transaction = null;
        return;
    }
    catch (CommitException ce)
    {
        System.out.println(Thread.currentThread() 
          +":Abort *****\n" );
      
        transaction.abort();
        transaction = null;
        countRestart();
    }
    finally 
    {
        if (transaction != null) 
        {
         transaction.abort();
        }
    }
    System.out.println(Thread.currentThread()  +":Retry: as transaction was failed due to conflict *****\n"); 
  }
  
}

synchronized void countRestart() {
    restarts++;
}

private void transferAmount_Internal()
{
    int acctNo = RND.nextInt(accounts.length);         
   // Account acc1 = ac[RND.nextInt(ac.length)];
    
    //// withdraw from ONE account 
    Account acc1 = accounts[acctNo];    
    long value = acc1.getBalance() / 2;    
    acc1.withdraw(value);
   // acc1.setDebit(value);    
    
    
    System.out.println(Thread.currentThread() +
       ":Withdrawn from a/c:" + acctNo +  
       ", Balance after withDrawn=" + acc1.getBalance() +
       ", Debitted="); //+ acc1.getDebit());        
    
    ////////// deposit in another account. 
    acctNo = RND.nextInt(accounts.length);  
    Account acc2 = accounts[acctNo];        
    acc2.deposit(value);        
   // acc2.setCredit(value);
    
    System.out.println(Thread.currentThread() +":Deposit to a/c:"
             + acctNo 
             + ", Balance after Deposit=" + acc2.getBalance() 
             + ", Credited=");// + acc2.getCredit() );    
    
    
    
    
    
    //counter.inc();
    
    mySleep(1000, 10);
}

static void mySleep(long millis, int nanos) 
{
    try {
        Thread.sleep(millis, nanos);
    } catch (InterruptedException ie) {
    }        
} 
}///////////////////////////////////////////////////////////////////////


/*import java.util.Random;
import jvstm.*;

public class TestTransfers {

    private static Random RND = new Random();

    Account ac[];
    //Counter counter;
    int restarts = 0;

    TestTransfers(int numAccounts) {
        //counter = new Counter();
        ac = new Account[numAccounts];
        
        for (int i = 0; i < numAccounts; i++) {
            ac[i] = new VAccount(10);
        }
    }

    void transTransferAmount() {
        while (true) {
            Transaction tx = Transaction.begin();
            System.out.println("\n"+ Thread.currentThread()  + ":Begin: New Transaction");
            try {
                transferAmount();
                tx.commit();
                tx = null;
                return;
            } catch (CommitException ce) {
                tx.abort();
                tx = null;
                countRestart();
            } finally {
                if (tx != null) {
                    tx.abort();
                }
            }
        }
    }

    synchronized void countRestart() {
        restarts++;
    }

    void transferAmount() {
        Account acc1 = ac[RND.nextInt(ac.length)];
        Account acc2 = ac[RND.nextInt(ac.length)];

        long value = acc1.getBalance() / 2;

        acc1.withdraw(value);
        acc2.deposit(value);
       // counter.inc();
        mySleep(0, 10);
    }

    static void mySleep(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        } catch (InterruptedException ie) {
        }        
    }


    public static void main(String[] args) {
        final int numAccounts = Integer.parseInt(args[0]);
        final int numThreads = Integer.parseInt(args[1]);
        final int numTotal = Integer.parseInt(args[2]);
        
        final int numAccounts = 3;
        final int numThreads = 5;
        final int numTotal = 10;

        Transaction.begin();
        final TestTransfers ti = new TestTransfers(numAccounts);
        Transaction.commit();

        Thread threads[] = new Thread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread() {
                public void run() {
                    long loops = numTotal / numThreads;
                    for (int i = 0; i < loops; i++) {
                        ti.transTransferAmount();
                    }
                }
            };
        }

        final long start = System.currentTimeMillis();

        for (int i = 0; i < numThreads; i++) {
            threads[i].start();
        }        
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (Throwable t) {
                throw new Error("erro");
            }
        }        
        System.out.println("TestTransfers\t" + numThreads 
                           + "\t" + numAccounts 
                           + "\t" + (System.currentTimeMillis() - start));
        System.out.println("Restarts = " + ti.restarts);
        //System.out.println("Counter = " + ti.counter.getCount());
    }
}
*/