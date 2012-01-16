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


public class SendCompanyResult extends NetMessage {
	CompanyResults s;
	
	public SendCompanyResult(byte[] Content) {		
		super(MessageType.SEND_COMPANYRESULTS, Content);
		
		
	}
	
	public SendCompanyResult(CompanyResults Content){
		super(MessageType.SEND_COMPANYRESULTS, getCompanyResultBytes(Content));
		s = Content;
	}
	private static byte[] getCompanyResultBytes(CompanyResults content) {
		byte[] result;
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		try {
			ObjectOutputStream o = new ObjectOutputStream(b);
			o.writeObject(content);
			result = b.toByteArray();
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Something happened");
		}
		
		
	}

}
