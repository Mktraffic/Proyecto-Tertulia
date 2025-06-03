function openModal(button) {
    document.getElementById('edit-code').value = button.getAttribute('data-code');
   document.getElementById('edit-name').value = button.getAttribute('data-name');
    document.getElementById('edit-description').value = button.getAttribute('data-description');
    document.getElementById('edit-presentation').value = button.getAttribute('data-presentation');
    document.getElementById('edit-quantity').value = button.getAttribute('data-quantity');
    document.getElementById('edit-price').value = button.getAttribute('data-price');
    document.getElementById('editModal').style.display = 'block';
}

function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

window.onclick = function(event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}
