package Client.Entities;

public class Employee
{
	public Employee(EmployeeType Type, double Wage)
	{
		type = Type;
		wage = Wage;		
	}
	
	private EmployeeType type;
	private double wage;
	private double severancePay;
	
	private int period;
	
	public double increaseSeverancePay()
	{
		period++;
		severancePay = (wage*0.1)+severancePay;
		
		return severancePay;
	}
	
	public EmployeeType getType() {
		return type;
	}
	
	public double getWage() {
		return wage;
	}
	
	public double getSeverancePay() {
		return severancePay;
	}
	

}
