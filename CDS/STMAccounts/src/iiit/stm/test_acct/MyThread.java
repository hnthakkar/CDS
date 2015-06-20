/*===========================================================
 * 
 * Shivdas Singh Tomar 
 * Roll No: 201350909 
 * 
 *        
 *   
 ===========================================================*/
package iiit.stm.test_acct;

import java.util.Hashtable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread
{
	public static AtomicInteger atomicInteger = new AtomicInteger(0);
	
	long start_time = System.currentTimeMillis();
	final static long static_start_time = System.currentTimeMillis();
	
	int  localCounter = 0;
	private BankAccountTransaction bankAccountTransaction;
 private Random rand = new Random();
 
 private Hashtable<String, String> threadStatus = new Hashtable<String, String>();
 private int abortCount; 
 private int successCount; 
 
 
 /**
  * 
  * @param bankAccountTransaction
  */
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
     successCount++; 
   }
   catch (InterruptedException e)
   {
    // TODO Auto-generated catch block
    e.printStackTrace();
   }  
   catch (Exception e)
   {
    // TODO Auto-generated catch block
    abortCount++; 
   } 
   finally
   {
    long now_time = System.currentTimeMillis(); 
    
    if((now_time - start_time) > 1000)
    {
      threadStatus.put("T"+localCounter, Thread.currentThread().getName() +":Success Count="+ successCount+", Abort Count="+abortCount);      
      start_time = now_time; 
      localCounter++;
    }
   }
   
 }
 /**
  * @return the bankAccountTransaction
  */
 public BankAccountTransaction getBankAccountTransaction()
 {
  return bankAccountTransaction;
 }
 /**
  * @param bankAccountTransaction the bankAccountTransaction to set
  */
 public void setBankAccountTransaction(
   BankAccountTransaction bankAccountTransaction)
 {
  this.bankAccountTransaction = bankAccountTransaction;
 }
 /**
  * @return the threadStatus
  */
 public Hashtable getThreadStatus()
 {
  return threadStatus;
 }
 
 /**
  * @param threadStatus the threadStatus to set
  */
 public void setThreadStatus(Hashtable threadStatus)
 {
  this.threadStatus = threadStatus;
 }
 
 public void setThreadStatus(String time, String status)
 {
  threadStatus.put(time, status ); 
 }
 /**
  * @return the abortCount
  */
 public int getAbortCount()
 {
  return abortCount;
 }
 /**
  * @param abortCount the abortCount to set
  */
 public void setAbortCount(int abortCount)
 {
  this.abortCount = abortCount;
 }
 /**
  * @return the successCount
  */
 public int getSuccessCount()
 {
  return successCount;
 }
 /**
  * @param successCount the successCount to set
  */
 public void setSuccessCount(int successCount)
 {
  this.successCount = successCount;
 }
	
}
