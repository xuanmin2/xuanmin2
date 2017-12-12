
public class Tree {
	public static final int limit = 50;
    public static final int maximumGrowth = 10;
	boolean nutrition;//whether the tree has nutrition support
	boolean durability;//whether the tree could withstand harsh weather
	int age;//age of the tree. May not exceed limit.
	int length;//total length of one branch.May be increased when growing
	Tree left;
	Tree right;//tree nodes will be sorted in terms of age (same ages allowed)
	Tree parent;
	public static int count = 0;
	public Tree() {
		
	}
	
	public Tree(boolean a, boolean b, int c) {
		this.nutrition = a;
		this.durability = b;
		this.age = c;
		this.length = 1;
	}
	public Tree(boolean a, boolean b, Tree c, Tree d, int e, Tree f) {//constructor to 
		//self-create and implement a tree
		this.nutrition = a;
		this.durability = b;
		this.age = e;
		this.left = c;
		this.right = d;
		this.parent = f;
		this.length = 1;
	}
	public Tree(Tree a) {//copy another tree
		this.nutrition = a.nutrition;
		this.durability = a.durability;
		this.age = a.age;
	}
	public boolean alive() {//check to see whether a tree can live healthily
		return this.length != 0 && this.getAge() <= limit;
	}
	public int getAge() {
		return this.age;
	}
	public Tree getLeft() {
		return this.left;
	}
	public Tree getRight() {
		return this.right;
	}
	public int getLength() {
		return this.length;
	}
	public void setNutrition(boolean a) {//manually set nutrition
		this.nutrition = a;
	}
	public void weatherAbility() {//check to see whether the durability changes
		if (!nutrition && age == 1) {
			durability = false;
		}
	}
	public boolean grow() {//the tree's actual growth
		if (!this.alive()) {
			this.nutrition = false;
			this.durability = false;
			return false;
		} else {
			this.length += (int) (Math.random() * maximumGrowth + 1) ;
			this.age++;
			return age <= limit;
		}
	}
	public boolean insert(Tree a) {//insert a new tree branch into the existing tree system
		if (!a.alive()) {
			return false;
		} else {
			if (a.getAge() == this.getAge()) {
				if (this.getRight() == null) {
					this.right = a;
					a.parent = this;
					return true;
				} else {
					Tree b = this.right;
					this.right = a;
					a.parent = this;
					a.right = b;
					b.parent = a;
					return true;
				}
			} else if (a.getAge() > this.getAge()) {
				if (this.getRight() == null) {
					this.right = a;
					a.parent = this;
					return true;
				} else {
					return this.right.insert(a);
				}
			}else {
				if (this.getLeft() == null) {
					this.left = a;
					a.parent = this;
					return true;
				} else {
					return this.left.insert(a);
				}	
		}
	}
		
}
	

	public boolean nutritionSupported() {//check to see whether the tree has
		//nutrition support and ensure that the tree is growing properly
		if (!durability || !this.alive() || !nutrition) {
			this.age = 0;
			this.length = 0;
			return false;
		}
		return this.grow();
	}
	public int countHeight(int result) {//ensure that it is called from below, and calculate
		//the total length above a tree branch (for dead part reset the result)
		if (this.parent == null) {
			return this.getLength() + result;
		}
		if (!this.alive()) {
			result = 0;
			return this.parent.countHeight(result);
		}
		return this.parent.countHeight(result + this.getLength());
	}
	/**
	 * Cuts off a branch on the tree resulting in collateral damage.
	 * Once branches have fallen off, dormant buds arise and the tree is unable to grow anymore
	 * in that area.
	 * @return whether the branch is impeded from growing
	 */
	public boolean cutOff() {
		if (this.equals(null)) {
			return false;
		}
		
				
        
        if (this.left == null && this.right == null) {
        	this.age = 0;
        	this.nutrition = false;
        	return true;
        }
		if (this.left == null || this.right == null) {
			
			return true;
		}
		if (!this.left.alive()) {
			return left.cutOff();
		}
		
		if (!this.right.alive()) {
			return right.cutOff();
		}
		
		return true;
	}
	
	/**
	 * Adds a branch to the tree
	 * @return whether the branch is added
	 */
	public boolean addBranch() {
		while (count < limit / 2) {
		if (this.age == 0) {
			return false;
		}
		
		if (this.left == null) {
			this.left = new Tree(true, true, 1);
			this.left.grow();
			count++;
		}
		else {
			this.left.addBranch();
		}
		
		if (this.right == null) {
			this.right = new Tree( true, true, 1);
			this.right.grow();
			count++;
		}
		else {
			this.right.addBranch();
		}
		
		return true;
	}
		return false;
	}
	
	public void badWeather() {//provides water and mineral first. 
		//If the weather becomes harsh, the tree breaks down.
		nutrition = true;
		if (!durability) {
		   age = 0;
		   length = 0;
		}
	}

	public static void main (String[] args) {
		Tree a = new Tree(true, true, 1);
		Tree b;
		for (int i = 0; i < limit; i++) {
		b = new Tree(true, true, (int) (Math.random() * i + 1));
		if (a.insert(b)) {
			a.nutritionSupported();
			b.nutritionSupported();
			a.weatherAbility();
			b.weatherAbility();
			a.addBranch();
			a.badWeather();
			b.badWeather();
			System.out.println("parentAge" + i + " = " + a.age);
			System.out.println("parentLength" + i + " = " + a.length);
			System.out.println("branchAge" + i + " = " + b.age);
			System.out.println("branchLength" + i + " = " + b.length);
			System.out.println("*total length is " + b.countHeight(0));
		}
	}
		for (int i = limit; i < limit * 2; i++) {
			if (i % 5 == 3) {
				b = new Tree(false, true, (int) (Math.random() * i + 1));			
				if (a.insert(b)) {
					a.nutritionSupported();
					b.nutritionSupported();
					a.weatherAbility();
					b.weatherAbility();
					a.addBranch();
					a.badWeather();
					b.badWeather();
					System.out.println("parentAge" + i + " = " + a.age);
					System.out.println("parentLength" + i + " = " + a.length);
					System.out.println("branchAge" + i + " = " + b.age);
					System.out.println("branchLength" + i + " = " + b.length);
					System.out.println("*total length is " + b.countHeight(0));
				}
			} else {
				if (i % 5 == 4) {
				b = new Tree(true, false, (int) (Math.random() * i + 1));
				if (a.insert(b)) {
					a.nutritionSupported();
					b.nutritionSupported();
					a.weatherAbility();
					b.weatherAbility();
					a.addBranch();
					a.badWeather();
					b.badWeather();
					System.out.println("parentAge" + i + " = " + a.age);
					System.out.println("parentLength" + i + " = " + a.length);
					System.out.println("branchAge" + i + " = " + b.age);
					System.out.println("branchLength" + i + " = " + b.length);
					System.out.println("*total length is " + b.countHeight(0));
				}
			} else {
				b = new Tree(true, true, (int) (Math.random() * i + 1));
				if (a.insert(b)) {
					a.nutritionSupported();
					b.nutritionSupported();
					a.weatherAbility();
					b.weatherAbility();
					a.addBranch();
					a.badWeather();
					b.badWeather();
					System.out.println("parentAge" + i + " = " + a.age);
					System.out.println("parentLength" + i + " = " + a.length);
					System.out.println("branchAge" + i + " = " + b.age);
					System.out.println("branchLength" + i + " = " + b.length);
					System.out.println("*total length is " + b.countHeight(0));
			}
		}
		
}
}
	}
}
