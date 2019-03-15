import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class algowork4 {
	public static ArrayList<Integer> arr;					//�Է����� ���� ����
	public static ArrayList<String> circle;					//����� c1,c2,c3...
	public static ArrayList<String> text;					//����� t1,t2,t3...
	public static ArrayList<String> group;					//����� g1,g2,g3...
	public static ArrayList<String> line;					//����� l1,l2,l3...
	public static ArrayList<Integer> destination;			//���� ������ ��� ����
	public static ArrayList<Integer> starting_point;		//���� ����� ��� ����
	static PrintWriter fileout;								//��� ��ü
	public static int change_count;							//����ȭ ȣ��� ��� ���� Ƚ�� ī��Ʈ
	public static int[] position_x;							//�Է������� x��ǥ
	public static int[] position_y;							//�Է������� y��ǥ
	
	public static void main(String[] args) {
		arr = new ArrayList<Integer>();
		circle = new ArrayList<String>();
		text = new ArrayList<String>();
		group = new ArrayList<String>();
		line = new ArrayList<String>();
		destination = new ArrayList<Integer>();
		starting_point = new ArrayList<Integer>();
		File file = new File("number31.txt");
		arr.add(999999);
		try {
			Scanner reader = new Scanner(file);
			while(reader.hasNext()) {
				arr.add(reader.nextInt());
			}
			reader.close();
		}	catch (FileNotFoundException e) {
			System.out.println("����");
		}
		for(int i=1; i<=arr.size(); i++){					//����ؽ�Ʈ�� �����
			circle.add("c"+i);
			text.add("t" + i);
			group.add("g" + i);
			line.add("l"+i);
		}
		int[] position_x = {240,  
							120, 360, 
							60, 180, 300, 420, 
							30, 90, 150, 210, 270, 330, 390, 450,
							15, 45, 75, 105, 135, 165, 195, 225, 255, 285, 315, 345, 375, 405, 435, 465
							};
		int[] position_y = {50,
							100, 100,
							150, 150, 150, 150,
							200, 200, 200, 200, 200, 200, 200, 200,
							250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250, 250 
							};
		print_tree(position_x, position_y);
	}
	
	public static void print_tree(int[] position_x, int[] position_y) {
		try {
			int i=0, line_check=0;
			int num=0;
			int n = arr.size()-1;
			change_count=0;
			String temp;										//���ҽ� �ӽ����� ����
			
			fileout = new PrintWriter(new BufferedWriter(new FileWriter("20143127.asu")));
			fileout.println("%animal 2.0");
			fileout.println("title \"algo18work_4_heapify animation creation\"");
			fileout.println("author \"PYW (20143127)\"\r\n");

			fileout.println("{");
			for(int j=0; j<4; j++) {							//��尣 ���� �׸���
				for(i=0; i<Math.pow(2, j); i++) {
					fileout.println(""
							+ "line" + " \"" + line.get(line_check) + "\" " + "(" + position_x[num] + "," + position_y[num] + ") "
							+ "(" + position_x[(num*2)+1] + "," + (j*50+100) + ") " + "color " + "black" + " fwArrow"
							);
					line_check++;
					fileout.println(""
							+ "line" + " \"" + line.get(line_check) + "\" " + "(" + position_x[num] + "," + position_y[num] + ") "
							+ "(" + position_x[(num+1)*2] + "," + (j*50+100) + ") " + "color " + "black" + " fwArrow"
							);
					line_check++;
					num++;
				}
			}
			for(i=0; i< arr.size()-1; i++) {					//��� �׸���
				fileout.println(""
						+ "circle" + " \"" + circle.get(i) + "\" " + "(" + position_x[i] + "," 
						+ position_y[i] + ") " + "radius 12 filled fillColor yellow\r\n"
						+ "text " + "\"" + text.get((i)) + "\" " + "\"" + arr.get(i+1) + "\" " 
						+ "offset " + "(0,0) from " + "\"" + circle.get(i) + "\" "
						+ "north centered" + "");
			}
			fileout.println("}");								//���� �ؽ�Ʈ ����
			for(i=0;i<arr.size()-1;i++) {
				fileout.println("group "+"\""+group.get(i)+"\" "+"\""+circle.get(i)+"\" "+"\""
			+text.get(i)+"\"");
			}
			for(int y = n / 2; y >= 1; y--) {					//����ȭ
				heapify(arr, y, n);
			}
			for(i=0; i<change_count;i++) {						
				fileout.println("{");							//���� ��� ��� �� ���� ����
				fileout.println("fillColor \""+group.get(starting_point.get(i)-1)+"\" red");
				fileout.println("fillColor \""+group.get(destination.get(i)-1)+"\" red");
				fileout.println("}");
				
				fileout.println("{");							//����
				fileout.println("line "+"\""+"move"+"\" "+"("+position_x[starting_point.get(i)-1]+","
				+position_y[starting_point.get(i)-1]+") "+ "("+position_x[destination.get(i)-1]
				+","+position_y[destination.get(i)-1]+") color red");
				fileout.println("move "+"\""+group.get(starting_point.get(i)-1)+"\" "+"via "+"\""+"move"+
						"\" "+"within 500 ticks");
				fileout.println("line "+"\""+"move"+"\" "+"("+position_x[destination.get(i)-1]+","
				+position_y[destination.get(i)-1]+") "+ "("+position_x[starting_point.get(i)-1]
				+","+position_y[starting_point.get(i)-1]+") color red");
				fileout.println("move "+"\""+group.get(destination.get(i)-1)+"\" "+"via "+"\""
				+"move"+"\" "+"within 500 ticks");
				fileout.println("}");
				
				fileout.println("{");							//���� ��� ��� �� ���� ���
				fileout.println("fillColor \""+group.get(starting_point.get(i)-1)+"\" yellow");
				fileout.println("fillColor \""+group.get(destination.get(i)-1)+"\" yellow");
				fileout.println("}");
				
				temp = group.get(starting_point.get(i)-1);		//���� �� ���� ���߾��ֱ�
				group.set(starting_point.get(i)-1, group.get(destination.get(i)-1));
				group.set(destination.get(i)-1, temp);
			}
			fileout.close();
			System.out.println("�Ϸ�");
		}
		
		catch(Exception e) {
			System.out.println("����");
		}
	}
	/*							����ȭ �Լ� 						*/
	public static void heapify(ArrayList<Integer> arr, int h, int m) {
		int v = arr.get(h);
		int j;
		for(j = 2*h; j <= m; j = j * 2) {
			if(j < m && arr.get(j) < arr.get(j+1)) {
				j = j + 1;
			}
			if(v >= arr.get(j)) {
				break;
			}
			else {
				arr.set(j/2,  arr.get(j));
				change_count++;					
				for(int a=1;a<arr.size();a++) {			//���� ��� ����
					for(int b=a+1;b<arr.size();b++) {
						if(arr.get(a) == arr.get(b)) {
							starting_point.add(a);
							destination.add(b);
						}
					}
				}
			}
		}
		arr.set(j/2,  v);
	}
}
