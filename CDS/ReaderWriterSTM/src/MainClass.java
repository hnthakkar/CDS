import java.io.IOException;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class MainClass {
	static int count = 1;
	
	public static Logger logger = Logger.getLogger(MainClass.class);

	public static void main(String[] arg) {
		TimeSlicer timeSlicer = new TimeSlicer();
		Counter counter = new NormalCounter();
		
		setLogger();
		
		// Creating 4 ReadClients
		for (int i = 0; i < 4; i++) {
			ReaderClient readerClient = new ReaderClient(timeSlicer, counter,
					1200);
			readerClient.setName("R" + i);
			readerClient.start();
		}
		
		for (int i = 0; i < 2; i++) {
			WriterClient writerClient = new WriterClient(timeSlicer, counter,1400);
			writerClient.setName("W" + i);
			writerClient.start();
		}
	}

	public static synchronized void getThreadStates(TimeSlicer timeSlicer) {
		try {
			timeSlicer.freezeTime();
			Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
			Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
			StringBuffer buf = new StringBuffer();
			for (int j = 0; j < threadArray.length; j++) {
				if (j == 0){
					buf.setLength(0);
					buf.append("\n At t" + count + "| ");
				}	
				if (threadArray[j] instanceof Client) {
					Client obj = (Client) threadArray[j];
					if (obj.getName().startsWith("W")){
						if(obj.currentState != 0)
							buf.append(obj.getName() + ":"
									+ Client.states[obj.getCurrentState()] + ":"
									+ obj.beforeValue + ":" + obj.afterValue
									+ "| ");
						else
							buf.append(obj.getName() + ":"
									+ Client.states[obj.getCurrentState()] + "| ");
					}else{
						buf.append(obj.getName() + ":" + obj.afterValue + "| ");
					}	
				}
			}
			logger.info(buf.toString());
			//logger.info("_________________________________________________________________________________");
			count++;
			timeSlicer.unFreezeTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setLogger( ) {
		Logger rootLogger = Logger.getRootLogger();
		rootLogger.setLevel(Level.INFO);
		PatternLayout layout = new PatternLayout("%m%n");
		try {
			// Define file appender with layout and output log file name
			RollingFileAppender fileAppender = new RollingFileAppender(
					layout, "timeSlicer.log");

			fileAppender.setAppend(true);
			fileAppender.setMaxFileSize("25MB");
			fileAppender.setMaxBackupIndex(100);
			fileAppender.setName("fileAppend");
			fileAppender.setImmediateFlush(false);

			rootLogger.addAppender(fileAppender);
			
		} catch (IOException e) {
			System.out.println("Failed to add appender !!");
		}
		
	}

}
