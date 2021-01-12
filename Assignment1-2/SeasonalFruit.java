
public class SeasonalFruit extends Fruit {

	public SeasonalFruit(String s, double weight, int price) {
		super(s,weight,price);
	}
	
	public int getCost() {
		int cost=(int)(super.getCost()*0.85);
		return cost;
	}
}
