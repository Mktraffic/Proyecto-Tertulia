window.addEventListener('DOMContentLoaded', () => {
    const successToast = document.getElementById('success');
    const errorToast = document.getElementById('error');

    setTimeout(() => {
        if (successToast) {
            successToast.style.transition = 'opacity 0.5s ease';
            successToast.style.opacity = '0';
            setTimeout(() => successToast.style.display = 'none', 500);
        }

        if (errorToast) {
            errorToast.style.transition = 'opacity 0.5s ease';
            errorToast.style.opacity = '0';
            setTimeout(() => errorToast.style.display = 'none', 500);
        }
    }, 3000); 
});
