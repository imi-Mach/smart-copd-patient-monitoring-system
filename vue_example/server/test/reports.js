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
 * Test suite for reporting posts
 */
describe('Reports', function() {

  /**
   * clear the database and reset it to the state defined in testData.json.
   **/
  beforeEach('[ Reports ]: Resetting database before next SUITE', function() {
    resetDatabase();
  });

  // after will run after the last 'describe' (test suite) at the root level
  after('[ Reports ]: Completed all tests and clearing the database', function () {
    db.setState({}).write(); // clears the test database after the last test to fully reset
    console.log('DATABASE CLEARED. END OF TESTING!');
  });

  describe('Reporting a New Post', function() {
    beforeEach('[ Reporting a Post ]: Resetting database before next SUITE', function() {
      resetDatabase();
    });
    describe('Successful Report', function() {
      // create a new report to insert into the database
      const newReport = {
        id: 1610657959589,
        reviewId: 1610657959575,
        reason: "spam",
        report: "test report"
      }

      it('POST to /reports - should create a new report', function () {

        // get the number of posts before insertion
        const prevNumReports = db.get('reports').size().value();

        // add the new post to the database
        db.get('reports').push(newReport).write();

        // get new number of posts
        const currNumReports = db.get('reports').size().value();

        // new number should be one more than previous number of posts
        expect(currNumReports).to.be.eq(prevNumReports + 1)
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
});