package sprint_1.accounting_automation;

public class MonthlyReport extends YearlyReport {
    private String itemName;

    private int quantity;
    private int sumOfOne;

    public String getItemName() {
        return itemName;
    }


    public int getQuantity() {
        return quantity;
    }

    public int getSumOfOne() {
        return sumOfOne;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setSumOfOne(int sumOfOne) {
        this.sumOfOne = sumOfOne;
    }
}
