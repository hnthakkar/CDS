import jvstm.CommitException;
import jvstm.Transaction;

public class WriterClient extends Client {
	long haltTime;
	TimeSlicer timeSlicer;
	Counter counter;

	public WriterClient(TimeSlicer ts, Counter pcounter, long waitTime) {
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
				MainClass.getThreadStates(timeSlicer);
				transaction = Transaction.begin(false);
				currentState = state_started;
				beforeValue = counter.getCount();
				counter.inc();
				afterValue = counter.getCount();
				Thread.sleep(1);
				timeSlicer.checkTimeState();
				MainClass.getThreadStates(timeSlicer);
				Transaction.commit();
				currentState = state_committed;
				Thread.sleep(1);
				timeSlicer.checkTimeState();
				MainClass.getThreadStates(timeSlicer);
			} catch (CommitException | InterruptedException ie) {
				currentState = state_aborted;
				Transaction.abort();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				timeSlicer.checkTimeState();
				MainClass.getThreadStates(timeSlicer);
			} finally {
				transaction = null;
			}
		}
	}
}
