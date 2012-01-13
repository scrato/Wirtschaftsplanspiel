package Client.Entities;

public class Employee
{
	public Employee(EmployeeType Type)
	{
		type = Type;
		
		if (type == EmployeeType.Verwaltung){
			wage = getAdminWages();
			units = getAdministrationUnits();
		}
		else if (type == EmployeeType.Produktion){
			wage = getProductionWages();
			units = getProductionUnits();
		}
	}
	
	public static final double EMPLOYCOST = 1000d;
	public static final double DISMISSCOST = 1500d;
	public static final double ADMINISTRATIONWAGE = 1800d;
	public static final double PRODUCTIONWAGES = 1200d;
	public static final int ADMINUNITS = 3000;
	public static final int PRODUCTIONUNITS = 2000;
	
	private EmployeeType type;
	private double wage;
	private double severancePay;
	private int units;
	
//	public double increaseSeverancePay(){
//		severancePay = (wage*0.1)+severancePay;
//		return severancePay;
//	}
//	
	public EmployeeType getType() {
		return type;
	}
	
	public double getWage() {
		return wage;
	}
	
	public static double getAdminWages() {
		return ADMINISTRATIONWAGE;
	}
	
	public static double getProductionWages() {
		return PRODUCTIONWAGES;
	}
	
	public static double getDismisscost() {
		return DISMISSCOST;
	}
	
	public static double getEmploycost() {
		return EMPLOYCOST;
	}
	
	public static int getProductionUnits() {
		return PRODUCTIONUNITS;
	}
	
	public static int getAdministrationUnits() {
		return ADMINUNITS;
	}
	
	public double getSeverancePay() {
		return severancePay;
	}
	
	public int getCapacity() {
		return units;
	}
}
