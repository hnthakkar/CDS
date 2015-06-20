
public class MediumThread extends MyThread {

	TimeSlicer timeSlicer;
 
	public MediumThread(BankAccountTransaction bankAccountTransaction, TimeSlicer ts)
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
 				Thread.sleep(200);
 			} 
 			catch (InterruptedException e) 
 			{
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 		} ////////end of while
	}/////////end: of method 
}
