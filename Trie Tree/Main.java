import java.util.Scanner;
public class Main{
	public static class TrieTree{
		int count;
		char ch;
		TrieTree sibling;
		TrieTree parent;
		TrieTree child;

		public TrieTree(char ch){
			this.ch = ch;
			this.sibling = null;
			this.child = null;
			this.parent = null;
			this.count = 0;
		}

		public void insert(String word){
			if (word.equals("")){
				this.count++;
				return;
			}
			if (this.child==null){
				this.count++;
				this.child = new TrieTree(word.charAt(0));
				this.child.insert(word.substring(1,word.length()));
			}
			else{
				TrieTree last = this.child;
				TrieTree temp = this.child;
				while (temp!=null){
					if (temp.ch==word.charAt(0)){
						this.count++;
						temp.insert(word.substring(1,word.length()));
						return;
					}
					else{
						last = temp;
						temp = temp.sibling;
					}
				}
				last.sibling = new TrieTree(word.charAt(0));
				last.sibling.insert(word.substring(1,word.length()));
				this.count++;
			}
		}

		public int findCount(String suf){
			if (suf.equals("")){
				return this.count;
			}
			if (this.child==null){
				return 0;
			}
			else{
				TrieTree last = this.child;
				TrieTree temp = this.child;
				while (temp!=null){
					if (temp.ch==suf.charAt(0)){
						return temp.findCount(suf.substring(1,suf.length()));
					}
					else{
						last = temp;
						temp = temp.sibling;
					}
				}
				return 0;
			}
		}

	}



	public static void main(String args[]){
		Scanner kb = new Scanner(System.in);
		int n = kb.nextInt();
		TrieTree dict = new TrieTree('#');
		for (int i=0;i<n;i++){
			String word = kb.next();
			dict.insert(word);
		}
		int m = kb.nextInt();
		for (int i=0;i<m;i++){
			String suf = kb.next();
			System.out.println(dict.findCount(suf));
		}
	}
}