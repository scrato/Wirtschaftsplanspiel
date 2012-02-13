package networkCommunication;

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
	
	
	// source of following code: http://www.daniweb.com/software-development/java/code/216874
	
	public static long toLong(byte[] data) {
		if (data == null || data.length != 8) throw new RuntimeException("Konnte nicht konvertiert werden.");
		// ----------
		return (long)(
			// (Below) convert to longs before shift because digits
			// are lost with ints beyond the 32-bit limit
			(long)(0xff & data[0]) << 56 |
			(long)(0xff & data[1]) << 48 |
			(long)(0xff & data[2]) << 40 |
			(long)(0xff & data[3]) << 32 |
			(long)(0xff & data[4]) << 24 |
			(long)(0xff & data[5]) << 16 |
			(long)(0xff & data[6]) << 8 |
			(long)(0xff & data[7]) << 0
		);
	}
	
	public static byte[] toBytes(long data) {
		return new byte[] {
			(byte)((data >> 56) & 0xff),
			(byte)((data >> 48) & 0xff),
			(byte)((data >> 40) & 0xff),
			(byte)((data >> 32) & 0xff),
			(byte)((data >> 24) & 0xff),
			(byte)((data >> 16) & 0xff),
			(byte)((data >> 8) & 0xff),
			(byte)((data >> 0) & 0xff),
		};
	}
		
	public static double toDouble(byte[] data) {
		if (data == null || data.length != 8) throw new RuntimeException("Konnte nicht konvertiert werden.");
		// ---------- simple:
		return Double.longBitsToDouble(toLong(data));
	}
	
	public static byte[] toBytes(double data) {
		return toBytes(Double.doubleToRawLongBits(data));
	}
	
}
