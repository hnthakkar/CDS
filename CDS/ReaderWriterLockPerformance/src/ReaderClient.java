public class ReaderClient extends Client {
	FifoReadWriterLock lock;
	long haltTime;
	TimeSlicer timeSlicer;

	public ReaderClient(TimeSlicer ts, FifoReadWriterLock readerWriterLock,
			long waitTime) {
		lock = readerWriterLock;
		haltTime = waitTime;
		timeSlicer = ts;
	}

	@Override
	public void run() {
		//while (true) {
		for(int i = 0; i<10 ; i ++){
			try {
				timeSlicer.checkTimeState();
				lock.readLock().lock();
				isActive = true;
				countSuccess();
				// Read Some file
				//Thread.sleep(haltTime);
			} catch (Exception ie) {
				countRestart();
			} finally {
				lock.readLock().unlock();
				isActive = false;
				/*try {
					// wait for some time
					//Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		}
	}// method
}// class

