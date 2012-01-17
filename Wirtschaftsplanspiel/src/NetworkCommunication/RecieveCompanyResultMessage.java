package NetworkCommunication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import common.entities.CompanyResultList;


public class RecieveCompanyResultMessage extends NetMessage {
	CompanyResultList companyResult;
	
	public RecieveCompanyResultMessage(byte[] Content) {		
		super(MessageType.RECIEVE_COMPANYRESULT, Content);
		companyResult = convertBytesToCompanyResult(Content);
		
	}
	public RecieveCompanyResultMessage(CompanyResultList Content){
		super(MessageType.RECIEVE_COMPANYRESULT, convertCompanyResulttoBytes(Content));
		companyResult = Content;
	}
	
	
	private CompanyResultList convertBytesToCompanyResult(byte[] content) {
		ByteArrayInputStream b = new ByteArrayInputStream(content);
		try {
			ObjectInputStream o = new ObjectInputStream(b);
			return (CompanyResultList) o.readObject();
		} catch (Exception e) {
			throw new RuntimeException("Conversion to CompanyResultList failed");
		}
	}

	public CompanyResultList getCompanyResults(){
		return companyResult;
	}
	private static byte[] convertCompanyResulttoBytes(CompanyResultList content) {
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
