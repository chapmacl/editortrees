package editortrees;

// A node in a height-balanced binary tree with rank.
// Except for the NULL_NODE (if you choose to use one), one node cannot
// belong to two different trees.

public class Node {

	enum Code {
		SAME, LEFT, RIGHT
	}

	// The fields would normally be private, but for the purposes of this class,
	// we want to be able to test the results of the algorithms in addition to
	// the
	// "publicly visible" effects

	char element;
	Node left, right, parent; // subtrees
	int rank; // Number of nodes in left subtree
	Code balance;

	// Node constructor with no element
	public Node() {
		this.element = '\0';
		this.left = EditTree.getNullNode();
		this.right = EditTree.getNullNode();
	}

	// Node constructor with only an element
	public Node(char element) {
		this.element = element;
		this.left = EditTree.getNullNode();
		this.right = EditTree.getNullNode();
	}

	// Returns the height of the tree
	public int height() {
		if (this == EditTree.getNullNode() || this.element == '\0') {
			return -1;
		}
		if (this.balance == Code.LEFT)
			return 1 + this.left.height();
		if (this.balance == Code.RIGHT)
			return 1 + this.right.height();
		return Math.max(1 + this.left.height(), 1 + this.right.height());

	}

	// Returns the number of nodes in the tree
	public int size() {
		if (this == EditTree.getNullNode()) {
			return 0;
		}

		int s = 0;
		s += this.rank;
		s += this.right.size() + 1;
		return s;
	}

	// Inorder toString
	@Override
	public String toString() {
		if (this.element == '\0') {
			if (this.left == null && this.right == null) {
				return "";
			}
			return this.left.toString() + '\0' + this.right.toString();
		}

		return this.left.toString() + this.element + this.right.toString();
	}

	/**
	 * Performs a single left rotation.
	 * 
	 * @param myParent
	 * @param myChild
	 * @return a node that could be the new root of the tree
	 */
	@SuppressWarnings("null")
	public Node singleRotateLeft(Node myParent, Node myChild) {
		EditTree.rotateCount++;
		Node temp = myChild.left;
		Node temp1 = myParent.parent;
		boolean isRight = false;

		if (temp1 != null && temp1.right == myParent) {
			isRight = true; // Check to see if this node is a right child
		}

		myChild.parent = myParent.parent;
		myChild.left = myParent; // Change pointers
		myChild.rank += myParent.rank + 1; // update rank
		myChild.balance = Code.SAME;
		myParent.right = temp; // Update balance codes
		if (myParent.right == EditTree.getNullNode()
				&& myParent.right == EditTree.getNullNode())
			myParent.balance = Code.SAME;
		if (isRight) {
			temp1.right = myChild;
		} // Reassigns children of the original parent node
		if (temp1 != null && !isRight) {
			temp1.left = myChild;
		}
		myParent.parent = myChild;
		Node temp2 = myChild;
		return temp2;
	}

	/**
	 * Performs a single right rotation.
	 * 
	 * @param myParent
	 * @param myChild
	 * @return a node that could be the new root of the tree
	 */
	@SuppressWarnings("null")
	public Node singleRotateRight(Node myParent, Node myChild) {
		EditTree.rotateCount++;

		myParent.rank -= myChild.rank + 1; // Update rank
		Node temp = myChild.right;
		Node temp1 = myParent.parent;
		boolean isRight = false;

		if (temp1 != null && temp1.right == myParent) {
			isRight = true;
		}
		myChild.parent = myParent.parent;
		myChild.right = myParent; // Reassign pointers
		myChild.balance = Code.SAME;
		myParent.left = temp; // update balance codes
		myParent.balance = Code.SAME;
		if (isRight) {
			temp1.right = myChild;
		} // Reassigns children of the original parent node
		if (temp1 != null && !isRight) {
			temp1.left = myChild;
		}
		myParent.parent = myChild;
		Node temp2 = myChild;
		return temp2;

	}

	/**
	 * Does a double left rotation.
	 * 
	 * @param myParent
	 * @param current
	 * @param myChild
	 * @return a node that could be the new root of the tree
	 */
	@SuppressWarnings("null")
	public Node doubleRotateLeft(Node myParent, Node current, Node myChild) {
		EditTree.rotateCount += 2;
		Node tempRight = myChild.right;
		Node tempLeft = myChild.left;
		Node tempParent = myParent.parent;
		boolean isRight = false;

		if (tempParent != null && tempParent.right == myParent) {
			isRight = true;
		}
		myChild.left = myParent;
		myChild.right = current;
		myParent.right = tempLeft;

		if (tempLeft != null) {
			tempLeft.parent = myParent;
		}
		current.left = tempRight;
		if (tempRight != null && tempRight != EditTree.getNullNode()) {
			tempRight.parent = current;
			current.rank = tempRight.rank + 1;
		} else {
			current.rank = 0;
		}

		current.parent = myChild;
		myChild.parent = tempParent;
		if (isRight) {
			tempParent.right = myChild;
		}

		if (tempParent != null && !isRight) {
			tempParent.left = myChild;
		}

		myChild.balance = Code.SAME;
		current.balance = Code.SAME;
		myChild.rank += myParent.rank + 1;
		myParent.parent = myChild;

		if (myParent.right != null && myParent.left != null) {
			if (myParent.left.height() < myParent.right.height()) {
				myParent.balance = Code.RIGHT;
			} else if (myParent.left.height() > myParent.right.height()) {
				myParent.balance = Code.LEFT;
			} else if (myParent.left.height() == myParent.right.height()) {
				myParent.balance = Code.SAME;
			}
		}

		else if (myParent.right == null && myParent.left != null) {
			myParent.balance = Code.LEFT;
		} else if (myParent.right != null && myParent.left == null) {
			myParent.balance = Code.RIGHT;
		} else {
			myParent.balance = Code.SAME;
		}
		return myChild;
	}

	/**
	 * Does a double right rotation.
	 * 
	 * @param myParent
	 * @param current
	 * @param myChild
	 * @return a node that could be the new root of the tree
	 */
	@SuppressWarnings("null")
	public Node doubleRotateRight(Node myParent, Node current, Node myChild) {
		EditTree.rotateCount += 2;
		Node tempRight = myChild.right;
		Node tempLeft = myChild.left;
		Node tempParent = myParent.parent;
		boolean isRight = false;

		if (tempParent != null && tempParent.right == myParent) {
			isRight = true;
		}
		myChild.right = myParent;
		myChild.left = current;
		myParent.left = tempRight;

		myParent.rank = tempRight.size();

		if (tempRight != null && tempRight != EditTree.getNullNode()) {
			tempRight.parent = myParent;
		} else {
			myParent.rank = 0;
		}
		current.right = tempLeft;

		if (tempLeft != null) {
			tempLeft.parent = current;
		} else {
			myParent.rank = 0;
		}
		current.parent = myChild;
		myChild.parent = tempParent;

		if (isRight) {
			tempParent.right = myChild;
		}
		if (tempParent != null && !isRight) {
			tempParent.left = myChild;
		}

		myChild.balance = Code.SAME;
		current.balance = Code.SAME;
		myChild.rank += current.rank + 1;
		myParent.parent = myChild;

		if (myParent.right != null && myParent.left != null) {
			if (myParent.left.height() < myParent.right.height()) {
				myParent.balance = Code.RIGHT;
			} else if (myParent.left.height() > myParent.right.height()) {
				myParent.balance = Code.LEFT;
			} else if (myParent.left.height() == myParent.right.height()) {
				myParent.balance = Code.SAME;
			}
		}

		else if (myParent.right == null && myParent.left != null) {
			myParent.balance = Code.LEFT;
		} else if (myParent.right != null && myParent.left == null) {
			myParent.balance = Code.RIGHT;
		} else {
			myParent.balance = Code.SAME;
		}
		return myChild;
	}

	// Adds a node with element c
	public void add(char c) {
		// add the node all the way to the right side
		if (this.right != EditTree.getNullNode()) {
			this.right.add(c);
		} else {
			this.right = new Node(c);
			this.right.parent = this;
			this.right.rank = 0;
			this.right.setBalanceInsert();
		}

	}

	// Gets the node at a certain position
	public char get(int pos) {
		if (this.rank == pos) {
			return this.element;
		} else if (pos < this.rank) {
			return this.left.get(pos);
		} else {
			return this.right.get(pos - this.rank - 1);
		}
	}

	// Gets the max node in a subtree
	public int getMax() {
		if (this.equals(EditTree.getNullNode())) {
			return -1;
		} else if (this.right.equals(EditTree.getNullNode())) {
			return this.rank;
		} else {
			return this.right.getMax();
		}
	}

	// Adds a node with element c at position pos
	public void add(char c, int pos) {
		if (pos < this.rank) {
			if (this.left.equals(EditTree.getNullNode())) {
				this.left = new Node(c);
				this.left.parent = this;
				this.rank++;
				this.left.setBalanceInsert();
			} else {
				this.rank++;
				this.left.add(c, pos);
			}
		} else if (pos > this.rank) {

			if (this.right.equals(EditTree.getNullNode())) {
				this.right = new Node(c);
				this.right.parent = this;
				this.right.rank = 0;
				this.right.setBalanceInsert();
			} else {
				int newPos = (pos - (this.rank + 1));
				this.right.add(c, newPos);
			}
		} else if (pos == this.rank) {
			if (pos == 0) {
				this.left = new Node(c);
				this.left.parent = this;
				this.rank++;
				this.left.setBalanceInsert();
			} else {
				this.rank++;
				this.left.add(c);
			}
		}
	}

	// Sets the balance codes after an insert
	public void setBalanceInsert() {
		if (this.right == EditTree.getNullNode()
				&& this.left == EditTree.getNullNode()) {
			this.balance = Code.SAME;
		}
		if (this.parent != EditTree.getNullNode() && this.parent != null) {

			if (this.parent.left == this) {
				if (this.parent.balance == Code.RIGHT) {
					this.parent.balance = Code.SAME;
				} else if (this.parent.balance == Code.SAME) {
					this.parent.balance = Code.LEFT;
					this.parent.setBalanceInsert();
				} else if (this.parent.balance == Code.LEFT) {
					if (this.balance == Code.RIGHT) {
						doubleRotateRight(this.parent, this, this.right);
					} else if (this.balance == Code.LEFT) {
						singleRotateRight(this.parent, this);
					}
				}
			} else if (this.parent.right == this) {
				if (this.parent.balance == Code.LEFT) {
					this.parent.balance = Code.SAME;
				} else if (this.parent.balance == Code.SAME) {
					this.parent.balance = Code.RIGHT;
					this.parent.setBalanceInsert();
				} else if (this.parent.balance == Code.RIGHT) {
					if (this.balance == Code.LEFT) {
						doubleRotateLeft(this.parent, this, this.left);
					} else if (this.balance == Code.RIGHT) {
						singleRotateLeft(this.parent, this);
					}
				}
			}
		}
	}

	// Delets a node at position pos
	public char delete(int pos) {
		// first finds the node, then removes it
		if (this.rank == pos) { // this is the root
			char temp = this.element;
			this.remove();
			return temp;
		} else if (this.rank != 0 && this.left.rank == pos) { // Left child is
																// the one we
																// delet
			char temp = this.left.element;
			this.left.remove();

			// Step up tree from this node

			return temp;
		} else if (this.right != null && this.right.rank == pos - this.rank - 1) {
			char temp = this.right.element;
			this.right.remove();

			// Step up tree from this node

			return temp;
		} else if (pos < this.rank)
			return this.left.delete(pos);
		else
			return this.right.delete(pos - this.rank - 1);
	}

	// Removes a node from the tree
	private void remove() {
		// if the node being removed has 2 children, returns the left most node
		// on the right side aka the successor
		if (this.right != EditTree.getNullNode()
				&& this.left != EditTree.getNullNode()) {

			Node temp = getLeft(this.right);

			this.adjustRank();

			this.element = temp.element;
			temp.setBalanceDelete();
		}
		// node has only left child
		else if (this.right == EditTree.getNullNode()
				&& this.left != EditTree.getNullNode()) {

			Node temp = this.left;

			this.left = temp.left;
			this.right = temp.right;
			this.element = temp.element;
			this.rank = temp.rank;
			this.adjustRank();

		}
		// node has only right child
		else if (this.right != EditTree.getNullNode()
				&& this.left == EditTree.getNullNode()) {

			Node temp = this.right;

			this.left = temp.left;
			this.right = temp.right;
			this.element = temp.element;
			this.rank = temp.rank;
			this.adjustRank();

		} else if (this.parent == null) {

			this.element = EditTree.getNullNode().element;
		}
		// node is a leaf, simply cut the link to its parent
		else {
			if (this.parent.left == this) {
				this.adjustRank();
				this.parent.left = EditTree.getNullNode();
				// this.setBalanceDelete();
				if (this.parent.right == EditTree.getNullNode()) {
					this.parent.balance = Code.SAME;
				} else if (this.parent.balance == Code.RIGHT
						&& this.parent.right.balance == Code.RIGHT) {
					singleRotateLeft(this.parent, this.parent.right);
				} else if (this.parent.balance == Code.RIGHT
						&& this.parent.right.balance == Code.LEFT) {
					doubleRotateLeft(this.parent, this.parent.right,
							this.parent.right.left);
				} else if (this.parent.balance == Code.SAME) {
					this.parent.balance = Code.RIGHT;
				}
				// Go back up till we hit the root
				Node temp = this;

				while (temp.parent != null
						&& temp.parent != EditTree.getNullNode()
						&& temp.parent.balance == Code.SAME) {
					temp = temp.parent;
				}
				if (temp.parent != null) {
					// checks if the parent needs a rotation before moving up

					temp.parent.setBalanceDeleted();
				}
			}

			else if (this.parent.right == this) {
				this.adjustRank();
				this.parent.right = EditTree.getNullNode();

				if (this.parent.left == EditTree.getNullNode()) {
					this.parent.balance = Code.SAME;
				}

				else if (this.parent.balance == Code.LEFT
						&& this.parent.left.balance == Code.LEFT) {
					singleRotateRight(this.parent, this.parent.left);
				} else if (this.parent.balance == Code.LEFT
						&& this.parent.left.balance == Code.RIGHT) {
					doubleRotateRight(this.parent, this.parent.left,
							this.parent.left.right);
				}

				else if (this.parent.balance == Code.SAME) {
					this.parent.balance = Code.LEFT;
				}

				// Go back up till we hit the root
				Node temp = this;
				while (temp.parent != null
						&& temp.parent != EditTree.getNullNode()
						&& temp.parent.balance == Code.SAME) {
					temp = temp.parent;
				}
				if (temp.parent != null) {

					if (temp.parent.balance == Code.RIGHT
							&& temp.parent.right.balance == Code.LEFT) {
						doubleRotateLeft(temp.parent, temp.parent.right,
								temp.parent.right.left);
					}
					// checks if the parent needs a rotation before moving up

					temp.parent.setBalanceDeleted();
				}
			}

		}

	}

	// Sets the balance codes after a delete
	private void setBalanceDelete() {
		if (this.parent != null) {
			// node is a right child
			if (this.parent.right == this) {
				if (this.right == EditTree.getNullNode()) {
					this.parent.right = EditTree.getNullNode();
				} else {
					this.parent.right = this.right;
					this.right.parent = this.parent;
				}

				if (this.parent.balance == Code.SAME) {
					this.parent.balance = Code.LEFT;
					this.parent.setBalance();
				} else if (this.parent.balance == Code.RIGHT) {
					this.parent.balance = Code.SAME;
					this.parent.setBalance();
				} else { // leaning LEFT
					if (this.parent.balance == Code.LEFT
							&& this.parent.left.balance == Code.RIGHT) {
						doubleRotateRight(this.parent, this.parent.left,
								this.parent.left.right);
					} else {
						singleRotateRight(this.parent, this.parent.left);
					}
					// this.parent.setBalance();

				}
			} else {// node is a left child

				if (this.parent.left == this) {
					if (this.left == EditTree.getNullNode()) {
						this.parent.left = EditTree.getNullNode();
					}
					// } else {
					// this.parent.left = this.left;
					// this.left.parent = this.parent;
					// }

					if (this.parent.balance == Code.SAME) {
						this.parent.balance = Code.RIGHT;
						this.parent.setBalance();
					} else if (this.parent.balance == Code.LEFT) {
						this.parent.balance = Code.SAME;

						// this.parent.setBalance();

						this.setBalance();
					} else { // leaning RIGHT
						if (this.parent.balance == Code.RIGHT
								&& this.parent.right.balance == Code.LEFT) {
							doubleRotateLeft(this.parent, this.parent.right,
									this.parent.right.left);
						} else {
							singleRotateLeft(this.parent, this.parent.right);
						}
						// this.parent.setBalance();
					}

				}
			}
		} else {
			if (this.balance == Code.LEFT && this.left.balance == Code.LEFT) {
				singleRotateRight(this, this.left);
			} else if (this.balance == Code.LEFT
					&& this.left.balance == Code.RIGHT) {
				doubleRotateRight(this, this.left, this.left.right);
			} else if (this.balance == Code.RIGHT
					&& this.right.balance == Code.RIGHT) {
				singleRotateLeft(this, this.right);
			} else if (this.balance == Code.RIGHT
					&& this.right.balance == Code.LEFT) {
				doubleRotateLeft(this, this.right, this.right.left);
			}
		}
	}

	// Sets the balance after a node is deleted
	private void setBalanceDeleted() {
		if (this.parent != null) {
			// node is a right child
			if (this.parent.right == this) {
				if (this.right == EditTree.getNullNode()) {
					// this.parent.right = EditTree.getNullNode();
					// } else {
					// this.parent.right = this.right;
					// this.right.parent = this.parent;
				}

				if (this.parent.balance == Code.SAME) {
					this.parent.balance = Code.LEFT;
					this.parent.setBalance();
				} else if (this.parent.balance == Code.RIGHT) {
					// this.parent.balance = Code.SAME;
					this.parent.setBalance();
				} else { // leaning LEFT
					if (this.parent.balance == Code.LEFT
							&& this.parent.left.balance == Code.RIGHT) {
						doubleRotateRight(this.parent, this.parent.left,
								this.parent.left.right);
					} else {
						singleRotateRight(this.parent, this.parent.left);
					}
					// this.parent.setBalance();

				}
			} else {// node is a left child

				if (this.parent.left == this) {
					if (this.left == EditTree.getNullNode()) {
						// this.parent.left = EditTree.getNullNode();
					}
					// } else {
					// this.parent.left = this.left;
					// this.left.parent = this.parent;
					// }

					if (this.parent.balance == Code.SAME) {
						this.parent.balance = Code.RIGHT;
						this.parent.setBalance();
					} else if (this.parent.balance == Code.LEFT) {
						// this.parent.balance = Code.SAME;

						// this.parent.setBalance();

						this.setBalance();
					} else { // leaning RIGHT
						if (this.parent.balance == Code.RIGHT
								&& this.parent.right.balance == Code.LEFT) {
							doubleRotateLeft(this.parent, this.parent.right,
									this.parent.right.left);
						} else {
							singleRotateLeft(this.parent, this.parent.right);
						}
						// this.parent.setBalance();
					}

				}
			}
		} else {
			if (this.balance == Code.LEFT && this.left.balance == Code.LEFT) {
				singleRotateRight(this, this.left);
			} else if (this.balance == Code.LEFT
					&& this.left.balance == Code.RIGHT) {
				doubleRotateRight(this, this.left, this.left.right);
			} else if (this.balance == Code.RIGHT
					&& this.right.balance == Code.RIGHT) {
				singleRotateLeft(this, this.right);
			} else if (this.balance == Code.RIGHT
					&& this.right.balance == Code.LEFT) {
				doubleRotateLeft(this, this.right, this.right.left);
			}
		}
	}

	// Sets the balance codes for a tree
	private void setBalance() {
		if (this.right == EditTree.getNullNode()
				&& this.left == EditTree.getNullNode()) {
			this.balance = Code.SAME;
		}
		if (this.parent != EditTree.getNullNode() && this.parent != null) {

			if (this.parent.left == this) {
				if (this.balance == Code.SAME) {
					if (this.parent.balance == Code.LEFT) {
						this.parent.balance = Code.SAME;
					} else if (this.parent.balance == Code.SAME) {
						this.parent.balance = Code.RIGHT;
					}
					this.parent.setBalance();
				} else if (this.balance == Code.LEFT) {
					if (this.left.balance == Code.LEFT) {
						singleRotateRight(this, this.left);
					} else if (this.left.balance == Code.RIGHT) {
						// double
					}
					this.parent.setBalance();

				}

			} else if (this.parent.right == this) {
				if (this.balance == Code.SAME) {
					if (this.parent.balance == Code.RIGHT) {
						this.parent.balance = Code.SAME;
					} else if (this.parent.balance == Code.SAME) {
						this.parent.balance = Code.LEFT;
					}

				}
				this.parent.setBalance();
			}
		}
	}

	// Adjusts the rank after a delete
	private void adjustRank() {
		if (this.parent != null) {
			if (this.parent.left == this) {
				this.parent.rank--;
			}
			this.parent.adjustRank();
		}
	}

	// Gets the left of a node
	private Node getLeft(Node node) {
		if (node.left != EditTree.getNullNode()) {
			return getLeft(node.left);
		}
		return node;
	}

	// Helper method for the EditTree constructor
	public void create(Node toMimic) {
		this.element = toMimic.element;
		this.rank = toMimic.rank;
		this.balance = toMimic.balance;

		if (toMimic.right != null
				&& !toMimic.right.equals(EditTree.getNullNode())) {
			this.right = new Node();
			this.right.parent = this;
			this.right.create(toMimic.right);
		}
		if (toMimic.left != null
				&& !toMimic.left.equals(EditTree.getNullNode())) {
			this.left = new Node();
			this.left.parent = this;
			this.left.create(toMimic.left);
		}
	}

	// Gets a substring starting at pos of length length
	public String get(int pos, int length) {
		String s = "";
		for (int x = 0; x < length; x++) {
			s += this.get(pos + x);
		}
		return s;
	}

	// Finds a string inside the tree
	public int find(String s) {
		String tree = this.toString();
		if (tree.contains(s)) {

			for (int x = 0; x < tree.length(); x++) {
				if (tree.charAt(x) == s.charAt(0)) {
					return x;
				}
			}
		}
		return -1;
	}

	// Gets a node at position pos
	public Node getNode(int pos) {
		if (this.rank == pos) {
			return this;
		} else if (pos < this.rank) {
			return this.left.getNode(pos);
		} else {
			return this.right.getNode(pos - this.rank - 1);
		}
	}

	// Finds the string in a subtree
	public int find(String s, String subTree) {
		String tree = this.toString();
		if (subTree.contains(s)) {

			for (int x = 0; x < tree.length(); x++) {
				if (tree.charAt(x) == s.charAt(0)) {
					return x;
				}
			}
		}
		return -1;
	}

	// Creates a tree from a string
	public Node createTree(String s) {
		if (!s.equals("")) {
			int rootNum = s.length() / 2;
			Node root = new Node(s.charAt(rootNum));
			if (s.substring(0, rootNum).length() > s.substring(rootNum + 1)
					.length()) {
				root.balance = Code.LEFT;
			} else if (s.substring(0, rootNum).length() < s.substring(
					rootNum + 1).length()) {
				root.balance = Code.RIGHT;
			} else {
				root.balance = Code.SAME;
			}
			root.rank = s.substring(0, rootNum).length();
			root.left = createTree(s.substring(0, rootNum));
			root.right = createTree(s.substring(rootNum + 1));
			return root;
		}
		return EditTree.getNullNode();
	}

	// Balances the tree after a concatenation
	public void concatenateBalance() {
		if (this != EditTree.getNullNode() && this.balance != Code.SAME) {
			if (this.balance == Code.LEFT) {
				if (this.left.balance == Code.SAME
						|| this.left.balance == Code.LEFT) {
					singleRotateRight(this, this.left);
				} else {
					doubleRotateRight(this, this.left, this.left.right);
				}
			} else if (this.balance == Code.RIGHT) {
				if (this.right.balance == Code.SAME
						|| this.right.balance == Code.RIGHT) {
					singleRotateLeft(this, this.right);
				} else {
					doubleRotateLeft(this, this.right, this.right.left);
				}
			}
			// do root trick
			Node root = this;
			while (root.parent != null) {
				root = root.parent;
			}

			root.left.concatenateBalance();
			root.right.concatenateBalance();
		}
	}
}