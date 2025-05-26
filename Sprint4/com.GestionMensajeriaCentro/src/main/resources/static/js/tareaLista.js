// Ejecuta el bloque de código cuando el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Referencia al cuerpo de la tabla donde se mostrarán las tareas
  const tbody = document.querySelector(".tabla-tareas tbody");

  // Elemento para mostrar mensajes de retroalimentación al usuario
  const mensajeFeedback = document.getElementById("mensaje-feedback");

  // Obtención del token CSRF y el nombre del header correspondiente desde el DOM
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

  /**
   * Carga las tareas desde el servidor y actualiza la tabla en la interfaz.
   */
  const cargarTareas = async () => {
    const res = await fetch("/api/profesor/tareas/listar"); // Solicita la lista de tareas
    const tareas = await res.json(); // Convierte la respuesta en JSON

    tbody.innerHTML = ""; // Limpia el contenido anterior de la tabla

    // Verifica si se recibió un arreglo válido con elementos
    if (!Array.isArray(tareas) || tareas.length === 0) {
      tbody.innerHTML = "<tr><td colspan='5'>No hay tareas registradas.</td></tr>";
      return;
    }

    // Crea dinámicamente una fila para cada tarea recibida
    tareas.forEach(t => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${t.titulo}</td>
        <td>${t.descripcion}</td>
        <td>${t.fechaCreacion}</td>
        <td>${t.estado}</td>
        <td>
          <button onclick="eliminar(${t.idTarea})">Eliminar</button>
        </td>`;
      tbody.appendChild(fila);
    });
  };

  /**
   * Función global que elimina una tarea específica mediante una solicitud DELETE.
   * @param {number} id - Identificador de la tarea a eliminar.
   */
  window.eliminar = async (id) => {
    if (confirm(`¿Seguro que deseas eliminar la tarea con ID ${id}?`)) {
      const res = await fetch(`/api/profesor/tareas/eliminar/${id}`, {
        method: "DELETE",
        headers: {
          [csrfHeader]: csrfToken // Añade el token CSRF a la cabecera de la solicitud
        }
      });

      // Muestra el resultado de la operación en el mensaje de feedback
      if (res.ok) {
        mensajeFeedback.textContent = "Tarea eliminada con éxito.";
        mensajeFeedback.classList.remove("error");
        await cargarTareas(); // Recarga la tabla de tareas
      } else {
        mensajeFeedback.textContent = "Error al eliminar la tarea.";
        mensajeFeedback.classList.add("error");
      }

      // Limpia el mensaje de feedback después de un breve intervalo
      setTimeout(() => {
        mensajeFeedback.textContent = "";
        mensajeFeedback.classList.remove("error");
      }, 4000);
    }
  };

  // Llamada inicial para cargar las tareas al iniciar la vista
  cargarTareas();
});
