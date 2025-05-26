// Ejecuta la función cuando el DOM se haya cargado completamente
document.addEventListener("DOMContentLoaded", () => {
  // Referencia al formulario de creación de mensaje
  const form = document.getElementById("form-mensaje");

  // Referencia al campo <select> donde se listan los tablones del centro
  const selectTablon = document.getElementById("idTablonCentro");

  // Elemento donde se mostrará retroalimentación al usuario
  const feedback = document.getElementById("mensaje-feedback");

  // Obtención de los tokens CSRF desde las etiquetas <meta> del documento
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  // Solicita al servidor los tablones del centro asociados al usuario (actualmente con ID hardcodeado)
  fetch("/api/admin/tablonCentro/consultarPorUsuario/1")
    .then(res => res.json())
    .then(tablanes => {
      // Itera sobre la lista de tablones recibidos y crea una opción <option> por cada uno
      tablanes.forEach(t => {
        const opt = document.createElement("option");
        opt.value = t.id; // Se asigna el ID del tablón como valor de la opción
        opt.textContent = t.nombre; // Se muestra el nombre del tablón como texto visible
        selectTablon.appendChild(opt); // Se añade la opción al <select>
      });
    });

  // Maneja el evento de envío del formulario
  form.addEventListener("submit", async (e) => {
    // Previene el comportamiento por defecto del formulario (recarga de página)
    e.preventDefault();

    // Construcción del objeto mensaje a partir de los valores del formulario
    const mensaje = {
      titulo: document.getElementById("titulo").value,
      contenido: document.getElementById("contenido").value,
      idTablonCentro: parseInt(document.getElementById("idTablonCentro").value),
      publicado: document.getElementById("publicado").checked,
      visibleParaTodos: document.getElementById("visibleParaTodos").checked
    };

    // Envío del objeto mensaje al servidor mediante una petición POST
    const res = await fetch("/api/admin/mensajes/crear", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        [csrfHeader]: csrfToken // Inclusión del token CSRF en la cabecera
      },
      body: JSON.stringify(mensaje) // Conversión del objeto mensaje a JSON
    });

    // Manejo de la respuesta del servidor
    if (res.ok) {
      // Si la respuesta es exitosa, se muestra un mensaje de éxito y se limpia el formulario
      feedback.textContent = "Mensaje creado con éxito.";
      form.reset();
    } else {
      // Si ocurre un error, se extrae el mensaje del error y se muestra al usuario
      const error = await res.text();
      feedback.textContent = "Error: " + error;
    }
  });
});
