import java.util.Scanner;
public class IntervalsMain{

	public static int countLevel=0;
	class Intervals{
		public Intervals parent;
		public Intervals rchild;
		public Intervals lchild;
		public int upperBound;
		public int lowerBound;

		public Intervals(int lowerBound, int upperBound){
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.rchild = null;
			this.lchild = null;
			this.parent = null;
 		}
 		public Intervals(int lowerBound, int upperBound, Intervals parent){
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.rchild = null;
			this.lchild = null;
			this.parent = parent;
 		}
 		public Intervals(int lowerBound, int upperBound, Intervals parent, Intervals rchild, Intervals lchild){
			this.lowerBound = lowerBound;
			this.upperBound = upperBound;
			this.rchild = rchild;
			this.lchild = lchild;
			this.parent = parent;
 		}

 		public Intervals insertLeft(int lowerBound, int upperBound){
 			Intervals temp = new Intervals(lowerBound,upperBound,this);
 			this.lchild = temp;
 			return temp;
 		} 
		public Intervals insertRight(int lowerBound, int upperBound){
 			Intervals temp = new Intervals(lowerBound,upperBound,this);
 			this.rchild = temp;
 			return temp;
 		}
 		public void print(){
 			if (this.lchild!=null){
 				countLevel++;
 				this.lchild.print();
 				countLevel--;
 			}
 			System.out.println(countLevel+" ["+lowerBound+","+upperBound+")");
 			if (this.rchild!=null){
 				countLevel++;
 				this.rchild.print();
 				countLevel--;
 			}
 		} 
	}

	class SplayTree{
		Intervals root;

		public SplayTree(){
			root = null;
		}

		public void add(int lowerBound, int upperBound, boolean withinDelete){
			Intervals temp = root;
			Intervals last = null;
			int count = 0;
			while (temp!=null){
				if (upperBound<=temp.lowerBound){
					last = temp;
					temp = temp.lchild;
					count++;
				} 
				else if (lowerBound>=temp.upperBound){
					last = temp;
					temp = temp.rchild;
					count++;
				}
				else if ((upperBound<=temp.upperBound&&upperBound>=temp.lowerBound)||(lowerBound<=temp.lowerBound&&lowerBound>=temp.upperBound)){
					System.out.println(count+" not added "+lowerBound+" "+upperBound);
					splay(temp);
					return;
				}
			}
			if (withinDelete==false){
				System.out.println(count+" added "+lowerBound+" "+upperBound);
			}
			Intervals toSplay = null;
			if (temp==root){
				root = new Intervals(lowerBound,upperBound);
				return;
			}
			if (upperBound<=last.lowerBound){
				toSplay = last.insertLeft(lowerBound,upperBound);
			}
			else if (lowerBound>=last.upperBound){
				toSplay = last.insertRight(lowerBound,upperBound);
			}
			if (toSplay!=null){
				splay(toSplay);
			}
		}

		public void delete(int lowerBound, int upperBound){
			Intervals temp = root;
			int count = 0;
			Intervals toSplay = null;
			while (temp!=null){
				if (upperBound<=temp.lowerBound){
					toSplay = temp;
					temp = temp.lchild;
					count++;
				}
				else if (lowerBound>=temp.upperBound){
					toSplay = temp;
					temp = temp.rchild;
					count++;
				}
				else if (upperBound<temp.upperBound&&lowerBound==temp.lowerBound){
					Intervals truncated = new Intervals(upperBound,temp.upperBound,temp.parent,temp.rchild,temp.lchild);
					if (truncated.parent==null)
						root = truncated;
					else if (truncated.parent.lchild==temp)
						truncated.parent.lchild = truncated;
					else if (truncated.parent.rchild==temp)
						truncated.parent.rchild = truncated;
					if (truncated.lchild!=null)
						truncated.lchild.parent = truncated;
					if (truncated.rchild!=null)
						truncated.rchild.parent = truncated;
					System.out.println(count+" truncated "+temp.lowerBound+" "+temp.upperBound);
					toSplay = truncated;
					break;
				}
				else if (upperBound==temp.upperBound&&lowerBound>temp.lowerBound){
					Intervals truncated = new Intervals(temp.lowerBound,lowerBound,temp.parent,temp.rchild,temp.lchild);
					if (truncated.parent==null)
						root = truncated;
					else if (truncated.parent.lchild==temp)
						truncated.parent.lchild = truncated;
					else if (truncated.parent.rchild==temp)
						truncated.parent.rchild = truncated;
					if (truncated.lchild!=null)
						truncated.lchild.parent = truncated;
					if (truncated.rchild!=null)
						truncated.rchild.parent = truncated;
					System.out.println(count+" truncated "+temp.lowerBound+" "+temp.upperBound);
					toSplay = truncated;
					break;
				}
				else if (upperBound==temp.upperBound&&lowerBound==temp.lowerBound){
					toSplay = removeNode(temp);
					System.out.println(count+" removed "+lowerBound+" "+upperBound);
					break;
				}
				else if (upperBound<temp.upperBound&&lowerBound>temp.lowerBound){
					Intervals truncated = new Intervals(temp.lowerBound,lowerBound,temp.parent,temp.rchild,temp.lchild);
					if (truncated.parent==null)
						root = truncated;
					else if (truncated.parent.lchild==temp)
						truncated.parent.lchild = truncated;
					else if (truncated.parent.rchild==temp)
						truncated.parent.rchild = truncated;
					if (truncated.lchild!=null)
						truncated.lchild.parent = truncated;
					if (truncated.rchild!=null)
						truncated.rchild.parent = truncated;
					add(upperBound,temp.upperBound,true);
					System.out.println(count+" split "+lowerBound+" "+upperBound);
					break;
				}
			}
			if (temp==null){
				System.out.println(count+" not found "+lowerBound+" "+upperBound);
			}
			if (toSplay!=null)
				splay(toSplay);
		}

		public Intervals removeNode(Intervals toRemove){
			if (toRemove.lchild==null&toRemove.rchild==null){
				if (toRemove.parent==null)
					root = null;
				else if (toRemove.parent.lchild==toRemove)
					toRemove.parent.lchild = null;
				else if (toRemove.parent.rchild==toRemove)
					toRemove.parent.rchild = null;
				return toRemove.parent;
			}
			else if (toRemove.lchild==null){
				if (toRemove.parent==null)
					root = toRemove.rchild;
				else if (toRemove.parent.lchild==toRemove)
					toRemove.parent.lchild = toRemove.rchild;
				else if (toRemove.parent.rchild==toRemove)
					toRemove.parent.rchild = toRemove.rchild;
				toRemove.rchild.parent = toRemove.parent;
				return toRemove.rchild;
			}
			else if (toRemove.rchild==null){
				if (toRemove.parent==null)
					root = toRemove.lchild;
				else if (toRemove.parent.lchild==toRemove)
					toRemove.parent.lchild = toRemove.lchild;
				else if (toRemove.parent.rchild==toRemove)
					toRemove.parent.rchild = toRemove.lchild;
				toRemove.lchild.parent = toRemove.parent;
				return toRemove.lchild;
			}
			else{
				Intervals successor = findSuccessor(toRemove);
				Intervals toSplay;
				if (successor.parent!=toRemove)
					if (successor.rchild!=null)
					toSplay = successor.rchild;
					else toSplay = successor.parent;
				else if (successor.parent.lchild!=null)
					toSplay = successor.parent.lchild;
				else toSplay = successor;
				removeNode(successor);
				substitute(toRemove,successor);
				return toSplay;
			}
		}

		public Intervals findSuccessor(Intervals toRemove){
			Intervals temp = toRemove.rchild;
			while (temp.lchild!=null){
				temp = temp.lchild;
			}
			return temp;
		}

		public void substitute(Intervals one,Intervals another){
			if (one.parent==null)
				root = another;
			else if (one.parent.lchild==one)
				one.parent.lchild = another;
			else if (one.parent.rchild==one)
				one.parent.rchild = another;
			if (one.rchild!=null)
				one.rchild.parent = another;
			if (one.lchild!=null)
				one.lchild.parent = another;
			another.parent = one.parent;
			another.lchild = one.lchild;
			another.rchild = one.rchild;
		}

		public void splay(Intervals toSplay){
			while (toSplay.parent!=null){
				if (toSplay.parent.parent==null){
					if (toSplay==toSplay.parent.lchild){
						leftZig(toSplay);
						return;
					}
					else if (toSplay==toSplay.parent.rchild){
						rightZig(toSplay);
						return;
					}
				}
				else if (toSplay.parent.parent!=null){
					if (toSplay==toSplay.parent.lchild){
						if (toSplay.parent==toSplay.parent.parent.lchild){
							leftZigZig(toSplay);
						}
						else if (toSplay.parent==toSplay.parent.parent.rchild){
							leftZigZag(toSplay);
						}
					}
					else if (toSplay==toSplay.parent.rchild){
						if (toSplay.parent==toSplay.parent.parent.rchild){
							rightZigZig(toSplay);
						}
						else if (toSplay.parent==toSplay.parent.parent.lchild){
						
							rightZigZag(toSplay);
						}
					}
				}
			}
		}

		public void leftZig(Intervals toSplay){
			Intervals a = toSplay.lchild;
			Intervals b = toSplay.rchild;
			Intervals c = toSplay.parent.rchild;
			root = toSplay;
			toSplay.rchild = toSplay.parent;
			toSplay.parent.lchild = b;
			if (b!=null)
				b.parent = toSplay.parent;
			toSplay.parent.parent = toSplay;
			toSplay.parent = null;
		}

		public void rightZig(Intervals toSplay){
			Intervals a = toSplay.rchild;
			Intervals b = toSplay.lchild;
			Intervals c = toSplay.parent.lchild;
			root = toSplay;
			toSplay.lchild = toSplay.parent;
			toSplay.parent.rchild = b;
			if (b!=null)
				b.parent = toSplay.parent;
			toSplay.parent.parent = toSplay;
			toSplay.parent = null;
		}

		public void leftZigZig(Intervals toSplay){
			Intervals a = toSplay.lchild;
			Intervals b = toSplay.rchild;
			Intervals c = toSplay.parent.rchild;
			Intervals d = toSplay.parent.parent.rchild;
			Intervals eventualParent = toSplay.parent.parent.parent;
			if (eventualParent!=null){
				if (toSplay.parent.parent==eventualParent.lchild)
					eventualParent.lchild = toSplay;
				else eventualParent.rchild = toSplay;
			}
			else
				root = toSplay;
			toSplay.rchild = toSplay.parent;
			toSplay.parent.lchild = b;
			toSplay.parent.rchild = toSplay.parent.parent;
			toSplay.parent.parent.lchild = c;
			if (c!=null)
				c.parent = toSplay.parent.parent;
			if (b!=null)
				b.parent = toSplay.parent;
			toSplay.parent.parent.parent = toSplay.parent;
			toSplay.parent.parent = toSplay;
			if (eventualParent!=null)
				toSplay.parent = eventualParent;
			else{
				root = toSplay;
				toSplay.parent = null;
			}
		}

		public void rightZigZig(Intervals toSplay){
			Intervals a = toSplay.rchild;
			Intervals b = toSplay.lchild;
			Intervals c = toSplay.parent.lchild;
			Intervals d = toSplay.parent.parent.lchild;
			Intervals eventualParent = toSplay.parent.parent.parent;
			if (eventualParent!=null){
				if (toSplay.parent.parent==eventualParent.lchild)
					eventualParent.lchild = toSplay;
				else eventualParent.rchild = toSplay;
			}
			else
				root = toSplay;
			toSplay.lchild = toSplay.parent;
			toSplay.parent.rchild = b;
			toSplay.parent.lchild = toSplay.parent.parent;
			toSplay.parent.parent.rchild = c;
			if (c!=null)
				c.parent = toSplay.parent.parent;
			if (b!=null)
				b.parent = toSplay.parent;
			toSplay.parent.parent.parent = toSplay.parent;
			toSplay.parent.parent = toSplay;
			if (eventualParent!=null)
				toSplay.parent = eventualParent;
			else{
				root = toSplay;
				toSplay.parent = null;
			}
		}

		public void leftZigZag(Intervals toSplay){
			Intervals a = toSplay.lchild;
			Intervals b = toSplay.rchild;
			Intervals c = toSplay.parent.rchild;
			Intervals d = toSplay.parent.parent.lchild;
			Intervals eventualParent = toSplay.parent.parent.parent;
			if (eventualParent!=null){
				if (toSplay.parent.parent==eventualParent.lchild)
					eventualParent.lchild = toSplay;
				else eventualParent.rchild = toSplay;
			}
			else
				root = toSplay;
			toSplay.rchild = toSplay.parent;
			toSplay.lchild = toSplay.parent.parent;
			toSplay.parent.lchild = b;
			toSplay.parent.parent.rchild = a;
			if (b!=null)
				b.parent = toSplay.parent;
			if (a!=null)
				a.parent = toSplay.parent.parent;
			toSplay.parent.parent.parent = toSplay;
			toSplay.parent.parent = toSplay;
			if (eventualParent!=null)
				toSplay.parent = eventualParent;
			else{
				root = toSplay;
				toSplay.parent = null;
			}
		}

		public void rightZigZag(Intervals toSplay){
			Intervals a = toSplay.lchild;
			Intervals b = toSplay.rchild;
			Intervals c = toSplay.parent.lchild;
			Intervals d = toSplay.parent.parent.rchild;
			Intervals eventualParent = toSplay.parent.parent.parent;
			if (eventualParent!=null){
				if (toSplay.parent.parent==eventualParent.lchild)
					eventualParent.lchild = toSplay;
				else eventualParent.rchild = toSplay;
			}
			else
				root = toSplay;
			toSplay.lchild = toSplay.parent;
			toSplay.rchild = toSplay.parent.parent;
			toSplay.parent.rchild = a;
			toSplay.parent.parent.lchild = b;
			if (a!=null)
				a.parent = toSplay.parent;
			if (b!=null)
				b.parent = toSplay.parent.parent;
			toSplay.parent.parent.parent = toSplay;
			toSplay.parent.parent = toSplay;
			if (eventualParent!=null)
				toSplay.parent = eventualParent;
			else{
				root = toSplay;
				toSplay.parent = null;
			}
		}

		public void printTree(){
			if (root!=null)
				root.print();
			else System.out.println("This tree is empty");
		}

	}

	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		IntervalsMain a = new IntervalsMain();
		SplayTree tree = a.new SplayTree();
		while (kb.hasNext()){
			String command = kb.next();
			if (command.equals("add")){
				tree.add(kb.nextInt(),kb.nextInt(),false);
			}
			if (command.equals("remove")){
				tree.delete(kb.nextInt(),kb.nextInt());
			}
		}
		tree.printTree();
	}
}