package client.entities;

public class ProfitAndLoss {

	public double changeInStock;
	public double changeInStockEarning;
	public double changeInStockExpenditures;

	public double deprecation;

	public double employeeDismissalCosts;
	public double employeeHiringCosts;
	
	public double lossDueDisposalOfAssets;

	public double interest;

	public double profit;

	public double rental;

	public double ressourceCost;

	public double sales;
	public double wages;

	public double warehouseCosts;

	public void calculateResult() {
		double expenditures = rental + lossDueDisposalOfAssets
				+ employeeHiringCosts + wages + employeeDismissalCosts
				+ deprecation + interest + ressourceCost + warehouseCosts;
		double earnings = sales;

		if (changeInStock > 0) {
			earnings += changeInStock;
			changeInStockEarning = changeInStock;
		} else {
			expenditures += Math.abs(changeInStock);
			changeInStockExpenditures = Math.abs(changeInStock);
		}
		this.profit = earnings - expenditures;
	}
	
	public double getTAccountSum() {
		return Math.max(ressourceCost + wages + deprecation + rental + interest + warehouseCosts + changeInStockExpenditures, sales + changeInStockEarning);
	}

}