describe('Gestión de Tablón del Profesor', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[name="email"]').type('profe@centro.com');
    cy.get('input[name="clave"]').type('profe123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe crear y eliminar un tablón del profesor', () => {
    cy.visit('/profesor/tablon');

    const nombreUnico = 'Tablón Test ' + Date.now();

    // Completar y enviar el formulario
    cy.get('input[name="nombre"]').type(nombreUnico);
    cy.get('form#form-tablon-profesor').submit();

    // Verificar que se ha creado
    cy.contains(nombreUnico).should('exist');

    // Eliminarlo
    cy.contains(nombreUnico)
      .parent('tr')
      .within(() => {
        cy.contains('Eliminar').click();
      });

    // Confirmar el diálogo
    cy.on('window:confirm', () => true);

    // Verificar que ya no aparece
    cy.contains(nombreUnico).should('not.exist');
  });
});
