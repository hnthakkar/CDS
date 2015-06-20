
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread
{
	public static AtomicInteger atomicInteger = new AtomicInteger(0);
	int  i = 0;
	private BankAccountTransaction bankAccountTransaction;
 private Random rand = new Random();
 
 public MyThread(BankAccountTransaction bankAccountTransaction)
 {
   this.bankAccountTransaction = bankAccountTransaction;
 }
 public MyThread()
 {
  
 }
	
	@Override
 public void run() 
 {
  
   try
   {
     
     bankAccountTransaction.transferAmount();
     Thread.sleep(10);
   }
   catch (InterruptedException e)
   {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }  
 }
	
}
