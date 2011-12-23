package NetworkCommunication;

public class ByteConverter {

	public static byte[] toBytes(int i)	{
	  byte[] result = new byte[4];

	  result[0] = (byte) (i >> 24);
	  result[1] = (byte) (i >> 16);
	  result[2] = (byte) (i >> 8);
	  result[3] = (byte) (i /*>> 0*/);

	  return result;
	  // source: http://stackoverflow.com/questions/1936857/convert-integer-into-byte-array-java (30.11.2011)
	}
	
	public static int toInt(byte[] by) throws RuntimeException {
		if (by.length != 4) {
			throw new RuntimeException("array length is not 4.");
		}
		int value = 0;
		for (int i = 0; i < 4; i++)
		{
		   value = (value << 8) + (by[i] & 0xff);
		}
		return value;
		// source: http://stackoverflow.com/questions/1026761/how-to-convert-a-byte-array-to-its-numeric-value-java
	}

	
}
