function loadExtraFields() {
    const type = document.getElementById("tipoProducto").value;
    if (!type) {
        document.getElementById("extra-fields").innerHTML = "";
        return;
    }
    fetch(`/fragment?typeProduct=${encodeURIComponent(type)}`)
    .then(resp => resp.text())
    .then(html => {
        document.getElementById("extra-fields").innerHTML = html;
    })
    .catch(() => {
        document.getElementById("extra-fields").innerHTML = "<p>Error al cargar campos extra</p>";
    });
}
