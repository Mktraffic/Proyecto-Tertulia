document.addEventListener('DOMContentLoaded', function () {
    const categoriaSelect = document.getElementById('categoria');
    const productoSelect = document.getElementById('producto');
    const presentacionSelect = document.getElementById('presentacion');
    const precioInput = document.querySelector('input[name="price"]');

    function showError(message) {
        console.error(message);

    }

    function toggleElement(element, disabled) {
        element.disabled = disabled;
        if (disabled) {
            element.classList.add('disabled');
        } else {
            element.classList.remove('disabled');
        }
    }

    categoriaSelect.addEventListener('change', function () {
        const categoria = this.value;

        if (!categoria) {
            productoSelect.innerHTML = '<option disabled selected>Seleccione un producto</option>';
            presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';
            precioInput.value = '';
            toggleElement(productoSelect, true);
            toggleElement(presentacionSelect, true);
            return;
        }

        productoSelect.innerHTML = '<option disabled selected>Cargando productos...</option>';
        toggleElement(productoSelect, true);

        fetch(`/productos?categoria=${encodeURIComponent(categoria)}`)
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Error HTTP: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                productoSelect.innerHTML = '<option disabled selected>Seleccione un producto</option>';

                if (data && data.length > 0) {
                    data.forEach(producto => {
                        const opt = document.createElement('option');
                        opt.value = producto;
                        opt.textContent = producto;
                        productoSelect.appendChild(opt);
                    });
                    toggleElement(productoSelect, false);
                } else {
                    productoSelect.innerHTML = '<option disabled selected>No hay productos disponibles</option>';
                }

                presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';
                toggleElement(presentacionSelect, true);
                precioInput.value = '';
            })
            .catch(error => {
                showError('Error al cargar productos: ' + error.message);
                productoSelect.innerHTML = '<option disabled selected>Error al cargar productos</option>';
                presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';
                toggleElement(presentacionSelect, true);
                precioInput.value = '';
            });
    });

    productoSelect.addEventListener('change', function () {
        const producto = this.value;

        if (!producto) {
            presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';
            toggleElement(presentacionSelect, true);
            precioInput.value = '';
            return;
        }

        presentacionSelect.innerHTML = '<option disabled selected>Cargando presentaciones...</option>';
        toggleElement(presentacionSelect, true);

        fetch(`/presentaciones?producto=${encodeURIComponent(producto)}`)
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Error HTTP: ${res.status}`);
                }
                return res.json();
            })
            .then(data => {
                presentacionSelect.innerHTML = '<option disabled selected>Seleccione presentación</option>';

                if (data && data.length > 0) {
                    data.forEach(presentacion => {
                        const opt = document.createElement('option');
                        opt.value = presentacion;
                        opt.textContent = presentacion;
                        presentacionSelect.appendChild(opt);
                    });
                    toggleElement(presentacionSelect, false);
                } else {
                    presentacionSelect.innerHTML = '<option disabled selected>No hay presentaciones disponibles</option>';
                }

                precioInput.value = '';
            })
            .catch(error => {
                showError('Error al cargar presentaciones: ' + error.message);
                presentacionSelect.innerHTML = '<option disabled selected>Error al cargar presentaciones</option>';
                precioInput.value = '';
            });
    });

    presentacionSelect.addEventListener('change', function () {
        const producto = productoSelect.value;
        const presentacion = this.value;

        if (!producto || !presentacion) {
            precioInput.value = '';
            return;
        }
        precioInput.value = 'Cargando...';

        fetch(`/precio?producto=${encodeURIComponent(producto)}&presentacion=${encodeURIComponent(presentacion)}`)
            .then(res => {
                if (!res.ok) {
                    throw new Error(`Error HTTP: ${res.status}`);
                }
                return res.json();
            })
            .then(precio => {
                if (precio !== null && precio !== undefined) {
                    precioInput.value = precio;
                } else {
                    precioInput.value = '';
                    showError('Precio no disponible');
                }
            })
            .catch(error => {
                showError('Error al cargar precio: ' + error.message);
                precioInput.value = '';
            });
    });
});