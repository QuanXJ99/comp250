
import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf



		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}



		// this method takes in a datalist (ArrayList of type datum) and a minSizeInClassification (int) and returns
		// the calling DTNode object as the root of a decision tree trained using the datapoints present in the
		// datalist variable
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {

			//YOUR CODE HERE
			
			if(datalist.size()>=minSizeDatalist) {
				int a=0;
				for(int i=0;i<datalist.size();i++) {
					a+=datalist.get(i).y;
				}
				if(a==0||a==datalist.size()) {
					if(a==0) {
						this.label=0;
					}else {
						this.label=1;
					}
					
					//return this;
				}
				else {
					double best_avg_entropy=Double.MAX_VALUE;
					int best_attr=-1;
					double best_thre=-1;
					
					for(int j=0;j<2;j++) {
						int attri=j;
						for(Datum d:datalist) {
							double thre=d.x[j];
							ArrayList<Datum> D1=new ArrayList<Datum>();
							ArrayList<Datum> D2=new ArrayList<Datum>();
							for(Datum e:datalist) {
								if(e.x[j]<thre) {
									D1.add(e);
								}else {
									D2.add(e);
								}
							}
							double  cur = calcEntropy(D1)*D1.size()/datalist.size() + calcEntropy(D2)*D2.size()/datalist.size();
							if(best_avg_entropy>cur) {
								best_avg_entropy=cur;
								best_attr=attri;
								best_thre=thre;
							}
						}
					}
					
					this.attribute=best_attr;
					this.threshold=best_thre;
					this.leaf=false;
					ArrayList<Datum> D1=new ArrayList<Datum>();
					ArrayList<Datum> D2=new ArrayList<Datum>();
						for(Datum c:datalist) {
							if(c.x[attribute]<threshold) {
								D1.add(c);
							}else{
								D2.add(c);
							}
					}
					this.left=(new DTNode()).fillDTNode(D1);
					this.right=(new DTNode()).fillDTNode(D2);
					//return this;
					}
			}else {
				this.label=findMajority(datalist);
			}
				return this;
		}



		//This is a helper method. Given a datalist, this method returns the label that has the most
		// occurences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist)
		{
			int l = datalist.get(0).x.length;
			int [] votes = new int[l];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			int max = -1;
			int max_index = -1;
			//find the label with the max occurrences
			for (int i = 0 ; i < l ;i++)
			{
				if (max<votes[i])
				{
					max = votes[i];
					max_index = i;
				}
			}
			return max_index;
		}




		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {
			//YOUR CODE HERE
			DTNode node=new DTNode();
			node=rootDTNode;
			if(node.leaf) {
				return node.label;
			}
			while(!(node.leaf)) {
				int attri=node.attribute;
				double thre=node.threshold;
				if(xQuery[attri]>=thre) {
					node=node.right;
				}else {
					node=node.left;
				}
			}
			return node.label;

			 //dummy code.  Update while completing the assignment.
		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2)
		{
			//YOUR CODE HERE

			DTNode node1=(DTNode)dt2;
			if(node1==null) {
				return false;
			}
			if(node1.leaf&&this.leaf) {
				if(node1.label==this.label) {
					return true;
				}else{
					return false;
				}
			}
			if(node1.attribute!=this.attribute||node1.threshold!=this.threshold) {
				return false;
			}
			
			boolean rightb=false;
			boolean leftb=false;
			
			if(node1.right!=null&&this.right!=null) {
				rightb=this.right.equals(node1.right);
			}else if(node1.right==null&&this.right==null) {
				rightb=true;	
			}
			
			if(node1.left!=null&&this.left!=null) {
				leftb=this.left.equals(node1.left);	
			}else if(node1.left==null&&this.left==null) {
				leftb=true;	
			}
			
			if(leftb==true&&rightb==true) {
				return true;
			}
			return false;
		}
	}



	//Given a dataset, this retuns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist)
	{
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		DTNode node = this.rootDTNode;
		return node.classifyAtNode( xQuery );
	}

    // Checks the performance of a DecisionTree on a dataset
    //  This method is provided in case you would like to compare your
    //results with the reference values provided in the PDF in the Data
    //section of the PDF

    String checkPerformance( ArrayList<Datum> datalist)
	{
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
