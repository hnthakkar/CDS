import jvstm.CommitException;
import jvstm.Transaction;

public class ReaderClient extends Client {
	long haltTime;
	TimeSlicer timeSlicer;
	Counter counter;
	
	public ReaderClient(TimeSlicer ts, Counter pcounter, long waitTime) {
		haltTime = waitTime;
		timeSlicer = ts;
		counter = pcounter;
	}

	@Override
	public void run() {
		while (true) {
			Transaction transaction = null;
			try {
				currentState = state_not_started;
				Thread.sleep(1);
				timeSlicer.checkTimeState();
				//MainClass.getThreadStates(timeSlicer);
				transaction = Transaction.begin(true);
				currentState = state_started;
				beforeValue = afterValue = counter.getCount();
				Thread.sleep(1);
				timeSlicer.checkTimeState();
				//MainClass.getThreadStates(timeSlicer);
				Transaction.commit();
				//successfulRead++;
				currentState = state_committed;
				Thread.sleep(1);
				timeSlicer.checkTimeState();
				//MainClass.getThreadStates(timeSlicer);
			} catch (CommitException | InterruptedException ie) {
				currentState = state_aborted;
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeSlicer.checkTimeState();
				//MainClass.getThreadStates(timeSlicer);
				Transaction.abort();
			} finally {
				transaction = null;
			}
		}
	}// method
}// class

