
public class RBTree {
	
	private Node root;
	private static Node nil=new Node();
	
	public RBTree(){
		root=new Node();
		nil.setBlack(true);
		root.setParent(nil);
		root.setLeft(nil);
		root.setRight(nil);
		root.setKey(Integer.MIN_VALUE);
		
	}

	public Node getNil() {
		return nil;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	public void insert(int value){
		Node node=root;
		Node parent=nil;
		boolean isleft=false;
		while(!node.equals(nil)){
			parent=node;
			if(node.getKey()>=value){
				node=node.getLeft();
				isleft=true;
			}
			else{
				if(node.getKey()==Integer.MIN_VALUE)
					parent=nil;
				node=node.getRight();
				isleft=false;
			}
		}
		node=new Node();
		node.setKey(value);
		node.setParent(parent);
		node.setLeft(nil);
		node.setRight(nil);
		node.setBlack(false);
		if(!parent.equals(nil)){
			if(isleft)
				parent.setLeft(node);
			else parent.setRight(node);
			
		}else
		{
			root=node;
		}
		insertFixup(node);
		
	}
	
	private void insertFixup(Node node){
		//System.out.println(node.equals(root));
		while(!node.getParent().isBlack()){
			Node parent=node.getParent();
			if(parent.equals(parent.getParent().getLeft())){
				Node pright=parent.getParent().getRight();
				if(!pright.isBlack()){
					parent.setBlack(true);
					pright.setBlack(true);
					parent.getParent().setBlack(false);
					node=parent.getParent();
				}else {
					if(node.equals(parent.getRight())){
					node=node.getParent();
					leftRotate(node);
				    }
					node.getParent().setBlack(true);
					node.getParent().getParent().setBlack(false);
					rightRotate(node.getParent().getParent());
				}		
			}else {
				Node pleft=parent.getParent().getLeft();
				if(!pleft.isBlack()){
					parent.setBlack(true);
					pleft.setBlack(true);
					parent.getParent().setBlack(false);
					node=parent.getParent();
				}else {
					if(node.equals(parent.getLeft())){
					node=node.getParent();
					rightRotate(node);
				    }
					node.getParent().setBlack(true);
					node.getParent().getParent().setBlack(false);
					leftRotate(node.getParent().getParent());
				}
				
			}
				
		}
		root.setBlack(true);
	}
	
	public Node min(){
		Node node=root;
		if(!node.equals(nil)){
			while(!node.getLeft().equals(nil))
				node=node.getLeft();
		}
		return node;
	}
	
	public Node max(){
		Node node=root;
		if(!node.equals(nil)){
			while(!node.getRight().equals(nil))
				node=node.getRight();
		}
		return node;
	}
	
	public Node min(Node root){
		Node node=root;
		if(!node.equals(nil)){
			while(!node.getLeft().equals(nil))
				node=node.getLeft();
		}
		return node;
	}
	
	public Node max(Node root){
		Node node=root;
		if(!node.equals(nil)){
			while(!node.getRight().equals(nil))
				node=node.getRight();
		}
		return node;
	}
	
	public void PrintSubtree(Node node){
		if(!node.equals(nil)){
			String color="black";
			if(!node.isBlack())color="red";
		System.out.print("{node: { value:"+node.getKey()+", color:"+color+"}");
		if(!node.getLeft().equals(nil)){
		System.out.print(",left:");
		PrintSubtree(node.getLeft());
		}
		if(!node.getRight().equals(nil)){
		System.out.print(",right:");
		PrintSubtree(node.getRight());
		}
		System.out.print("}");
		}else{
			System.out.print("Nil");
		}
	}
	
	public void PrintSubtreeInorder(Node node){
		if(!node.equals(nil)){
		PrintSubtreeInorder(node.getLeft());
		System.out.print(node.getKey()+" ");
		PrintSubtreeInorder(node.getRight());
		}else{
			System.out.print(" ");
		}
	}
	
	public Node successor(Node node){	
	    Node succ=nil;
		if(!node.equals(nil)){
			if(!node.getRight().equals(nil))
				succ=min(node.getRight());
			else{
				succ=node.getParent();
				while(!succ.equals(nil) && !succ.getRight().equals(nil) && succ.getRight().getKey()==node.getKey()){
					  node=succ;
					  succ=succ.getParent();
					}
			}
		}
		
		return succ;
	}
	
	public Node predecessor(Node node){	
	    Node pre=nil;
		if(!node.equals(nil)){
			if(!node.getLeft().equals(nil))
				pre=max(node.getLeft());
			else{
				pre=node.getParent();
				while(!pre.equals(nil) && !pre.getLeft().equals(nil) && pre.getLeft().getKey()==node.getKey()){
					  node=pre;
					  pre=pre.getParent();
					}
			}
		}
		
		return pre;
	}
	
	private void transplant(Node parent,Node child){
		if(parent.getParent().equals(nil))
			root=child;
		else if(parent.equals(parent.getParent().getLeft()))
			parent.getParent().setLeft(child);
		else parent.getParent().setRight(child);
		child.setParent(parent.getParent());
	}
	
	public void delete(Node node){
		if(!node.equals(nil)){
			Node n=node;
			boolean isblack=n.isBlack();
			Node replace=new Node();
			if(node.getLeft().equals(nil)){
				replace=node.getRight();
				transplant(node,node.getRight());
			}else if(node.getRight().equals(nil)){
				replace=node.getLeft();
				transplant(node,node.getLeft());
			}else{
				n=min(node.getRight());
				isblack=n.isBlack();
				replace=n.getRight();
				if(n.getParent().equals(node))
					replace.setParent(n);
				else {
					transplant(n,n.getRight());
					n.setRight(node.getRight());
					n.getRight().setParent(n);
				}
				transplant(node,n);
				n.setLeft(node.getLeft());
				n.getLeft().setParent(n);
				n.setBlack(node.isBlack());
			}
			if(isblack)
				deleteFixup(replace);
				
		}
		
	}
	
	public void deleteFixup(Node node){
		while(!node.equals(root)&& node.isBlack()){
			if(node.equals(node.getParent().getLeft())){
				Node pright=node.getParent().getRight();
				if(!pright.isBlack()){
					pright.setBlack(true);
					node.getParent().setBlack(false);
					leftRotate(node.getParent());
					pright=node.getParent().getRight();
					
				}
				if(pright.getLeft().isBlack() && pright.getRight().isBlack()){
					pright.setBlack(false);
					node=node.getParent();
				}else {
					if(pright.getRight().isBlack()){
					pright.getLeft().setBlack(true);
					pright.setBlack(false);
					rightRotate(pright);
					pright=node.getParent().getRight();
				}
					pright.setBlack(node.getParent().isBlack());
					node.getParent().setBlack(true);
					pright.getRight().setBlack(true);
					leftRotate(node.getParent());
					node=root;
					}
			}else{
				Node pleft=node.getParent().getLeft();
				if(!pleft.isBlack()){
					pleft.setBlack(true);
					node.getParent().setBlack(false);
					rightRotate(node.getParent());
					pleft=node.getParent().getLeft();
				}
				if(pleft.getRight().isBlack() && pleft.getLeft().isBlack()){
					pleft.setBlack(false);
					node=node.getParent();
				}else {
					if(pleft.getLeft().isBlack()){
					pleft.getLeft().setBlack(true);
					pleft.setBlack(false);
					leftRotate(pleft);
					pleft=node.getParent().getLeft();
				}
					pleft.setBlack(node.getParent().isBlack());
					node.getParent().setBlack(true);
					pleft.getLeft().setBlack(true);
					rightRotate(node.getParent());
					node=root;
					}
			}
		}
		node.setBlack(true);
		
	}
	
	public void leftRotate(Node node){
		Node right=node.getRight();
		node.setRight(right.getLeft());
		if(!right.getLeft().equals(nil))
			right.getLeft().setParent(node);
		right.setParent(node.getParent());
		if(node.getParent().equals(nil))
			root=right;
		else if(node.equals(node.getParent().getLeft()))
			node.getParent().setLeft(right);
		else node.getParent().setRight(right);
		right.setLeft(node);
		node.setParent(right);
	}
	
	public void rightRotate(Node node){
		Node left=node.getLeft();
		node.setLeft(left.getRight());
		if(!left.getRight().equals(nil))
			left.getRight().setParent(node);
		left.setParent(node.getParent());
		if(node.getParent().equals(nil))
			root=left;
		else if(node.equals(node.getParent().getRight()))
			  node.getParent().setRight(left);
		else node.getParent().setLeft(left);
		left.setRight(node);
		node.setParent(left);
	}
	
	private int heigh(Node node){
		int heigh=0;
		if(!node.getLeft().equals(nil))
			heigh=heigh(node.getLeft())+1;
		if(!node.getRight().equals(nil))
			if(heigh<heigh(node.getRight())+1)
				heigh=heigh(node.getRight())+1;
		return heigh;
	}
	
	public int heigh(){
		return heigh(root);
	}
	
	public Node search(int value){
		Node node=root;
		if(node.getKey()>Integer.MIN_VALUE){
			while(node.getKey()!=value&&!node.equals(nil)){
				if(node.getKey()>value)
					node=node.getLeft();
				else node=node.getRight();
			}
			
		}
		return node;
	}
	
	public void delete(int value){
		delete(search(value));
	}

	public void PrintTreeInorder() {
		// TODO Auto-generated method stub
		PrintSubtreeInorder(root);
		
	}

	public void PrintTree() {
		// TODO Auto-generated method stub
	     PrintSubtree(root);
	}

}
