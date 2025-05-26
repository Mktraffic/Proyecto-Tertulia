document.addEventListener('DOMContentLoaded', function () {
    const categoriaSelect = document.getElementById('categoria');
    const productoSelect = document.getElementById('producto');
    const presentacionSelect = document.getElementById('presentacion');
    const precioInput = document.querySelector('input[name="price"]');

    categoriaSelect.addEventListener('change', function () {
        fetch(`/productos?categoria=${this.value}`)
            .then(res => res.json())
            .then(data => {
                productoSelect.innerHTML = '<option disabled selected>Seleccione un producto</option>';
                data.forEach(p => {
                    const opt = document.createElement('option');
                    opt.value = p;
                    opt.text = p;
                    productoSelect.appendChild(opt);
                });
                presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';
                precioInput.value = '';
            });
    });

    productoSelect.addEventListener('change', function () {
        fetch(`/presentaciones?producto=${this.value}`)
            .then(res => res.json())
            .then(data => {
                presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';
                data.forEach(p => {
                    const opt = document.createElement('option');
                    opt.value = p;
                    opt.text = p;
                    presentacionSelect.appendChild(opt);
                });
                precioInput.value = '';
            });
    });

    presentacionSelect.addEventListener('change', function () {
        const producto = productoSelect.value;
        const presentacion = this.value;
        fetch(`/precio?producto=${producto}&presentacion=${presentacion}`)
            .then(res => res.json())
            .then(precio => {
                precioInput.value = precio;
            });
    });
});
