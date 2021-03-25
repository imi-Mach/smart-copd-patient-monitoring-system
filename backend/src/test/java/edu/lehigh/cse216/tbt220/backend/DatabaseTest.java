package edu.lehigh.cse216.tbt220.backend;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



/**
 * Unit test for simple App.
 */
public class DatabaseTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatabaseTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(DatabaseTest.class);
    }

    /**
     * Ensure that the constructor populates every field of the object it
     * creates
     */
    public void testConstructor() {
        String title = "Test Title";
        String content = "Test Content";
        int id = 17;
        int like = 1;
        int dislike = 1;
        DataRow d = new DataRow(id, title, content, like, dislike);

        assertTrue(d.mTitle.equals(title));
        assertTrue(d.mContent.equals(content));
        assertTrue(d.mId == id);
        assertTrue(d.mLike == like);
        assertTrue(d.mDislike == dislike);
    }

    /**
     * Ensure that the copy constructor works correctly
     */
    public void testCopyconstructor() {
        String title = "Test Title For Copy";
        String content = "Test Content For Copy";
        int id = 177;
        int like = 1;
        int dislike = 2;
        DataRow d = new DataRow(id, title, content, like, dislike);
        DataRow d2 = new DataRow(d);
        assertTrue(d2.mTitle.equals(d.mTitle));
        assertTrue(d2.mContent.equals(d.mContent));
        assertTrue(d2.mId == d.mId);
        assertTrue(d2.mLike == d.mLike);
        assertTrue(d2.mDislike == d.mDislike);
       
    }
}