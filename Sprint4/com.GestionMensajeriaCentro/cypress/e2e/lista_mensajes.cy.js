describe('Gestión de mensajes: listar y eliminar', () => {
  beforeEach(() => {
    // Login como ADMIN antes de cada test
    cy.visit('/login');
    cy.get('input[name="email"]').type('admin@centro.com');
    cy.get('input[name="clave"]').type('admin123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe mostrar los mensajes y permitir eliminarlos', () => {
    cy.visit('/admin/mensajes/vista');

    // Verifica que la tabla tiene al menos un mensaje
    cy.get('table tbody tr').should('have.length.at.least', 1);

    // Captura el texto del primer mensaje (opcional)
    cy.get('table tbody tr').first().within(() => {
      cy.get('td').first().invoke('text').as('mensajeOriginal');
      cy.contains('Eliminar').click();
    });

    // Aceptar la alerta de confirmación
    cy.on('window:confirm', () => true);

    // Esperar a que desaparezca o se actualice la tabla
    cy.wait(1000);

    // Verifica que el mensaje original ya no esté en la tabla (si se capturó antes)
    cy.get('@mensajeOriginal').then((texto) => {
      cy.get('table tbody').should('not.contain', texto.trim());
    });
  });
});
