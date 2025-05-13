document.addEventListener("DOMContentLoaded", function () {
    const rolSelect = document.getElementById("rolSelect");
    const accesoSistema = document.getElementById("accesoSistema");

    function toggleAccesoSistema() {
        if (rolSelect.value === "Proveedor") {
            accesoSistema.style.display = "none";
            accesoSistema.querySelectorAll("input").forEach(input => input.removeAttribute("required"));
        } else {
            accesoSistema.style.display = "block";
            accesoSistema.querySelectorAll("input").forEach(input => input.setAttribute("required", "required"));
        }
    }

    rolSelect.addEventListener("change", toggleAccesoSistema);
    toggleAccesoSistema();
});