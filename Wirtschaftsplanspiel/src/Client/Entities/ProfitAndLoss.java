package Client.Entities;

public class ProfitAndLoss {

	public double changeInStockFinishedProducts;

	public double changeInStockRessources;

	public double deprecation;

	public double employeeDismissalCosts;

	public double employeeHiringCosts;

	public double employerSallery;

	public double interest;

	public double profit;

	public double rental;

	public double ressourceCost;

	public double sales;
	public double wages;

	public double warehouseCosts;

	public void calculateResult() {
		double expenditures = employerSallery + rental
				+ employeeHiringCosts + wages + employeeDismissalCosts
				+ deprecation + interest + warehouseCosts;
		double earnings = sales;

		if (changeInStockRessources > 0)
			earnings += changeInStockRessources;
		else
			expenditures += Math.abs(changeInStockRessources);

		if (changeInStockFinishedProducts > 0)
			earnings += changeInStockFinishedProducts;
		else
			expenditures += Math.abs(changeInStockFinishedProducts);

		this.profit = earnings - expenditures;
	}

}