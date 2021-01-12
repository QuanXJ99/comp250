
public class Customer {
	private String name;
	private int balance;
	private Basket products; 
	
	public Customer(String n,int b) {
		this.products=new Basket();
		this.name=n;
		this.balance=b;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getBalance() {
		return this.balance;
	}
	
	public Basket getBasket() {
		return this.products;
	}
	
	public int addFunds(int a) {
		if(a<0) {
			throw new IllegalArgumentException("You can not add funds with a negative number.");
		}else {
			this.balance=this.balance+a;
		}
		return this.balance;
	}
	
	public void addToBasket(MarketProduct a) {
		this.products.add(a);
	}
	
	public boolean removeFromBasket(MarketProduct a) {
		if(this.products.remove(a)) {
			return true;
		}
		return false;
	}
	
	public String checkOut() {
		String a =null;
		if(this.balance<this.products.getTotalCost()) {
			throw new IllegalStateException("You don't have enough money to pay");
		}else {
			this.balance=this.balance-this.products.getTotalCost();
			a=this.products.toString();
			products.clear();
		}
		return a;
	}
}

