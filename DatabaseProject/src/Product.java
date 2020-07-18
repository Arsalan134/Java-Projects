
public class Product {

	private String name;
	private double price;
	private double amount;
	private String category;

	public Product(String name, double price, double amount, String category) {
		super();
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
