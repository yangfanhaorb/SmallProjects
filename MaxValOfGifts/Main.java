import java.util.Scanner;
public class Main{
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		int n = kb.nextInt();
		int m = kb.nextInt();
		int[][] edge = new int[n][m];
		int[][] value = new int[n][m];
		for (int i=0;i<n;i++){
			for (int j=0;j<m;j++){
				edge[i][j] = kb.nextInt();
			}
		}
		for (int i=0;i<n+m-1;i++){
			for (int j=0;j<=i;j++){
				if (j<n&&(i-j)<m){
					int val1=0;
					int val2=0;
					if (j-1>=0){
						val1 = value[j-1][i-j];
					}
					if (i-j-1>=0){
						val2 = value[j][i-j-1];
					}
					int val=0;
					if (val1>val2){
						val = val1;
					}
					else val = val2;
					value[j][i-j] = val+edge[j][i-j];
				}
			}
		}
		System.out.println(value[n-1][m-1]);
	}
}