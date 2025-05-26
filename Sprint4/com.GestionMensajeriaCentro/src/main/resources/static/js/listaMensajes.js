// Ejecuta el bloque de código una vez que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Obtención de los tokens CSRF desde las etiquetas <meta>
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

  // Elemento para mostrar mensajes de retroalimentación al usuario
  const feedback = document.getElementById("mensaje-feedback");

  /**
   * Recorre todos los botones con la clase 'btn-eliminar' y asigna el evento de eliminación
   */
  document.querySelectorAll(".btn-eliminar").forEach(btn => {
    btn.addEventListener("click", async () => {
      // Obtención del ID del mensaje a eliminar desde el atributo data-id del botón
      const id = btn.getAttribute("data-id");

      // Confirmación del usuario antes de realizar la operación
      if (confirm("¿Seguro que deseas eliminar este mensaje?")) {
        try {
          // Envío de la petición DELETE al backend para eliminar el mensaje
          const res = await fetch(`/api/admin/mensajes/eliminar/${id}`, {
            method: "DELETE",
            headers: {
              "Content-Type": "application/json",
              [csrfHeader]: csrfToken
            }
          });

          // Evaluación del resultado de la petición
          if (res.ok) {
            feedback.textContent = "Mensaje eliminado con éxito.";
            feedback.classList.remove("error");

            // Recarga la página después de un breve intervalo para reflejar los cambios
            setTimeout(() => location.reload(), 1000);
          } else {
            feedback.textContent = "Error al eliminar el mensaje.";
            feedback.classList.add("error");
          }
        } catch (e) {
          // Manejo de errores de red o fallos inesperados
          feedback.textContent = "Error de red.";
          feedback.classList.add("error");
        }
      }
    });
  });
});
