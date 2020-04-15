/// <reference types="cypress" />

context('Assertions', () => {
  beforeEach(() => {
    cy.visit('/')
  })

  describe('When navigating to the main ui page', () => {
    it('should display Demo-App!!!', () => {
        cy.xpath('//div[@id="container"]//h1').should('contain', 'Demo-App!!!')
    });
  });
});