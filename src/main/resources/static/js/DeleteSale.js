
function confirmDelete(button) {
    const id = button.getAttribute('data-id');
    document.getElementById('ventaId').value = id;
    document.getElementById('deleteModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('deleteModal').style.display = 'none';
}

window.onclick = function (event) {
    const modal = document.getElementById('deleteModal');
    if (event.target === modal) {
        closeModal();
    }
}

