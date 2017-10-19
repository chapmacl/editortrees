package editortrees;

import editortrees.Node.Code;

// A height-balanced binary tree with rank that could be the basis for a text editor.

@SuppressWarnings("javadoc")
public class EditTree {
	private final static Node NULL_NODE = new Node();
	private Node root;
	public static int rotateCount = 0;
	public int treeSize;

	/**
	 * Construct an empty tree
	 */
	public EditTree() {
		this.root = NULL_NODE;
		rotateCount = 0;
		this.treeSize = 0;
	}

	/**
	 * Construct a single-node tree whose element is c
	 * 
	 * @param c
	 */
	public EditTree(char c) {
		this.root = new Node(c);
		this.root.setBalanceInsert();
		this.root.left = NULL_NODE;
		this.root.right = NULL_NODE;
		rotateCount = 0;
		this.treeSize = 1;
	}

	/**
	 * Create an EditTree whose toString is s. This can be done in O(N) time,
	 * where N is the length of the tree (repeatedly calling insert() would be
	 * O(N log N), so you need to find a more efficient way to do this.
	 * 
	 * @param s
	 */
	public EditTree(String s) {
		int rootNum = s.length() / 2;

		this.root = new Node(s.charAt(rootNum));
		if (s.substring(0, rootNum).length() > s.substring(rootNum + 1)
				.length()) {
			this.root.balance = Code.LEFT;
		} else if (s.substring(0, rootNum).length() < s.substring(rootNum + 1)
				.length()) {
			this.root.balance = Code.RIGHT;
		} else {
			this.root.balance = Code.SAME;
		}
		this.root.rank = s.substring(0, rootNum).length();
		this.root.left = this.root.createTree(s.substring(0, rootNum));
		this.root.right = this.root.createTree(s.substring(rootNum + 1));
		this.treeSize = s.length();
	}

	/**
	 * Make this tree be a copy of e, with all new nodes, but the same shape and
	 * contents.
	 * 
	 * @param e
	 */
	public EditTree(EditTree e) {
		rotateCount = 0;
		this.treeSize = e.treeSize;
		if (e.root == null || e.root.equals(NULL_NODE)) {
			this.root = NULL_NODE;
			return;
		}
		this.root = new Node(e.root.element);
		this.root.balance = e.root.balance;
		this.root.rank = e.root.rank;

		if (e.root.left != null && !e.root.left.equals(NULL_NODE)) {
			this.root.left = new Node();
			this.root.left.parent = this.root;
			this.root.left.create(e.root.left);
		}

		if (e.root.left != null && !e.root.right.equals(NULL_NODE)) {
			this.root.right = new Node();
			this.root.right.parent = this.root;
			this.root.right.create(e.root.right);
		}
	}

	/**
	 * 
	 * @return the height of this tree
	 */
	public int height() {
		if (this.root == null || this.root.element == '\0') {
			return -1;
		}
		return this.root.height(); // Replace by a real calculation.
	}

	/**
	 * Returns the total number of rotations done in this tree since it was
	 * created. A double rotation counts as two.
	 *
	 * @return number of rotations since tree was created.
	 */
	public int totalRotationCount() {
		return rotateCount; // replace by a real calculation.
	}

	/**
	 * Return the string produced by an inorder traversal of this tree
	 */
	@Override
	public String toString() {
		if (this.root == null || this.root.element == '\0') {
			return "";
		}
		return this.root.toString();
	}

	/**
	 * @param pos
	 *            position in the tree
	 * @return the character at that position
	 * @throws IndexOutOfBoundsException
	 */
	public char get(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos >= this.treeSize || this.root == NULL_NODE) {
			throw new IndexOutOfBoundsException();
		}
		return this.root.get(pos);
	}

	/**
	 * @param c
	 *            character to add to the end of this tree.
	 */
	public void add(char c) {
		if (!this.root.equals(NULL_NODE)) {
			this.root.add(c);
			this.treeSize++;
		} else {
			this.treeSize++;
			this.root = new Node(c);
			this.root.parent = null;
			this.root.setBalanceInsert();
			this.root.rank = 0;
		}
		if (this.root.parent != null) {
			this.root = this.root.parent;
		}
	}

	/**
	 * @param c
	 *            character to add
	 * @param pos
	 *            character added in this inorder position
	 * @throws IndexOutOfBoundsException
	 *             id pos is negative or too large for this tree
	 */
	public void add(char c, int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos > this.treeSize) {
			throw new IndexOutOfBoundsException();
		} else if (this.root.equals(NULL_NODE)) {
			this.root = new Node(c);
			this.root.parent = null;
			this.root.rank = 0;
			this.root.balance = Code.SAME;
			this.treeSize++;
		} else {
			this.root.add(c, pos);
			this.treeSize++;
		}
		if (this.root.parent != null) {
			this.root = this.root.parent;
		}
	}

	/**
	 * @return the number of nodes in this tree
	 */
	public int size() {
		if (this.root == null) {
			return 0;
		}
		return this.root.size();
	}

	/**
	 * 
	 * @param pos
	 *            position of character to delete from this tree
	 * @return the character that is deleted
	 * @throws IndexOutOfBoundsException
	 */
	public char delete(int pos) throws IndexOutOfBoundsException {
		if (pos < 0 || pos >= this.treeSize || this.root == NULL_NODE) {
			throw new IndexOutOfBoundsException();
		}

		char thingy = this.root.delete(pos);
		if (this.root.parent != null) {
			this.root = this.root.parent;
		}
		this.treeSize--;
		return thingy;
		// Implementation requirement:
		// When deleting a node with two children, you normally replace the
		// node to be deleted with either its in-order successor or predecessor.
		// The tests assume that you will replace it with the
		// *successor*.
	}

	/**
	 * This method operates in O(length*log N), where N is the size of this
	 * tree.
	 * 
	 * @param pos
	 *            location of the beginning of the string to retrieve
	 * @param length
	 *            length of the string to retrieve
	 * @return string of length that starts in position pos
	 * @throws IndexOutOfBoundsException
	 *             unless both pos and pos+length-1 are legitimate indexes
	 *             within this tree.
	 */
	public String get(int pos, int length) throws IndexOutOfBoundsException {
		if (this.treeSize == 0) {
			throw new IndexOutOfBoundsException();
		}
		if (pos < 0) {
			throw new IndexOutOfBoundsException();
		}
		if (pos + length > this.treeSize) {
			throw new IndexOutOfBoundsException();
		}
		return this.root.get(pos, length);
	}

	/**
	 * This method is provided for you, and should not need to be changed. If
	 * split() and concatenate() are O(log N) operations as required, delete
	 * should also be O(log N)
	 * 
	 * @param start
	 *            position of beginning of string to delete
	 * 
	 * @param length
	 *            length of string to delete
	 * @return an EditTree containing the deleted string
	 * @throws IndexOutOfBoundsException
	 *             unless both start and start+length-1 are in range for this
	 *             tree.
	 */
	public EditTree delete(int start, int length)
			throws IndexOutOfBoundsException {
		if (start < 0 || start + length >= this.size())
			throw new IndexOutOfBoundsException(
					(start < 0) ? "negative first argument to delete"
							: "delete range extends past end of string");
		EditTree t2 = this.split(start);
		EditTree t3 = t2.split(length);
		this.concatenate(t3);
		return t2;
	}

	/**
	 * Append (in time proportional to the log of the size of the larger tree)
	 * the contents of the other tree to this one. Other should be made empty
	 * after this operation.
	 * 
	 * @param other
	 * @throws IllegalArgumentException
	 *             if this == other
	 */
	public void concatenate(EditTree other) throws IllegalArgumentException {
		if (this == other) {
			throw new IllegalArgumentException();
		}

		if (this.size() == 0) {
			this.root = other.root;
			this.treeSize = other.treeSize;
			other.root = null;
			other.treeSize = 0;
		} else if (other.size() == 0) {
			return;
		} else if (this.size() == 1) {
			this.root.right = other.root;
			this.treeSize += other.treeSize;
			if (other.root != NULL_NODE) {
				other.root.parent = this.root;
			}
			other.root = NULL_NODE;
			other.treeSize = 0;
		} else {
			Node temp = this.root;
			int tempPos = temp.rank;

			// Gets the rightmost node
			while (temp.right != NULL_NODE) {
				temp = temp.right;
				tempPos += temp.rank + 1;
			}
			if (this.height() == 0) {
				this.root.right = other.root;
				if (other.root != NULL_NODE) {
					other.root.parent = this.root;
				}
				this.treeSize += other.treeSize;
			} else {
				// Deletes temp from the rightmost tree + cuts ties from temp
				this.delete(tempPos);
				temp.parent = null;
				temp.rank = this.treeSize;

				// Sets this as the left child, and other as the right child
				temp.left = this.root;
				temp.right = other.root;
				if (other.root != NULL_NODE) {
					other.root.parent = temp;
				}
				this.root.parent = temp;
				this.root = temp;
				this.treeSize += other.treeSize + 1;
			}
			int lHeight = this.root.left.height();
			int rHeight = this.root.right.height();

			if (Math.abs(lHeight - rHeight) <= 1) {
				if (lHeight > rHeight) {
					this.root.balance = Code.LEFT;
				} else if (rHeight > lHeight) {
					this.root.balance = Code.RIGHT;
				} else {
					this.root.balance = Code.SAME;
				}
			} else {
				// if (lHeight > rHeight) {
				// this.root.balance = Code.LEFT;
				// } else if (rHeight > lHeight) {
				// this.root.balance = Code.RIGHT;
				// } else {
				// this.root.balance = Code.SAME;
				// }
				this.root.concatenateBalance();
			}

			other.root = NULL_NODE;
			other.treeSize = 0;
		}
	}

	/**
	 * This operation must be done in time proportional to the height of this
	 * tree.
	 * 
	 * @param pos
	 *            where to split this tree
	 * @return a new tree containing all of the elements of this tree whose
	 *         positions are >= position. Their nodes are removed from this
	 *         tree.
	 * @throws IndexOutOfBoundsException
	 */
	public EditTree split(int pos) throws IndexOutOfBoundsException {
		Node newRightRoot = this.root.getNode(pos);
		Node newLeftRoot = newRightRoot.left;
		newRightRoot.left = NULL_NODE;
		newLeftRoot.parent = null;
		Node current = newRightRoot;

		// Continues up to the node, splitting and reassigning parents
		while (current.parent != null) {
			// If current is a left child
			if (current.parent.left == current) {
				current.parent.left = newRightRoot;
				current = current.parent;
				if (newRightRoot != NULL_NODE) {
					newRightRoot.parent = current;
					newRightRoot = newRightRoot.parent;
				} else {
					newRightRoot = current;
				}
			}
			// If current is a right child
			else if (current.parent.right == current) {
				current.parent.right = newLeftRoot;
				current = current.parent;
				if (newLeftRoot != NULL_NODE) {
					newLeftRoot.parent = current;
					newLeftRoot = newLeftRoot.parent;
				} else {
					newLeftRoot = current;
				}
			}
		}
		EditTree returnTree = new EditTree();
		returnTree.root = newRightRoot;
		this.root = newLeftRoot;
		return returnTree;
	}

	/**
	 * Don't worry if you can't do this one efficiently.
	 * 
	 * @param s
	 *            the string to look for
	 * @return the position in this tree of the first occurrence of s; -1 if s
	 *         does not occur
	 */
	public int find(String s) {
		if (s.equals("") && this.treeSize == 0) {
			return 0;
		}
		if (s.equals("")) {
			return 0;
		}
		return this.root.find(s);
	}

	/**
	 * 
	 * @param s
	 *            the string to search for
	 * @param pos
	 *            the position in the tree to begin the search
	 * @return the position in this tree of the first occurrence of s that does
	 *         not occur before position pos; -1 if s does not occur
	 */
	public int find(String s, int pos) {
		String tree = this.toString();
		return this.root.find(s, tree.substring(pos));
	}

	/**
	 * @return The root of this tree.
	 */
	public Node getRoot() {
		return this.root;
	}

	public static Node getNullNode() {
		return NULL_NODE;
	}
}
