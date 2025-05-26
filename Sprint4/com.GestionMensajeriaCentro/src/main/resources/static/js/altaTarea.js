// Ejecuta el bloque de código una vez que el DOM esté completamente cargado
document.addEventListener("DOMContentLoaded", () => {
  // Elemento para mostrar retroalimentación en la interfaz
  const feedback = document.getElementById("mensaje-feedback");

  // Obtención de los valores CSRF desde las etiquetas meta
  const csrfToken = document.querySelector('meta[name="_csrf"]').content;
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

  /**
   * Carga todos los tablones del profesor y los inserta en el <select> correspondiente.
   */
  fetch("/api/profesor/tablonProfesor/consultarTodos")
    .then(res => res.json())
    .then(tablanes => {
      const select = document.getElementById("importar-idTablon");
      tablanes.forEach(t => {
        const opt = document.createElement("option");
        opt.value = t.idTablonProfesor;
        opt.textContent = t.nombre;
        select.appendChild(opt);
      });
    });

  /**
   * Carga los usuarios del sistema para poblar los selectores relacionados con receptor y comentarios.
   */
  fetch("/api/usuarios/listar")
    .then(res => res.json())
    .then(usuarios => {
      const selectImport = document.getElementById("importar-receptor");
      const selectComentario = document.getElementById("comentario-idUsuario");
      usuarios.forEach(u => {
        const opt1 = document.createElement("option");
        opt1.value = u.idUsuario;
        opt1.textContent = u.nombre + " " + u.apellidos;
        selectImport.appendChild(opt1);

        const opt2 = document.createElement("option");
        opt2.value = u.idUsuario;
        opt2.textContent = u.nombre + " " + u.apellidos;
        selectComentario.appendChild(opt2);
      });
    });

  /**
   * Carga las tareas para el selector utilizado al crear comentarios.
   */
  fetch("/api/profesor/tareas/listar")
    .then(res => res.json())
    .then(tareas => {
      const select = document.getElementById("comentario-idTarea");
      tareas.forEach(t => {
        const opt = document.createElement("option");
        opt.value = t.idTarea;
        opt.textContent = t.titulo;
        select.appendChild(opt);
      });
    });

  /**
   * Sincroniza el valor del receptor con el campo de usuario en el formulario de comentario.
   */
  document.getElementById("importar-receptor").addEventListener("change", (e) => {
    const idReceptor = e.target.value;
    const campoUsuarioComentario = document.getElementById("comentario-idUsuario");
    if (campoUsuarioComentario) {
      campoUsuarioComentario.value = idReceptor;
    }
  });

  /**
   * Envía una solicitud para importar una tarea externa y la asocia a un tablón.
   */
  document.getElementById("form-importar").addEventListener("submit", async (e) => {
    e.preventDefault();

    const now = new Date();
    const dto = {
      titulo: document.getElementById("importar-titulo").value,
      descripcion: document.getElementById("importar-descripcion").value,
      estado: document.getElementById("importar-estado").value,
      categoria: document.getElementById("importar-categoria").value,
      prioridad: document.getElementById("importar-prioridad").value,
      fechaCreacion: now.toISOString(),
      fechaLimite: new Date(now.getTime() + 7 * 86400000).toISOString(),
      receptorId: parseInt(document.getElementById("importar-receptor").value)
    };

    const idTablon = document.getElementById("importar-idTablon").value;

    try {
      const res = await fetch(`/api/profesor/tareas/importar-externa/${idTablon}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          [csrfHeader]: csrfToken
        },
        body: JSON.stringify(dto)
      });

      const msg = await res.text();
      feedback.textContent = res.ok ? "Tarea importada con éxito." : `Error: ${msg}`;
      feedback.classList.toggle("error", !res.ok);

      // Si la tarea se importa correctamente, se actualiza el selector de tareas y se cargan comentarios
      if (res.ok) {
        const tarea = JSON.parse(msg);
        document.getElementById("comentario-idTarea").value = tarea.idTarea;
        cargarComentarios(tarea.idTarea);
      }
    } catch (err) {
      feedback.textContent = "Error al importar la tarea.";
      feedback.classList.add("error");
    }
  });

  /**
   * Envía un comentario asociado a una tarea.
   */
  document.getElementById("form-comentario").addEventListener("submit", async function(e) {
    e.preventDefault();

    const contenido = document.getElementById("comentario-contenido").value;
    const idTarea = document.getElementById("comentario-idTarea").value;
    const idUsuario = document.getElementById("comentario-idUsuario").value;
    const feedback = document.getElementById("feedback-comentario");

    const fecha = new Date().toISOString().split(".")[0]; // Elimina milisegundos para un formato más limpio

    const comentario = {
      contenido,
      idTarea: parseInt(idTarea),
      idUsuario: parseInt(idUsuario),
      fechaComentario: fecha
    };

    try {
      const res = await fetch("/api/profesor/comentariosTarea/crear", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          [csrfHeader]: csrfToken
        },
        body: JSON.stringify(comentario)
      });

      if (res.ok) {
        feedback.textContent = "Comentario añadido correctamente.";
        feedback.style.color = "green";
        document.getElementById("comentario-contenido").value = "";
        cargarComentarios(idTarea);
      } else {
        const msg = await res.text();
        feedback.textContent = "Error: " + msg;
        feedback.style.color = "red";
      }
    } catch (err) {
      feedback.textContent = "Error de red al enviar el comentario.";
      feedback.style.color = "red";
    }
  });

  /**
   * Carga y muestra los comentarios asociados a una tarea.
   * @param {number} idTarea - Identificador de la tarea para la que se buscan los comentarios.
   */
  async function cargarComentarios(idTarea) {
    const res = await fetch("/api/profesor/comentariosTarea/listar");
    const comentarios = await res.json();

    const lista = document.getElementById("lista-comentarios");
    lista.innerHTML = "";

    comentarios
      .filter(c => c.tarea.idTarea == idTarea)
      .forEach(c => {
        const li = document.createElement("li");
        const fecha = new Date(c.fechaComentario).toLocaleString("es-ES");
        li.innerHTML = `<strong>${c.usuario.nombre}:</strong> ${c.contenido} <em>(${fecha})</em>`;
        lista.appendChild(li);
      });
  }
});
