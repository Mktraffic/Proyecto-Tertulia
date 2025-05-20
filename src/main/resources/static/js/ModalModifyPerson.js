function openModal(button) {
    document.getElementById('edit-id').value = button.getAttribute('data-id');
    document.getElementById('edit-nombre').value = button.getAttribute('data-nombre');
   document.getElementById('edit-apellido').value = button.getAttribute('data-apellido');
    document.getElementById('edit-telefono').value = button.getAttribute('data-telefono');
    document.getElementById('edit-correo').value = button.getAttribute('data-correo');
    document.getElementById('edit-rol').value = button.getAttribute('data-rol');
    document.getElementById('edit-userName').value = button.getAttribute('data-userName');
    document.getElementById('edit-password').value = button.getAttribute('data-password');
     document.getElementById('edit-state').value = button.getAttribute('data-state');
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
