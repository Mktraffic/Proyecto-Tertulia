
    const estadoSelect = document.getElementById('edit-state');
    const estadoLabel = document.getElementById('estado-label');

    function actualizarEstadoLabel() {
        const isActive = estadoSelect.value === 'true';
        estadoLabel.textContent = isActive ? 'Activo' : 'Inactivo';
        estadoLabel.style.backgroundColor = isActive ? '#d4edda' : '#f8d7da'; 
        estadoLabel.style.color = isActive ? '#155724' : '#721c24';        
        estadoLabel.style.border = '1px solid ' + (isActive ? '#c3e6cb' : '#f5c6cb');
    }
    estadoSelect.addEventListener('change', actualizarEstadoLabel);

    document.addEventListener('DOMContentLoaded', actualizarEstadoLabel);
