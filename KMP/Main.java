//KMP String Matching Algorithm
import java.util.Scanner;
public class Main{
	public static void main(String args[]){
		Scanner kb = new Scanner(System.in);
		int n = kb.nextInt();
		for (int i=0;i<n;i++){
			String par = kb.next();
			String str = kb.next();
			int[] next = findNext(par);
			System.out.println(findOcc(str,par,next));
		}
	}

	public static int[] findNext(String par){
		int[] next = new int[par.length()];
		next[0] = -1;
		for (int i=0;i<par.length()-1;i++){
			int q = next[i];
			int p = i;
			while (q>=0){
				if (par.charAt(q+1)==par.charAt(p+1)){
					next[i+1] = q+1;
					break;
				}
				q = next[q];
			}
			if (q==-1){
				if (par.charAt(q+1)==par.charAt(p+1)){
					next[i+1] = q+1;
				}
				else next[i+1] = q;
			}
		}
		return next;
	}

	public static int findOcc(String str, String par, int[] next){
		int p = -1;
		int q = -1;
		int count = 0;
		while(p!=str.length()-1){
			while (str.charAt(p+1)==par.charAt(q+1)){
				p++;
				q++;
				if (q==par.length()-1){
					count++;
					break;
				}
				if (p==str.length()-1){
					break;
				}
			}
			if (p==str.length()-1){
					break;
			}
			while (q>=0){
				q = next[q];
				if (str.charAt(p+1)==par.charAt(q+1)){
					break;
				}
			}
			if (q<0&&str.charAt(p+1)!=par.charAt(q+1)){
				p++;
			}
		}
		return count;
	}
}