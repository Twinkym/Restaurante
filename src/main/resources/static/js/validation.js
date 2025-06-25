// src/main/resources/static/js/form-validation.js
/**
 * Lógica JavaScript para la validación de formularios de Bootstrap 5.
 * Este script activa las clases de validación de Bootstrap para mostrar el feedback visual
 * basado en la validación nativa del navegador, complementando la validación del lado del servidor.
 *
 * Se encarga de añadir la clase 'was-validated' al formulario
 * para activar los estilos CSS de Bootstrap para los estados de validación.
 *
 * @author David De La Puente Enriquez - KirgoDev
 * @version 1.0.0
 * @since 2025-06-25
 */
document.addEventListener('DOMContentLoaded', function () {
    // Selecciona todos los formularios que tienen la clase 'needs-validation' de Bootstrap.
    const forms = document.querySelectorAll('.needs-validation');

    // Convierte la NodeList de formularios a un Array y itera sobre cada uno.
    Array.from(forms).forEach(function (form) {
        // Agrega un 'event listener' al evento 'submit' de cada formulario.
        form.addEventListener('submit', function (event) {
            // Comprueba la validez del formulario usando la API de validación nativa del navegador.
            // Si el formulario no es válido, previene el envío y detiene la propagación del evento.
            if (!form.checkValidity()) {
                event.preventDefault(); // Evita que el formulario se envíe.
                event.stopPropagation(); // Detiene la propagación del evento a elementos padre.
            }

            // Añade la clase 'was-validated' al formulario.
            // Esto activa los estilos CSS de Bootstrap para mostrar el feedback de validación (válido/inválido).
            form.classList.add('was-validated');
        }, false); // El tercer argumento 'false' indica que el evento se maneja en la fase de burbujeo.
    });
});
