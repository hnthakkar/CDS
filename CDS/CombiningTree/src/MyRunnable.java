public class MyRunnable implements Runnable {

	private CombiningTree counter;
	int id;
	public MyRunnable(CombiningTree counter, int j) {
		this.counter = counter;
		id=j;
	}

	@Override
	public void run() {
		try {
			GraphicalMain.threadControl[this.id].acquire();
			
			System.out.println("Thread "+this.id+" :"+ counter.getAndIncrement());
			//i++;}
		} catch (PanicException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
