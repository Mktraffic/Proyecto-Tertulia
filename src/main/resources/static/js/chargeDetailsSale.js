document.getElementById('categoria').addEventListener('change', function () {
    fetch(`/productos?categoria=${this.value}`)
        .then(res => res.json())
        .then(data => {
            const productoSelect = document.getElementById('producto');
            productoSelect.innerHTML = '';
            data.forEach(p => {
                const opt = document.createElement('option');
                opt.value = opt.text = p;
                productoSelect.appendChild(opt);
            });
            productoSelect.dispatchEvent(new Event('change'));
        });
});

document.getElementById('producto').addEventListener('change', function () {
    fetch(`/presentaciones?producto=${this.value}`)
        .then(res => res.json())
        .then(data => {
            const presentacionSelect = document.getElementById('presentacion');
            presentacionSelect.innerHTML = '';
            data.forEach(p => {
                const opt = document.createElement('option');
                opt.value = opt.text = p;
                presentacionSelect.appendChild(opt);
            });
            presentacionSelect.dispatchEvent(new Event('change'));
        });
});

document.getElementById('presentacion').addEventListener('change', function () {
    fetch(`/precio?presentacion=${this.value}`)
        .then(res => res.json())
        .then(precio => {
            document.querySelector('input[name="price"]').value = precio;
        });
});