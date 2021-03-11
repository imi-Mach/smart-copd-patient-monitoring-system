/*
* This file contains all of the end-to-end tests for Sprint 0.
* You will be required to write a 'spec' file similar to this one for each subsequent development sprint.
*
* For more information about writing end-to-end tests with the Cypress framework,
* checkout their documentation: https://docs.cypress.io/guides/getting-started/testing-your-app.html
*/
describe('Sprint 0', () => {
  // Before running each test, navigate to the '/sprint0' page, where are the UI components are:
  beforeEach(() => {
    cy.visit('/sprint0')
  })

  // Here's a group of tests which check to make sure that the form for adding messages works properly:
  describe('Message Form', () => {
    // Make sure that all of the important elements are displaying correctly:
    it('Should have a text input field and a submission button', () => {
      // Check the text field:
      cy.get('textarea')
        .should('have.class', 'form-control');

      // Check the submission button:
      cy.get('button')
        .should('have.class', 'btn')
        .and('have.attr', 'type');
    });

    // Make sure that messages are routed correctly each time the submit button is pressed:
    it('Should post new messages to the /posts API', () => {
      const POST_DATA = 'This is a sample message which is being uploaded to the API';

      // Configure Cypress to intercept the API request before it goes to the server:
      cy.intercept('POST', '/api/posts', req => {
        req.reply(req.body);
      }).as('testRoute');

      // Type the message into the form & click the submission button:
      cy.get('textarea')
        .type(POST_DATA);
      cy.get('button')
        .click();

      // Wait for the response from the mock API:
      cy.wait('@testRoute')
        .its('request.body')
        .should('deep.equal', { text: POST_DATA });
    });
  });

  // Here are some more tests, this time focusing on testing the list of messages which appears after the submission form:
  describe('Message View', () => {
    // Make sure that empty message lists are displayed correctly:
    it('Should correctly display an empty message list', () => {
      // Intercept the posts API request, responding with the sample messages list:
      cy.intercept('GET', '/api/posts', []);

      // Reload the page to update the messages list:
      cy.reload();

      // Make sure that no messages are displayed:
      cy.get('.card-header').should('not.exist');
      cy.get('.card-text').should('not.exist');
    });

    // Make sure that our sample message list is displayed correctly on the page:
    it('Should display a list of messages', () => {
      // Load the sample messages from '../fixtures/messages.json':
      cy.fixture('messages.json').then(messages => {
        // Intercept the posts API request, responding with the sample messages list:
        cy.intercept('GET', '/api/posts', messages);

        // Reload the page to update the messages list:
        cy.reload();

        // Inspect the displayed message header fields:
        cy.get('.card-header')
          .should('have.length', messages.length)
          .then(headers => {
            // Make sure that the message headers are displayed in reverse-order and each contain the correct date:
            for(let i = 0; i < messages.length; i++) {
              expect(headers[i].innerText).to.equal(messages[messages.length - i - 1].date);
            }
          });

        // Inspect the displayed message text fields:
        cy.get('.card-text')
          .should('have.length', messages.length)
          .then(textFields => {
            // Make sure that the messages are displayed in reverse-order and each contain the correct text:
            for(let i = 0; i < messages.length; i++) {
              expect(textFields[i].innerText).to.equal(messages[messages.length - i - 1].text);
            }
          });
      });
    });

    // Make sure that the 'Delete Post' button works as intended:
    it('Should delete a post when the corresponding "Delete Post" button is clicked', () => {
      // Load the sample messages from '../fixtures/messages.json':
      cy.fixture('messages.json').then(messages => {
        // Intercept the posts API request, responding with the sample messages list:
        cy.intercept('GET', '/api/posts', messages);

        // Intercept the posts DELETE request, responding with the message list:
        cy.intercept('DELETE', '/api/posts', req => {
          req.reply(messages);
        }).as('testRoute');

        // Reload the page to update the messages list:
        cy.reload();

        // Obtain a list of all 'Delete Post' buttons in the document:
        cy.get('.card-body button')
          .should('have.length', messages.length)
          .then(deleteButtons => {
            // Make sure that each delete button triggers the correct delete route:
            for(let i = 0; i < messages.length; i++) {
              // Click the button:
              deleteButtons[i].click();

              // Make sure that the request contains the correct message ID:
              cy.wait('@testRoute')
                .its('request.url')
                .should('match', new RegExp(`/${messages[messages.length - i - 1].id}$`));
            }
          });
      });
    });
  });
});

