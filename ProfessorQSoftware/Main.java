import java.util.Scanner;
import java.io.*;
import java.util.Vector;
import java.util.HashMap;
public class Main{

	public static class Module{
		Signal start;
		Signal[] generate = null;
		long count;

		public Module(Signal start, Signal[] generate){
			this.start = start;
			this.generate = generate;
			this.count  = 0;
		}
		public void visit(){
			count = (count+1)%142857;
			if (generate==null){
				return;
			}
			else{
				for (int i=0;i<generate.length;i++){
					generate[i].visit();
				}
			}
		}
	}

	public static class Signal{
		Vector<Module> start = new Vector<Module>();
		String code;
		public Signal(String code){
			this.code = code;
		}


		public void visit(){
			if (start.isEmpty()){
				return;
			}
			else{
				for (int i=0;i<start.size();i++){
					start.get(i).visit();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		String line = kb.readLine();
		int t = Integer.parseInt(line);
		for (int i=0;i<t;i++){
			HashMap<String,Signal> map = new HashMap<String,Signal>();
			line = kb.readLine();
			String[] tokens = line.split(" ");
			int n = Integer.parseInt(tokens[0]);
			int m = Integer.parseInt(tokens[1]);
			line = kb.readLine();
			tokens = line.split(" ");
			Signal[] startSignals = new Signal[m];
			Module[] modules = new Module[n];
			for (int j=0;j<m;j++){
				Signal temp = new Signal(tokens[j]);
				map.put(tokens[j],temp);
				startSignals[j] = temp;
			}
			for (int j=0;j<n;j++){
				line = kb.readLine();
				 tokens = line.split(" ");
				Signal start;
				if (map.containsKey(tokens[0])){
					start = map.get(tokens[0]);
				}
				else{
					start = new Signal(tokens[0]);
					map.put(tokens[0],start);
				}
				Signal[] generate = new Signal[Integer.parseInt(tokens[1])];
				for (int k=2;k<tokens.length;k++){
					Signal temp;
					if (map.containsKey(tokens[k])){
						temp = map.get(tokens[k]);
					}
					else{
						temp = new Signal(tokens[k]);
						map.put(tokens[k],temp);
					}
					generate[k-2] = temp;
				}
				modules[j] = new Module(start,generate);
				start.start.add(modules[j]);
			}
			for (int j=0;j<m;j++){
				startSignals[j].visit();
			}
			for (int j=0;j<n;j++){
				System.out.print(modules[j].count+" ");
			}
			System.out.println();
		}
	}
}