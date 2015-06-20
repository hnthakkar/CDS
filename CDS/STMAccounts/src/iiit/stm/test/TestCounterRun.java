package iiit.stm.test;


import jvstm.*;

public class TestCounterRun {

 public static void main(String[] args)
 {
    final Counter counter = new CFOCounter();

     new Thread() 
     {
         public void run() 
         {
           int i=0; 
           while (i<10) 
           {
            i++; 
            
            Transaction.begin();
            counter.inc();
            Transaction.commit();
            try 
            {
             Thread.sleep(100);
            } 
            catch (Exception e) {
             // ok
            }
          }
         }
     }.start();
  
   new Thread()
    {
     public void run() 
     {
      
      while (true) 
      {
        Transaction.begin();
        System.out.println(Thread.currentThread() +":Value = " + counter.getCount());
        Transaction.commit();
        try {
           Thread.sleep(100);
        } catch (Exception e) 
        {
          // ok
        }
       }
     }
    }.start();
   }
  
}
