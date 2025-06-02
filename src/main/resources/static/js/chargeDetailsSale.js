document.addEventListener('DOMContentLoaded', function () {
    const categoriaSelect = document.getElementById('categoria');
    const productoSelect = document.getElementById('producto');
    const presentacionSelect = document.getElementById('presentacion');
    const precioInput = document.getElementById('precio');

    function toggleElement(element, disabled) {
        element.disabled = disabled;
        if (disabled) {
            element.classList.add('disabled');
        } else {
            element.classList.remove('disabled');
        }
    }

    categoriaSelect.addEventListener('change', function () {
        const categoriaSeleccionada = this.value;

        productoSelect.innerHTML = '<option value="" disabled selected>Cargando productos...</option>';
        presentacionSelect.innerHTML = '<option value="" disabled selected>Seleccione presentación</option>';
        precioInput.value = '';
        toggleElement(productoSelect, true);
        toggleElement(presentacionSelect, true);

        if (!categoriaSeleccionada) return;

        fetch('/productos?categoria=' + encodeURIComponent(categoriaSeleccionada))
            .then(response => response.json())
            .then(data => {
                productoSelect.innerHTML = '<option value="" disabled selected>Seleccione un producto</option>';

                if (data && data.length > 0) {
                    data.forEach(producto => {
                        const option = document.createElement('option');
                        option.value = producto.id; 
                        option.text = producto.name; 
                        productoSelect.appendChild(option);
                    });
                    toggleElement(productoSelect, false);
                } else {
                    productoSelect.innerHTML = '<option value="" disabled selected>No hay productos disponibles</option>';
                }
            })
            .catch(error => {
                console.error('Error al obtener productos:', error);
                productoSelect.innerHTML = '<option value="" disabled selected>Error al cargar productos</option>';
            });
    });

    productoSelect.addEventListener('change', function () {
        const productoId = this.value;

        presentacionSelect.innerHTML = '<option value="" disabled selected>Cargando presentaciones...</option>';
        precioInput.value = '';
        toggleElement(presentacionSelect, true);

        if (!productoId) return;

        fetch('/presentaciones?productoId=' + encodeURIComponent(productoId))
            .then(response => response.json())
            .then(data => {
                presentacionSelect.innerHTML = '<option value="" disabled selected>Seleccione presentación</option>';

                if (data && data.length > 0) {
                    data.forEach(presentacion => {
                        const option = document.createElement('option');
                        option.value = presentacion; 
                        option.text = presentacion;
                        presentacionSelect.appendChild(option);
                    });
                    toggleElement(presentacionSelect, false);
                } else {
                    presentacionSelect.innerHTML = '<option value="" disabled selected>No hay presentaciones disponibles</option>';
                }
            })
            .catch(error => {
                console.error('Error al obtener presentaciones:', error);
                presentacionSelect.innerHTML = '<option value="" disabled selected>Error al cargar presentaciones</option>';
            });
    });

    presentacionSelect.addEventListener('change', function () {
        const productoId = productoSelect.value;
        const presentacion = this.value;

        if (!productoId || !presentacion) {
            precioInput.value = '';
            return;
        }
        document.getElementById('presentacionHidden').value = this.value;
        precioInput.value = 'Cargando...';

        fetch(`/precio?productoId=${encodeURIComponent(productoId)}&presentacion=${encodeURIComponent(presentacion)}`)
            .then(response => response.json())
            .then(precio => {
                if (precio !== null && precio !== undefined) {
                    precioInput.value = precio;
                } else {
                    precioInput.value = '';
                    console.error('Precio no disponible');
                }
            })
            .catch(error => {
                console.error('Error al obtener precio:', error);
                precioInput.value = '';
            });
    });
});