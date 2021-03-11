/*
 * This file contains all of the end-to-end tests for Sprint 3.
 */
describe("Sprint 3", () => {

    // Frontend test to see that the report feature appears when the user is not a Guest
    describe("Report feature display", () => {
        it("Should display posts without reporting feature while Guest", () => {
        cy.visit("/reviews/CSE017");

        // Check that no report shows up in the header when a Guest
        cy.setCookie("profile", "Guest");

        cy.fixture("reviews.json").then((reviews) => {
            // Intercept the posts API request, responding with a list of reviews:
            cy.intercept("GET", "/api/reviews/CSE017", reviews);

            // Reload the page to update the reviews:
            cy.reload();

            // Inspect the displayed review header fields:
            cy.get(".card-header")
            .should("have.length", reviews.length)
            .then((headers) => {
                // Make sure that the review headers are displayed in reverse-order, contain name, date:
                for (let i = 0; i < reviews.length; i++) {
                let currentReview = reviews[reviews.length - i - 1];
                expect(headers[i].innerText).to.equal(
                    currentReview.name + ", " + currentReview.date
                );
                }
            });

            // Inspect review text fields (*3 w/ Prof, Semester, and Review displayed):
            cy.get(".card-text").should("have.length", reviews.length * 3);
        });
        });

        it("Should display reporting feature when not a Guest", () => {
        cy.visit("/reviews/CSE017");

        // Check that no report shows up in the header when a Guest
        cy.setCookie("profile", "Not a Guest");

        cy.fixture("reviews.json").then((reviews) => {
            // Intercept the posts API request, responding with a list of reviews:
            cy.intercept("GET", "/api/reviews/CSE017", reviews);

            // Reload the page to update the reviews:
            cy.reload();

            // Inspect the displayed review header fields:
            cy.get(".card-header")
            .should("have.length", reviews.length)
            .then((headers) => {
                // Make sure that the review headers are displayed in reverse-order, contain name, date:
                for (let i = 0; i < reviews.length; i++) {
                let currentReview = reviews[reviews.length - i - 1];
                expect(headers[i].innerText).to.equal(
                    currentReview.name + ", " + currentReview.date + "\nReport review 🚩"
                );
                }
            });
        });
        });     // End it("Should display reporting feature when not a Guest", ...)
    })      // End describe("Report feature display", ...)

    // Checks that there is a user profile button if the user is logged in
    describe("Display User Name Button", () => {
        it('Should display button with user name', () => {
            cy.visit("/sprint3");
            cy.setCookie("profile", "Not a Guest"); // Sets user name to "Not a Guest"
            cy.reload(); // Reloads the page to update
            cy.get("#profile-button").contains("Not a Guest") // Checks that the name "Not a Guest" is in the user profile button
            cy.setCookie("profile", 'null') // Sets the cookie to null, which would happen if a user signed out
            cy.reload(); // Reloads the page to update
            cy.get("#profile-button").should('not.exist'); // Checks that the user profile button is no longer on the page
        });
    })
});         // End describe("Sprint 3", ...)
