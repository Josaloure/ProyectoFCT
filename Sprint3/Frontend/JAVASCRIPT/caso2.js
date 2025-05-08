function cerrarVentana() {
    if (window.close) {
      window.close();
    } else {
      alert("Esta pestaña no puede cerrarse automáticamente.");
    }
  }
  
  document.getElementById("buscarTablonForm").addEventListener("submit", async function (event) {
    event.preventDefault();
  
    const id = event.target.idTablon.value;
    const resultado = document.getElementById("resultadoTablon");
  
    try {
      const res = await fetch(`http://localhost:8080/tablonProfesor/listarPorId/${id}`);
      if (!res.ok) throw new Error("No encontrado");
  
      const tablon = await res.json();
      resultado.innerHTML = `
        <p><strong>ID:</strong> ${tablon.idTablonProfesor}</p>
        <p><strong>Nombre:</strong> ${tablon.nombre}</p>
        <p><strong>ID Usuario:</strong> ${tablon.usuario?.idUsuario || "N/A"}</p>
      `;
  
      // Rellenar el formulario para editar
      const form = document.getElementById("formGuardarTablon");
      form.idTablon.value = tablon.idTablonProfesor;
      form.nombre.value = tablon.nombre;
      form.idUsuario.value = tablon.usuario?.idUsuario || "";
      resultado.style.color = "black";
    } catch (error) {
      resultado.textContent = "Error: no se pudo encontrar el tablón.";
      resultado.style.color = "red";
    }
  });
  
  document.getElementById("formGuardarTablon").addEventListener("submit", async function (event) {
    event.preventDefault();
  
    const form = event.target;
    const id = form.idTablon.value;
    const metodo = id ? "PUT" : "POST";
    const url = id
      ? `http://localhost:8080/tablonProfesor/actualizar/${id}`
      : `http://localhost:8080/tablonProfesor/crear`;
  
    const body = {
      idTablonProfesor: id ? parseInt(id) : undefined,
      nombre: form.nombre.value,
      usuario: { idUsuario: parseInt(form.idUsuario.value) }
    };
  
    try {
      const res = await fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
      });
  
      const msgDiv = document.getElementById("mensajeGuardar");
      if (res.ok) {
        const data = await res.json();
        msgDiv.textContent = `✔️ Tablón ${id ? "actualizado" : "creado"} con éxito. ID: ${data.idTablonProfesor}`;
        msgDiv.style.color = "green";
      } else {
        const errorMsg = await res.text();
        msgDiv.textContent = `❌ Error: ${errorMsg}`;
        msgDiv.style.color = "red";
      }
    } catch (error) {
      document.getElementById("mensajeGuardar").textContent = "Error al guardar el tablón.";
      document.getElementById("mensajeGuardar").style.color = "red";
    }
  });
  
  async function eliminarTablon() {
    const id = document.getElementById("formGuardarTablon").idTablon.value;
    if (!id) {
      alert("Primero busca o rellena un tablón con ID para poder eliminarlo.");
      return;
    }
  
    const confirmacion = confirm(`¿Estás seguro de que deseas eliminar el tablón con ID ${id}?`);
    if (!confirmacion) return;
  
    try {
      const res = await fetch(`http://localhost:8080/tablonProfesor/eliminar/${id}`, {
        method: "DELETE"
      });
  
      const mensaje = await res.text();
      const msgDiv = document.getElementById("mensajeGuardar");
      if (res.ok) {
        msgDiv.textContent = `✅ ${mensaje}`;
        msgDiv.style.color = "green";
        document.getElementById("formGuardarTablon").reset();
        document.getElementById("resultadoTablon").textContent = "";
      } else {
        msgDiv.textContent = `❌ ${mensaje}`;
        msgDiv.style.color = "red";
      }
    } catch (error) {
      alert("Error al eliminar el tablón.");
    }
  }
  