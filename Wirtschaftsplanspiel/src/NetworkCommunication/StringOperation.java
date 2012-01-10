package NetworkCommunication;

public class StringOperation {

	public static String padRight(String input, int count) {
		if (input.length() == 10) return input;
		if (input.length() > 10) return input.substring(0, 10);
		
		StringBuffer buff = new StringBuffer(input);
		
		int diff = 10 - input.length();
		for (int i = 0; i < diff; i++) {
			buff.append(' ');
		}
		return buff.toString();
	}
	
}
