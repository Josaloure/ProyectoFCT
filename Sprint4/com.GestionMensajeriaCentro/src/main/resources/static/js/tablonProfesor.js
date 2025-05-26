// Ejecuta el bloque cuando el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Referencia al cuerpo de la tabla de tablones de profesor
  const tablaBody = document.querySelector("#tabla-tablon-profesor tbody");

  // Referencia al formulario para crear un nuevo tablón
  const form = document.getElementById("form-crear-tablon-profesor");

  // Obtención del token CSRF y su header desde las etiquetas <meta>
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  /**
   * Carga y muestra los tablones de profesor asociados al usuario especificado.
   */
  const cargarTablones = async () => {
    tablaBody.innerHTML = ""; // Limpia el contenido actual de la tabla

    const idUsuario = document.getElementById("idUsuario").value;
    if (!idUsuario) return; // Si no hay ID de usuario, se interrumpe

    const res = await fetch(`/api/profesor/tablonProfesor/listarPorUsuario/${idUsuario}`);
    const tablones = await res.json();

    if (Array.isArray(tablones) && tablones.length > 0) {
      // Recorre y muestra cada tablón
      tablones.forEach(tablon => {
        const fila = `
          <tr>
            <td>${tablon.idTablonProfesor}</td>
            <td>${tablon.nombre}</td>
            <td>${tablon.usuario?.nombre || '-'}</td>
            <td>
              <button onclick="ver(${tablon.idTablonProfesor})">Ver</button>
              <button class="btn-eliminar" data-id="${tablon.idTablonProfesor}">Eliminar</button>
            </td>
          </tr>`;
        tablaBody.innerHTML += fila;
      });

      /**
       * Asigna eventos a los botones de eliminación para cada fila.
       */
      document.querySelectorAll(".btn-eliminar").forEach(boton => {
        boton.addEventListener("click", async (e) => {
          const id = e.target.getAttribute("data-id");
          if (confirm(`¿Seguro que quieres eliminar el tablón con ID ${id}?`)) {
            const res = await fetch(`/api/profesor/tablonProfesor/eliminar/${id}`, {
              method: "DELETE",
              headers: {
                [csrfHeader]: csrfToken
              }
            });
            if (res.ok) {
              await cargarTablones(); // Refresca la tabla tras eliminar
            } else {
              alert("Error al eliminar el tablón.");
            }
          }
        });
      });

    } else {
      // Muestra mensaje cuando no existen tablones para el usuario
      tablaBody.innerHTML = "<tr><td colspan='4'>No hay tablones para este usuario.</td></tr>";
    }
  };

  /**
   * Manejador del envío del formulario de creación de tablón de profesor.
   */
  form.addEventListener("submit", async (e) => {
    e.preventDefault(); // Previene el envío tradicional del formulario

    const nombre = document.getElementById("nombre").value;
    const idUsuario = document.getElementById("idUsuario").value;

    // Envía la solicitud POST para crear un nuevo tablón
    await fetch("/api/profesor/tablonProfesor/crear", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        [csrfHeader]: csrfToken
      },
      body: JSON.stringify({ nombre, idUsuario })
    });

    form.reset(); // Limpia los campos del formulario
    document.getElementById("idUsuario").value = idUsuario;
    await cargarTablones(); // Actualiza la tabla con los nuevos datos
  });

  /**
   * Carga los tablones automáticamente cuando se cambia el ID de usuario.
   */
  document.getElementById("idUsuario").addEventListener("change", cargarTablones);
});

/**
 * Función global para redirigir a la vista de lista de tablones de un usuario específico.
 * Solicita al usuario un ID y redirige al endpoint correspondiente.
 */
function redirigirListaTablon() {
  const id = prompt("Introduce el ID del usuario:");
  if (id && !isNaN(id)) {
    window.location.href = `/api/profesor/tablonProfesor/lista?usuarioId=${id}`;
  }
}
