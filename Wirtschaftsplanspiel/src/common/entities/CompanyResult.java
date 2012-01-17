package common.entities;

import java.io.Serializable;

public class CompanyResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 878728121867704823L;
	public double result;
	public int clientid;
	
	public CompanyResult(double result, int clientid){
		this.result = result;
		this.clientid = clientid;
	}
}
