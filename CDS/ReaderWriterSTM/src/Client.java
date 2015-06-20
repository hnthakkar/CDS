public abstract class Client extends Thread{
	final static String[] states = {"No-Trans","Trans-Started","Trans-Committed","Trans-Aborted"};
	final int state_not_started = 0;
	final int state_started = 1;
	final int state_committed = 2;
	final int state_aborted = 3;
	int currentState = 0;
	
	long beforeValue;
	long afterValue;
	
	public int getCurrentState(){
		return currentState;
	}
}
