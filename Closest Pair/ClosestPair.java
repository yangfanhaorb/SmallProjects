import java.lang.Math;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
public class ClosestPair{
    public static double[][] xList;
    public static double[][] yList;
    public static double sd = Double.MAX_VALUE;
    public static double[] point1 = new double[2];
    public static double[] point2 = new double[2];
    public static void main(String args[]){
        Date date = new Date();
        double time1 = date.getTime();
        Scanner kb = new Scanner(System.in);
        int num = kb.nextInt();
        double[][] list = new double[num][2];
        for (int i=0;i<num;i++){
            list[i][0] = kb.nextDouble();
            list[i][1] = kb.nextDouble();
        }
        double[][] xList = new double[num][2];
        double[][] yList = new double[num][2];
        for (int i=0;i<num;i++){
            xList[i][0] = list[i][0];
            xList[i][1] = list[i][1];
            yList[i][0] = list[i][0];
            yList[i][1] = list[i][1];
        }
        //Sorting xList by y and then by x ensures it's in sorted by x and tie-broken by y
        mergeSort(xList,1,0,num-1);
        mergeSort(xList,0,0,num-1);
        mergeSort(yList,1,0,num-1);
        findClosestPair(xList,yList);
        System.out.println("("+point1[0]+","+point1[1]+")-("+point2[0]+","+point2[1]+")="+sd);
        Date date2 = new Date();
        double time2 = date2.getTime();
        double diff = time2-time1;
        System.out.println(diff);
    }
    
    public static double findDP(double[][] aList){
        int l = aList.length;
        return (aList[l/2-1][0]+aList[l/2][0])/2;
    }
    
    public static double findDPY(double[][] aList){
        int l = aList.length;
        return (aList[l/2-1][1]+aList[l/2][1])/2;
    }
    
    public static void thisIsClosest(double val,double[] p1,double[] p2){
        sd = val;
        if (p1[0]<p2[0]){
            point1[0] = p1[0];
            point1[1] = p1[1];
            point2[0] = p2[0];
            point2[1] = p2[1];
        }
        else if (p1[0]==p2[0]&&p1[1]<p2[1]){
            point1[0] = p1[0];
            point1[1] = p1[1];
            point2[0] = p2[0];
            point2[1] = p2[1];
        }
        else{
            point2[0] = p1[0];
            point2[1] = p1[1];
            point1[0] = p2[0];
            point1[1] = p2[1];
        }
    }
    
    public static void bruteForce(double[][] aList){
        if (aList.length==2){
            double temp = compDist(aList[0],aList[1]);
            if (temp<sd){
                thisIsClosest(temp,aList[0],aList[1]);
            }
            return;
        }
        double temp01 = compDist(aList[0],aList[1]);
        double temp02 = compDist(aList[0],aList[2]);
        double temp12 = compDist(aList[1],aList[2]);
        if(temp01<temp02&temp01<temp12&&temp01<sd)
            thisIsClosest(temp01,aList[0],aList[1]);
        if(temp02<temp01&temp02<temp12&&temp02<sd)
            thisIsClosest(temp02,aList[0],aList[2]);
        if(temp12<temp01&temp12<temp02&&temp12<sd)
            thisIsClosest(temp12,aList[1],aList[2]);
    }
    
    public static void findClosestPair(double[][] aList,double[][] bList){
        //bruteforce cases wih less than three points
        if (aList.length<=3){
            bruteForce(aList);
            return;
        }
        //find the dividing point(actually dividing line)
        double dp = findDP(aList);
        //find the tie-breaking y point
        double dpy = findDPY(aList);
        //divide points into two parts and set up corresponding lists for them
        //sorted with respect to x-coordinate and y-coordinate respectively
        double[][] xlist1 = new double[aList.length/2][2];
        double[][] ylist1 = new double[aList.length/2][2];
        double[][] xlist2 = new double[aList.length-aList.length/2][2];
        double[][] ylist2 = new double[aList.length-aList.length/2][2];
        for (int i=0;i<aList.length/2;i++){
            xlist1[i][0]=aList[i][0];
            xlist1[i][1]=aList[i][1];
        }
        for (int i=aList.length/2;i<aList.length;i++){
            xlist2[i-aList.length/2][0]=aList[i][0];
            xlist2[i-aList.length/2][1]=aList[i][1];
        }
        int k=0;
        for (int i=0;i<bList.length;i++){
            if (bList[i][0]<dp){
                ylist1[k][0]=bList[i][0];
                ylist1[k][1]=bList[i][1];
                k++;
            }
            if (bList[i][0]==dp&&bList[i][1]<dpy){
                ylist1[k][0]=bList[i][0];
                ylist1[k][1]=bList[i][1];
                k++;
            }
        }
        k=0;
        for (int i=0;i<bList.length;i++){
            if (bList[i][0]>dp){
                ylist2[k][0]=bList[i][0];
                ylist2[k][1]=bList[i][1];
                k++;
            }
            if (bList[i][0]==dp&&bList[i][1]>dpy){
                ylist2[k][0]=bList[i][0];
                ylist2[k][1]=bList[i][1];
                k++;
            }
        }
        //divide
        findClosestPair(xlist1,ylist1);
        findClosestPair(xlist2,ylist2);
        //generate the list of the points in the corridor
        ArrayList<Integer> cList = new ArrayList<Integer>();
        for (int i=0;i<bList.length;i++){
            if (Math.abs(bList[i][0]-dp)<sd){
                cList.add(i);
            }
        }
        //compare the distance of the points within the corridor with the current shortest distance
        for (int i=0;i<cList.size();i++){
            int j=i+1;
            while (j<cList.size()&&Math.abs(bList[cList.get(i)][1]-bList[cList.get(j)][1])<sd){
                double temp = compDist(bList[cList.get(i)],bList[cList.get(j)]);
                if (temp<sd)
                    thisIsClosest(temp,bList[cList.get(i)],bList[cList.get(j)]);
                j++;
            }
        }
    }
    public static double compDist(double[] p1, double[] p2){
        double dx = p1[0]-p2[0];
        double dy = p1[1]-p2[1];
        return Math.sqrt(dx*dx+dy*dy);
    }
    
    public static void mergeSort(double[][] list,int xy,int s,int e){
        if (e>s){
            int m = (s+e)/2;
            mergeSort(list,xy,s,m);
            mergeSort(list,xy,m+1,e);
            merge(list,xy,s,m,e);
        }
    }
    
    public static void merge(double[][] list,int xy,int s,int m,int e){
        int i=s;
        int j=m+1;
        double[][] temp = new double[e-s+1][2];
        int k=0;
        while (i<=m&&j<=e){
            if (list[i][xy]<=list[j][xy]){
                temp[k][0]=list[i][0];
                temp[k][1]=list[i][1];
                k++;
                i++;
            }
            else{
                temp[k][0]=list[j][0];
                temp[k][1]=list[j][1];
                k++;
                j++;
            }
        }
        while (i<=m){
            temp[k][0]=list[i][0];
            temp[k][1]=list[i][1];
            k++;
            i++;
        }
        while (j<=e){
            temp[k][0]=list[j][0];
            temp[k][1]=list[j][1];
            k++;
            j++;
        }
        for (int t=0;t<e-s+1;t++){
            list[s+t][0]=temp[t][0];
            list[s+t][1]=temp[t][1];
        }
    }
    
}