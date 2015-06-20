public class WriterClient extends Client {
	FifoReadWriterLock lock;
	long haltTime;
	TimeSlicer timeSlicer;

	public WriterClient(TimeSlicer ts, FifoReadWriterLock readerWriterLock,
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
				lock.writeLock().lock();
				isActive = true;
				countSuccess();
				// write to the file
				//Thread.sleep(haltTime);
			} catch (Exception ie) {
				countRestart();
			} finally {
				lock.writeLock().unlock();
				isActive = false;
				/*try {
					// wait for some time
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
		}
	}
}
