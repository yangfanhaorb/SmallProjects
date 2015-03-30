import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class CoinChange {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner kb = new Scanner(System.in);
        int n = kb.nextInt();
        int m  =kb.nextInt();
        int[] c = new int[m];
        for (int i=0;i<m;i++){
            c[i] = kb.nextInt();
        }
        long[][] a = new long[m+1][n+1];
        a[0][0] = 1;
        for (int i=1;i<m+1;i++){
            for (int j=0;j<n+1;j++){
                int k = j;
                while (k>=0){
                    a[i][j] = a[i][j] + a[i-1][k];
                    k = k-c[i-1];
                }
            }
        }
        System.out.println(a[m][n]);
    }
}