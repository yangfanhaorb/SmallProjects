import java.util.*;
public class SortingAlgos{
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		int n = kb.nextInt();
		int[] list = new int[n];
		for (int i=0;i<n;i++){
			list[i] = kb.nextInt();
		}
		//quickSort(list,0,n-1);
		//selectionSort(list);
		//mergesort(list,0,n-1);
		countingSort(list);
		for (int i=0;i<n;i++){
			System.out.print(list[i]+" ");
		}
	}

	public static void selectionSort(int[] list){
		int n = list.length;
		for (int i=0;i<n-1;i++){
			int smallest = i;
			for (int j=i+1;j<n;j++){
				if (list[j]<list[smallest])
					smallest = j;
			}
			if (smallest!=i){
				int temp = list[i];
				list[i] = list[smallest];
				list[smallest] = temp;
			}
		}
	}

	public static void quickSort(int[] list, int p, int r){
		int n = r-p+1;
		if (n==1)
			return;
		if (n==2){
			if (list[p]>list[r]){
				int temp = list[p];
				list[p] = list[r];
				list[r] = temp;
			}
			return;
		}
		int q = partition(list,p,r);
		quickSort(list,p,q);
		quickSort(list,q+1,r);
	}

	public static int partition(int[] list, int p, int r){
		int n = r-p+1;
		Random rg = new Random();
		int pivot = p+rg.nextInt(n);
		int temp = list[pivot];
		list[pivot] = list[r];
		list[r] = temp;
		pivot = list[r];
		int i=p-1;
		for (int j=p;j<r;j++){
			if (list[j]<pivot){
				i++;
				temp = list[j];
				list[j] = list[i];
				list[i] = temp;
			}
		}
		temp = list[i+1];
		list[i+1] = list[r];
		list[r] = temp;
		return i+1;
	}

	public static void mergesort(int[] list, int p, int r){
		int n = r-p+1;
		if (n==1)
			return;
		if (n==2){
			if (list[p]>list[r]){
				int temp = list[p];
				list[p] = list[r];
				list[r] = temp;
			}
			return;
		}
		int q = (p+r)/2;
		mergesort(list,p,q);
		mergesort(list,q+1,r);
		merge(list,p,q,q+1,r);
	}

	public static void merge(int[] list, int p1, int r1,
										int p2, int r2){
		int n = r2-p1+1;
		int[] tlist = new int[n];
		int i = p1;
		int j = p2;
		int k = 0;
		while (i<=r1&&j<=r2){
			while (i<=r1&&list[i]<=list[j]){
				tlist[k] = list[i];
				i++;
				k++;
			}
			if (i>r1) break;
			while (j<=r2&&list[j]<=list[i]){
				tlist[k] = list[j];
				j++;
				k++;
			}
		}
		while (i<=r1){
			tlist[k] = list[i];
			i++;
			k++;
		}
		while (j<=r2){
			tlist[k] = list[j];
			j++;
			k++;
		}
		for (int l=0;l<n;l++){
			list[p1+l] = tlist[l];
		}
	}

	public static void countingSort(int[] list){
		int n = list.length;
		int largest = list[0];
		int smallest = list[0];
		for (int i=1;i<n;i++){
			if (largest<list[i]){
				largest = list[i];
			}
			else if (smallest>list[i])
				smallest = list[i];
		}
		int k = largest-smallest+1;
		int[] alist = new int[k];
		for (int i=0;i<n;i++){
			alist[list[i]-smallest]++;
		}
		for (int i=1;i<k;i++){
			alist[i] = alist[i]+alist[i-1];
		}
		int[] blist = new int[n];
		for (int i=n-1;i>=0;i--){
			blist[alist[list[i]-smallest]-1] = list[i];
			alist[list[i]-smallest]--;
		}
		for (int i=0;i<n;i++){
			list[i] = blist[i];
		}
	}
}