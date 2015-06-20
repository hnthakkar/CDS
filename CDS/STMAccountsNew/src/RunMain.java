
import java.util.ArrayList;

import jvstm.Transaction;

public class RunMain
{

  public static void main(String[] args) 
  {  
     /*
          final int numAccounts = Integer.parseInt(args[0]);
          final int numThreads = Integer.parseInt(args[1]);
          final int numTotal = Integer.parseInt(args[2]);
          */
       
    final int numAccounts = 5;
    final int numThreads = 10;
    //final int numTotal = 4; 
    final long start = System.currentTimeMillis();
   
    System.out.println(" " + System.currentTimeMillis()) ; 
    
    Transaction.begin();    
    final BankAccountTransaction bankAccountTransaction = new BankAccountTransaction(numAccounts);    
    Transaction.commit();
    
    System.out.println("=============================================================");
    for (int i = 0; i < numAccounts; i++) 
    {
      System.out.println("Initial Balance in a/c:"+i +"=" + bankAccountTransaction.accounts[i].getBalance() ) ;
    }
    System.out.println("=============================================================");
    
    ///////////////////////////////////////////////////////
    /*
    Thread threads[] = new Thread[numThreads];

    for (int i = 0; i < numThreads; i++) 
    {
      threads[i] = new Thread() 
      {
        public void run()
        {
          long loops = numTotal / numThreads;
          
          for (int i = 0; i < loops; i++) 
          {
           bankAccountTransaction.transferAmount();
          }
        }
      };
    }
    /////////////////////////////////////////////////////
    
    

    for (int i = 0; i < numThreads; i++) 
    {
      threads[i].start();
    } 
    
    for (int i = 0; i < numThreads; i++) 
    {
       try 
       {
         threads[i].join();
       } catch (Throwable t) 
       {
           throw new Error("error:" +t.getMessage());
       }
    }       
    
    */
    
    
   /* TimeSlicer timeSlicer = new TimeSlicer();
    ArrayList <MyThread> threadList = new  ArrayList <MyThread>(3);
    
    // Instance of a slow Counter   
    SlowThread sc = new SlowThread(bankAccountTransaction,timeSlicer);
    sc.setName("Thread A");
    sc.start();
    threadList.add(sc);
    
    // Instance of a Medium Counter  
    MediumThread mc = new MediumThread(bankAccountTransaction,timeSlicer);
    mc.setName("Thread B");
    mc.start();
    threadList.add(mc);

    // Instance of a Fast Counter 
    FastThread fc = new FastThread(bankAccountTransaction,timeSlicer);
    fc.setName("Thread C");
    fc.start();
    threadList.add(fc);*/
    
    int counter = 0;
    
    while (counter < 5) 
    {
      try 
      {
        Thread.sleep(1000);
        timeSlicer.freezeTime();       
     
        for (int j = 0; j < threadList.size(); j++)
        {         
          if(j==0)
          {
            System.out.print("\n At time t"+ counter + " | ");
          }
          
          System.out.print(
             (threadList.get(j)).getName() +
             ": Transaction Success Count="+ threadList.get(j).getSuccessCount() + 
             ", Failure Count="+ threadList.get(j).getAbortCount() +" | ");
         
        }
             
        System.out.print("\n____________________________________________________________________________________________________\n");
                      
        counter++;
        timeSlicer.unFreezeTime();      
      } 
      catch (InterruptedException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
   }//////////////////////////////////////////////////////
   
   for (int j = 0; j < threadList.size(); j++)
   {
    
     for(int i=0; i < (threadList.get(j)).getThreadStatus().size(); i++)
     {
      System.out.print("\n Summary Status at time: "+  "T" +i+" | " 
        + (threadList.get(j)).getThreadStatus().get("T"+i));
     }     
     System.out.println();
   }
   
    
    
    
    //////////////////////////////////////////////
    // display a/c final status 
    System.out.println("\n=============================================================");
    for (int i = 0; i < numAccounts; i++) 
    {
      System.out.println("Final Balance in a/c:"+i +"=" + bankAccountTransaction.accounts[i].getBalance() ) ;
    }
    System.out.println("=============================================================");
    
    
    
    ///////////////////////////////////////////////
    //System.out.println("TestTransfers\t NumThreads:" + numThreads 
   //                    + "\t NumAcconts:" + numAccounts 
   //                   + "\t TimeDiff:" + (System.currentTimeMillis() - start));
    
    //System.out.println("Restarts = " + bankAccountTransaction.restarts);
    
    //System.out.println("Counter = " + bankAccountTransaction.counter.getCount());
      
   }

}
