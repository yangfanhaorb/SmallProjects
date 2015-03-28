import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
public class ShortestPathMain{
	

	//An item in the adjacency list that stores the key for the vertex and the weight of the edge
	//I probably should have named this edge, but anyways...
	public static class AdjItem{
		String key;
		int weight;
		public AdjItem(String key, int weight){
			this.key = key;
			this.weight = weight;
		}
		public String toString(){
			return key+" "+weight;
		}
	}

	public static class Graph{
		int height;
		int width;
		int vertexPerCell;
		//Set up hash table to quickly look up the adjacency list of a given vertex
		HashMap<String,LinkedList<AdjItem>> hash;
		//The hash table where the distance between corners are calculated
		HashMap<String,AdjItem[]> hashCorners;
		//The hash table for the quickly querying the distance between corners
		HashMap<String,Integer> hashCornerDist;
		//The hash table for the quickly querying the path between corners
		HashMap<String,String> hashCornerPath;
		//Constructor for the graph, setting up the height, width, vertex per cell and the hash tables
		Graph(int height,int width, int vertexPerCell){
			this.height = height;
			this.width = width;
			this.vertexPerCell = vertexPerCell;
			hash = new HashMap<String,LinkedList<AdjItem>>();
			hashCorners = new HashMap<String,AdjItem[]>();
			hashCornerDist = new HashMap<String,Integer>();
			hashCornerPath = new HashMap<String,String>();
		}
		//Method for adding edges into this graph
		public void addEdge(String v1, String v2, int weight){
			if (v1.charAt(0)=='g'){
				//Add this edge when v1 and v2 are both corner vertices
				if (v2.charAt(0)=='g'){
					if (hash.containsKey(v1)==true)
						hash.get(v1).add(new AdjItem(v2,weight));
					else{
						hash.put(v1,new LinkedList<AdjItem>());
						hash.get(v1).add(new AdjItem(v2,weight));
					}
					if (hash.containsKey(v2)==true)
						hash.get(v2).add(new AdjItem(v1,weight));
					else{
						hash.put(v2,new LinkedList<AdjItem>());
						hash.get(v2).add(new AdjItem(v1,weight));
					}
				}
				//Add this edge when v1 is corner vertex and v2 is in-cell vertex
				if (v2.charAt(0)=='v'){
					if (hash.containsKey(v1)==true)
						hash.get(v1).add(new AdjItem(v2,weight));
					else{
						hash.put(v1,new LinkedList<AdjItem>());
						hash.get(v1).add(new AdjItem(v2,weight));
					}
					if (hash.containsKey(v2)==true)
						hash.get(v2).add(new AdjItem(v1,weight));
					else{
						hash.put(v2,new LinkedList<AdjItem>());
						hash.get(v2).add(new AdjItem(v1,weight));
					}
				}
			}
			else{
				//Add this edge when v1 and v2 are both in-cell vertices
				if (hash.containsKey(v1)==true)
						hash.get(v1).add(new AdjItem(v2,weight));
				else{
					hash.put(v1,new LinkedList<AdjItem>());
					hash.get(v1).add(new AdjItem(v2,weight));
				}
				if (hash.containsKey(v2)==true)
						hash.get(v2).add(new AdjItem(v1,weight));
				else{
					hash.put(v2,new LinkedList<AdjItem>());
					hash.get(v2).add(new AdjItem(v1,weight));
				}
			}
		}
		//Method for printing all the edges(only from in-cell vertices)
		public void print(){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					for (int k=0;k<vertexPerCell;k++){
						String v = "v"+i+"."+j+'.'+k;
						LinkedList<AdjItem> list = hash.get(v);
						while (list.peek()!=null){
							System.out.println("v"+i+"."+j+'.'+k+" "+list.remove());
						}
					}
				}
			}
		}

		public void badDijkstra(String v1, String v2){
			PriorityQueue<String,Integer> q = new PriorityQueue<String,Integer>();
			HashMap<String,Integer> d = new HashMap<String,Integer>();
			HashMap<String,String> p = new HashMap<String,String>();
			HashMap<String,Boolean> onQueue = new HashMap<String,Boolean>();
			for (int i=0;i<=height;i++){
				for (int j=0;j<=width;j++){
					String key = "g"+i+"."+j;
					d.put(key,Integer.MAX_VALUE);
					p.put(key,null);
					onQueue.put(key,true);
					q.addItem(key,d.get(key));
					if (i<height&&j<width){
						for (int k=0;k<vertexPerCell;k++){
							key = "v"+i+"."+j+'.'+k;
							d.put(key,Integer.MAX_VALUE);
							p.put(key,null);
							onQueue.put(key,true);
							q.addItem(key,d.get(key));
						}
					}
				}
			}
			q.decreasePriority(v1,0);
			d.put(v1,0);
			onQueue.put(v1,false);
			while (q.getSize()!=0){
				String v = q.removeItem();
				LinkedList<AdjItem> list = hash.get(v);
				for (int i=0;i<list.size();i++){
					AdjItem u = list.get(i);
					if (onQueue.get(u.key)==true&&d.get(u.key)>d.get(v)+u.weight){
						q.decreasePriority(u.key,d.get(v)+u.weight);
						d.put(u.key,d.get(v)+u.weight);
						p.put(u.key,v);
					}
					onQueue.put(v,false);
				}
			}
			StringBuilder path = new StringBuilder();
			path.insert(0,"]");
			path.insert(0,v2);
			String curr = p.get(v2);
			while (curr!=null){
				path.insert(0," ,");
				path.insert(0,curr);
				curr = p.get(curr);
			}
			path.insert(0,"[");
			System.out.println(d.get(v2)+" "+path);
		}

		////This calculates the shortest path from a vertex to every other vertex in the same cell with proper output
		public void incellDijkstra(String v1, String v2, int h, int w){
			PriorityQueue<String,Integer> q = new PriorityQueue<String,Integer>();
			HashMap<String,Integer> d = new HashMap<String,Integer>();
			HashMap<String,String> p = new HashMap<String,String>();
			HashMap<String,Boolean> onQueue = new HashMap<String,Boolean>();
			for (int i=0;i<=1;i++){
				for (int j=0;j<=1;j++){
					String key = "g"+(h+i)+"."+(w+j);
					d.put(key,Integer.MAX_VALUE);
					p.put(key,null);
					onQueue.put(key,true);
					q.addItem(key,d.get(key));
				}
			}
			for (int k=0;k<vertexPerCell;k++){
				String key = "v"+h+"."+w+'.'+k;
				d.put(key,Integer.MAX_VALUE);
				p.put(key,null);
				onQueue.put(key,true);
				q.addItem(key,d.get(key));
			}
			q.decreasePriority(v1,0);
			d.put(v1,0);
			onQueue.put(v1,false);
			while (q.getSize()!=0){
				String v = q.removeItem();
				LinkedList<AdjItem> list = hash.get(v);
				for (int i=0;i<list.size();i++){
					AdjItem u = list.get(i);
					if (onQueue.containsKey(u.key)&&onQueue.get(u.key)==true&&d.get(u.key)>d.get(v)+u.weight){
						q.decreasePriority(u.key,d.get(v)+u.weight);
						d.put(u.key,d.get(v)+u.weight);
						p.put(u.key,v);
					}
					onQueue.put(v,false);
				}
			}
			StringBuilder path = new StringBuilder();
			path.insert(0,"]");
			path.insert(0,v2);
			String curr = p.get(v2);
			while (curr!=null){
				path.insert(0," ,");
				path.insert(0,curr);
				curr = p.get(curr);
			}
			path.insert(0,"[");
			System.out.println(d.get(v2)+" "+path);
		}

		//This calculates the shortest path from a vertex to every other vertex in the same cell
		public ReturnPacket cellDijkstra(String v1,int h, int w){
			PriorityQueue<String,Integer> q = new PriorityQueue<String,Integer>();
			HashMap<String,Integer> d = new HashMap<String,Integer>();
			HashMap<String,String> p = new HashMap<String,String>();
			HashMap<String,Boolean> onQueue = new HashMap<String,Boolean>();
			for (int i=0;i<=1;i++){
				for (int j=0;j<=1;j++){
					String key = "g"+(h+i)+"."+(w+j);
					d.put(key,Integer.MAX_VALUE);
					p.put(key,null);
					onQueue.put(key,true);
					q.addItem(key,d.get(key));
				}
			}
			for (int k=0;k<vertexPerCell;k++){
				String key = "v"+h+"."+w+'.'+k;
				d.put(key,Integer.MAX_VALUE);
				p.put(key,null);
				onQueue.put(key,true);
				q.addItem(key,d.get(key));
			}
			q.decreasePriority(v1,0);
			d.put(v1,0);
			onQueue.put(v1,false);
			while (q.getSize()!=0){
				String v = q.removeItem();
				LinkedList<AdjItem> list = hash.get(v);
				for (int i=0;i<list.size();i++){
					AdjItem u = list.get(i);
					if (onQueue.containsKey(u.key)&&onQueue.get(u.key)==true&&d.get(u.key)>d.get(v)+u.weight){
						q.decreasePriority(u.key,d.get(v)+u.weight);
						d.put(u.key,d.get(v)+u.weight);
						p.put(u.key,v);
					}
					onQueue.put(v,false);
				}
			}
			ReturnPacket rp = new ReturnPacket();
			rp.d = d;
			rp.p = p;
			return rp;
		}

		//This calculates the "edges" between the corners
		public void generateDistBetweenCorners(){
			for (int i=0;i<height;i++){
				for (int j=0;j<width;j++){
					String v = "g"+i+"."+j;
					ReturnPacket rp = cellDijkstra(v,i,j);
					for (int k=1;k<=3;k++){
						String u = "g"+(i+(k/2))+"."+(j+(k%2));
						if (hashCorners.containsKey(v)==false){
							hashCorners.put(v,new AdjItem[9]);
							for (int l=0;l<9;l++){
								hashCorners.get(v)[l] = new AdjItem(null,Integer.MAX_VALUE);
							}
							if (rp.d.get(u)<hashCorners.get(v)[4+k+k/2].weight){
								hashCorners.get(v)[4+k+k/2] = new AdjItem(u,rp.d.get(u));
								hashCornerDist.put(v+u,rp.d.get(u));
								hashCornerDist.put(u+v,rp.d.get(u));
								StringBuilder path = new StringBuilder();
								String curr = rp.p.get(u);
								while (!curr.equals(v)){
									path.insert(0,curr+", ");
									curr = rp.p.get(curr);
								}
								path.insert(0, ", ");
								String thepath = path.toString();
								hashCornerPath.put(u+v,thepath);
								hashCornerPath.put(v+u,thepath);
							}
						}
						else{
							if (rp.d.get(u)<hashCorners.get(v)[4+k+k/2].weight){
								hashCorners.get(v)[4+k+k/2] = new AdjItem(u,rp.d.get(u));
								hashCornerDist.put(v+u,rp.d.get(u));
								hashCornerDist.put(u+v,rp.d.get(u));
								StringBuilder path = new StringBuilder();
								String curr = rp.p.get(u);
								while (!curr.equals(v)){
									path.insert(0,curr+", ");
									curr = rp.p.get(curr);
								}
								path.insert(0, ", ");
								String thepath = path.toString();
								hashCornerPath.put(u+v,thepath);
								hashCornerPath.put(v+u,thepath);
							}
						}
						if (hashCorners.containsKey(u)==false){
							hashCorners.put(u,new AdjItem[9]);
							for (int l=0;l<9;l++){
								hashCorners.get(u)[l] = new AdjItem(null,Integer.MAX_VALUE);
							}
							if (rp.d.get(u)<hashCorners.get(u)[8-(4+k+k/2)].weight){
								hashCorners.get(u)[8-(4+k+k/2)] = new AdjItem(v,rp.d.get(u));
								hashCornerDist.put(v+u,rp.d.get(u));
								hashCornerDist.put(u+v,rp.d.get(u));
								StringBuilder path = new StringBuilder();
								String curr = rp.p.get(u);
								while (!curr.equals(v)){
									path.insert(0,curr+", ");
									curr = rp.p.get(curr);
								}
								path.insert(0, ", ");
								String thepath = path.toString();
								hashCornerPath.put(u+v,thepath);
								hashCornerPath.put(v+u,thepath);
							}
						}
						else{
							if (rp.d.get(u)<hashCorners.get(u)[8-(4+k+k/2)].weight){
								hashCorners.get(u)[8-(4+k+k/2)] = new AdjItem(v,rp.d.get(u));
								hashCornerDist.put(v+u,rp.d.get(u));
								hashCornerDist.put(u+v,rp.d.get(u));
								StringBuilder path = new StringBuilder();
								String curr = rp.p.get(u);
								while (!curr.equals(v)){
									path.insert(0,curr+", ");
									curr = rp.p.get(curr);
								}
								path.insert(0, ", ");
								String thepath = path.toString();
								hashCornerPath.put(u+v,thepath);
								hashCornerPath.put(v+u,thepath);
							}
						}
					}
					v = "g"+(i+1)+"."+j;
					rp = cellDijkstra(v,i,j);
					String u = "g"+(i+1)+"."+(j+1);
					if (rp.d.get(u)<hashCorners.get(v)[5].weight){
						hashCorners.get(v)[5] = new AdjItem(u,rp.d.get(u));
						hashCornerDist.put(v+u,rp.d.get(u));
						hashCornerDist.put(u+v,rp.d.get(u));
						StringBuilder path = new StringBuilder();
						String curr = rp.p.get(u);
						while (!curr.equals(v)){
							path.insert(0,curr+", ");
							curr = rp.p.get(curr);
						}
						path.insert(0, ", ");
						String thepath = path.toString();
						hashCornerPath.put(u+v,thepath);
						hashCornerPath.put(v+u,thepath);
					}
					if (rp.d.get(u)<hashCorners.get(u)[3].weight){
						hashCorners.get(u)[3] = new AdjItem(v,rp.d.get(u));
						hashCornerDist.put(v+u,rp.d.get(u));
						hashCornerDist.put(u+v,rp.d.get(u));
						StringBuilder path = new StringBuilder();
						String curr = rp.p.get(u);
						while (!curr.equals(v)){
							path.insert(0,curr+", ");
							curr = rp.p.get(curr);
						}
						path.insert(0, ", ");
						String thepath = path.toString();
						hashCornerPath.put(u+v,thepath);
						hashCornerPath.put(v+u,thepath);
					}
					v = "g"+i+"."+(j+1);
					rp = cellDijkstra(v,i,j);
					u = "g"+(i+1)+"."+(j+1);
					if (rp.d.get(u)<hashCorners.get(v)[7].weight){
						hashCorners.get(v)[7] = new AdjItem(u,rp.d.get(u));
						hashCornerDist.put(v+u,rp.d.get(u));
						hashCornerDist.put(u+v,rp.d.get(u));
						StringBuilder path = new StringBuilder();
						String curr = rp.p.get(u);
						while (!curr.equals(v)){
							path.insert(0,curr+", ");
							curr = rp.p.get(curr);
						}
						path.insert(0, ", ");
						String thepath = path.toString();
						hashCornerPath.put(u+v,thepath);
						hashCornerPath.put(v+u,thepath);
					}
					if (rp.d.get(u)<hashCorners.get(u)[1].weight){
						hashCorners.get(u)[1] = new AdjItem(v,rp.d.get(u));
						hashCornerDist.put(v+u,rp.d.get(u));
						hashCornerDist.put(u+v,rp.d.get(u));
						StringBuilder path = new StringBuilder();
						String curr = rp.p.get(u);
						while (!curr.equals(v)){
							path.insert(0,curr+", ");
							curr = rp.p.get(curr);
						}
						path.insert(0, ", ");
						String thepath = path.toString();
						hashCornerPath.put(u+v,thepath);
						hashCornerPath.put(v+u,thepath);
					}
					v = "g"+i+"."+(j+1);
					rp = cellDijkstra(v,i,j);
					u = "g"+(i+1)+"."+j;
					if (rp.d.get(u)<hashCorners.get(v)[6].weight){
						hashCorners.get(v)[6] = new AdjItem(u,rp.d.get(u));
						hashCornerDist.put(v+u,rp.d.get(u));
						hashCornerDist.put(u+v,rp.d.get(u));
						StringBuilder path = new StringBuilder();
						String curr = rp.p.get(u);
						while (!curr.equals(v)){
							path.insert(0,curr+", ");
							curr = rp.p.get(curr);
						}
						path.insert(0, ", ");
						String thepath = path.toString();
						hashCornerPath.put(u+v,thepath);
						hashCornerPath.put(v+u,thepath);
					}
					if (rp.d.get(u)<hashCorners.get(u)[2].weight){
						hashCorners.get(u)[2] = new AdjItem(v,rp.d.get(u));
						hashCornerDist.put(v+u,rp.d.get(u));
						hashCornerDist.put(u+v,rp.d.get(u));
						StringBuilder path = new StringBuilder();
						String curr = rp.p.get(u);
						while (!curr.equals(v)){
							path.insert(0,curr+", ");
							curr = rp.p.get(curr);
						}
						path.insert(0, ", ");
						String thepath = path.toString();
						hashCornerPath.put(u+v,thepath);
						hashCornerPath.put(v+u,thepath);
					}
				}
			}
			// for (int i=0;i<=height;i++){
			// 	for (int j=0;j<=width;j++){
			// 		String v = "g"+i+"."+j;
			// 		for (int k=0;k<9;k++){
			// 			System.out.println(v+" "+hashCorners.get(v)[k].key+" "+hashCorners.get(v)[k].weight);
			// 		}
			// 	}
			// }
		}

		//This calculates the shortest path from one corner to every corner vertex
		public ReturnPacket wholeGraphDijkstra(String v1){
			PriorityQueue<String,Integer> q = new PriorityQueue<String,Integer>();
			HashMap<String,Integer> d = new HashMap<String,Integer>();
			HashMap<String,String> p = new HashMap<String,String>();
			HashMap<String,Boolean> onQueue = new HashMap<String,Boolean>();
			for (int i=0;i<=height;i++){
				for (int j=0;j<=width;j++){
					String key = "g"+i+"."+j;
					d.put(key,Integer.MAX_VALUE);
					p.put(key,null);
					onQueue.put(key,true);
					q.addItem(key,d.get(key));
				}
			}
			q.decreasePriority(v1,0);
			d.put(v1,0);
			onQueue.put(v1,false);
			while (q.getSize()!=0){
				String v = q.removeItem();
				AdjItem[] list = hashCorners.get(v);
				for (int i=0;i<list.length;i++){
					AdjItem u = list[i];
					if (u.key!=null&&onQueue.get(u.key)==true&&d.get(u.key)>d.get(v)+u.weight){
						q.decreasePriority(u.key,d.get(v)+u.weight);
						d.put(u.key,d.get(v)+u.weight);
						p.put(u.key,v);
					}
					onQueue.put(v,false);
				}
			}
			ReturnPacket rp = new ReturnPacket();
			rp.d = d;
			rp.p = p;
			return rp;
		}

		//The optimized Dijkstra algorithm
		public void goodDijkstra(String v1, String v2){
			String coordinate1 = v1.substring(1);
			String coordinate2 = v2.substring(1);
			int height1 = Integer.parseInt(coordinate1.split("\\.")[0]);
			int height2 = Integer.parseInt(coordinate2.split("\\.")[0]);
			int width1 = Integer.parseInt(coordinate1.split("\\.")[1]);
			int width2 = Integer.parseInt(coordinate2.split("\\.")[1]);
			if (height1==height2&&width1==width2)
				incellDijkstra(v1,v2,height1,width1);
			else{
				ReturnPacket rpv1 = cellDijkstra(v1,height1,width1);
				ReturnPacket rpv2 = cellDijkstra(v2,height2,width2);
				String[] v1Corners = new String[4];
				String[] v2Corners = new String[4];
				for (int i=0;i<=1;i++){
					for (int j=0;j<=1;j++){
						v1Corners[i*2+j] = "g"+(height1+i)+"."+(width1+j);
						v2Corners[i*2+j] = "g"+(height2+i)+"."+(width2+j);
					}
				}
				ReturnPacket[] rpwg = new ReturnPacket[4];
				for (int i=0;i<4;i++){
					rpwg[i] = wholeGraphDijkstra(v1Corners[i]);
				}
				int totalDistance = Integer.MAX_VALUE;
				String v1Corner = "";
				String v2Corner = "";
				int v1num = -1;
				for (int i=0;i<4;i++){
					for (int j=0;j<4;j++){
						if (hash.containsKey(v1Corners[i])==true&&hash.containsKey(v2Corners[j])==true){
							if (rpv1.d.get(v1Corners[i])+rpwg[i].d.get(v2Corners[j])+rpv2.d.get(v2Corners[j])<totalDistance){
								totalDistance = rpv1.d.get(v1Corners[i])+rpwg[i].d.get(v2Corners[j])+rpv2.d.get(v2Corners[j]);
								v1Corner = v1Corners[i];
								v2Corner = v2Corners[j];
								v1num = i;
							}
						}
					}
				}
				StringBuilder path = new StringBuilder();
				String curr = rpv2.p.get(v2Corner);
				//generate the path from a corner vertex to destination vertex
				//path.append("("+rpv2.d.get(v2Corner)+")");
				path.append(v2Corner);
				while (curr!=null){
					path.append(", ");
					path.append(curr);
					curr = rpv2.p.get(curr);
				}
				path.append("]");
				//generate the path between the corner vertices
				curr = v2Corner;
				while (!curr.equals(v1Corner)){
					if (!curr.equals(v2Corner)){
						path.insert(0,curr);
					}
					String prev = rpwg[v1num].p.get(curr);
					path.insert(0,hashCornerPath.get(curr+prev));
					//path.insert(0,"("+hashCornerDist.get(curr+prev)+")");
					curr = prev;
				}
				//generate the path between starting vertex to a corner vertex
				curr = v1Corner;
				while (!curr.equals(v1)){
					if (!curr.equals(v1Corner)||!v1Corner.equals(v2Corner)){
						path.insert(0,curr);
						path.insert(0,", ");
					}
					else path.insert(0,", ");
					curr = rpv1.p.get(curr);
				}
				//path.insert(0,"("+rpv1.d.get(v1Corner)+")");
				path.insert(0,v1);
				path.insert(0,"[");
				System.out.println(totalDistance+" "+path);
			}
		}
	}

	static class ReturnPacket{
		HashMap<String,Integer> d;
		HashMap<String,String> p;
	}


	public static void main(String args[]){
		Scanner kb = new Scanner(System.in);
		Graph g = new Graph(kb.nextInt(),kb.nextInt(),kb.nextInt());
		while (true){
			//If the new line starts with "queries", then break the while loop
			String v1 = kb.next();
			if (v1.equals("queries"))
				break;
			String v2 = kb.next();
			int weight = kb.nextInt();
			g.addEdge(v1,v2,weight);
		}
		//g.print();
		//Calculate the "edges" between corners, takes (k^2)logk
		g.generateDistBetweenCorners();
		//Read the queries k times and do the optimized Dijkstra, which is klogk each time
		while (kb.hasNext()){
			String v1 = kb.next();
			String v2 = kb.next();
			//g.badDijkstra(v1,v2);
			g.goodDijkstra(v1,v2);
		}
	}
}