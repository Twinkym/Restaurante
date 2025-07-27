/**
 * Lógica JavaScript para la página de listado de reservas  (reserva_list.html).
 * Maneja la confirmación y el envío del formulario de eliminación de reservas.
 *
 * Autor: David De La Puente - KirgoDev / Grupo 4
 * Versión: 1.0.0
 * Fecha: 2025-06-25
 */

document.addEventListener('DOMContentLoaded', function() {
    let currentReservaIdToDelete = null; // Almacena el ID de la reserva a eliminar.

     /**
     * Itera sobre todos los botones con la clase 'btn-delete'.
     * Cuando se hace clic en uno, guarda el ID de la reserva y lo muestra en el modal de confirmación.
     */

     document.querySelectorAll('.btn-delete').forEach(function(button) {
        button.addEventListener('click', function() {
            currentReservaToDelete = this.getAttribute('data-reserva-id');
            // Actualiza el texto en el modal para mostrar el ID de la reserva seleccionada.
            const reservaIdElement = document.getElementById('reservaToDeleteId');
            if (reservaIdElement) {
                reservaIdElement.textContent = currentReservaIdToDelete;
            }
        });
     });

     /**
     * Maneja el clic en el botón de confirmación dentro del modal de eliminación.
     * Si hay un ID de reserva almacenado, configura la acción del formulario oculto y lo envía.
     */
     const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');
     if (confirmDeleteBtn) {
        confirmDeleteBtn.addEventListener('click', function() {
            if (currentReservaIdToDelete) {
                const deleteForm = document.getElementByID('deleteForm');
                if (deleteForm) {
                    // Configura la URL de acción del formulario para apuntar al endpoint de eliminación correcto.
                    deleteForm.action = '/reservas/' + currentReservaIdToDelete + '/delete';
                    deleteForm.submit(); // Envía el formulario para realizar la petición POST de eliminación
                }
            }
        });
     }
 });