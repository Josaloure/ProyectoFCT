// Ejecuta el bloque cuando el DOM se haya cargado completamente
document.addEventListener("DOMContentLoaded", () => {
  // Obtención de parámetros de la URL
  const params = new URLSearchParams(window.location.search);
  const usuarioId = params.get("usuarioId"); // ID del usuario extraído de la URL

  // Referencias a elementos del DOM
  const tbody = document.getElementById("tabla-body"); // Cuerpo de la tabla de tablones
  const infoUsuario = document.getElementById("info-usuario"); // Contenedor de información del usuario

  // Verificación de la presencia del parámetro usuarioId
  if (!usuarioId) {
    infoUsuario.textContent = "Falta el ID de usuario en la URL (?usuarioId=123)";
    return;
  }

  /**
   * Solicita al backend los tablones asociados al usuario y los muestra en la tabla.
   */
  fetch(`/api/profesor/tablonProfesor/listarPorUsuario/${usuarioId}`)
    .then(res => res.json())
    .then(tablones => {
      // Verificación de que se haya recibido un arreglo no vacío
      if (!Array.isArray(tablones) || tablones.length === 0) {
        infoUsuario.textContent = "No se encontraron tablones para este usuario.";
        return;
      }

      // Muestra el nombre del usuario utilizando el primer resultado recibido
      infoUsuario.innerHTML = `<p><strong>Usuario:</strong> ${tablones[0].nombreUsuario}</p>`;

      // Limpia el contenido previo de la tabla
      tbody.innerHTML = "";

      // Crea una fila en la tabla por cada tablón recibido
      tablones.forEach(tablon => {
        const fila = `
          <tr>
            <td>${tablon.idTablonProfesor}</td>
            <td>${tablon.nombre}</td>
            <td>${tablon.nombreUsuario}</td>
            <td>
              <button onclick="window.location.href='/api/profesor/tablonProfesor/vista/${tablon.idTablonProfesor}'">Ver</button>
              <button onclick="eliminar(${tablon.idTablonProfesor})">Eliminar</button>
            </td>
          </tr>`;
        tbody.innerHTML += fila;
      });
    });

  /**
   * Función global para eliminar un tablón.
   * Envia una solicitud DELETE al backend y actualiza la interfaz.
   * @param {number} id - Identificador del tablón a eliminar.
   */
  window.eliminar = async (id) => {
    if (confirm("¿Seguro que quieres eliminar este tablón?")) {
      await fetch(`/api/profesor/tablonProfesor/eliminar/${id}`, {
        method: "DELETE",
        headers: {
          "X-CSRF-TOKEN": document.querySelector('meta[name="_csrf"]').getAttribute("content")
        }
      });

      // Limpia la tabla e informa al usuario tras eliminar
      tbody.innerHTML = "";
      infoUsuario.textContent = "Tablón eliminado.";
    }
  };
});
