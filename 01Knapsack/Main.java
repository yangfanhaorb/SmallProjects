import java.util.Scanner;
public class Main{
	public static void main(String args[]){
		Scanner kb = new Scanner(System.in);
		int n = kb.nextInt();
		int m = kb.nextInt();
		int[] need = new int[n];
		int[] value = new int[n];
		for (int i=0;i<n;i++){
			need[i] = kb.nextInt();
			value[i] = kb.nextInt();
		}
		int[] payoff = new int[m];
		for (int i=0;i<n;i++){
			for (int j=m-1;j>=need[i];j--){
				if (payoff[j]<payoff[j-need[i]]+value[i]){
					payoff[j] = payoff[j-need[i]]+value[i];
				}
			}
		}
		System.out.println(payoff[m-1]);
	}
}