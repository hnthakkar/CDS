package iiit.stm.test;

import jvstm.*;
//import pt.ist.esw.atomicannotation.*;

import pt.ist.esw.advice.*; 

import java.util.Random;
import java.util.concurrent.*;

public class TestSTM {
    
  private VBox<Long>[] nums;

  public TestSTM(int num) 
  {
     nums = new VBox[num];
     
     for (int i = 0; i < num; i++) 
     {
       nums[i] = new VBox<Long>(0L);
     }
      
      //System.out.println(nums[0]);
  }
    
  void start(int numThreads) 
  {
	    Executor executor = Executors.newFixedThreadPool(numThreads);
	    
	    for (int i = 0; i < numThreads; i++) 
	    {   
	        Worker worker = new Worker(); 
	        
	        Thread thread = new Thread(worker);	        
	        thread.setName("" + i);
	       
	        
	        //executor.execute(new Worker());
	        executor.execute(thread);
	        
	    }
     
	   //System.out.println("Done"); 
  }
  
  /**
   * 
   * @author sstomar
   *
   */
  class Worker implements Runnable 
  {
      private Random rnd = new Random();
      //long total = 0; 
      
      public void run() 
      {
        boolean flag = true; 
          while (flag)
          {
              long total = sumAll();
              changeOne(total < 10);              
              if(total>=5) flag= false; 
          }
      }
        
        
     
     @Atomic long sumAll() 
     { 
         System.out.println(Thread.currentThread() +":sumAll:begin" ); 
      
         long sum = 0;
         for (int i = 0; i < nums.length; i++) 
         {
             sum += nums[i].get();
         }
         
         System.out.println(Thread.currentThread() +":sumAll:" + sum + " :end"); 
         return sum;
     }
     
     @Atomic void changeOne(boolean inc) 
     {
       System.out.println(Thread.currentThread() +":changeOne:begin" );
    
       int pos = rnd.nextInt(nums.length);
       
       System.out.println(Thread.currentThread() +":changeOne: position:" + pos); 
       
       long val = nums[pos].get() + (inc ? 1 : -1);
       
       System.out.println(Thread.currentThread() +":changeOne:" + val); 
       
       nums[pos].put(val);
       
       if(Thread.currentThread().getName().contains("thread-1"))
       {
         try
         {
          Thread.sleep(50000);
         }catch(Exception e) {}
       }
       System.out.println(Thread.currentThread() +"changeOne:end");
     }
 }////////////////////////////////////////////////////////////////////
    

  public static void main(String[] args) throws Exception {
      //Thread.sleep(5000);
      System.out.println("Will start now...");
      
      args = new String[2];
      
      args[0] = "2"; 
      args[1] = "2";
      
      new TestSTM(Integer.parseInt(args[0])).start(Integer.parseInt(args[1]));
      
      Thread.currentThread().join();
      
      System.out.println("========Done========");
      
      
  }
   
}