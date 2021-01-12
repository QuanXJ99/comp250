
public class Basket {
	private MarketProduct[] products;
	
	public Basket() {
		this.products=new MarketProduct[0];
	}
	
	public MarketProduct[] getProducts() {
		MarketProduct[] shallow=new MarketProduct [this.products.length];
		for(int i=0;i<this.products.length;i++) {
			shallow[i]=this.products[i];
		}
		return shallow;
	}
	
	public void add(MarketProduct a) {
		MarketProduct[] array= new MarketProduct[this.products.length+1];
		for(int i=0;i<this.products.length;i++) {
			array[i]=this.products[i];
		}
		array[array.length-1]=a;
		this.products=array;
	}
	
	public boolean remove(MarketProduct a) {
		int b =this.products.length;
		int m=0;
		boolean u=false;
		for(int i=0;i<b;i++) {
			if(a.equals(this.products[i])) {
				m=i;
				u=true;
				break;
				}
		}
			if(u) {
				MarketProduct[] array= new MarketProduct[b-1];
				for(int i=0;i<m;i++) {
					array[i]=products[i];
				}
				for(int j=m;j<b-1;j++) {
					array[j]=products[j+1];
				}
				this.products=array;
				return true;
			}else {
				return false;
			}
	}
			
	public void clear() {
		this.products=new MarketProduct[0];
	}
	
	public int getNumOfProducts() {
		return this.products.length;
	}
	
	public int getSubTotal() {
		int sum=0;
		for(int i=0;i<this.products.length;i++) {
			int a=this.products[i].getCost();
			sum=sum+a;
		}
		return sum;
	}
	
	public int getTotalTax() {
		int tax=0;
		for(int i=0;i<this.products.length;i++) {
			if(products[i] instanceof Jam) {
				int jam=(int)(products[i].getCost()*0.15);
				tax=tax+jam;
			}
		}
		return tax;
	}
	
	public int getTotalCost() {
		int total=this.getSubTotal()+this.getTotalTax();
		return total;
	}
	
	private String helper() {
		String costFormat="";
		for(int i=0;i<this.products.length;i++) {
			double cost=this.products[i].getCost()/100.0;
			String name=this.products[i].getName();
			if(this.products[i].getCost()>0) {
				costFormat=costFormat+"\n"+name+"\t"+ String.format("%.2f",cost);
				}else {
				costFormat=costFormat+"\n"+name+"\t"+"-";
				}
		}
		return costFormat;
	}
	
	public String toString() {
		String a="\n";
		double subTotal=this.getSubTotal()/100.0;
		String st="Subtotal "+"\t"+String.format("%.2f",subTotal);
		double totalTax=this.getTotalTax()/100.0;
		String tt="Total Tax"+"\t"+String.format("%.2f",totalTax);
		double total=this.getTotalCost()/100.0;
		String to="Total Cost"+"\t"+String.format("%.2f",total);
		if(this.getSubTotal()==0) {
			st="Subtotal "+"\t"+"-";
		}
		if(this.getTotalTax()==0) {
			tt="Total Tax"+"\t"+"-";
		}
		if(this.getTotalCost()==0) {
			to="Total Cost"+"\t"+"-";
		}
		String format=this.helper()+a+a+st+a+tt+a+a+to;
		
		return format;
	}
}
		
	


