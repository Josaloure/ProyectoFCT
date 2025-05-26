describe('Alta de mensaje desde formulario HTML', () => {
  beforeEach(() => {
    // Login como ADMIN antes del test
    cy.visit('/login');
    cy.get('input[name="email"]').type('admin@centro.com');
    cy.get('input[name="password"]').type('admin123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe completar el formulario y guardar un mensaje', () => {
    // Visitar la vista donde está el formulario HTML
    cy.visit('/admin/mensajes/formulario');


    // Llenar campos
    cy.get('input[name="titulo"]').type('Título de prueba desde Cypress');
    cy.get('textarea[name="contenido"]').type('Contenido generado automáticamente');

    // Esperar a que se carguen las opciones del select dinámicamente
    cy.wait(500); // o usar intercept si es fetch

    // Seleccionar el primer tablón disponible
    cy.get('select[name="idTablonCentro"]').find('option').eq(1).then($opt => {
      const value = $opt.attr('value');
      if (value) {
        cy.get('select[name="idTablonCentro"]').select(value);
      }
    });

    // Marcar los checkboxes
    cy.get('#publicado').check();
    cy.get('#visibleParaTodos').check();

    // Enviar el formulario
    cy.get('form#form-mensaje').submit();

    // Verificar que aparece mensaje de éxito
    cy.get('#mensaje-feedback')
      .should('exist')
      .and('contain.text', 'Mensaje guardado correctamente');
  });
});
