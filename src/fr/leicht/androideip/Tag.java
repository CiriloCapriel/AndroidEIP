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

public class Tag {
	private SimpleLogixCommunicator comm;
	private String name;
	private Date readValueTime = new Date(System.currentTimeMillis() -24*60*60*1000);
	private long msCycle=10;//number of millisec to do a new read in PLC
	private String stringValue;
	private Float floatValue;//value to write
	private int intValue;//value to write
	private Boolean boolValue;//value to write
	private Boolean flagWriteFloat=false;
	private Boolean flagWriteInt=false;
	private Boolean flagWriteBool=false;
	
	Tag(SimpleLogixCommunicator comm, String name, long msCycle) {
		this.comm = comm;
		this.name = name;
		this.msCycle = msCycle;
	}
	
	Tag(SimpleLogixCommunicator comm, String name) {
		this.comm = comm;
		this.name = name;
	}
	
	public void readTag() throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		Date vTime = new Date(System.currentTimeMillis() - this.getMsCycle());
		if (vTime.after(readValueTime)) {
			Object q = comm.read(name);
			setStringValue(q.toString());
		}
	}
	
	public void writeTag() throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		try {
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
		}
	}
	
	public void updateTag() throws PathSegmentException, ItemNotFoundException, ProcessingAttributesException, InsufficientCommandException, InsufficientNrOfAttributesException, OtherWithExtendedCodeException, ResponseBufferOverflowException, InvalidTypeException, IOException, EmbeddedServiceException, NotImplementedException {
		if (flagWriteBool || flagWriteInt || flagWriteFloat)
			writeTag();
		else
			readTag();
	}
	
	public Float getFloatValue() {
		return Float.parseFloat(stringValue);
	}

	public void setValue(Float floatValue) {
		flagWriteFloat = true;
		this.floatValue = floatValue;
	}

	public int getIntValue() {
		return Integer.parseInt(stringValue);
	}

	public void setValue(int intValue) {
		flagWriteInt = true;
		this.intValue = intValue;
	}

	public Boolean getBoolValue() {
		return Boolean.parseBoolean(stringValue);
	}

	public void setValue(Boolean boolValue) {
		flagWriteBool = true;
		this.boolValue = boolValue;
	}

	public String getStringValue() {
		return stringValue;
	}
	
	public String getStringValue(String patern) {
		DecimalFormat newFormat = new DecimalFormat(patern);
		String output = newFormat.format(getFloatValue());
		return output;
	}
	
	private void setStringValue(String value){
		this.readValueTime = new Date();
		this.stringValue = value;
	}

	public long getMsCycle() {
		return msCycle;
	}

	public void setMsCycle(long msCycle) {
		this.msCycle = msCycle;
	}
	

}
