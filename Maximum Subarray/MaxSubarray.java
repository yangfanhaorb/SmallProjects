import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class MaxSubarray{

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner kb = new Scanner(System.in);
        int T = kb.nextInt();
        int n = 0;
        int[] a;
        int[] b;
        for (int i=0;i<T;i++){
            n = kb.nextInt();
            a = new int[n];
            for (int j=0;j<n;j++){
                a[j] = kb.nextInt();
            }
            int currentBest = a[0];
            int currentValue = a[0];
            for (int j=1;j<n;j++){
                if (a[j]+currentValue>a[j]){
                    currentValue = a[j]+currentValue;
                }
                else{
                    currentValue = a[j];
                }
                if (currentValue>currentBest){
                    currentBest = currentValue;
                }
            }
            System.out.print(currentBest+" ");
            int sumOfPositive = 0;
            for (int j=0;j<n;j++){
                if (a[j]>0){
                    sumOfPositive = sumOfPositive + a[j];
                }
            }
            if (sumOfPositive==0){
                sumOfPositive = a[0];
                for (int j=1;j<n;j++){
                    if (a[j]>sumOfPositive){
                        sumOfPositive = a[j];
                    }
                }
            }
            System.out.println(sumOfPositive);
        }
    }
}