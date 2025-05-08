const API_BASE = "/historialCorreos";

document.getElementById("formCorreo").addEventListener("submit", async function (e) {
  e.preventDefault();

  const id = document.getElementById("idHistorial").value;
  const method = id ? "PUT" : "POST";
  const endpoint = id ? `${API_BASE}/actualizar/${id}` : `${API_BASE}/crear`;

  const data = {
    idHistorial: id || null,
    destinatarioEmail: document.getElementById("destinatarioEmail").value,
    fechaEnvio: document.getElementById("fechaEnvio").value,
    tipoNotificacion: document.getElementById("tipoNotificacion").value,
    tarea: { id: parseInt(document.getElementById("idTarea").value) },
    mensaje: { id: parseInt(document.getElementById("idMensaje").value) }
  };

  try {
    const res = await fetch(endpoint, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error(await res.text());

    alert(id ? "Actualizado correctamente" : "Creado correctamente");
    this.reset();
    cargarHistorial();
  } catch (error) {
    alert("Error: " + error.message);
  }
});

async function cargarHistorial() {
  const lista = document.getElementById("listaCorreos");
  lista.innerHTML = "";
  try {
    const res = await fetch(`${API_BASE}/listar`);
    const correos = await res.json();

    correos.forEach(correo => {
      const li = document.createElement("li");
      li.innerHTML = `
        <span>${correo.destinatarioEmail} | ${correo.tipoNotificacion} | ${correo.fechaEnvio}</span>
        <div>
          <button onclick="cargarEnFormulario(${correo.idHistorial})">✏️</button>
          <button onclick="eliminar(${correo.idHistorial})">🗑️</button>
        </div>
      `;
      lista.appendChild(li);
    });
  } catch (error) {
    alert("Error al cargar historiales.");
  }
}

async function cargarEnFormulario(id) {
  try {
    const res = await fetch(`${API_BASE}/listarPorId/${id}`);
    const correo = await res.json();

    document.getElementById("idHistorial").value = correo.idHistorial;
    document.getElementById("destinatarioEmail").value = correo.destinatarioEmail;
    document.getElementById("fechaEnvio").value = correo.fechaEnvio;
    document.getElementById("tipoNotificacion").value = correo.tipoNotificacion;
    document.getElementById("idTarea").value = correo.tarea?.id || '';
    document.getElementById("idMensaje").value = correo.mensaje?.id || '';
  } catch {
    alert("No se pudo cargar el historial.");
  }
}

async function eliminar(id) {
  if (!confirm("¿Seguro que quieres eliminar este historial?")) return;

  try {
    const res = await fetch(`${API_BASE}/eliminar/${id}`, { method: "DELETE" });
    const mensaje = await res.text();
    alert(mensaje);
    cargarHistorial();
  } catch {
    alert("No se pudo eliminar.");
  }
}
