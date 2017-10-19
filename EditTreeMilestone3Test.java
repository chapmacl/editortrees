package editortrees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.Test;

/**
 * Tests for {@link editortrees.EditTree#concatenate(EditTree)}
 * 
 * @author Jimmy Theis
 */
public class EditTreeMilestone3Test {

	private static double m1points = 0;
	private static double m2points = 0;
	private static double m3points = 0;

	private static final int MAX_MILESTONE1 = 50;
	private static final int MAX_MILESTONE2 = 90;
	private static final int MAX_MILESTONE3 = 80;
	private static final int MAX_DESIRED_MILESTONE1 = 15;
	private static final int MAX_DESIRED_MILESTONE2 = 45;
	private static final int MAX_DESIRED_MILESTONE3 = 80;
	private static final int MAX_POINTS = MAX_DESIRED_MILESTONE1
			+ MAX_DESIRED_MILESTONE2 + MAX_DESIRED_MILESTONE3;

	private static final double m1weight = (double) MAX_DESIRED_MILESTONE1
			/ MAX_MILESTONE1; // 0.3
	private static final double m2weight = (double) MAX_DESIRED_MILESTONE2
			/ MAX_MILESTONE2; // 0.5
	private static final double m3weight = (double) MAX_DESIRED_MILESTONE3
			/ MAX_MILESTONE3; // 1

	private EditTree makeFullTreeWithHeight(int height, char start) {
		EditTree t = new EditTree();
		// This would be much easier to do recursively, if we could
		// depend on having a Node constructor that took two children
		// as arguments!

		for (int i = 0; i <= height; i++) {
			int offset = (int) Math.pow(2, height - i) - 1;
			int increment = (int) Math.pow(2, height + 1 - i);
			for (int j = 0; j < Math.pow(2, i); j++) {
				t.add((char) (start + offset), 2 * j);
				offset += increment;
			}
		}
		return t;
	}

	// MILESTONE 1
	@Test
	public void testDoubleLeftRotationFirstLevel() {
		EditTree t = new EditTree();

		t.add('a');
		t.add('c');

		t.add('b', 1); // Rotation happens here

		assertEquals("abc", t.toString());
		assertEquals(1, t.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleLeftRotationSecondLevel() {
		// Cause a rotation in the right subtree
		EditTree t1 = new EditTree();

		t1.add('b');
		t1.add('a', 0);
		t1.add('c'); // Two full levels

		t1.add('e');
		t1.add('d', 3); // Rotation happens here

		assertEquals("abcde", t1.toString());
		assertEquals(2, t1.height());

		m1points += m1weight;

		// Cause a rotation in the left subtree
		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('a', 0);
		t2.add('e'); // Two full levels

		t2.add('c', 1);
		t2.add('b', 1); // Rotation happens here

		assertEquals("abcde", t2.toString());
		assertEquals(2, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleLeftRotationThirdLevel() {
		// Cause a rotation on the rightmost branch
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('g'); // Three full levels

		t1.add('i');
		t1.add('h', 7); // Rotation happens here

		assertEquals("abcdefghi", t1.toString());
		assertEquals(3, t1.height());

		// Cause a rotation on the leftmost branch of the right subtree
		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('i'); // Three full levels

		t2.add('g', 5);
		t2.add('f', 5); // Rotation happens here

		assertEquals("abcdefghi", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;

		// Cause a rotation on the rightmost branch of the left subtree
		EditTree t3 = new EditTree();

		t3.add('f');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); // Three full levels

		t3.add('e', 3);
		t3.add('d', 3); // Rotation happens here

		assertEquals("abcdefghi", t3.toString());
		assertEquals(3, t3.height());

		// Cause a rotation on the leftmost branch
		EditTree t4 = new EditTree();

		t4.add('f');
		t4.add('d', 0);
		t4.add('h');
		t4.add('a', 0);
		t4.add('e', 2);
		t4.add('g', 4);
		t4.add('i'); // Three full levels

		t4.add('c', 1);
		t4.add('b', 1); // Rotation happens here

		assertEquals("abcdefghi", t4.toString());
		assertEquals(3, t4.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleLeftRotationTwoLevelFromFirstLevel() {
		// Cause a rotation on the right subtree
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('i'); // Three full levels

		t1.add('h', 6);
		t1.add('j');
		t1.add('g', 6); // Rotation happens here

		assertEquals("abcdefghij", t1.toString());
		assertEquals(3, t1.height());

		m1points += m1weight;

		// Cause a rotation on the left subtree
		EditTree t2 = new EditTree();

		t2.add('g');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('e', 2);
		t2.add('h', 4);
		t2.add('j'); // Three full levels

		t2.add('d', 2);
		t2.add('f', 4);
		t2.add('c', 2); // Rotation happens here

		assertEquals("abcdefghij", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleLeftRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('e'); // Two full levels

		t.add('d', 2);
		t.add('f');
		t.add('c', 2); // Rotation happens here

		assertEquals("abcdef", t.toString());
		assertEquals(2, t.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleRightRotationFirstLevel() {
		EditTree t = new EditTree();
		t.add('c');
		t.add('a', 0);
		t.add('b', 1); // Rotation happens here

		assertEquals("abc", t.toString());
		assertEquals(1, t.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleRightRotationSecondLevel() {
		// Cause a rotation from the right subtree
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('c', 0);
		t1.add('e'); // Two full levels

		t1.add('a', 0);
		t1.add('b', 1); // Rotation happens here

		assertEquals("abcde", t1.toString());
		assertEquals(2, t1.height());

		m1points += m1weight;

		// Cause a rotation in the left subtree
		EditTree t2 = new EditTree();

		t2.add('b');
		t2.add('a', 0);
		t2.add('e'); // Two full levels

		t2.add('c', 2);
		t2.add('d', 3); // Rotation happens here

		assertEquals("abcde", t2.toString());
		assertEquals(2, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleRightRotationThirdLevel() {
		// Cause a rotation on the leftmost branch
		EditTree t1 = new EditTree();

		t1.add('f');
		t1.add('d', 0);
		t1.add('h');
		t1.add('c', 0);
		t1.add('e', 2);
		t1.add('g', 4);
		t1.add('i'); // Three full levels

		t1.add('a', 0);
		t1.add('b', 1); // Rotation happens here

		assertEquals("abcdefghi", t1.toString());
		assertEquals(3, t1.height());

		// Cause a rotation on the rightmost branch of the left subtree
		EditTree t2 = new EditTree();

		t2.add('f');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('e', 2);
		t2.add('g', 4);
		t2.add('i'); // Three full levels

		t2.add('c', 2);
		t2.add('d', 3); // Rotation happens here

		assertEquals("abcdefghi", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;

		// Cause a rotation on the leftmost branch of the right subtree
		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); // Three full levels

		t3.add('e', 4);
		t3.add('f', 5); // Rotation happens here

		assertEquals("abcdefghi", t3.toString());
		assertEquals(3, t3.height());

		// Cause a rotation on the rightmost branch
		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('i', 6); // Three full levels

		t4.add('g', 6);
		t4.add('h', 7); // Rotation happens here

		assertEquals("abcdefghi", t4.toString());
		assertEquals(3, t4.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleRightRotationTwoLevelFromFirstLevel() {
		EditTree t1 = new EditTree();

		t1.add('g');
		t1.add('e', 0);
		t1.add('i');
		t1.add('b', 0);
		t1.add('f', 2);
		t1.add('h', 4);
		t1.add('j'); // Three full levels

		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('d', 3); // Rotation happens here

		assertEquals("abcdefghij", t1.toString());
		assertEquals(3, t1.height());

		m1points += m1weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('f', 4);
		t2.add('j'); // Three full levels

		t2.add('e', 4);
		t2.add('g', 6);
		t2.add('h', 7); // Rotation happens here

		assertEquals("abcdefghij", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testDoubleRightRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();

		t.add('e');
		t.add('b', 0);
		t.add('f'); // Two full levels

		t.add('a', 0);
		t.add('c', 2);
		t.add('d', 3); // Rotation happens here

		assertEquals("abcdef", t.toString());
		assertEquals(2, t.height());

		m1points += m1weight;
	}

	@Test
	public void testEmpty() {
		EditTree t = new EditTree();
		assertEquals("", t.toString());
		assertEquals(-1, t.height());
		m1points += m1weight;
	}

	@Test
	public void testFourLevelsNoRotations() {
		EditTree t = new EditTree();
		t.add('h');
		t.add('d', 0);
		t.add('l');
		t.add('b', 0);
		t.add('f', 2);
		t.add('j', 4);
		t.add('n');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		t.add('i', 8);
		t.add('k', 10);
		t.add('m', 12);
		t.add('o');

		assertEquals("abcdefghijklmno", t.toString());
		assertEquals(3, t.height());

		m1points += m1weight;
	}

	@Test
	public void testGetAfterRotations() {
		EditTree t = new EditTree();
		t.add('a');
		t.add('b');
		t.add('c'); // Single left rotation

		assertStringByChar("abc", t);

		t.add('d', 0);
		t.add('e', 0); // Single right rotation

		assertStringByChar("edabc", t);

		m1points += m1weight;

		t.add('f', 4);
		t.add('g');
		t.add('h');
		t.add('i', 7); // Double left rotation

		assertStringByChar("edabfcgih", t);

		m1points += m1weight;

		t.add('j', 0);
		t.add('k', 2);
		t.add('l', 4);
		t.add('m', 6);
		t.add('n', 8);
		t.add('o', 10);
		t.add('p', 0);
		t.add('q', 1); // Double right rotation

		assertStringByChar("pqjekdlambnfocgih", t);

		m1points += m1weight;
	}

	@Test
	public void testGetRoot() {
		EditTree t = new EditTree();
		t.add('a');
		assertStringByChar("a", t);

		m1points += m1weight;
	}

	@Test
	public void testGetThreeLevelFull() {
		EditTree t = new EditTree();
		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g'); // Three full levels

		assertStringByChar("abcdefg", t);

		m1points += m1weight;
	}

	@Test
	public void testGetThreeLevelJagged() {
		EditTree t1 = new EditTree();

		t1.add('c');
		t1.add('b', 0);
		t1.add('d');
		t1.add('a', 0);

		assertStringByChar("abcd", t1);

		EditTree t2 = new EditTree();

		t2.add('c');
		t2.add('a', 0);
		t2.add('d');
		t2.add('b', 1);

		assertStringByChar("abcd", t2);

		EditTree t3 = new EditTree();

		t3.add('b');
		t3.add('a', 0);
		t3.add('d');
		t3.add('c', 2);

		assertStringByChar("abcd", t3);

		EditTree t4 = new EditTree();

		t4.add('b');
		t4.add('a', 0);
		t4.add('c');
		t4.add('d');

		assertStringByChar("abcd", t4);

		m1points += m1weight;
	}

	@Test
	public void testGetTwoLevelFull() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('c'); // Two full levels

		assertStringByChar("abc", t);

		m1points += m1weight;
	}

	@Test
	public void testGetTwoLevelJagged() {
		EditTree t1 = new EditTree();
		t1.add('a');
		t1.add('b');

		assertStringByChar("ab", t1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);

		assertStringByChar("ab", t2);

		m1points += m1weight;
	}

	@Test
	public void testInsertingIntoLastElement() {
		EditTree t = new EditTree();
		t.add('h', 0); // Insertion into last element
		t.add('d', 0);
		t.add('l', 2); // Insertion into last element
		t.add('b', 0);
		t.add('f', 2);
		t.add('j', 4);
		t.add('n', 6); // Insertion into last element
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		t.add('i', 8);
		t.add('k', 10);
		t.add('m', 12);
		t.add('o', 14); // Insertion into last element

		assertEquals("abcdefghijklmno", t.toString());
		assertEquals(3, t.height());

		m1points += m1weight;
	}

	@Test
	public void testRoot() {
		EditTree t = new EditTree();
		t.add('a');
		assertEquals("a", t.toString());
		assertEquals(0, t.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleLeftRotationFirstLevel() {
		EditTree t = new EditTree();
		t.add('a');
		t.add('b');

		t.add('c'); // Rotation happens here

		assertEquals("abc", t.toString());
		assertEquals(1, t.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleLeftRotationSecondLevel() {
		// Cause a rotation on the right subtree
		EditTree t1 = new EditTree();
		t1.add('b');
		t1.add('a', 0);
		t1.add('c'); // Two full levels

		t1.add('d');
		t1.add('e'); // Rotation happens here

		assertEquals("abcde", t1.toString());
		assertEquals(2, t1.height());

		m1points += m1weight;

		// Cause a rotation on the left subtree
		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('a', 0);
		t2.add('e'); // Two full levels

		t2.add('b', 1);
		t2.add('c', 2); // Rotation happens here

		assertEquals("abcde", t2.toString());
		assertEquals(2, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleLeftRotationThirdLevel() {
		// Cause a rotation on the farthest rightmost from the third level
		EditTree t1 = new EditTree();
		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('g'); // Three full levels

		t1.add('h');
		t1.add('i'); // Rotation happens here

		assertEquals("abcdefghi", t1.toString());
		assertEquals(3, t1.height());

		// Cause a rotation on the leftmost branch of the right branch from the
		// third level
		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('i'); // Three full levels

		t2.add('f', 5);
		t2.add('g', 6); // Rotation happens here

		assertEquals("abcdefghi", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;

		// Cause a rotation on the rightmost branch of the left branch from the
		// third level
		EditTree t3 = new EditTree();
		t3.add('f');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); // Three full levels

		t3.add('d', 3);
		t3.add('e', 4); // Rotation happens here

		assertEquals("abcdefghi", t3.toString());
		assertEquals(3, t3.height());

		// Cause a rotation on the leftmost branch from the third level
		EditTree t4 = new EditTree();
		t4.add('f');
		t4.add('d', 0);
		t4.add('h');
		t4.add('a', 0);
		t4.add('e', 2);
		t4.add('g', 4);
		t4.add('i'); // Three full levels

		t4.add('b', 1);
		t4.add('c', 2); // Rotation happens here

		assertEquals("abcdefghi", t4.toString());
		assertEquals(3, t4.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleLeftRotationTwoLevelFromFirstLevel() {
		// Cause a rotation on the right subtree
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('h'); // Three full levels

		t1.add('g', 6);
		t1.add('i');
		t1.add('j'); // Rotation happens here

		assertEquals("abcdefghij", t1.toString());
		assertEquals(3, t1.height());

		m1points += m1weight;

		// Cause a rotation on the left subtree
		EditTree t2 = new EditTree();

		t2.add('g');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('d', 2);
		t2.add('h', 4);
		t2.add('j'); // Three full layers

		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('f', 5); // Rotation happens here

		assertEquals("abcdefghij", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleLeftRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('d'); // Two full levels

		t.add('c', 2);
		t.add('e');
		t.add('f'); // Rotation happens here

		assertEquals("abcdef", t.toString());
		assertEquals(2, t.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleRightRotationFirstLevel() {
		EditTree t = new EditTree();
		t.add('c');
		t.add('b', 0);

		t.add('a', 0); // Rotation happens here

		assertEquals("abc", t.toString());
		assertEquals(1, t.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleRightRotationSecondLevel() {
		// Cause a rotation on the left subtree
		EditTree t1 = new EditTree();
		t1.add('d');
		t1.add('c', 0);
		t1.add('e'); // Two full levels

		t1.add('b', 0);
		t1.add('a', 0); // Rotation happens here

		assertEquals("abcde", t1.toString());
		assertEquals(2, t1.height());

		m1points += m1weight;

		// Cause a rotation on the right subtree
		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('e'); // Two full levels

		t2.add('d', 2);
		t2.add('c', 2); // Rotation happens here

		assertEquals("abcde", t2.toString());
		assertEquals(2, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleRightRotationThirdLevel() {
		// Cause a rotation on the leftmost branch of the left subtree
		EditTree t1 = new EditTree();

		t1.add('f');
		t1.add('d', 0);
		t1.add('h');
		t1.add('c', 0);
		t1.add('e', 2);
		t1.add('g', 4);
		t1.add('i'); // Three full levels

		t1.add('b', 0);
		t1.add('a', 0); // Rotation happens here

		assertEquals("abcdefghi", t1.toString());
		assertEquals(3, t1.height());

		// Cause a rotation on the rightmost branch of the left subtree
		EditTree t2 = new EditTree();

		t2.add('f');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('e', 2);
		t2.add('g', 4);
		t2.add('i'); // Three full levels

		t2.add('d', 2);
		t2.add('c', 2); // Rotation happens here

		assertEquals("abcdefghi", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;

		// Cause a rotation on the leftmost branch of the right subtree
		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); // Three full levels

		t3.add('f', 4);
		t3.add('e', 4); // Rotation happens here

		assertEquals("abcdefghi", t3.toString());
		assertEquals(3, t3.height());

		// Cause a rotation on the rightmost branch of the right subtree
		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('i'); // Three full levels

		t4.add('h', 6);
		t4.add('g', 6); // Rotation happens here

		assertEquals("abcdefghi", t4.toString());
		assertEquals(3, t4.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleRightRotationTwoLevelFromFirstLevel() {
		// Cause a rotation on the left subtree
		EditTree t1 = new EditTree();

		t1.add('g');
		t1.add('e', 0);
		t1.add('i');
		t1.add('c', 0);
		t1.add('f', 2);
		t1.add('h', 4);
		t1.add('j'); // Three full levels

		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('a', 0); // Rotation happens here

		assertEquals("abcdefghij", t1.toString());
		assertEquals(3, t1.height());

		m1points += m1weight;

		// Cause a rotation on the right subtree
		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('g', 4);
		t2.add('j'); // Three full levels

		t2.add('f', 4);
		t2.add('h', 6);
		t2.add('e', 4); // Rotation happens here

		assertEquals("abcdefghij", t2.toString());
		assertEquals(3, t2.height());

		m1points += m1weight;
	}

	@Test
	public void testSingleRightRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();

		t.add('e');
		t.add('c', 0);
		t.add('f'); // Two full levels

		t.add('b', 0);
		t.add('d', 2);
		t.add('a', 0); // Rotation happens here

		assertEquals("abcdef", t.toString());
		assertEquals(2, t.height());

		m1points += m1weight;
	}

	@Test
	public void testThrowsGetIndexExceptions() {
		EditTree t1 = new EditTree();
		try {
			t1.get(0);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t2 = new EditTree();
		try {
			t2.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t3 = new EditTree();
		t3.add('a');
		try {
			t3.get(1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t4 = new EditTree();
		t4.add('a');
		try {
			t4.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t5 = new EditTree();
		t5.add('b');
		t5.add('a', 0);
		t5.add('c');
		try {
			t5.get(3);
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t6 = new EditTree();
		t6.add('b');
		t6.add('a', 0);
		t6.add('c');
		try {
			t6.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t7 = new EditTree();
		t7.add('d');
		t7.add('b', 0);
		t7.add('f');
		t7.add('a', 0);
		t7.add('c', 2);
		t7.add('e', 4);
		t7.add('g');
		try {
			t7.get(7);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t8 = new EditTree();
		t8.add('d');
		t8.add('b', 0);
		t8.add('f');
		t8.add('a', 0);
		t8.add('c', 2);
		t8.add('e', 4);
		t8.add('g');
		try {
			t8.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		m1points += 2 * m1weight;
	}

	@Test
	public void testThreeLevelsNoRotations() {
		EditTree t = new EditTree();
		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g');

		assertEquals("abcdefg", t.toString());
		assertEquals(2, t.height());

		m1points += m1weight;
	}

	@Test
	public void testThrowsAddIndexExceptions() {
		EditTree t1 = new EditTree();
		try {
			t1.add('a', 1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t2 = new EditTree();
		try {
			t2.add('a', -1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			m1points += m1weight;
		}

		EditTree t3 = new EditTree();
		t3.add('b');
		t3.add('a', 0);
		t3.add('c');
		try {
			t3.add('d', 4);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t4 = new EditTree();
		t4.add('b');
		t4.add('a', 0);
		t4.add('c');
		try {
			t4.add('d', -1);
		} catch (IndexOutOfBoundsException e) {
			m1points += m1weight;
		}
	}

	@Test
	public void testTwoLevelsNoRotations() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('c');

		assertEquals("abc", t.toString());
		assertEquals(1, t.height());

		m1points += m1weight;
	}

	// MILESTONE 2
	/**
	 * Test method for {@link editortrees.EditTree#delete(int)}.
	 */
	@Test
	public void testDeleteInt() {
		// Tests all the simplest rotation cases for delete (causing each type
		// of rotation)
		EditTree t = new EditTree();
		t.add('o');
		t.add('u');
		t.add('i', 0);
		t.add('e', 0);
		t.delete(3); // u
		assertEquals("eio", t.toString());
		assertEquals(1, t.height());
		assertEquals(1, t.totalRotationCount());

		m2points += m2weight;

		t.add('g', 1);
		t.delete(3); // o
		assertEquals("egi", t.toString());
		assertEquals(1, t.height());
		assertEquals(3, t.totalRotationCount());

		m2points += m2weight;

		t.add('o');
		t.delete(0); // e
		assertEquals("gio", t.toString());
		assertEquals(1, t.height());
		assertEquals(4, t.totalRotationCount());

		m2points += m2weight;

		t.add('k', 2);
		t.delete(0); // g
		assertEquals("iko", t.toString());
		assertEquals(1, t.height());
		assertEquals(6, t.totalRotationCount());

		m2points += m2weight;

		t.add('e', 0);
		t.delete(2); // k (root)
		assertEquals("eio", t.toString());
		assertEquals(1, t.height());
		// CONSIDER: Change to 7 only if delete successor of node with 2
		// children
		assertTrue(7 == t.totalRotationCount() || 6 == t.totalRotationCount());

		m2points += m2weight;

		t.add('g', 1);
		t.delete(2); // i (root)
		assertEquals("ego", t.toString());
		assertEquals(1, t.height());
		// CONSIDER: Change to 9 only if delete successor of node with 2
		// children
		assertTrue(9 == t.totalRotationCount() || 6 == t.totalRotationCount());

		m2points += m2weight;

		t.add('u');
		t.delete(1); // g (root)
		assertEquals("eou", t.toString());
		assertEquals(1, t.height());

		m2points += m2weight;

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

		// Now delete the nodes in level order (by reverse level) so no
		// rotations happen

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

		t.delete(0); // d
		assertEquals("", t.toString());
		assertEquals(-1, t.height());

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
	}

	@Test
	public void testDeleteRootWithSingleChild() {
		// Root has left child
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('a', 0);

		t1.delete(1);

		assertEquals("a", t1.toString());
		assertEquals(0, t1.height());

		m2points += m2weight;

		// Root has right child
		EditTree t2 = new EditTree();

		t2.add('X');
		t2.add('a');

		t2.delete(0);

		assertEquals("a", t2.toString());
		assertEquals(0, t2.height());

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;

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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

		t.delete(10);

		assertEquals("abcdefXghijkl", t.toString());
		assertEquals(3, t.height());

		m2points += m2weight;

		t.delete(6);

		assertEquals("abcdefghijkl", t.toString());
		assertEquals(3, t.height());

		m2points += m2weight;
	}

	@Test
	public void testDeleteRootWithTwoChildrenCausingSingleRotation() {
		// Two level tree, rotation caused if substitution happens from right
		// subtree
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('b', 0);
		t1.add('c');
		t1.add('a', 0);

		t1.delete(2);

		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());

		m2points += m2weight;

		// Two level tree, rotation caused if substitution happens from left
		// subtree
		EditTree t2 = new EditTree();

		t2.add('X');
		t2.add('a', 0);
		t2.add('b');
		t2.add('c');

		t2.delete(1);

		assertEquals("abc", t2.toString());
		assertEquals(1, t2.height());

		m2points += m2weight;

		// Three level tree, rotation caused if substitution happens from right
		// subtree
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

		m2points += m2weight;

		// Three level tree, rotation caused if substitution happens from left
		// subtree
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

		m2points += m2weight;
	}

	@Test
	public void testDeleteRootWithTwoChildrenCausingDoubleRotation() {
		// Two level tree, rotation caused if substitution happens from right
		// subtree
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('a', 0);
		t1.add('c');
		t1.add('b', 1);

		t1.delete(2);

		assertEquals("abc", t1.toString());
		assertEquals(1, t1.height());

		m2points += m2weight;

		// Two level tree, rotation caused if substitution happens from left
		// subtree
		EditTree t2 = new EditTree();

		t2.add('X');
		t2.add('a', 0);
		t2.add('c');
		t2.add('b', 2);

		t2.delete(1);

		assertEquals("abc", t2.toString());
		assertEquals(1, t2.height());

		m2points += m2weight;

		// Three level tree, rotation caused if substitution happens from right
		// subtree
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

		m2points += m2weight;

		// Three level tree, rotation caused if substitution happens from left
		// subtree
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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;

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

		m2points += m2weight;

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

		m2points += m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;

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
		assertEquals(3, t3.totalRotationCount());
		m2points += 2 * m2weight;

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

		m2points += 2 * m2weight;
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

		m2points += m2weight;

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

		m2points += m2weight;

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

		m2points += m2weight;

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

		m2points += 2 * m2weight;
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

		m2points += m2weight;

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

		m2points += 2 * m2weight;

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

		m2points += 2 * m2weight;

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

		m2points += 2 * m2weight;
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
		assertEquals(2, t1.totalRotationCount());

		m2points += m2weight;

		// at this point, a, f, h, j, l, n, r, t, x should make up your bottom
		// level.
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
		assertEquals(2, t1.totalRotationCount());

		t1.delete(0); // last node of bottom level

		assertEquals(3, t1.height());

		m2points += m2weight;

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

		m2points += m2weight;

		t2.delete(15); // node X
		assertEquals(4, t2.totalRotationCount());

		assertEquals("abcdefghijklmnopqrstuvwx", t2.toString());
		assertEquals(4, t2.height());

		m2points += 2 * m2weight;

		// at this point, a, e, g, k, m, o, q, v, x should make up your bottom
		// level.
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

		m2points += m2weight;

		t2.delete(0); // last node of bottom level

		assertEquals(3, t2.height());

		m2points += 2 * m2weight;
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
		} catch (IndexOutOfBoundsException e) {
			// Success
		}

		t.add('b');

		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch (IndexOutOfBoundsException e) {
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

		m2points += m2weight;
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
		m2points += 2 * m2weight;

		deleted = t.delete(7);
		assertEquals('K', deleted);
		assertEquals("ABCDFGILMNOPRSTUWXYZa", t.toString());

		deleted = t.delete(7);
		assertEquals('L', deleted);
		assertEquals("ABCDFGIMNOPRSTUWXYZa", t.toString());

		deleted = t.delete(1);
		assertEquals('B', deleted);
		assertEquals("ACDFGIMNOPRSTUWXYZa", t.toString());

		m2points += 2 * m2weight;
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
		assertEquals(0, t.totalRotationCount());

		return t;
	}

	@Test
	public void testConstructorWithEditTree() {
		EditTree t1s = new EditTree();
		EditTree t2s = makeFullTreeWithHeight(0, 'a');
		EditTree t3s = makeFullTreeWithHeight(2, 'a');

		EditTree t1 = new EditTree(t1s);
		EditTree t2 = new EditTree(t2s);
		EditTree t3 = new EditTree(t3s);

		// Test to see that nodes are clones of nodes from the original tree,
		// and not just aliases to the same nodes.
		// We make use of the getRoot() method and its left and right children.
		Node t3root = t3.getRoot();
		Node t3originalRoot = t3s.getRoot();
		assertFalse(t3root == t3originalRoot);
		assertFalse(t3root.left == t3originalRoot.left);
		assertFalse(t3root.right.left == t3originalRoot.right.left);

		// Test using sizes and heights.
		assertEquals(t1s.toString(), t1.toString());
		assertTrue(t1.height() <= maxHeight(t1.size()));
		assertEquals(t2s.toString(), t2.toString());
		assertTrue(t2.height() <= maxHeight(t2.size()));
		assertEquals(t3s.toString(), t3.toString());
		assertTrue(t3.height() <= maxHeight(t3.size()));
		assertEquals(0, t3.totalRotationCount());

		assertEquals(t1s.height(), t1.height());
		assertEquals(t2s.height(), t2.height());
		assertEquals(t3s.height(), t3.height());
		m2points += 2 * m2weight;

		t3.add('x');
		EditTree t4 = new EditTree(t3);
		assertEquals(t3.toString(), t4.toString());
		assertTrue(t4.height() <= maxHeight(t4.size()));

		// Add to tree I copied to. Are balance codes and ranks set?
		t4.add('y', 2);
		EditTree t5 = new EditTree(t4);
		assertEquals(t4.toString(), t5.toString());
		assertTrue(t5.height() <= maxHeight(t5.size()));

		// Add again to tree I copied to, but causing a rotation
		t5.add('z', 2);
		EditTree t6 = new EditTree(t5);
		assertEquals(t5.toString(), t6.toString());
		assertTrue(t6.height() <= maxHeight(t6.size()));
		assertEquals(0, t3.totalRotationCount());
		m2points += 2 * m2weight;
	}

	private int maxHeight(int nodes) {
		int height = -1;
		int maxNodes = 1;
		int prevMaxNodes = 0;

		while (nodes >= maxNodes) {
			int temp = prevMaxNodes;
			prevMaxNodes = maxNodes;
			maxNodes = temp + maxNodes + 1;
			height++;
		}

		return height;
	}

	@Test
	public void testManyRotations() {
		EditTree t = new EditTree();
		t.add('J');
		t.add('T');
		t.add('O', 1);
		assertEquals(2, t.totalRotationCount());
		t.add('L', 1);
		t.add('N', 2);
		assertEquals(3, t.totalRotationCount());
		t.add('M', 2);
		assertEquals(5, t.totalRotationCount());
		t.add('m');
		assertEquals(6, t.totalRotationCount());
		t.add('o');
		t.add('d', 6);
		t.add('g', 7);
		assertEquals(8, t.totalRotationCount());
		t.add('R', 5);
		assertEquals(10, t.totalRotationCount());
		t.add('b', 7);
		assertEquals(12, t.totalRotationCount());
		t.add('q');
		t.add('r');
		assertEquals(13, t.totalRotationCount());
		assertEquals(4, t.height());
		t.add('s');
		assertEquals(14, t.totalRotationCount());
		t.add('t');
		assertEquals(15, t.totalRotationCount());
		t.add('u');
		assertEquals(16, t.totalRotationCount());
		t.add('v');
		assertEquals(17, t.totalRotationCount());
		t.add('w');
		assertEquals(18, t.totalRotationCount());
		t.add('x');
		assertEquals(19, t.totalRotationCount());
		t.add('y');
		assertEquals(20, t.totalRotationCount());
		t.add('z');
		assertEquals(21, t.totalRotationCount());
		t.add('{');
		assertEquals(22, t.totalRotationCount());
		t.add('|');
		assertEquals(23, t.totalRotationCount());
		assertEquals(4, t.height());
		t.add('}');
		assertEquals(24, t.totalRotationCount());
		t.add('~');
		assertEquals(25, t.totalRotationCount());
		t.add('[');
		assertEquals(26, t.totalRotationCount());
		t.add(']');
		assertEquals(27, t.totalRotationCount());
		assertEquals(4, t.height());
		t.add('&');
		assertEquals(27, t.totalRotationCount());
		assertEquals(5, t.height());
		t.add('!');
		assertEquals(28, t.totalRotationCount());
		assertEquals(5, t.height());
		m2points += 2 * m2weight; // CONSIDER: change to m1weight
	}

	/*
	 * Random add/delete test case by James Breen
	 * 
	 * Modified by Jimmy Theis 2011-05-02: - Added randomized position to insert
	 * into
	 */
	@Test
	public void testRandomAddDelete() {

		Random gen = new Random();
		int alphabetSize = 26;
		int lengthWord = 1;

		StringBuffer analog = new StringBuffer(""); // String to test against

		// create a random tree
		EditTree t = new EditTree();
		for (int i = 0; i < lengthWord; i++) {
			char c = (char) ('a' + (gen.nextInt(alphabetSize)));
			int pos = gen.nextInt(t.size() + 1);
			t.add(c, pos);
			analog.insert(pos, c);
		}

		assertEquals(analog.toString(), t.toString());

		m2points += 4 * m2weight;

		// delete at random location from tree
		for (int i = 0; i < lengthWord; i++) {
			int pos = gen.nextInt(lengthWord - i);
			t.delete(pos);
			analog.deleteCharAt(pos);
		}

		assertEquals(analog.toString(), t.toString());

		m2points += 4 * m2weight;
	}

	// Called by several tests for add()
	public void assertStringByChar(String expected, EditTree t) {
		for (int i = 0; i < expected.length(); i++) {
			assertEquals(expected.charAt(i), t.get(i));
		}
	}

	// MILESTONE 3 TESTS

	@Test
	public void testFindInEmptyTree() {
		EditTree t1 = new EditTree();
		assertEquals(0, t1.find(""));
		assertEquals(-1, t1.find("abcdefg"));

		m3points += 1 * m3weight;
	}

	@Test
	public void testFindEntireTreeFullTree() {
		EditTree t1 = makeFullTreeWithHeight(0, 'a');
		EditTree t2 = makeFullTreeWithHeight(1, 'a');
		EditTree t3 = makeFullTreeWithHeight(3, 'a');

		assertEquals(0, t3.totalRotationCount());
		assertEquals(0, t1.find(""));
		assertEquals(0, t1.find("a"));
		assertEquals(0, t2.find("abc"));
		assertEquals(1, t2.find("bc"));
		assertEquals(0, t3.find("abcdefghijklmno"));
		assertEquals(2, t3.find("cdefg"));

		m3points += 1 * m3weight;
	}

	@Test
	public void testFindEntireTreeJaggedTree() {
		EditTree t1 = new EditTree();
		t1.add('c');
		t1.add('a', 0);
		t1.add('d');
		t1.add('b', 1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('d');
		t2.add('c', 2);

		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		assertEquals(0, t1.find("abcd"));
		assertEquals(0, t2.find("abcd"));
		assertEquals(0, t3.find("abcdefghijklmnop"));
		assertEquals(0, t3.find("abcdefghijklmnop"));
		assertEquals(3, t3.find("defgh"));
		m3points += 1 * m3weight;
	}

	@Test
	public void testFindSubstringFullTree() {
		EditTree t1 = makeFullTreeWithHeight(1, 'a');
		EditTree t2 = makeFullTreeWithHeight(3, 'a');

		assertEquals(0, t1.find("a"));
		assertEquals(2, t1.find("c"));
		assertEquals(2, t1.find("c", 2));
		assertEquals(1, t1.find("bc"));
		assertEquals(1, t1.find("bc", 1));
		assertEquals(2, t2.find("cdef"));
		assertEquals(2, t2.find("cdef", 1));
		assertEquals(1, t2.find("bcdefg"));
		assertEquals(1, t2.find("bcdefg", 1));

		m3points += 1 * m3weight;
	}

	@Test
	public void testFindSubstringJaggedTree() {
		EditTree t1 = new EditTree();
		t1.add('c');
		t1.add('a', 0);
		t1.add('d');
		t1.add('b', 1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('d');
		t2.add('c', 2);

		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		assertEquals(0, t1.totalRotationCount());
		assertEquals(0, t2.totalRotationCount());
		assertEquals(0, t3.totalRotationCount());
		assertEquals(0, t4.totalRotationCount());
		assertEquals(0, t1.find("abc"));
		assertEquals(1, t2.find("bcd"));
		assertEquals(1, t2.find("bcd", 0));

		m3points += 1 * m3weight;

		assertEquals(1, t3.find("bcdefghi"));
		assertEquals(1, t3.find("bcdefghi", 1));
		assertEquals(0, t3.find("abc"));
		assertEquals(3, t4.find("defghijklmnop"));
		m3points += 1 * m3weight;
	}

	@Test
	public void testFindInvalidSubString() {
		EditTree t1 = makeFullTreeWithHeight(1, 'a');
		EditTree t2 = makeFullTreeWithHeight(3, 'a');
		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		assertEquals(-1, t1.find("az"));
		assertEquals(-1, t1.find("ab", 1));
		assertEquals(-1, t2.find("bz"));
		assertEquals(-1, t3.find("z"));
		assertEquals(-1, t4.find("abcdefg", 1));
		assertEquals(-1, t4.find("bcd", 2));

		m3points += 1 * m3weight;
	}

	@Test
	public void testGetEntireTree() {
		EditTree t1 = new EditTree();
		t1.add('c');
		t1.add('a', 0);
		t1.add('d');
		t1.add('b', 1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('d');
		t2.add('c', 2);

		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		EditTree t5 = makeFullTreeWithHeight(1, 'a');
		EditTree t6 = makeFullTreeWithHeight(3, 'a');

		assertEquals(t1.toString(), t1.get(0, t1.size()));
		assertEquals(t2.toString(), t2.get(0, t2.size()));
		assertEquals(t3.toString(), t3.get(0, t3.size()));
		assertEquals(t4.toString(), t4.get(0, t4.size()));
		assertEquals(t5.toString(), t5.get(0, t5.size()));
		assertEquals(t6.toString(), t6.get(0, t6.size()));

		m3points += 1 * m3weight;
	}

	@Test
	public void testGetSubstring() {
		EditTree t1 = new EditTree();
		t1.add('c');
		t1.add('a', 0);
		t1.add('d');
		t1.add('b', 1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('d');
		t2.add('c', 2);

		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		assertEquals("abc", t1.get(0, 3));
		assertEquals("bcd", t1.get(1, 3));
		assertEquals("d", t2.get(3, 1));
		assertEquals("abcdef", t3.get(0, 6));
		assertEquals("lmnop", t4.get(11, 5));

		m3points += 1 * m3weight;
	}

	@Test
	public void testGetInvalidRange() {
		EditTree t1 = new EditTree();
		EditTree t2 = makeFullTreeWithHeight(2, 'a');

		// empty tree; beginning of string out of bounds
		try {
			t1.get(-1, 2);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// assertTrueed
		}

		// empty tree; end of string out of bounds
		try {
			t1.get(0, 1);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// assertTrueed
		}

		// beginning of string out of bounds
		try {
			t2.get(-1, 3);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// assertTrueed
		}

		// end of string out of bounds
		try {
			t2.get(4, 4);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// assertTrueed
		}

		try {
			t2.get(0, 8);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// assertTrueed
		}

		// beginning of string too large
		try {
			t2.get(7, 1);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			// assertTrueed
		}

		m3points += 1 * m3weight;
	}

	@Test
	public void testConstructorWithString() {
		EditTree t1 = new EditTree();
		EditTree t2 = new EditTree("abc");
		EditTree t3 = new EditTree("abcdefghijkl");

		int h, max;
		assertEquals(
				0,
				t1.totalRotationCount() + t2.totalRotationCount()
						+ t3.totalRotationCount());
		// Can build these trees in ways that require no rotations.
		assertEquals("", t1.toString());
		h = t1.height();
		max = maxHeight(t1.size());
		assertTrue(h <= max);
		assertEquals("abc", t2.toString());
		assertTrue(t2.height() <= maxHeight(t2.size()));
		assertEquals("abcdefghijkl", t3.toString());
		h = t3.height();
		max = maxHeight(t3.size());
		assertTrue(h <= max);

		m3points += 5 * m3weight;

		// Test using random additions to make sure that rank and balance codes
		// have been set.
		t1.add('d');
		t1.add('f'); // causes rotation
		t1.add('e', 1);
		t1.add('g', 0);
		t1.add('h', 2);

		t3.add('m', 5);
		t3.add('n', 7);
		t3.add('o', 2);
		t3.add('p', 7);
		t3.add('q', 11);
		t3.add('r', 3);
		t3.add('s', 15);

		m3points += 5 * m3weight;
	}

	public boolean attemptConcatenation(EditTree t1, EditTree t2) {
		int hT1 = t1.height();
		int hT2 = t2.height();
		int sT1 = t1.size();
		int sT2 = t2.size();

		boolean result = true;

		// Expected toString() of concatenated tree
		String expected = t1.toString() + t2.toString();

		t1.concatenate(t2);

		// Check output of concatenated tree
		result = result && t1.toString().equals(expected);

		// Make sure the old tree is now empty
		result = result && t2.toString().equals("");

		// Make sure new tree is height-balanced
		int newHT1 = t1.height();

		// Based on Fibonacci trees; see solution to assignment 6, 201130,
		// and maxHeight() definition below.
		int maxNewHeight = maxHeight(sT1 + sT2);
		result = result && newHT1 <= maxNewHeight;

		// Concatenation can't grow the tallest tree by more than 1 level.
		result = result && newHT1 <= (Math.max(hT1, hT2) + 1);

		// Add a few elements, just to make sure balance codes aren't messed up
		t1.add('1', 0);
		t1.add('0', 0);
		t1.add('1');
		t1.add('0');

		result = result && t1.toString().equals("01" + expected + "10");
		result = result && t1.height() <= maxHeight(t1.size());

		return result;
	}

	@Test
	public void testConcatenateTwoEmptyTrees() {
		EditTree t1 = new EditTree();
		EditTree t2 = new EditTree();

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateOneEmptyTree() {
		EditTree t1 = makeFullTreeWithHeight(3, 'a');
		EditTree t2 = new EditTree();

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = new EditTree();
		t2 = makeFullTreeWithHeight(3, 'a');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateTwoFullTreesEqualHeights() {
		EditTree t1 = makeFullTreeWithHeight(1, 'a');
		EditTree t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(2, 'a');
		t2 = makeFullTreeWithHeight(2, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(0, 'a');
		t2 = makeFullTreeWithHeight(0, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateTwoFullTreesLeftLarger() {
		EditTree t1 = makeFullTreeWithHeight(2, 'a');
		EditTree t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(4, 'a');
		t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t2 = makeFullTreeWithHeight(2, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(4, 'a');
		t2 = makeFullTreeWithHeight(2, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(5, 'a');
		t2 = makeFullTreeWithHeight(2, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t2 = makeFullTreeWithHeight(0, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateTwoFullTreesRightLarger() {
		EditTree t1 = makeFullTreeWithHeight(1, 'a');
		EditTree t2 = makeFullTreeWithHeight(2, 'A');

		//assertConcatenation(t1, t2);

		t1 = makeFullTreeWithHeight(0, 'a');
		t2 = makeFullTreeWithHeight(2, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(1, 'a');
		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(1, 'a');
		t2 = makeFullTreeWithHeight(4, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(2, 'a');
		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(2, 'a');
		t2 = makeFullTreeWithHeight(4, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(2, 'a');
		t2 = makeFullTreeWithHeight(5, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(0, 'a');
		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateWithRotationCausedByDeleteOnRightTree() {
		EditTree t1 = makeFullTreeWithHeight(3, 'a');
		EditTree t2 = new EditTree();
		t2.add('B');
		t2.add('A', 0);
		t2.add('D');
		t2.add('C', 2);

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(2, 'a');
		t2 = new EditTree();
		t2.add('B');
		t2.add('A', 0);
		t2.add('C');
		t2.add('D');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateWithLeftTreeRotationAfterPaste() {
		EditTree t1 = makeFullTreeWithHeight(2, 'a');
		t1.delete(2);
		t1.add('h', 5); // so t1.toString() is "abdefhg"

		EditTree t2 = new EditTree();
		t2.add('B');
		t2.add('A', 0);
		t2.add('D');
		t2.add('C', 2);

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateNonLeafRightmostNode() {
		// Empty second tree
		EditTree t1 = new EditTree();
		t1.add('b');
		t1.add('a', 0);
		t1.add('d');
		t1.add('c', 2);

		EditTree t2 = new EditTree();

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		// Second tree of height 1
		t1 = new EditTree();
		t1.add('b');
		t1.add('a', 0);
		t1.add('d');
		t1.add('c', 2);

		t2 = makeFullTreeWithHeight(1, 'a');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		// Second tree of height 3
		t1 = new EditTree();
		t1.add('b');
		t1.add('a', 0);
		t1.add('d');
		t1.add('c', 2);

		t2 = makeFullTreeWithHeight(3, 'a');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		// Empty second tree
		t1 = new EditTree();
		t1.add('f');
		t1.add('c', 0);
		t1.add('h');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('g', 4);
		t1.add('j');
		t1.add('a', 0);
		t1.add('e', 4);
		t1.add('i', 8);

		t2 = new EditTree();

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		// Second tree of height 1
		t1 = new EditTree();
		t1.add('f');
		t1.add('c', 0);
		t1.add('h');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('g', 4);
		t1.add('j');
		t1.add('a', 0);
		t1.add('e', 4);
		t1.add('i', 8);

		t2 = makeFullTreeWithHeight(1, 'a');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		// Second tree of height 3
		t1 = new EditTree();
		t1.add('f');
		t1.add('c', 0);
		t1.add('h');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('g', 4);
		t1.add('j');
		t1.add('a', 0);
		t1.add('e', 4);
		t1.add('i', 8);

		t2 = makeFullTreeWithHeight(3, 'a');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;
	}

	@Test
	public void testConcatenateDeepRightmostNode() {
		EditTree t1 = makeFullTreeWithHeight(0, 'a');
		t1.add('b');

		EditTree t2 = makeFullTreeWithHeight(0, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(0, 'a');
		t1.add('b');

		t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(0, 'a');
		t1.add('b');

		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(1, 'a');
		t1.add('d');

		t2 = makeFullTreeWithHeight(0, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(1, 'a');
		t1.add('d');

		t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(1, 'a');
		t1.add('d');

		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t1.add('p');

		t2 = makeFullTreeWithHeight(0, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t1.add('p');

		t2 = makeFullTreeWithHeight(1, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

		t1 = makeFullTreeWithHeight(3, 'a');
		t1.add('p');

		t2 = makeFullTreeWithHeight(3, 'A');

		assertTrue(attemptConcatenation(t1, t2));
		m3points += 1 * m3weight;

	}

	@Test
	public void testConcatenateSelf() {
		EditTree t1 = makeFullTreeWithHeight(3, 'a');

		try {
			t1.concatenate(t1);
			fail("Did now throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// assertTrueed
		}

		EditTree t2 = new EditTree();

		try {
			t2.concatenate(t2);
			fail("Did not throw IllegalArgumentException");
		} catch (IllegalArgumentException e) {
			// assertTrueed
		}

		m3points += 1 * m3weight;
	}

	@Test
	public void testSplitFullTree() {
		for (int height = 0; height < 5; height++) {
			EditTree t1 = makeFullTreeWithHeight(height, 'a');
			String str = t1.toString();
			for (int i = 0; i < t1.size(); i++) {
				// Start with a fresh copy of t1 every time since t1 is modified
				// by the split.
				EditTree t3 = new EditTree(t1);
				EditTree t2 = t3.split(i);
				String s1 = str.substring(0, i);
				String s2 = str.substring(i);
				assertEquals(s1, t3.toString());
				assertEquals(s2, t2.toString());
				assertTrue(t3.height() <= maxHeight(t3.size()));
				assertTrue(t2.height() <= maxHeight(t2.size()));
			}
		}

		m3points += 5 * m3weight;
	}

	@Test
	public void testSplitJaggedTree() {
		EditTree t1 = makeTreeFromSlides();
		String str = t1.toString();
		for (int i = 0; i < t1.size(); i++) {
			// Start with a fresh copy of t1 every time since t1 is modified by
			// the split.
			EditTree t3 = new EditTree(t1);
			EditTree t2 = t3.split(i);
			String s1 = str.substring(0, i);
			String s2 = str.substring(i);
			assertEquals(s1, t3.toString());
			assertEquals(s2, t2.toString());
			assertTrue(t3.height() <= maxHeight(t3.size()));
			assertTrue(t2.height() <= maxHeight(t2.size()));
		}

		m3points += 5 * m3weight;
	}

	// STRESS TESTS FOR ADD

	private static final int NUM_NODES = 1000000;
	private static final int SKIP_INTERVAL = 10;

	@Test
	public void testAddManyInc() {
		EditTree t = new EditTree();
		for (int k = 0; k < NUM_NODES; k++) {
			t.add((char) k);
		}

		assertEquals(19, t.height());
		assertEquals(NUM_NODES, t.size());
		assertEquals(999980, t.totalRotationCount());
		m3points += 2 * m3weight;
	}

	@Test
	public void testAddManyDec() {
		EditTree t = new EditTree();
		for (int k = NUM_NODES; k > 0; k--) {
			t.add((char) k);
		}

		assertEquals(19, t.height());
		assertEquals(NUM_NODES, t.size());
		assertEquals(999980, t.totalRotationCount());
		m3points += 2 * m3weight;
	}

	/**
	 * If a student should fail this test once, they might have reached the
	 * rotation count outside of the tested range. If a student should failed
	 * this test consistently - excessive rotations are being performed.
	 */
	@Test
	public void testAddManyRandom() {

		EditTree t = new EditTree();
		Random random = new Random();
		for (int k = 0; k < NUM_NODES / SKIP_INTERVAL; k++) {
			for (int j = 0; j < 10; j++) {
				char toAdd = (char) ('0' + j);
				t.add(toAdd, random.nextInt(SKIP_INTERVAL * k + j + 1));
			}
		}
		assertEquals(NUM_NODES, t.size());

		m3points += 2 * m3weight;

		// System.out.println(t.totalRotationCount());
		int height = t.height();
		int maxH = (int) Math.ceil(1.44 * (Math.log(NUM_NODES) / Math.log(2)));
		int minH = (int) Math.floor(Math.log(NUM_NODES) / Math.log(2));
		boolean heightCheck = false;
		if (height >= minH && height <= maxH) {
			heightCheck = true;
		}
		assertTrue(heightCheck);
		m3points += 2 * m3weight;

		// This range was created using several complete EditorTrees project
		// that returned similar results
		// It has a slight amount of built in padding, but even for the random
		// insertion values should still fit in this range.
		// Dr. B. found min of 696758, max of 700715 when running 150-200x.
		int maxR = 701000;
		int minR = 696500;
		int rot = t.totalRotationCount();
		boolean rotCheck = false;
		if (rot >= minR && rot <= maxR) {
			rotCheck = true;
		}
		assertTrue(rotCheck);
		m3points += 2 * m3weight;
	}

	@AfterClass
	public static void printSummary() {
		System.out.print("\n ===============     ");
		System.out.print("Milestone 1 again: ");
		System.out.printf("%4.1f / %d ", m1points, MAX_DESIRED_MILESTONE1);
		System.out.println("     ===============");

		System.out.print("\n ===============     ");
		System.out.print("Milestone 2 again: ");
		System.out.printf("%4.1f / %d ", m2points, MAX_DESIRED_MILESTONE2);
		System.out.println("     ===============");

		System.out.print("\n ===============     ");
		System.out.print("Milestone 3:       ");
		System.out.printf("%4.1f / %d ", m3points, MAX_DESIRED_MILESTONE3);
		System.out.println("     ===============");

		double points = m1points + m2points + m3points;
		System.out.print("\n ===============     ");
		System.out.print("Total Points:     ");
		System.out.printf("%4.1f / %d ", points, MAX_POINTS);
		System.out.println("     ===============");
	}
}
