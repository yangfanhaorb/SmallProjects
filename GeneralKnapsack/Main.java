import java.util.Scanner;
public class Main{
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		int n = kb.nextInt();
		int m = kb.nextInt();
		int[] need = new int[n];
		int[] value = new int[n];
		int[] payout = new int[m];
		for (int i=0;i<n;i++){
			need[i] = kb.nextInt();
			value[i] = kb.nextInt();
		}
		for (int i=0;i<m;i++){
			for (int j=0;j<n;j++){
				if (i>need[j]){
					if (payout[i-need[j]]+value[j]>payout[i]){
						payout[i] = payout[i-need[j]]+value[j];
					}
				}
			}
		}
		System.out.println(payout[m-1]);
	}
}