package fr.leicht.androideip;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import se.opendataexchange.ethernetip4j.clx.SimpleLogixCommunicator;
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

public class Tags {
	private SimpleLogixCommunicator comm;
	private Object[] objects;
	private int arraySize;
	private String name;
	private Date readValueTime = new Date(System.currentTimeMillis() -24*60*60*1000);
	private long msCycle=10;//number of millisec to do a new read in PLC
	//private String stringValue;
	private Float floatValue;//value to write
	private int intValue;//value to write
	private Boolean boolValue;//value to write
	private Boolean flagWriteFloat=false;
	private Boolean flagWriteInt=false;
	private Boolean flagWriteBool=false;
	
	Tags(SimpleLogixCommunicator comm, String name, int arraySize, long msCycle) {
		this.comm = comm;
		this.name = name;
		this.arraySize = arraySize;
		this.objects = new Object[arraySize];
		this.msCycle = msCycle;
	}
	
	Tags(SimpleLogixCommunicator comm, String name, int arraySize) {
		this.comm = comm;
		this.name = name;
		this.arraySize = arraySize;
		this.objects = new Object[arraySize];
	}
	
	public void readTag() throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		Date vTime = new Date(System.currentTimeMillis() - this.getMsCycle());
		if (vTime.after(readValueTime)) {
			objects = (Object[]) comm.read(name,arraySize);
			//setStringValue(q.toString());
		}
	}
	
	public void writeTag() throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		/*try {
			if (flagWriteFloat) 
				comm.write(name, floatValue, 1);
			if (flagWriteInt) 
				comm.write(name, intValue, 1);
			if (flagWriteBool) 
				comm.write(name, boolValue, 1);	
			//lecture immédiate après écriture
			Object q = comm.read(name);
			setStringValue(q.toString());
		} finally {
			flagWriteFloat = false;
			flagWriteInt = false;
			flagWriteBool = false;
		}*/
	}
	
	public void updateTag() throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		if (flagWriteBool || flagWriteInt || flagWriteFloat)
			writeTag();
		else
			readTag();
	}
	
	public Float getFloatValue(int index) {
		return Float.parseFloat(getStringValue(index));
	}

	/*public void setValue(Float floatValue) {
		flagWriteFloat = true;
		this.floatValue = floatValue;
	}*/

	public int getIntValue(int index) {
		return Integer.parseInt(getStringValue(index));
	}

	/*public void setValue(int intValue) {
		flagWriteInt = true;
		this.intValue = intValue;
	}*/

	public Boolean getBoolValue(int index) {
		return Boolean.parseBoolean(getStringValue(index));
	}

	/*public void setValue(Boolean boolValue) {
		flagWriteBool = true;
		this.boolValue = boolValue;
	}*/

	public String getStringValue(int index) {
		return objects[index].toString();
	}
	
	public String getStringValue(int index, String patern) {
		DecimalFormat newFormat = new DecimalFormat(patern);
		String output = newFormat.format(getFloatValue(index));
		return output;
	}
	
	/*private void setStringValue(String value){
		this.readValueTime = new Date();
		this.stringValue = value;
	}*/

	public long getMsCycle() {
		return msCycle;
	}

	public void setMsCycle(long msCycle) {
		this.msCycle = msCycle;
	}
	

}
