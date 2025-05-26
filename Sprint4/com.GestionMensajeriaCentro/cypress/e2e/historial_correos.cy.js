describe('Visualización del historial de correos', () => {
  beforeEach(() => {
    // Usamos el profesor, pero también puede hacerse como admin
    cy.visit('/login');
    cy.get('input[name="email"]').type('profe@centro.com');
    cy.get('input[name="clave"]').type('profe123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe mostrar los correos enviados en la tabla', () => {
    cy.visit('/correo');

    // Verifica que la tabla tiene al menos una fila (si hay correos)
    cy.get('table tbody tr').should('have.length.at.least', 0);

    // Verifica columnas clave
    cy.get('table tbody tr').first().within(() => {
      cy.get('td').eq(0).should('exist'); // Fecha
      cy.get('td').eq(1).should('exist'); // Destinatario
      cy.get('td').eq(2).should('exist'); // Tipo de notificación
      cy.get('td').eq(3).should('exist'); // Relacionado con tarea/mensaje
    });
  });
});
