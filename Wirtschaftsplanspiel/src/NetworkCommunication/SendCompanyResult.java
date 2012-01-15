package NetworkCommunication;

import java.io.UnsupportedEncodingException;


public class SendCompanyResult extends NetMessage {

	public SendCompanyResult(byte[] Content) {
		super(MessageType.SEND_COMPANYRESULTS, Content);
		/*try {
			message = new String(content, "UTF-16LE");
		} catch (UnsupportedEncodingException e) {
			// should never reach this point.
		}*/
	}

}
