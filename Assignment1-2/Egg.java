//Name: Xijing Quan
//ID: 260765293
public class Egg extends MarketProduct {
	private int number;
	private int price;
	
	
	public Egg(String s, int n, int p) {
		super(s);
		this.number=n;
		this.price=p;
	}
	
	public int getCost() {
		int cost=(int)((this.price/12.0)*this.number);
		return cost;
	}

	public boolean equals(Object o) {
		if(o==this) {
			return true;
		}if(!(o instanceof Egg)) {
			return false;
		}
		Egg egg=(Egg)o;
		return this.number==egg.number &&this.price==egg.price && egg.getName().equals(this.getName());
	}
	
	
}
