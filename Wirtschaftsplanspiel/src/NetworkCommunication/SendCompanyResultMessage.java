package NetworkCommunication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;


import common.entities.CompanyResult;
import common.entities.CompanyResults;


public class SendCompanyResultMessage extends NetMessage {
	CompanyResults companyResult;
	
	public SendCompanyResultMessage(byte[] Content) {		
		super(MessageType.SEND_COMPANYRESULTS, Content);
		companyResult = convertBytesToCompanyResult(Content);
		
	}
	public SendCompanyResultMessage(CompanyResults Content){
		super(MessageType.SEND_COMPANYRESULTS, convertCompanyResulttoBytes(Content));
		companyResult = Content;
	}
	
	
	private CompanyResults convertBytesToCompanyResult(byte[] content) {
		ByteArrayInputStream b = new ByteArrayInputStream(content);
		try {
			ObjectInputStream o = new ObjectInputStream(b);
			return (CompanyResults) o.readObject();
		} catch (Exception e) {
			throw new RuntimeException("Conversion to CompanyResult failed");
		}
	}

	public CompanyResults getCompanyResults(){
		return companyResult;
	}
	private static byte[] convertCompanyResulttoBytes(CompanyResults content) {
		byte[] result;
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		try {
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(content);
			result = b.toByteArray();
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Conversion to ByteArray failed");
		}
		
		
	}

}
