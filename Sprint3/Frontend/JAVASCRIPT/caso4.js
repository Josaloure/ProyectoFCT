function cerrarVentana() {
  if (window.close) {
    window.close();
  } else {
    alert("Esta pestaña no puede cerrarse automáticamente.");
  }
}

document.getElementById("buscarTablonForm").addEventListener("submit", async function (e) {
  e.preventDefault();
  const id = e.target.idTablonCentro.value;
  const resultado = document.getElementById("resultadoTablon");

  try {
    const res = await fetch(`http://localhost:8080/tablonCentro/listarPorId/${id}`);
    if (!res.ok) throw new Error();
    const tablon = await res.json();

    resultado.innerHTML = `
      <p><strong>ID:</strong> ${tablon.idTablonCentro}</p>
      <p><strong>Nombre:</strong> ${tablon.nombre}</p>
      <p><strong>Usuario:</strong> ${tablon.usuario?.idUsuario}</p>
    `;

    const form = document.getElementById("formTablon");
    form.idTablonCentro.value = tablon.idTablonCentro;
    form.nombre.value = tablon.nombre;
    form.idUsuario.value = tablon.usuario?.idUsuario || "";

  } catch {
    resultado.innerHTML = `<p style="color:red;">❌ Tablón no encontrado.</p>`;
  }
});

document.getElementById("listarUsuarioForm").addEventListener("submit", async function (e) {
  e.preventDefault();
  const idUsuario = e.target.idUsuario.value;
  const resultado = document.getElementById("resultadoTablon");

  try {
    const res = await fetch(`http://localhost:8080/tablonCentro/consultarPorUsuario/${idUsuario}`);
    const tablones = await res.json();

    if (!tablones.length) {
      resultado.innerHTML = `<p style="color:red;">⚠️ No hay tablones para ese usuario.</p>`;
      return;
    }

    resultado.innerHTML = tablones.map(t => `
      <p><strong>ID:</strong> ${t.idTablonCentro} - <strong>Nombre:</strong> ${t.nombre}</p>
    `).join('');
  } catch {
    resultado.innerHTML = `<p style="color:red;">Error al consultar.</p>`;
  }
});

document.getElementById("formTablon").addEventListener("submit", async function (e) {
  e.preventDefault();
  const form = e.target;
  const id = form.idTablonCentro.value;
  const method = id ? "PUT" : "POST";
  const url = id
    ? `http://localhost:8080/tablonCentro/actualizar/${id}`
    : `http://localhost:8080/tablonCentro/crear`;

  const data = {
    idTablonCentro: id ? parseInt(id) : undefined,
    nombre: form.nombre.value,
    usuario: { idUsuario: parseInt(form.idUsuario.value) }
  };

  try {
    const res = await fetch(url, {
      method: method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    const msg = await res.text();
    const resultado = document.getElementById("mensajeTablon");
    resultado.textContent = msg;
    resultado.style.color = res.ok ? "green" : "red";
  } catch {
    document.getElementById("mensajeTablon").textContent = "❌ Error al guardar.";
    document.getElementById("mensajeTablon").style.color = "red";
  }
});

async function eliminarTablon() {
  const id = document.getElementById("formTablon").idTablonCentro.value;
  if (!id) return alert("Primero busca un tablón para eliminar.");

  if (!confirm(`¿Eliminar el tablón con ID ${id}?`)) return;

  try {
    const res = await fetch(`http://localhost:8080/tablonCentro/eliminar/${id}`, {
      method: "DELETE"
    });

    const mensaje = await res.text();
    document.getElementById("mensajeTablon").textContent = mensaje;
    document.getElementById("mensajeTablon").style.color = res.ok ? "green" : "red";

    if (res.ok) {
      document.getElementById("formTablon").reset();
      document.getElementById("resultadoTablon").innerHTML = "";
    }
  } catch {
    alert("❌ Error al eliminar el tablón.");
  }
}
