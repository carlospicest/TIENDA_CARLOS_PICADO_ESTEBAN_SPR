$(function() {

	/*
		Obligar al usuario a seleccionar un método de pago para poder
		habilitar la opción de realizar pago.
	*/

	$('input[name="paymentMethod"]').change(function() {
		
		if (this.checked) {
			$('#doPayment').prop('disabled', false);
		} else {
			$('#doPayment').prop('disabled', true);
		}
		
	});

	/* Acción para el botón de volver al carrito. */
	
	$('button[id="cart"]').click(() => {
		window.location = "carrito";
	});

});