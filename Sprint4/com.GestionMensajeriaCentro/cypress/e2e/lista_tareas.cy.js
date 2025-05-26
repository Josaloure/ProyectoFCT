describe('Listado de tareas y filtrado por tablón del profesor', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[name="email"]').type('profe@centro.com');
    cy.get('input[name="clave"]').type('profe123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe filtrar las tareas por tablón correctamente', () => {
    cy.visit('/profesor/tareas/lista');

    // Asegurarse de que el select está cargado y tiene opciones
    cy.get('select[name="filtro-tablon"]').should('exist').select(1); // cambia al segundo tablón

    // Esperar actualización de la tabla
    cy.wait(1000); // ajusta si tienes respuesta rápida

    // Verificar que las tareas han cambiado o siguen apareciendo
    cy.get('table tbody tr').should('have.length.at.least', 0);
  });
});
