import java.util.Scanner;
public class Main{
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);
		int[] numbers = new int[3];
		int[] differences = new int[3];
		int[] xyz = new int[3];
		for (int i=0;i<3;i++){
			xyz[i] = kb.nextInt();
		}
		int max = 0;
		String input = kb.next();
		for (int i=0;i<input.length();i++){
			char nextChar = input.charAt(i);
			if (nextChar=='R'){
				numbers[0]++;
				differences[0]++;
				differences[1]++;
			}
			else if (nextChar=='Y'){
				numbers[1]++;
				differences[0]--;
				differences[2]++;
			}
			else if (nextChar=='B'){
				numbers[2]++;
				differences[1]--;
				differences[2]--;
			}
			if (numbers[0]+numbers[1]+numbers[2]>max){
					max = numbers[0]+numbers[1]+numbers[2];
			}
			if (match(differences,xyz)){
				numbers = new int[3];
				differences = new int[3];
			}
		}
		System.out.println(max);
	}

	public static boolean match(int[] diff, int[] xyz){
		int[] abs = new int[3];
		for (int i=0;i<3;i++){
			if (diff[i]<0){
				abs[i] = -diff[i];
			}
			else abs[i] = diff[i];
		}
		if (abs[0]==xyz[0]){
			if (abs[1]==xyz[1]){
				if (abs[2]==xyz[2]){
					return true;
				}
			}
			else if (abs[1]==xyz[2]&&abs[2]==xyz[1]){
				return true;
			}
		}
		else if (abs[0]==xyz[1]){
			if (abs[1]==xyz[0]){
				if (abs[2]==xyz[2]){
					return true;
				}
			}
			else if (abs[1]==xyz[2]&&abs[2]==xyz[0]){
				return true;
			}
		}
		else if (abs[0]==xyz[2]){
			if (abs[1]==xyz[0]){
				if (abs[2]==xyz[1]){
					return true;
				}
			}
			else if (abs[1]==xyz[1]&&abs[2]==xyz[0]){
				return true;
			}
		}
		return false;
	}
}