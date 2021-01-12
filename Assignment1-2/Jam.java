//Name: Xijing Quan
//ID: 260765293
public class Jam extends MarketProduct{
	private int number;
	private int price;
	
	public Jam(String s,int n,int p) {
		super(s);
		this.number=n;
		this.price=p;
	}
	
	public int getCost() {
		int cost=this.number*this.price;
		return cost;
	}
	
	public boolean equals(Object o) {
		if(o==this) {
			return true;
		}if(!(o instanceof Jam)) {
			return false;
		}
		Jam jam=(Jam)o;
		return this.number==jam.number &&this.price==jam.price && jam.getName().equals(this.getName());
}
}
