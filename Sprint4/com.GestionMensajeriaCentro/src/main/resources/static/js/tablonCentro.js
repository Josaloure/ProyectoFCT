// Ejecuta el bloque de código cuando el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Referencia al cuerpo de la tabla que mostrará los tablones
  const tablaBody = document.querySelector("#tabla-tablones tbody");

  // Referencia al formulario de creación de tablones
  const form = document.getElementById("form-crear-tablon");

  // Obtención del token CSRF y del nombre del header desde las etiquetas <meta>
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  /**
   * Función que carga los tablones del centro asociados a un usuario específico.
   * Refresca el contenido de la tabla con los datos obtenidos.
   */
  const cargarTareas = async () => {
    tablaBody.innerHTML = ""; // Limpia el contenido actual de la tabla

    const idUsuario = document.getElementById("idUsuario").value;
    if (!idUsuario) return; // Si no se ha seleccionado usuario, se interrumpe la ejecución

    const res = await fetch(`/api/admin/tablonCentro/consultarPorUsuario/${idUsuario}`);
    const datos = await res.json();

    // Itera sobre cada tablón y genera una fila en la tabla
    datos.forEach(tablon => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${tablon.id}</td>
        <td>${tablon.nombre}</td>
        <td>${tablon.nombreUsuario}</td>
        <td><button class="btn-eliminar" data-id="${tablon.id}">Eliminar</button></td>
      `;
      tablaBody.appendChild(fila);
    });

    /**
     * Asigna eventos de eliminación a los botones correspondientes
     * Genera una solicitud DELETE al backend con el ID del tablón
     */
    document.querySelectorAll(".btn-eliminar").forEach(boton => {
      boton.addEventListener("click", async (e) => {
        const id = e.target.getAttribute("data-id");
        if (confirm(`¿Seguro que quieres eliminar el tablón con ID ${id}?`)) {
          const res = await fetch(`/api/admin/tablonCentro/eliminar/${id}`, {
            method: "DELETE",
            headers: {
              [csrfHeader]: csrfToken
            }
          });
          if (res.ok) {
            // Recarga la tabla después de eliminar
            await cargarTareas();
          } else {
            alert("Error al eliminar el tablón.");
          }
        }
      });
    });
  };

  /**
   * Manejador del evento de envío del formulario de creación de tablón
   * Envía los datos al backend y actualiza la tabla con los nuevos datos
   */
  form.addEventListener("submit", async (e) => {
    e.preventDefault(); // Previene el envío estándar del formulario

    const nombre = document.getElementById("nombre").value;
    const idUsuario = document.getElementById("idUsuario").value;

    await fetch("/api/admin/tablonCentro/crear", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        [csrfHeader]: csrfToken
      },
      body: JSON.stringify({ nombre, idUsuario }) // Envía el nuevo tablón en formato JSON
    });

    // Resetea el formulario y vuelve a cargar la tabla de tablones
    form.reset();
    document.getElementById("idUsuario").value = idUsuario;
    await cargarTareas();
  });

  /**
   * Cuando se selecciona un usuario diferente, se actualiza la tabla de tablones
   */
  document.getElementById("idUsuario").addEventListener("change", cargarTareas);
});
