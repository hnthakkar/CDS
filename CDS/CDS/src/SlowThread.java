public class SlowThread extends MyThread {

	TimeSlicer timeSlicer;
	

	public SlowThread(BankAccountTransaction bankAccountTransaction, TimeSlicer ts)
 {
   super(bankAccountTransaction); 
   this.timeSlicer = ts;
 }
 

	@Override
	public void run() 
	{
	  
 		while (true) 
 		{
   			timeSlicer.checkTimeState();
   			super.run();
   			try 
   			{
   				 Thread.sleep(100);
   			} 
   			catch (InterruptedException e)
   			{
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}
   			i++;
 		} 		
	}//////////////////////////////////
	
}
