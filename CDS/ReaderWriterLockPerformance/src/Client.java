public abstract class Client extends Thread{
	boolean isActive;
	static int restarts = 0;
	static int success = 0;

	static synchronized void countRestart() {
		restarts++;
	}
	
	static synchronized void countSuccess() {
		success++;
	}
}
