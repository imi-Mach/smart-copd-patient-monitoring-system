const chai   = require('chai');
const should = chai.should();
const expect = chai.expect;

const db     = require('../datastore/datastore');
const { add } = require('../datastore/datastore');

const resetDatabase = () => {
  db.setState({}).write(); // clears by setting to empty JSON object.
  db.setState(require('./testData.json')).write(); // Syncrhonously reads the testData.json file and makes that the db.
}

/**
 * This 'describe' at the root level is used to define a Test Suite. This is a set of tests that are targeted
 * towards a specific aspect of the codebase. In this case we'd like to test the posting functionality of the app.
 * For this reason, we call this root Test Suite 'Posts'.
 */
describe('Posts', function() {

  /**
   * Before each individual test, clear the database and reset it to the state defined in testData.json.
   * This ensures each test runs with the same, unmodified data, so that each test can safely modify the data without
   * fear of messing up the test database.
   **/
  beforeEach('[ Posts ]: Resetting database before next SUITE', function() {
    resetDatabase();
  });

  // after will run after the last 'describe' (test suite) at the root level
  after('[ Posts ]: Completed all tests and clearing the database', function () {
    db.setState({}).write(); // clears the test database after the last test to fully reset
    console.log('DATABASE CLEARED. END OF TESTING!');
  });

  /**
   * You can create test suites inside of the root one to test different aspects of the larger functionality. In the 
   * case of posts, this would include tests surrounding posting a message.
   */
  describe('Posting a New Message', function() {
    beforeEach('[ Posting a Message ]: Resetting database before next SUITE', function() {
      resetDatabase();
    });
    describe('Successful Post', function() {
      // create a new post to insert into the database
      const newPost = {
        text: "New Post Testing Successful Post",
        id: 1609275273319,
        date: "Dec 29th, 15:54"
      }

      it('POST to /posts - should create a new post', function () {

        // get the number of posts before insertion
        const prevNumPosts = db.get('posts').size().value();

        // add the new post to the database
        db.get('posts').push(newPost).write();

        // get new number of posts
        const currNumPosts = db.get('posts').size().value();

        // new number should be one more than previous number of posts
        expect(currNumPosts).to.be.eq(prevNumPosts + 1)
      });
      it('post should have a text property', function() {
        // find the newly added post in the test database
        const addedPost = db.get('posts').find({ id: newPost.id }).value();

        // make sure the added post has a 'text' property
        expect(addedPost).to.have.property('text');

        // make sure the 'text' property is a string
        expect(addedPost.text).to.be.a('string');
      });
      it('post should have the correct text property', function() {
        // find the newly added post in the test database
        const addedPost = db.get('posts').find({ id: newPost.id }).value();

        // make sure the text from the added post is the same as we intended
        expect(addedPost.text).to.be.eq(newPost.text);
      });
      it('post should have a date property', function() {
        // find the newly added post in the test database
        const addedPost = db.get('posts').find({ id: newPost.id }).value();

        // make sure the added post has a 'date' property
        expect(addedPost).to.have.property('date');

        // make sure the 'date' property is a string
        expect(addedPost.text).to.be.a('string');
      });
      it('post should have the correct date property', function() {
        // find the newly added post in the test database
        const addedPost = db.get('posts').find({ id: newPost.id }).value();

        // make sure the date from the added post is the same as we intended
        expect(addedPost.date).to.be.eq(newPost.date);
      });
    });
    describe('Unsuccessful Post', function() {
      /**
       * This is known as a 'pending' test because there is no implementation for what 'it' should do.
       * Whenever there is no function as the second parameter, mocha/chai will highlight it blue to let
       * you know that you haven't completed it yet.
       */
      it('should produce some error when posting');
    })
  });

  /**
   * Another larger function of posting is making sure deleting posts work
   */
  describe('Deleting a Message', function() {
    /**
     * These are additional 'pending' tests that you should complete for practice with writing tests. Look to the above
     * testing suite for ideas.
     *
     * These links will be helpful for what is possible!
     *      https://www.chaijs.com/api/bdd/  [ official documentation for chai.js ]
     *      https://docs.cypress.io/guides/references/assertions.html#BDD-Assertions [ cleaner representation of above ]
     */
    it('DELETE to /posts/:id -  should delete a post');
    it('post with id [:id] should be deleted');
  })
});
