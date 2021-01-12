
public class Fruit extends MarketProduct {
	private double weight;
	private int price;
	
	public Fruit(String s,double d, int i) {
		super(s);
		this.weight=d;
		this.price=i;
	}
	
	public int getCost() {
		int cost=(int)(weight*price);
		return cost;
	}
	
	public boolean equals(Object o) {
		if(o==this) {
			return true;
		}if(!(o instanceof Fruit)) {
			return false;
		}
		Fruit fruit=(Fruit)o;
		return this.weight==fruit.weight &&this.price==fruit.price && fruit.getName().equals(this.getName());
}
}
