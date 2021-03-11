/*
* This file contains all of the end-to-end tests for Sprint 2.
*/
describe('Sprint 2', () => {

    // First backend test which checks that POST request works
    describe('Database Post', () => {

        it('Should make a post', () => {

            cy.visit('/new-review');

            const REVIEW_DATA = { courseNumber: 'TEST', professor: 'test professor', semester: 'Fall', year: '2020', review: 'Test object'};

            // Configure Cypress to intercept the API request before it goes to the server:
            cy.intercept('POST', '/api/reviews', req => {
                req.reply(req.body);
            }).as('testRoute');

            // set form data
            cy.get('#courseNumber').type(REVIEW_DATA.courseNumber);
            cy.get('#professor').type(REVIEW_DATA.professor);
            cy.get('#semester').select(REVIEW_DATA.semester);
            cy.get('#year').select(REVIEW_DATA.year);
            cy.get('#review').type(REVIEW_DATA.review);
            cy.get('#submitDetails').click();

            // Wait for the response from the mock API:
            cy.wait('@testRoute').its('request.body').should('deep.equal', REVIEW_DATA);

        })
    })

    // Frontend test to see that Dog API generates image
    describe('Dog API', () => {

        it('Should generate dog image', () => {

            cy.visit('/sprint2');

            // confirm image container is empty on load
            cy.get('#randomImageContainer').children().should('have.length', 0);

            cy.get('#dog-button').click();

            // confirm image container has one child after button click
            cy.get('#randomImageContainer').children().should('have.length', 1);

        })

    })

    // Frontend test to check that colors change when "Change the theme" is clicked
    describe('Theme change', () => {
        it('Should change the class', () => {
            cy.visit('/sprint2');

            // expect first click to render green elements
            cy.get('.toggle').click();
            cy.get('h1').should('have.class', 'green');
            cy.get('.course-links').should('have.class', 'green-button');

            // expect second click to render purple elements
            cy.get('.toggle').click();
            cy.get('h1').should('have.class', 'purple');
            cy.get('.course-links').should('have.class', 'purple-button');

            // expect third click to render red elements
            cy.get('.toggle').click();
            cy.get('h1').should('have.class', 'red');
            cy.get('.course-links').should('have.class', 'red-button');

            // expect fourth click to render blue elements
            cy.get('.toggle').click();
            cy.get('h1').should('have.class', 'blue');
            cy.get('.course-links').should('have.class', 'blue-button');


        })

        it('Should render colors', () => {
            let green = 'rgb(85, 107, 47)';
            let purple = 'rgb(102, 51, 153)';
            let red = 'rgb(139, 0, 0)';
            let blue = 'rgb(73, 135, 232)';

            // check cycle after each click
            cy.get('.toggle').click();
            cy.get('h1').should('have.css', 'color', green);
            cy.get('.toggle').click();
            cy.get('h1').should('have.css', 'color', purple);
            cy.get('.toggle').click();
            cy.get('h1').should('have.css', 'color', red);
            cy.get('.toggle').click();
            cy.get('h1').should('have.css', 'color', blue);
        })

        it('Should match cookies', () => {
            // match color to cookies
            cy.get('.toggle').click();
            cy.getCookie('theme').should('have.property', 'value', 'green');
            cy.get('h1').should('have.class', 'green');

            cy.get('.toggle').click();
            cy.getCookie('theme').should('have.property', 'value', 'purple');
            cy.get('.toggle').click();
            cy.getCookie('theme').should('have.property', 'value', 'red');
            cy.get('.toggle').click();
            cy.getCookie('theme').should('have.property', 'value', 'blue');
        })
    })

    

});
  
  