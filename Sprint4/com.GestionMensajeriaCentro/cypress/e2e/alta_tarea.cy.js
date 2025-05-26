describe('Alta manual de tarea', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[name="email"]').type('profe@centro.com');
    cy.get('input[name="clave"]').type('profe123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe crear una tarea manual correctamente', () => {
    cy.visit('/profesor/tareas/crear');

    const titulo = 'Tarea manual ' + Date.now();

    cy.get('input[name="titulo"]').type(titulo);
    cy.get('textarea[name="descripcion"]').type('Tarea manual creada desde Cypress');

    // Selects dinámicos
    cy.get('select[name="receptor"]').select(0);
    cy.get('select[name="prioridad"]').select('ALTA');
    cy.get('select[name="estado"]').select('EN_CURSO');
    cy.get('select[name="categoria"]').select('REUNIONES');
    cy.get('select[name="idTablon"]').select(0);

    // Enviar el formulario
    cy.get('form#form-alta-tarea').submit();

    // Verificación: mensaje o listado
    cy.contains('Tarea guardada correctamente').should('exist');
    // o
    // cy.contains(titulo).should('exist');
  });
});
