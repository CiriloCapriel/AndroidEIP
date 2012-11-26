package se.opendataexchange.ethernetip4j.test;

import java.io.IOException;

import se.opendataexchange.ethernetip4j.Log;
import se.opendataexchange.ethernetip4j.clx.ControlLogixConnector;
import se.opendataexchange.ethernetip4j.clx.SimpleLogixCommunicator;
import se.opendataexchange.ethernetip4j.clx.UnconnectedMessagingRead;
import se.opendataexchange.ethernetip4j.clx.UnconnectedMessagingWrite;
import se.opendataexchange.ethernetip4j.exceptions.EmbeddedServiceException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientCommandException;
import se.opendataexchange.ethernetip4j.exceptions.InsufficientNrOfAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.InvalidTypeException;
import se.opendataexchange.ethernetip4j.exceptions.ItemNotFoundException;
import se.opendataexchange.ethernetip4j.exceptions.NotImplementedException;
import se.opendataexchange.ethernetip4j.exceptions.OtherWithExtendedCodeException;
import se.opendataexchange.ethernetip4j.exceptions.PathSegmentException;
import se.opendataexchange.ethernetip4j.exceptions.ProcessingAttributesException;
import se.opendataexchange.ethernetip4j.exceptions.ResponseBufferOverflowException;

public class CommTest extends Thread {

	public CommTest() {
		// TODO Auto-generated constructor stub
	}

	public static boolean run = true;
	
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		new CommTest().start();
		Thread.sleep(15000);
		run = false;
	}
	
	public void run1(){
		try {
                        float a = (float)14.3;
			ControlLogixConnector comm = new ControlLogixConnector("192.168.1.18", 0xAF12);
			UnconnectedMessagingRead read = new UnconnectedMessagingRead("Temp_Confort", comm.getSessionHandle());
			UnconnectedMessagingWrite write = new UnconnectedMessagingWrite("Temp_Confort",a, comm.getSessionHandle());
			
                        Log.p("Hello ");
			
			while(run){
				//Log.px("Writing..");
				//comm.write("TestREAL", a, 1);
                                comm.executeMessage(write);
				comm.executeMessage(read);
				Object q = read.getValues();
				if (q != null){
					Log.p("Reading: "+q);					
				}
					
				Thread.sleep(1000);
				a += 1;
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
                } catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();                        
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  

	public void run() {
		try {
			SimpleLogixCommunicator comm = new SimpleLogixCommunicator("192.168.1.18", 0xAF12);
			Log.p("Hello ");
			//float a = (float)14.3;
                        int a = 10;
			while(run){
				Log.p("Writing..");
				comm.write("Vint", a, 1);
				Object q = comm.read("Vint");
				if (q != null){
					Log.p("Reading: "+q);					
				}
					
				Thread.sleep(1000);
				a += 0.1;
			}		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PathSegmentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ItemNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResponseBufferOverflowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessingAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InsufficientNrOfAttributesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OtherWithExtendedCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmbeddedServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();                        
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
