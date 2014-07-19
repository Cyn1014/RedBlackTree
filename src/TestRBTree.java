import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TestRBTree {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RBTree tree=new RBTree();
		BufferedReader br = new BufferedReader(new FileReader("Red-Black_Tree_data"));
		String line;
		while((line=br.readLine())!=null){
			String[] values=line.split(",");
			for(String v:values){
				//System.out.println(v);
				tree.insert(Integer.parseInt(v));
//				tree.PrintTree();
//				System.out.println();
			}
		}
		System.out.println("heigh of tree: "+tree.heigh());
		System.out.println("tree:");
		tree.PrintTree();
		System.out.println();
		tree.PrintTreeInorder();
		System.out.println();
		Node min=tree.min();
	    System.out.println("min:"+min.getKey());
	    System.out.println("heigh of tree: "+tree.heigh());
	    Node max=tree.max();
	    System.out.println("max:"+max.getKey());
	    System.out.println("heigh of tree: "+tree.heigh());
	    Node pre=tree.predecessor(tree.search(5));
	    System.out.println("predecessor of 5:"+pre.getKey());
	    System.out.println("heigh of tree: "+tree.heigh());
	    Node succ=tree.successor(tree.search(5));
	    System.out.println("successor of 5:"+succ.getKey());
	    System.out.println("heigh of tree: "+tree.heigh());
	    Node succmin=tree.successor(min);
	    System.out.println("successor of min:"+succmin.getKey());
	    System.out.println("heigh of tree: "+tree.heigh());
	    Node premax=tree.predecessor(max);
	    System.out.println("predecessor of max:"+premax.getKey());
	    System.out.println("heigh of tree: "+tree.heigh());
	    System.out.println("delete 5:");
	    tree.delete(7);
	    tree.delete(4);
	    tree.delete(8);
	    tree.PrintTree();
		System.out.println();
	    tree.PrintTreeInorder();
	    System.out.println();
	    System.out.println("heigh of tree: "+tree.heigh());
	    System.out.println("search 24:"+(!tree.search(24).equals(tree.getNil())));
	    System.out.println("search 8:"+(!tree.search(8).equals(tree.getNil())));
		
	    
		

	}

}
