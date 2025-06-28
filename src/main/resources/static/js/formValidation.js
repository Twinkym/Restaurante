(function () {
    'use strict';
    let forms = document.querySelectorAll('need-validation');
    Array.prototype.slice.call(form).forEach(function (form) {
        form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        }, false);
    });
})();

// Lógica para evitar enviar el formulario automáticamente al cambiar  fecha o comensales.
document.addEventListener('DOMContentloaded', function() {
    const fechaInput = document.getElementById('fecha');
    const numComensalesSelect = document.getElementById('numComensales');
    const for = document.querySelector('form.needs-validation');
    const actionTypeInput = document.getElementById('actionType');

    function submitForFilter() {
    actionTypeInput.value = 'filtrar';
    form.submit();
    }

    if (fechaInput) {
        fechaInput.addEventListener('change', submitForFilter);
    }
    if (numComensalesSelect) {
        numComensalesSelect.addEventListener('change', submitForFilter);
    }
});