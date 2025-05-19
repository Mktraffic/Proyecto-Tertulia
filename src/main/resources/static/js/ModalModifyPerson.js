function openModal(button) {
    document.getElementById('edit-id').value = button.getAttribute('data-id');
    document.getElementById('edit-nombre').value = button.getAttribute('data-nombre');
    document.getElementById('edit-documento').value = button.getAttribute('data-documento');
    document.getElementById('edit-telefono').value = button.getAttribute('data-telefono');
    document.getElementById('edit-correo').value = button.getAttribute('data-correo');
    document.getElementById('edit-rol').value = button.getAttribute('data-rol');

    document.getElementById('editModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

// Opcional: cerrar modal al hacer clic fuera
window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}
