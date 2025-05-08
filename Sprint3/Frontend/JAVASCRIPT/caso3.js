function cerrarVentana() {
    if (window.close) {
      window.close();
    } else {
      alert("No se puede cerrar esta pestaña automáticamente.");
    }
  }
  
  document.getElementById("buscarMensajeForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const id = e.target.idMensaje.value;
    const area = document.getElementById("resultadoMensaje");
  
    try {
      const res = await fetch(`http://localhost:8080/mensajes/listarPorId/${id}`);
      if (!res.ok) throw new Error();
  
      const m = await res.json();
      area.innerHTML = `
        <p><strong>ID:</strong> ${m.idMensaje}</p>
        <p><strong>Título:</strong> ${m.titulo}</p>
        <p><strong>Contenido:</strong> ${m.contenido}</p>
        <p><strong>Publicador:</strong> ${m.publicador?.idUsuario}</p>
        <p><strong>Tablón:</strong> ${m.tablonCentro?.idTablonCentro}</p>
      `;
    } catch {
      area.innerHTML = `<p style="color:red;">❌ Mensaje no encontrado.</p>`;
    }
  });
  
  document.getElementById("filtrarPorTablonForm").addEventListener("submit", async function (e) {
    e.preventDefault();
    const id = e.target.idTablon.value;
    const area = document.getElementById("resultadoMensaje");
  
    try {
      const res = await fetch(`http://localhost:8080/mensajes/filtrarPorTC/${id}`);
      const mensajes = await res.json();
  
      if (!mensajes.length) {
        area.innerHTML = `<p style="color:red;">⚠️ No hay mensajes para ese tablón.</p>`;
        return;
      }
  
      area.innerHTML = mensajes.map(m => `
        <div style="margin-bottom:10px;">
          <p><strong>ID:</strong> ${m.idMensaje} - <strong>Título:</strong> ${m.titulo}</p>
        </div>
      `).join('');
    } catch {
      area.innerHTML = `<p style="color:red;">Error al obtener mensajes.</p>`;
    }
  });
  
  document.getElementById("formGuardarMensaje").addEventListener("submit", async function (e) {
    e.preventDefault();
    const form = e.target;
  
    const data = {
      titulo: form.titulo.value,
      contenido: form.contenido.value,
      fechaCreacion: form.fechaCreacion.value,
      fechaPublicacion: form.fechaPublicacion.value || null,
      publicado: form.publicado.value === "true",
      visibleParaTodos: form.visibleParaTodos.value === "true",
      publicador: { idUsuario: parseInt(form.publicador.value) },
      tablonCentro: { idTablonCentro: parseInt(form.tablonCentro.value) }
    };
  
    try {
      const res = await fetch("http://localhost:8080/mensajes/crear", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data)
      });
  
      const msg = await res.text();
      const result = document.getElementById("mensajeFinal");
      result.textContent = msg;
      result.style.color = res.ok ? "green" : "red";
    } catch {
      document.getElementById("mensajeFinal").textContent = "❌ Error al crear el mensaje.";
      document.getElementById("mensajeFinal").style.color = "red";
    }
  });
  