describe('Importar tarea externa', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[name="email"]').type('profe@centro.com');
    cy.get('input[name="clave"]').type('profe123');
    cy.get('form').submit();
    cy.url().should('include', '/indice');
  });

  it('Debe importar una tarea externa correctamente', () => {
    cy.visit('/profesor/tareas/crear'); // ajusta si usas otra ruta

    const titulo = 'Tarea externa ' + Date.now();

    cy.get('input[name="titulo"]').type(titulo);
    cy.get('textarea[name="descripcion"]').type('Tarea importada desde prueba automática');

    // Select dinámicos
    cy.get('select[name="receptor"]').select(0);
    cy.get('select[name="prioridad"]').select('MEDIA');
    cy.get('select[name="estado"]').select('PENDIENTE');
    cy.get('select[name="categoria"]').select('TUTORÍAS');
    cy.get('select[name="idTablon"]').select(0);

    cy.get('form#form-importar-tarea').submit();

    // Verifica que aparece en algún lado (mensaje, tabla, etc.)
    cy.contains('Tarea guardada correctamente').should('exist');
    // o si se muestra en tabla
    // cy.contains(titulo).should('exist');
