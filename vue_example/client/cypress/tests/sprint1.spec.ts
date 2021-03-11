/*
* This file contains all of the end-to-end tests for Sprint 1.
*/
describe('Sprint 1', () => {
  // Before running each test, navigate to the '/sprint1' page, where are the UI components are:
  beforeEach(() => {
    cy.visit('/sprint1')
  })

  // First test which checks that the CDN Google Fonts is imported correctly and that the text on the page is styled
  describe('Style Font', () => {
     // Check that Roboto font renders
     it('Should have Roboto Font for Title', () => {
      // Check the h1 and .mt-3 class to ensure both use Roboto font
      cy.get('h1, .mt-3')
        .should('be.visible')
        .should('have.css', 'font-family')
        .and('match', /Roboto/);
    });
  });

  // Second test which checks that the search bar npm package is imported correctly and visible
  describe('Visible Search Bar',() => {
    // Checks if search bar is visible and that the NPM vue-select list has loaded
    it('Should correctly display a search bar', () => {

      cy.get('#search_bar')
        .should('be.visible');

      cy.get('#vs1__listbox')
        .should('be.hidden');
    });
  });

  // Final test which checks that there are the same number of options in the search bar as the database and that these options are all valid options in the database
  describe('Search Options Match Database', () => {
    // Loop through the dummy database and check that all search options are in the dropdown list
    it('Database options should match search options', () => {

      cy.fixture('messages.json').then(messages => {
        // Intercept the posts API request, responding with the sample messages list:
        cy.intercept('GET', '/api/posts', messages);

        // Reload to update the list of sample messages
        cy.reload();

        // Click into the search bar to load the list of messages
        cy.get('#vs1__combobox').click();

        // Check that the length of the loaded messages matches the messages in the JSON
        cy.get('#vs1__listbox li')
          .should('have.length', messages.length);

        // Loop through each element in the list and check that it matches each element in the database
        cy.get('#vs1__listbox li')
          .then(text => {
            for (let i = 0; i < text.length; i++) {
              expect(text[i].innerText).to.equal(messages[i].text);   // [messages.length - i - 1] for reverse order
            }
          })
      });
    })
  })
});

