describe('Login de usuario', () => {
  it('Debe iniciar sesión correctamente como ADMIN', () => {
    cy.visit('/login'); // o '/api/login' si tu baseUrl es localhost:8080

    cy.get('input[name="email"]').type('admin@centro.com');
    cy.get('input[name="password"]').type('admin123');
    cy.wait(500);
    cy.get('form').submit();

    cy.url().should('include', '/indice');
    cy.contains('Gestión de mensajes').should('exist');
    cy.contains('Gestión tablón centro').should('exist');
  });

  it('Debe iniciar sesión correctamente como PROFESOR', () => {
    cy.visit('/login');

    cy.get('input[name="email"]').type('profe@centro.com');
    cy.get('input[name="password"]').type('profe123');
    cy.get('form').submit();

    cy.url().should('include', '/indice');
    cy.contains('Alta Tarea').should('exist');
    cy.contains('Gestión Tablón Profesor').should('exist');
  });
});
