package edu.lehigh.cse216.tbt220.backend;

public class DataRow {
    /**
     * The unique identifier associated with this element.  It's final, because
     * we never want to change it.
     */
    public final int mId;

    /**
     * The title for this row of data
     */
    public String mTitle;

    /**
     * The content for this row of data
     */
    public String mContent;

     /**
     * The like for this row of data
     */
    public int mLike;

     /**
     * The dislike for this row of data
     */
    public int mDislike;

    

    /**
     * Create a new DataRow with the provided id and title/content, and a 
     * creation date based on the system clock at the time the constructor was
     * called
     * 
     * @param id The id to associate with this row.  Assumed to be unique 
     *           throughout the whole program.
     * 
     * @param title The title string for this row of data
     * 
     * @param content The content string for this row of data
     * 
     * @param like The like for this row of data
     * 
     * @param dislike The dislike for this row of data
     */
    DataRow(int id, String title, String content, int like, int dislike) {
        mId = id;
        mTitle = title;
        mContent = content;
        mLike = like;
        mDislike = dislike;
    }

    /**
     * Copy constructor to create one datarow from another
     */
    DataRow(DataRow data) {
        mId = data.mId;
        // NB: Strings and Dates are immutable, so copy-by-reference is safe
        mTitle = data.mTitle;
        mContent = data.mContent;
        mLike = data.mLike;
        mDislike = data.mDislike;
    }
}