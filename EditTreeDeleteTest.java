package editortrees;


import static org.junit.Assert.*;

import org.junit.Test;

import editortrees.EditTree;

/**
 * @author Matt Boutell, Claude Anderson, Jimmy Theis.
 *         Created Apr 15, 2011.  Modified 2012.
 */
public class EditTreeDeleteTest {

	/**
	 * Test method for {@link editortrees.EditTree#delete(int)}.
	 */
	@Test
	public void testDeleteInt() {
		// Tests all the simplest rotation cases for delete (causing each type of rotation)
		EditTree t = new EditTree();
		t.add('o');
		t.add('u');
		t.add('i', 0);
		t.add('e', 0);
		t.delete(3); // u
		assertEquals("eio", t.toString());
		assertEquals(1, t.height());

		t.add('g', 1);
		t.delete(3); //o
		assertEquals("egi", t.toString());
		assertEquals(1, t.height());
		
		t.add('o');
		t.delete(0); // e
		assertEquals("gio", t.toString());
		assertEquals(1, t.height());
		
		t.add('k', 2);
		t.delete(0); // g
		assertEquals("iko", t.toString());
		assertEquals(1, t.height());
		
		t.add('e', 0);
		t.delete(2); // k (root)
		assertEquals("eio", t.toString());
		assertEquals(1, t.height());

		t.add('g', 1);
		t.delete(2); // i (root) 
		assertEquals("ego", t.toString()); 
		assertEquals(1, t.height());

		t.add('u');
		t.delete(1); // g (root)  
		assertEquals("eou", t.toString()); 
		assertEquals(1, t.height());
		
		t.add('m', 2);
		t.delete(1); // o (root)  
		assertEquals("emu", t.toString()); 
		assertEquals(1, t.height());
	}
	
	@Test
	public void testDeleteSimpleLeaf() {
		EditTree t = new EditTree();
		
		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g'); // three full levels in alphabetical order
		
		// Now delete the nodes in level order (by reverse level) so no rotations happen
		
		t.delete(0); // a
		assertEquals("bcdefg", t.toString());
		assertEquals(2, t.height());
		
		t.delete(1); // c
		assertEquals("bdefg", t.toString());
		assertEquals(2, t.height());
		
		t.delete(2); // e
		assertEquals("bdfg", t.toString());
		assertEquals(2, t.height());
		
		t.delete(3); // g
		assertEquals("bdf", t.toString());
		assertEquals(1, t.height());
		
		t.delete(0); // b
		assertEquals("df", t.toString());
		assertEquals(1, t.height());
		
		t.delete(1); // f
		assertEquals("d", t.toString());
		assertEquals(0, t.height());
		
		t.delete(0); //d
		assertEquals("", t.toString());
		assertEquals(-1, t.height());
	}
	
	@Test
	public void testDeleteLeafCausingSingleLeftRotation() {
		// Cause a single left rotation at the root
		EditTree t1 = new EditTree();
		
		t1.add('c');
		t1.add('b', 0);
		t1.add('X');
		t1.add('a', 0);
		
		t1.delete(3);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Cause a single left rotation on the left subtree
		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('c', 0);
		t2.add('f');
		t2.add('b', 0);
		t2.add('X', 2);
		t2.add('e', 4);
		t2.add('g');
		t2.add('a', 0);
		
		t2.delete(3);
		
		assertEquals("abcdefg", t2.toString());
		assertEquals(2, t2.height());
		
		// Cause a single left rotation on the right subtree
		EditTree t3 = new EditTree();
		t3.add('d');
		t3.add('b', 0);
		t3.add('g');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('f', 4);
		t3.add('X');
		t3.add('e', 4);
		
		t3.delete(7);
		
		assertEquals("abcdefg", t3.toString());
		assertEquals(2, t3.height());
	}
	
	@Test
	public void testDeleteLeafCausingSingleRightRotation() {
		// Cause a single right rotation at the root
		EditTree t1 = new EditTree();
		t1.add('a');
		t1.add('X', 0);
		t1.add('b');
		t1.add('c');
		
		t1.delete(0);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Cause a single right rotation on the right subtree
		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('x', 4);
		t2.add('f');
		t2.add('g');
		
		t2.delete(4);
		
		assertEquals("abcdefg", t2.toString());
		assertEquals(2, t2.height());
		
		// Cause a single right rotation on the left subtree
		EditTree t3 = new EditTree();
		t3.add('d');
		t3.add('a', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('b', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('c', 3);
		
		t3.delete(0);
		
		assertEquals("abcdefg", t3.toString());
		assertEquals(2, t3.height());
	}
	
	@Test
	public void testDeleteLeafCausingDoubleLeftRotation() {
		// Cause a double left rotation at the root
		EditTree t1 = new EditTree();
		
		t1.add('c');
		t1.add('a', 0);
		t1.add('X');
		t1.add('b', 1);
		
		t1.delete(3);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Cause a double left rotation on the left subtree
		EditTree t2 = new EditTree();
		
		t2.add('d');
		t2.add('c', 0);
		t2.add('f');
		t2.add('a', 0);
		t2.add('X', 2);
		t2.add('e', 4);
		t2.add('g');
		t2.add('b', 1);
		
		t2.delete(3);
		
		assertEquals("abcdefg", t2.toString());
		assertEquals(2, t2.height());		
		
		// Cause a double left rotation on the right subtree
		EditTree t3 = new EditTree();
		
		t3.add('d');
		t3.add('a', 0);
		t3.add('f', 2);
		t3.add('X', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('b', 2);
		
		t3.delete(0);
		
		assertEquals("abcdefg", t3.toString());
		assertEquals(2, t3.height());
	}
	
	@Test
	public void testDeleteLeafCausingDoubleRightRotation() {
		// Cause a double right rotation at the root
		EditTree t1 = new EditTree();
		
		t1.add('a');
		t1.add('X', 0);
		t1.add('c');
		t1.add('b', 2);
		
		t1.delete(0);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Cause a double right rotation on the right subtree
		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('X', 4);
		t2.add('g');
		t2.add('f', 6);
		
		t2.delete(4);
		
		assertEquals("abcdefg", t2.toString());
		assertEquals(2, t2.height());
		
		// Cause a double right rotation on the left subtree
		EditTree t3 = new EditTree();
		t3.add('d');
		t3.add('a', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('b', 2);
		
		t3.delete(0);
		
		assertEquals("abcdefg", t3.toString());
		assertEquals(2, t3.height());
	}
	
	@Test
	public void testDeletRootWithSingleChild() {
		// Root has left child
		EditTree t1 = new EditTree();
		
		t1.add('X');
		t1.add('a', 0);
		
		t1.delete(1);
		
		assertEquals("a", t1.toString());
		assertEquals(0, t1.height());
		
		// Root has right child
		EditTree t2 = new EditTree();
		
		t2.add('X');
		t2.add('a');
		
		t2.delete(0);
		
		assertEquals("a", t2.toString());
		assertEquals(0, t2.height());
	}
	
	@Test
	public void testDeleteNodeWithSingleChild() {
		// Delete nodes with only left children from second level
		EditTree t1 = new EditTree();
		
		t1.add('b');
		t1.add('X', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 3);
		
		t1.delete(1);
		
		assertEquals("abcX", t1.toString());
		assertEquals(2, t1.height());
		
		t1.delete(3);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Delete nodes with only right children from second level
		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('X', 0);
		t2.add('X');
		t2.add('a', 1);
		t2.add('c');
		
		t2.delete(0);
		
		assertEquals("abXc", t2.toString());
		assertEquals(2, t2.height());
		
		t2.delete(2);
		
		assertEquals("abc", t2.toString());
		assertEquals(1, t2.height());
		
		// Delete nodes with only left children from third level
		EditTree t3 = new EditTree();
		
		t3.add('d');
		t3.add('b', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('X', 2);
		t3.add('X', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 3);
		t3.add('e', 6);
		t3.add('g', 9);
		
		t3.delete(1);
		
		assertEquals("abcXdeXfgX", t3.toString());
		assertEquals(3, t3.height());
		
		t3.delete(3);
		
		assertEquals("abcdeXfgX", t3.toString());
		assertEquals(3, t3.height());
		
		t3.delete(5);
		
		assertEquals("abcdefgX", t3.toString());
		assertEquals(3, t3.height());
		
		t3.delete(7);
		
		assertEquals("abcdefg", t3.toString());
		assertEquals(2, t3.height());
		
		// Delete nodes with only right children from third level
		EditTree t4 = new EditTree();
		
		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('X', 0);
		t4.add('X', 2);
		t4.add('X', 4);
		t4.add('X');
		t4.add('a', 1);
		t4.add('c', 4);
		t4.add('e', 7);
		t4.add('g');
		
		t4.delete(0);
		
		assertEquals("abXcdXefXg", t4.toString());
		assertEquals(3, t4.height());
		
		t4.delete(2);
		
		assertEquals("abcdXefXg", t4.toString());
		assertEquals(3, t4.height());
		
		t4.delete(4);
		
		assertEquals("abcdefXg", t4.toString());
		assertEquals(3, t4.height());
		
		t4.delete(6);
		
		assertEquals("abcdefg", t4.toString());
		assertEquals(2, t4.height());
	}
	
	@Test
	public void testDeleteRootWithTwoChildrenNoRotation() {
		// Two levels
		EditTree t1 = new EditTree();
		
		t1.add('X');
		t1.add('a', 0);
		t1.add('b');
		
		t1.delete(1);
		
		assertEquals("ab", t1.toString());
		assertEquals(1, t1.height());
		
		// Three levels
		EditTree t2 = new EditTree();
		t2.add('X');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('d', 4);
		t2.add('f');
		
		t2.delete(3);
		
		assertEquals("abcdef", t2.toString());
		assertEquals(2, t2.height());
	}
	
	@Test
	public void testDeleteNodeWithTwoChildrenNoRotation() {
		// Left subtree
		EditTree t1 = new EditTree();
		
		t1.add('c');
		t1.add('X', 0);
		t1.add('e');
		t1.add('a', 0);
		t1.add('b', 2);
		t1.add('d', 4);
		t1.add('f');
		
		t1.delete(1);
		
		assertEquals("abcdef", t1.toString());
		assertEquals(2, t1.height());
		
		// Right subtree
		EditTree t2 = new EditTree();
		
		t2.add('d');
		t2.add('b', 0);
		t2.add('X');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('f');
		
		t2.delete(5);
		
		assertEquals("abcdef", t2.toString());
		assertEquals(2, t2.height());
	}
	
	@Test
	public void testDeleteMultipleNodesWithTwoChildrenNoRotation() {
		EditTree t = new EditTree();
		
		t.add('X');
		t.add('X', 0);
		t.add('X');
		t.add('b', 0);
		t.add('e', 2);
		t.add('h', 4);
		t.add('k', 6);
		t.add('a', 0);
		t.add('c', 2);
		t.add('d', 4);
		t.add('f', 6);
		t.add('g', 8);
		t.add('i', 10);
		t.add('j', 12);
		t.add('l');

		t.delete(3);
		
		assertEquals("abcdefXghiXjkl", t.toString());
		assertEquals(3, t.height());
		
		t.delete(10);
		
		assertEquals("abcdefXghijkl", t.toString());
		assertEquals(3, t.height());
		
		t.delete(6);
		
		assertEquals("abcdefghijkl", t.toString());
		assertEquals(3, t.height());
	}
	
	@Test
	public void testDeleteRootWithTwoChildrenCausingSingleRotation() {
		// Two level tree, rotation caused if substitution happens from right subtree
		EditTree t1 = new EditTree();
		
		t1.add('X');
		t1.add('b', 0);
		t1.add('c');
		t1.add('a', 0);
		
		t1.delete(2);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Two level tree, rotation caused if substitution happens from left subtree
		EditTree t2 = new EditTree();
		
		t2.add('X');
		t2.add('a', 0);
		t2.add('b');
		t2.add('c');
		
		t2.delete(1);
		
		assertEquals("abc", t2.toString());
		assertEquals(1, t2.height());
		
		// Three level tree, rotation caused if substitution happens from right subtree
		EditTree t3 = new EditTree();
		
		t3.add('X');
		t3.add('b', 0);
		t3.add('e');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('d', 4);
		t3.add('f');
		t3.add('g');
		
		t3.delete(3);
		
		assertEquals("abcdefg", t3.toString());
		assertTrue(t3.height() == 2 || t3.height() == 3);
		
		// Three level tree, rotation caused if substitution happens from left subtree
		EditTree t4 = new EditTree();
		
		t4.add('X');
		t4.add('c', 0);
		t4.add('f');
		t4.add('b', 0);
		t4.add('d', 2);
		t4.add('e', 4);
		t4.add('g');
		t4.add('a', 0);
		
		t4.delete(4);
		
		assertEquals("abcdefg", t4.toString());
		assertTrue(t4.height() == 2 || t4.height() == 3);
	}
	
	@Test
	public void testDeleteRootWithTwoChildrenCausingDoubleRotation() {
		// Two level tree, rotation caused if substitution happens from right subtree
		EditTree t1 = new EditTree();
		
		t1.add('X');
		t1.add('a', 0);
		t1.add('c');
		t1.add('b', 1);
		
		t1.delete(2);
		
		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());
		
		// Two level tree, rotation caused if substitution happens from left subtree
		EditTree t2 = new EditTree();
		
		t2.add('X');
		t2.add('a', 0);
		t2.add('c');
		t2.add('b', 2);
		
		t2.delete(1);
		
		assertEquals("abc", t2.toString());
		assertEquals(1, t2.height());
		
		// Three level tree, rotation caused if substitution happens from right subtree
		EditTree t3 = new EditTree();
		
		t3.add('X');
		t3.add('b', 0);
		t3.add('e');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('d', 4);
		t3.add('g');
		t3.add('f', 6);
		
		t3.delete(3);
		
		assertEquals("abcdefg", t3.toString());
		assertTrue(t3.height() == 2 || t3.height() == 3);
		
		// Three level tree, rotation caused if substitution happens from left subtree
		EditTree t4 = new EditTree();
		
		t4.add('X');
		t4.add('c', 0);
		t4.add('f');
		t4.add('a', 0);
		t4.add('d', 2);
		t4.add('e', 4);
		t4.add('g');
		t4.add('b', 1);
		
		t4.delete(4);
		
		assertEquals("abcdefg", t4.toString());
		assertTrue(t4.height() == 2 || t4.height() == 3);
	}
	
	@Test
	public void testDeleteNodeWithTwoChildrenCausingSingleRotation() {
		// Rotation caused if substitution happens from right subtree
		EditTree t1 = new EditTree();
		
		t1.add('d');
		t1.add('b', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('f', 4);
		t1.add('g', 6);
		t1.add('e', 4);
		
		t1.delete(6);
		
		assertEquals("abcdefg", t1.toString());
		assertEquals(2, t1.height());
		
		// Rotation caused if substitution happens from left subtree
		EditTree t2 = new EditTree();
		
		t2.add('d');
		t2.add('X', 0);
		t2.add('f');
		t2.add('a', 0);
		t2.add('b', 2);
		t2.add('e', 4);
		t2.add('g');
		t2.add('c', 3);
		
		t2.delete(1);
		
		assertEquals("abcdefg", t2.toString());
		assertEquals(2, t2.height());
	}
	
	@Test
	public void testDeleteNodeWithTwoChildrenCausingDoubleRotation() {
		// Rotation caused if substitution happens from right subtree
		EditTree t1 = new EditTree();
		
		t1.add('d');
		t1.add('b', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('g');
		t1.add('f', 5);
		
		t1.delete(6);
		
		assertEquals("abcdefg", t1.toString());
		assertEquals(2, t1.height());
		
		// Rotation caused if substitution happens from left subtree
		EditTree t2 = new EditTree();
		
		t2.add('d');
		t2.add('X', 0);
		t2.add('f');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('g', 6);
		t2.add('b', 2);
		
		t2.delete(1);
		
		assertEquals("abcdefg", t2.toString());
		assertEquals(2, t2.height());
	}
	
	@Test
	public void testDeleteCausingTwoRotationsStartingWithSingleLeft() {
		// single left => single left
		EditTree t1 = new EditTree();
		
		t1.add('d');
		t1.add('a', 0);
		t1.add('h');
		t1.add('X', 0);
		t1.add('b', 2);
		t1.add('f', 4);
		t1.add('j');
		t1.add('c', 3);
		t1.add('e', 5);
		t1.add('g', 7);
		t1.add('i', 9);
		t1.add('k');
		t1.add('l');
		t1.delete(0);
		
		assertEquals("abcdefghijkl", t1.toString());
		assertEquals(3, t1.height());
		
		// single left => single right
		EditTree t2 = new EditTree();
		
		t2.add('i');
		t2.add('e', 0);
		t2.add('j');
		t2.add('c', 0);
		t2.add('g', 2);
		t2.add('X', 4);
		t2.add('k');
		t2.add('b', 0);
		t2.add('d', 2);
		t2.add('f', 4);
		t2.add('h', 6);
		t2.add('l');
		t2.add('a', 0);
		
		t2.delete(9);

		assertEquals("abcdefghijkl", t2.toString());
		assertEquals(3, t2.height());
		
		// single left => double left
		EditTree t3 = new EditTree();
		
		t3.add('d');
		t3.add('a', 0);
		t3.add('i');
		t3.add('X', 0);
		t3.add('b', 2);
		t3.add('g', 4);
		t3.add('k');
		t3.add('c', 3);
		t3.add('f', 5);
		t3.add('h', 7);
		t3.add('j', 9);
		t3.add('l');
		t3.add('e', 5);
		
		t3.delete(0);

		assertEquals("abcdefghijkl", t3.toString());
		assertEquals(3, t3.height());
		
		// single left => double right
		EditTree t4 = new EditTree();
		
		t4.add('i');
		t4.add('d', 0);
		t4.add('j');
		t4.add('b', 0);
		t4.add('f', 2);
		t4.add('X', 4);
		t4.add('k');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('g', 6);
		t4.add('l');
		t4.add('h', 7);
		
		t4.delete(9);
		
		assertEquals("abcdefghijkl", t4.toString());
		assertEquals(3, t4.height());
	}
	
	@Test
	public void testDeleteCausingTwoRotationsStartingWithSingleRight() {
		// single right => single right
		EditTree t1 = new EditTree();
		
		t1.add('i');
		t1.add('e', 0);
		t1.add('l');
		t1.add('c', 0);
		t1.add('g', 2);
		t1.add('k', 4);
		t1.add('X');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('f', 4);
		t1.add('h', 6);
		t1.add('j', 8);
		t1.add('a', 0);
		
		t1.delete(12);
		
		assertEquals("abcdefghijkl", t1.toString());
		assertEquals(3, t1.height());
		
		// single right => single left
		EditTree t2 = new EditTree();
		
		t2.add('d');
		t2.add('c', 0);
		t2.add('h');
		t2.add('b', 0);
		t2.add('X', 2);
		t2.add('f', 4);
		t2.add('j');
		t2.add('a', 0);
		t2.add('e', 5);
		t2.add('g', 7);
		t2.add('i', 9);
		t2.add('k');
		t2.add('l');
		
		t2.delete(3);
		
		assertEquals("abcdefghijkl", t2.toString());
		assertEquals(3, t2.height());
		
		// single right => double right
		EditTree t3 = new EditTree();
		t3.add('i');
		t3.add('d', 0);
		t3.add('l');
		t3.add('b', 0);
		t3.add('f', 2);
		t3.add('k', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('j', 8);
		t3.add('h', 7);
		
		t3.delete(12);
		
		assertEquals("abcdefghijkl", t3.toString());
		assertEquals(3, t3.height());
		
		// single right => double left
		EditTree t4 = new EditTree();
		t4.add('d');
		t4.add('c', 0);
		t4.add('i');
		t4.add('b', 0);
		t4.add('X', 2);
		t4.add('g', 4);
		t4.add('k');
		t4.add('a', 0);
		t4.add('f', 5);
		t4.add('h', 7);
		t4.add('j', 9);
		t4.add('l');
		t4.add('e', 5);
		
		t4.delete(3);
		
		assertEquals("abcdefghijkl", t4.toString());
		assertEquals(3, t4.height());
	}
	
	@Test
	public void testDeleteCausingTwoRotationsStartingWithDoubleLeft() {
		// double left => single left
		EditTree t1 = new EditTree();
		
		t1.add('d');
		t1.add('a', 0);
		t1.add('h');
		t1.add('X', 0);
		t1.add('c', 2);
		t1.add('f', 4);
		t1.add('j');
		t1.add('b', 2);
		t1.add('e', 5);
		t1.add('g', 7);
		t1.add('i', 9);
		t1.add('k');
		t1.add('l');
		
		t1.delete(0);
		
		assertEquals("abcdefghijkl", t1.toString());
		assertEquals(3, t1.height());
		
		// double left => single right
		EditTree t2 = new EditTree();
		
		t2.add('i');
		t2.add('e', 0);
		t2.add('j');
		t2.add('c', 0);
		t2.add('g', 2);
		t2.add('X', 4);
		t2.add('l');
		t2.add('b', 0);
		t2.add('d', 2);
		t2.add('f', 4);
		t2.add('h', 6);
		t2.add('k', 10);
		t2.add('a', 0);
		
		t2.delete(9);
		
		assertEquals("abcdefghijkl", t2.toString());
		assertEquals(3, t2.height());
		
		// double left => double left
		EditTree t3 = new EditTree();
		
		t3.add('d');
		t3.add('a', 0);
		t3.add('i');
		t3.add('X', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('k');
		t3.add('b', 2);
		t3.add('f', 5);
		t3.add('h', 7);
		t3.add('j', 9);
		t3.add('l');
		t3.add('e', 5);
		
		t3.delete(0);
		
		assertEquals("abcdefghijkl", t3.toString());
		assertEquals(3, t3.height());
		
		// double left => double right
		EditTree t4 = new EditTree();
		
		t4.add('i');
		t4.add('d', 0);
		t4.add('j');
		t4.add('b', 0);
		t4.add('f', 2);
		t4.add('X', 4);
		t4.add('l');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('g', 6);
		t4.add('k', 10);
		t4.add('h', 7);
		
		t4.delete(9);
		
		assertEquals("abcdefghijkl", t4.toString());
		assertEquals(3, t3.height());
	}
	
	@Test
	public void testDeleteCausingTwoRotationsStartingWithDoubleRight() {
		// double right => single right
		EditTree t1 = new EditTree();
		t1.add('i');
		t1.add('e', 0);
		t1.add('l');
		t1.add('c', 0);
		t1.add('g', 2);
		t1.add('j', 4);
		t1.add('X');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('f', 4);
		t1.add('h', 6);
		t1.add('k', 9);
		t1.add('a', 0);
		
		t1.delete(12);
		
		assertEquals("abcdefghijkl", t1.toString());
		assertEquals(3, t1.height());
		
		// double right => single left
		EditTree t2 = new EditTree();
		
		t2.add('d');
		t2.add('c', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('X', 2);
		t2.add('f', 4);
		t2.add('j');
		t2.add('b', 1);
		t2.add('e', 5);
		t2.add('g', 7);
		t2.add('i', 9);
		t2.add('k');
		t2.add('l');
		
		t2.delete(3);
		
		assertEquals("abcdefghijkl", t2.toString());
		assertEquals(3, t2.height());
		
		// double right => double right
		EditTree t3 = new EditTree();
		
		t3.add('i');
		t3.add('d', 0);
		t3.add('l');
		t3.add('b', 0);
		t3.add('f', 2);
		t3.add('j', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('k', 9);
		t3.add('h', 7);
		
		t3.delete(12);
		
		assertEquals("abcdefghijkl", t3.toString());
		assertEquals(3, t3.height());
		
		// double right => double left
		EditTree t4 = new EditTree();
		
		t4.add('d');
		t4.add('c', 0);
		t4.add('i');
		t4.add('a', 0);
		t4.add('X', 2);
		t4.add('g', 4);
		t4.add('k');
		t4.add('b', 1);
		t4.add('f', 5);
		t4.add('h', 7);
		t4.add('j', 9);
		t4.add('l');
		t4.add('e', 5);
		
		t4.delete(3);
		
		assertEquals("abcdefghijkl", t4.toString());
		assertEquals(3, t4.height());
	}
	
	@Test
	public void testDeleteCausingTwoRotationsBelowRoot() {
		// single left => single right all on left subtree
		EditTree t1 = new EditTree();
		
		t1.add('m');
		t1.add('i', 0);
		t1.add('s');
		t1.add('e', 0);
		t1.add('j', 2);
		t1.add('p', 4);
		t1.add('v');
		t1.add('c', 0);
		t1.add('g', 2);
		t1.add('X', 4);
		t1.add('k', 6);
		t1.add('o', 8);
		t1.add('q', 10);
		t1.add('u', 12);
		t1.add('w');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('f', 4);
		t1.add('h', 6);
		t1.add('l', 11);
		t1.add('n', 13);
		t1.add('r', 17);
		t1.add('t', 19);
		t1.add('x');
		t1.add('a', 0);
		
		t1.delete(9);
		
		assertEquals("abcdefghijklmnopqrstuvwx", t1.toString());
		assertEquals(4, t1.height());
		
		// at this point, a, f, h, j, l, n, r, t, x should make up your bottom level.
		// if not, you've over-rotated, which is tested next
		
		t1.delete(23);
		t1.delete(19);
		t1.delete(17);
		t1.delete(13);
		t1.delete(11);
		t1.delete(9);
		t1.delete(7);
		t1.delete(5);
		
		assertEquals(4, t1.height());
		
		t1.delete(0); // last node of bottom level
		
		assertEquals(3, t1.height());
		
		// double right => double left all on right subtree
		EditTree t2 = new EditTree();
		
		t2.add('l');
		t2.add('f', 0);
		t2.add('p');
		t2.add('c', 0);
		t2.add('i', 2);
		t2.add('o', 4);
		t2.add('u');
		t2.add('b', 0);
		t2.add('d', 2);
		t2.add('h', 4);
		t2.add('j', 6);
		t2.add('m', 8);
		t2.add('X', 10);
		t2.add('s', 12);
		t2.add('w');
		assertEquals(3, t2.height());
		t2.add('a', 0);
		assertEquals(4, t2.height());
		t2.add('e', 4);
		t2.add('g', 6);
		t2.add('k', 10);
		t2.add('n', 13);
		t2.add('r', 17);
		t2.add('t', 19);
		t2.add('v', 21);
		t2.add('x');
		t2.add('q', 17);
		assertEquals(5, t2.height());
		assertEquals("abcdefghijklmnoXpqrstuvwx", t2.toString());
		
		t2.delete(15); // node X

		
		assertEquals("abcdefghijklmnopqrstuvwx", t2.toString());
		assertEquals(4, t2.height());
		
		// at this point, a, e, g, k, m, o, q, v, x should make up your bottom level.
		// if not, you've over-rotated, which is tested next

		t2.delete(23);
		t2.delete(21);
		t2.delete(16);
		t2.delete(14);
		t2.delete(12);
		t2.delete(10);
		t2.delete(6);
		t2.delete(4);
		
		assertEquals(4, t2.height());

		t2.delete(0); // last node of bottom level
//		DisplayTree.display(t2);
//		while (true);
				
		assertEquals(3, t2.height());
	}
	
	@Test
	public void testDeleteInvalidNode() {
		EditTree t = new EditTree();
		
		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch (IndexOutOfBoundsException e) {
			// Success
		} 
		
		try {
			t.delete(0);
			fail("Did not throw IndexOutOfBoundsException for index 0");
		} catch(IndexOutOfBoundsException e) {
			// Success
		}
		
		t.add('b');
		
		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch(IndexOutOfBoundsException e) {
			// Success
		}
		
		try {
			t.delete(1);
			fail("Did not throw IndexOutOfBoundsExcpeption for index 1");
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
		
		t.add('a', 0);
		t.add('c');
		
		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
		
		try {
			t.delete(3);
			fail("Did not throw IndexOutOfBoundsExcpetion for index 3");
		} catch (IndexOutOfBoundsException e) {
			// Success
		}
		
		try {
			t.delete(230);
			fail("Did not throw IndexOutOfBoundsException for index 230");
		} catch (IndexOutOfBoundsException e) {
			// Success 
		}
	}
	
	@Test
	// Tests double rotations
	public void testDoubleRotationOnTreeFromSlides() {
		char deleted; 
		EditTree t = makeTreeFromSlides();
		assertEquals("ABCDFGHIKLMNOPRSTUWXYZa", t.toString());

		deleted = t.delete(6);
		assertEquals('H', deleted);
		assertEquals("ABCDFGIKLMNOPRSTUWXYZa", t.toString());


		deleted = t.delete(7);
		assertEquals('K', deleted);
		assertEquals("ABCDFGILMNOPRSTUWXYZa", t.toString());

		deleted = t.delete(7);
		assertEquals('L', deleted);
		assertEquals("ABCDFGIMNOPRSTUWXYZa", t.toString());

		deleted = t.delete(1);
		assertEquals('B', deleted);
		assertEquals("ACDFGIMNOPRSTUWXYZa", t.toString());
	}
	
	public EditTree makeTreeFromSlides() {
		// Level-order insertion so no rotations (we're pretty sure).
		// CONSIDER: a check to make sure there were no rotations?
		EditTree t = new EditTree();
		t.add('I', 0);
		t.add('C', 0);
		t.add('W', 2);
		t.add('A', 0);
		t.add('G', 2);
		t.add('M', 4);
		t.add('Y', 6);
		t.add('B', 1);
		t.add('F', 3);
		t.add('H', 5);
		t.add('L', 7);
		t.add('R', 9);
		t.add('X', 11);
		t.add('a', 13);
		t.add('D', 3);
		t.add('K', 8);
		t.add('O', 11);
		t.add('T', 13);
		t.add('Z', 17);
		t.add('N', 11);
		t.add('P', 13);
		t.add('S', 15);
		t.add('U', 17);
		return t;
	}
}
