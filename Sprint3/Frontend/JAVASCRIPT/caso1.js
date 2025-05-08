const API_TAREA = "/tarea";
const API_COMENTARIOS = "/comentarioTarea";

document.getElementById("formTarea").addEventListener("submit", async function (e) {
  e.preventDefault();

  const formData = new FormData(this);
  const tarea = {
    titulo: formData.get("titulo"),
    descripcion: formData.get("descripcion"),
    fechaCreacion: formData.get("fechaCreacion"),
    prioridad: formData.get("prioridad"),
    estado: formData.get("estado"),
    categoria: formData.get("categoria"),
    emisor: { id: formData.get("emisor") },
    receptor: { id: formData.get("receptor") },
    tablonProfesor: { id: formData.get("tablonProfesor") }
  };

  try {
    const res = await fetch(`${API_TAREA}/crear`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(tarea)
    });

    if (!res.ok) throw new Error(await res.text());
    const nuevaTarea = await res.json();

    document.getElementById("mensajeResultado").textContent = "Tarea creada con éxito";
    document.getElementById("idTareaComentario").value = nuevaTarea.id;

    cargarComentarios(nuevaTarea.id);
  } catch (err) {
    document.getElementById("mensajeResultado").textContent = "Error al crear la tarea";
  }
});

document.getElementById("formComentario").addEventListener("submit", async function (e) {
  e.preventDefault();

  const contenido = document.getElementById("contenidoComentario").value;
  const idTarea = document.getElementById("idTareaComentario").value;
  const idUsuario = document.getElementById("idUsuarioComentario").value;

  const comentario = {
    contenido,
    fechaComentario: new Date().toISOString(),
    tarea: { id: idTarea },
    usuario: { id: idUsuario }
  };

  const res = await fetch(`${API_COMENTARIOS}/crear`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(comentario)
  });

  if (res.ok) {
    this.reset();
    cargarComentarios(idTarea);
  } else {
    alert("Error al publicar comentario.");
  }
});

async function cargarComentarios(idTarea) {
  const lista = document.getElementById("listaComentarios");
  lista.innerHTML = "";

  const res = await fetch(`${API_COMENTARIOS}/listar`);
  const comentarios = await res.json();

  comentarios
    .filter(c => c.tarea.id == idTarea)
    .forEach(c => {
      const li = document.createElement("li");
      li.innerHTML = `
        <span><strong>Usuario #${c.usuario.id}</strong>: ${c.contenido}</span>
        <small>${new Date(c.fechaComentario).toLocaleString()}</small>
      `;
      lista.appendChild(li);
    });
}
