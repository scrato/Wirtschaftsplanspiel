package common.entities;

import java.io.Serializable;

public class CompanyResult implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 878728121867704823L;
	public double profit;
	public double marketShare;
	public double sales;
	public int clientid;
	
	public CompanyResult(double result, int clientid){
		this.profit = result;
		this.clientid = clientid;
	}
}
