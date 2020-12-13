package concurrent;


/*Implementation of optional problem. Runtime is about NlogN for clone(). Uses almost no extra space by avioding recursion on
 * tree traversals (which require space on stack
 */




public class Cache {
	private Node root;
	private Node head;
	
	public static Cache clone(Cache original){
		if(original == null)
			return null;
		if(original.root == null)
			return new Cache();
		
		Cache copy = treeCopy(original); //First copy the tree
	
		return copy;
		
		
	}
	
	private static void linkTheList(Cache original, Cache copy){
		copy.head = findNode(original.head.data, copy.root);
		Node prev = copy.head; 
		Node originalCurr = original.head.next;
		Node curr;

		while(originalCurr != null){
			curr  = findNode(originalCurr.data, copy.root);
			curr.prev = prev;
			prev.next = curr;
			originalCurr = originalCurr.next;
			prev = curr;
		
		}
		
	}
	
	
	private static Node findNode(int data, Node root){
		Node curr = root;
		while(curr != null){
			if(data == curr.data)
				return curr;
			curr = data < curr.data? curr.left : curr.right;
		}
		return null;
		
	}
	
	static final int LEFT = 0; static final int BOTH = 1; static final int NONE = 2;

	private static Cache treeCopy(Cache original){
		Node curr, oldCurr;
		Cache copy = new Cache();
		copy.root = new Node(original.root.data);
		curr = copy.root;
		oldCurr = original.root;
		int childEXPLORED = NONE;
		Node parent, oldParent;
		
		while(!(curr == copy.root && childEXPLORED == BOTH)){
			
			if(childEXPLORED == NONE){ //have not explored left yet
				if(oldCurr.left == null)
					childEXPLORED = LEFT;
				else{                  //Go explore left subtree
					curr.left = new Node(oldCurr.left.data);
					curr.left.prev = curr;  //This way we can climb back up the tree
					curr.left.next = oldCurr; //So we can climb back up old tree
					curr    = curr.left;
					oldCurr = oldCurr.left;
					childEXPLORED = NONE;
				}
				
			}
			
			else if(childEXPLORED == LEFT){
				if(oldCurr.right == null)
					childEXPLORED = BOTH; 
				else{				   //Go explore right subtree
					curr.right = new Node(oldCurr.right.data);
					curr.right.prev = curr;  //This way we can climb back up the tree
					curr.right.next = oldCurr; //So we can climb back up old tree
					curr       = curr.right;
					oldCurr    = oldCurr.right;
					childEXPLORED = NONE;
				}
			}
			
			else{ //left and right have already been explored so need go backwards
				parent    = curr.prev;
				oldParent = curr.next;
				
				childEXPLORED = (curr == parent.left)? LEFT : BOTH; //Before going back up tree, let parent know if I am left or right child
				curr = parent; oldCurr = oldParent;	
				
			}
		
		}
		
		return copy;
				
	}
	
	
	
	
	
	
	private static class Node{
		public Node left, right, prev, next;
		int data;
		
		public Node(int data, Node left, Node right){
			this.data = data; this.left = left; this.right = right;
		}
		public Node(int data){
			this.data = data;
		}
		public Node(){;}
		
	
		
	}
	

}
