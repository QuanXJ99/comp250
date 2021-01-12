
public abstract class MarketProduct {
	private String name;
	
	public MarketProduct(String s){
		this.name=s;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public abstract int getCost();
	
	public abstract boolean equals(Object o);
}
