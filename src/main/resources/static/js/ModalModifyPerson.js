function openModal(button) {
    console.log('Intentando abrir modal...');
    
    try {
        const modal = document.getElementById('editModal');
        if (!modal) {
            console.error('Error: Modal con ID "editModal" no encontrado');
            return;
        }
        
        const setFieldValue = (fieldId, value) => {
            const field = document.getElementById(fieldId);
            if (field) {
                field.value = value || '';
                console.log(`Campo ${fieldId} establecido:`, value);
            } 
        };
        
        const personaId = button.getAttribute('data-id');
        const nombre = button.getAttribute('data-nombre');
        const apellido = button.getAttribute('data-apellido');
        const telefono = button.getAttribute('data-telefono');
        const correo = button.getAttribute('data-correo');
        const rol = button.getAttribute('data-rol');
        const userName = button.getAttribute('data-userName');
        const password = button.getAttribute('data-password');
        const estado = button.getAttribute('data-state');
        
        console.log('Datos obtenidos:', {
            personaId, nombre, apellido, telefono, correo, rol, userName, estado
        });
        
        setFieldValue('edit-id', personaId);
        setFieldValue('edit-nombre', nombre);
        setFieldValue('edit-apellido', apellido);
        setFieldValue('edit-telefono', telefono);
        setFieldValue('edit-correo', correo);
        setFieldValue('edit-rol', rol);
        setFieldValue('edit-userName', userName);
        setFieldValue('edit-password', password);
        setFieldValue('edit-state', estado);
        
        const datosUsuarioDiv = document.getElementById('datosUsuario');
        const rolField = document.getElementById('edit-rol');
        const userNameField = document.getElementById('edit-userName');
        const passwordField = document.getElementById('edit-password');
        
        if (datosUsuarioDiv) {
            if (rol && rol.toLowerCase() !== 'proveedor') {
                datosUsuarioDiv.style.display = 'block';
                if (rolField) rolField.setAttribute('required', 'required');
                if (passwordField) passwordField.setAttribute('required', 'required');
                console.log('Mostrando datos de usuario para rol:', rol);
            } else {
                datosUsuarioDiv.style.display = 'none';
                if (rolField) {
                    rolField.removeAttribute('required');
                    rolField.value = 'Proveedor'; 
                }
                if (passwordField) {
                    passwordField.removeAttribute('required');
                    passwordField.value = '';
                }
                if (userNameField) {
                    userNameField.value = '';
                }
                console.log('Ocultando datos de usuario para rol:', rol);
            }
        } else {
            console.warn('Div datosUsuario no encontrado');
        }

        modal.style.display = 'block';
        console.log('Modal mostrado exitosamente');

        const primerCampoEditable = document.getElementById('edit-telefono');
        if (primerCampoEditable) {
            setTimeout(() => primerCampoEditable.focus(), 100);
        }
        
    } catch (error) {
        console.error('Error al abrir el modal:', error);
        alert('Error al abrir el formulario de edición. Revisa la consola para más detalles.');
    }
}

function closeModal() {
    console.log('Cerrando modal...');
    const modal = document.getElementById('editModal');
    if (modal) {
        modal.style.display = 'none';
        console.log('Modal cerrado exitosamente');
    } else {
        console.error('Error: No se pudo cerrar el modal - elemento no encontrado');
    }
}

function handleOutsideClick(event) {
    const modal = document.getElementById('editModal');
    if (modal && event.target === modal) {
        closeModal();
    }
}

function handleEscapeKey(event) {
    if (event.key === 'Escape') {
        closeModal();
    }
}

function validateForm(event) {
    const rol = document.getElementById('edit-rol').value;
    const datosUsuarioDiv = document.getElementById('datosUsuario');

    if (rol === 'Proveedor' || datosUsuarioDiv.style.display === 'none') {
        const userNameField = document.getElementById('edit-userName');
        const passwordField = document.getElementById('edit-password');
        const rolField = document.getElementById('edit-rol');
        
        if (userNameField) userNameField.value = '';
        if (passwordField) passwordField.value = '';
        if (rolField) rolField.value = 'Proveedor';
        
        console.log('Formulario ajustado para proveedor');
    }
    
    return true; 
}

function initializeModal() {
    console.log('Inicializando eventos del modal...');
  
    window.addEventListener('click', handleOutsideClick);
    document.addEventListener('keydown', handleEscapeKey);

    const form = document.querySelector('#editModal form');
    if (form) {
        form.addEventListener('submit', validateForm);
    }
}

if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initializeModal);
} else {
    initializeModal();
}