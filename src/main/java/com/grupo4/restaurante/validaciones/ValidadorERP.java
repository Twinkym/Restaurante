package com.grupo4.restaurante.validaciones;

import com.grupo4.restaurante.dto.ReservaFormDTO;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Validador personalizado para reglas de negocio del ERP de restaurante.
 * Ampliable a futuras áreas como empleados, productos, etc.
 */

public class ValidadorERP {

    /*
     * Normaliza los datos del formulario de reserva: asegura que haya fecha y número de comensales válidos.
     */
    public static void normalizarReservaForm(ReservaFormDTO form) {
        if (form.getFecha() == null) {
            form.setFecha(LocalDate.now());
        }
        if (form.getNumComensales() == null || form.getNumComensales() <= 0) {
            form.setNumComensales(1);
        }
    }

    /**
     * Verifica si el turno ha sido seleccionado correctamente 8no nulo).
     */
    public static boolean validarTurnoSeleccionado(ReservaFormDTO form) {
        return form.getHoraTurno() != null;
    }
    /*
     * Válida si el formulario de reserva tiene los campos mínimos requeridos.
     * @param reservaForm formulario de reserva a validar.
     * @return true si el formulario es válido, false en caso contrario.
     */
    public static boolean esFormularioReservaValido(ReservaFormDTO reservaForm) {
        return reservaForm != null && reservaForm.getFecha() != null && reservaForm.getNumComensales() != null && reservaForm.getNumComensales() > 0;
    }

    /*
     * Establece valores por defecto al formulario de reserva si alguno es nulo.
     * @param reservaForm formulario a completar.
     */
    public static void aplicarValoresPorDefecto(ReservaFormDTO reservaForm) {
        if (reservaForm.getFecha() == null) {
            reservaForm.setFecha(LocalDate.now());
        }
        if (reservaForm.getNumComensales() == null || reservaForm.getNumComensales() <= 0) {
            reservaForm.setNumComensales(1);
        }
    }

    /*
     * Verifica si se ha seleccionado una hora de turno válida
     * @param horaTurno la hora del turno.
     * @return true si está definida, false si es nula.
     */
    public static boolean esTurnoSeleccionado(LocalTime horaTurno) {
        return horaTurno != null;
    }

    /*
     * Valida si una cadena no es nula ni vacía.
     * @param valor cadena a evaluar.
     * @return true si contiene texto, false si es nula o vaciá.
     */
    public static boolean esTextoValido(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    // --- Métodos RESERVADOS PARA VALIDACIONES FUTURAS --- //

    /*
     * Validación placeholder para logica futura de productos.
     */
    public static boolean esProductoValido(Object producto) {
        return producto != null; //
    }

    /*
     * Validación placeholder para logica futura de pedidos.
     */
    public static boolean esPedidoValido(Object pedido) {
        return pedido != null; //
    }

    /*
     * Validación placeholder para logica futura de productos.
     */
    public static boolean esEmpleadoValido(Object empleado) {
        return empleado != null; //
    }

    /*
     * Validación placeholder para logica futura de productos.
     */
    public static boolean esClienteValido(Object cliente) {
        return cliente != null; //
    }

    /*
     * Validación placeholder para logica futura de productos.
     */
    public static boolean esProveedorValido(Object proveedor) {
        return proveedor != null; //
    }

    /*
     * Validación placeholder para logica futura de productos.
     */
    public static boolean esCategoriaValido(Object categoria) {
        return categoria != null; //
    }


}
