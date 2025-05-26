// Ejecuta el bloque una vez que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", async () => {
  // Referencias a elementos del DOM
  const tbody = document.querySelector("#tabla-correos tbody");
  const form = document.getElementById("form-crear-correo");
  const feedback = document.getElementById("feedback-correo");

  // Obtención de los valores CSRF necesarios para solicitudes POST/DELETE
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute("content");
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute("content");

  /**
   * Función que carga y renderiza el historial de correos en la tabla.
   */
  const cargarHistorial = async () => {
    tbody.innerHTML = ""; // Limpia el contenido previo de la tabla
    const res = await fetch("/api/historialCorreos/listar"); // Solicita todos los correos registrados
    const datos = await res.json(); // Parsea la respuesta en formato JSON

    datos.forEach(correo => {
      // Crea una fila por cada correo recibido
      const fila = document.createElement("tr");
      fila.setAttribute('data-id', correo.idHistorial);

      // Formatea la fecha y prepara los campos
      const fecha = new Date(correo.fechaEnvio).toLocaleString("es-ES");
      const destinatario = correo.destinatarioEmail || "-";
      const tipo = correo.tipoNotificacion || "-";
      let relacionado = "-";

      // Determina si el correo está relacionado con un mensaje o una tarea
      if (correo.mensaje && correo.mensaje.titulo) {
        relacionado = correo.mensaje.titulo;
      } else if (correo.tarea && correo.tarea.titulo) {
        relacionado = correo.tarea.titulo;
      }

      // Botones para ver detalles y eliminar
      const verDetallesBtn = `<button onclick="verDetalles(${correo.idHistorial})">Ver Detalles</button>`;
      const eliminarBtn = `<button onclick="eliminarCorreo(${correo.idHistorial})">Eliminar</button>`;

      // Inserta los datos en la fila
      fila.innerHTML = `
        <td>${fecha}</td>
        <td>${destinatario}</td>
        <td>${tipo}</td>
        <td>${relacionado}</td>
        <td>${verDetallesBtn} ${eliminarBtn}</td>
      `;
      tbody.appendChild(fila); // Añade la fila a la tabla
    });
  };

  /**
   * Función global para mostrar los detalles de un correo específico.
   * @param {number} id - Identificador del historial de correo.
   */
  window.verDetalles = function(id) {
    fetch(`/api/historialCorreos/listarPorId/${id}`)
      .then(res => res.json())
      .then(correo => {
        alert(`Detalles del correo:\nFecha: ${correo.fechaEnvio}\nDestinatario: ${correo.destinatarioEmail}\nTipo: ${correo.tipoNotificacion}`);
      })
      .catch(() => alert("No se pudo obtener los detalles del correo"));
  };

  /**
   * Función global para eliminar un correo del historial.
   * @param {number} id - Identificador del historial de correo a eliminar.
   */
  window.eliminarCorreo = function(id) {
    if (confirm("¿Estás seguro de que quieres eliminar este correo?")) {
      fetch(`/api/historialCorreos/eliminar/${id}`, {
        method: "DELETE",
        headers: {
          [csrfHeader]: csrfToken // Inclusión del token CSRF
        }
      })
      .then(res => {
        if (res.ok) {
          const row = document.querySelector(`tr[data-id="${id}"]`);
          if (row) row.remove(); // Elimina la fila correspondiente de la tabla
          alert("Correo eliminado");
        } else {
          alert("Error al eliminar el correo");
        }
      })
      .catch(() => alert("Error de conexión"));
    }
  };

  /**
   * Maneja el envío del formulario para registrar un nuevo historial de correo.
   */
  form.addEventListener("submit", async (e) => {
    e.preventDefault(); // Evita el envío estándar del formulario

    // Obtención de los valores del formulario
    const email = document.getElementById("destinatarioEmail").value;
    const tipo = document.getElementById("tipoNotificacion").value;
    const idRelacionado = parseInt(document.getElementById("idRelacionado").value);

    // Validación del campo tipo
    if (!tipo) {
      feedback.textContent = "Debes seleccionar un tipo de notificación.";
      feedback.style.color = "red";
      return;
    }

    // Construcción del objeto DTO para el backend
    const dto = {
      destinatarioEmail: email,
      tipoNotificacion: tipo,
      idMensaje: tipo === "MENSAJE" ? idRelacionado : null,
      idTarea: tipo === "TAREA" ? idRelacionado : null,
      fechaEnvio: new Date().toISOString()
    };

    // Envío de la solicitud POST al servidor
    try {
      const res = await fetch("/api/historialCorreos/crear", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          [csrfHeader]: csrfToken
        },
        body: JSON.stringify(dto)
      });

      if (res.ok) {
        feedback.textContent = "Correo registrado con éxito.";
        feedback.style.color = "green";
        form.reset(); // Limpia los campos del formulario
        await cargarHistorial(); // Recarga la tabla con los nuevos datos
      } else {
        feedback.textContent = "Error al enviar el correo.";
        feedback.style.color = "red";
      }
    } catch (err) {
      feedback.textContent = "Error de conexión.";
      feedback.style.color = "red";
    }
  });

  // Carga inicial del historial de correos
  await cargarHistorial();
});
