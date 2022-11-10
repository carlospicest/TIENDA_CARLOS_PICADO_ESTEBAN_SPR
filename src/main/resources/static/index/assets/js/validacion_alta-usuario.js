$(function() {

	$('button[type="submit"]').click(function() {

		let datosCorrectos = true;


		// Nombre.
		let nombreInput = $('input[name="nombre"]');

		if (nombreInput.val() == null || nombreInput.val().length == 0) {
			$('#bad-data-nombre').html('El <strong>Nombre</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {
			$('#bad-data-nombre').empty();
			datosCorrectos = true;
		}

		// Primer apellido.
		let primerApellido = $('input[name="apellido1"]');

		if (primerApellido.val() == null || primerApellido.val().length == 0) {
			$('#bad-data-apellido1').html('El <strong>Primer apellido</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {
			$('#bad-data-apellido1').empty();
			datosCorrectos = true;
		}

		// Segundo apellido.
		let segundoApellido = $('input[name="apellido2"]');

		if (segundoApellido.val() == null || segundoApellido.val().length == 0) {
			$('#bad-data-apellido2').html('El <strong>Segundo apellido</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {
			$('#bad-data-apellido2').empty();
			datosCorrectos = true;
		}

		// DNI.
		let dni = $('input[name="dni"]');

		if (dni.val() == null || dni.val().length == 0) {
			$('#bad-data-dni').html('El <strong>DNI</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {

			// Validar el formato del DNI.

			if (!/^([0-9]{8}[a-zA-Z]{1})$/.test(dni.val())) {
				$('#bad-data-dni').html('El <strong>DNI</strong> no tiene un formato válido.');
				datosCorrectos = false;
			} else {
				$('#bad-data-dni').empty();
				datosCorrectos = true;
			}

		}

		// Dirección.
		let direccion = $('input[name="direccion"]');

		if (direccion.val() == null || direccion.val().length == 0) {
			$('#bad-data-direccion').html('La <strong>Dirección</strong> no puede estar en blanco o vacía.');
			datosCorrectos = false;
		} else {
			$('#bad-data-direccion').empty();
			datosCorrectos = true;
		}

		// Provincia.
		let provincia = $('select#provincia');

		if (provincia.val().length == 0) {
			$('#bad-data-provincia').html('La <strong>Provincia</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {
			$('#bad-data-provincia').empty();
			datosCorrectos = true;
		}

		// Localidad.
		let localidad = $('input[name="localidad"]');

		if (localidad.val() == null || localidad.val().length == 0) {
			$('#bad-data-localidad').html('La <strong>Localidad</strong> no puede estar en blanco o vacía.');
			datosCorrectos = false;
		} else {
			$('#bad-data-localidad').empty();
			datosCorrectos = true;
		}

		// Teléfono.
		let telefono = $('input[name="telefono"]');

		if (telefono.val() == null || telefono.val().length == 0) {
			$('#bad-data-telefono').html('El <strong>Teléfono</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {

			// Validar formato del teléfono:

			if (!/^\+{1,1}[1-9]+\s?\d+$|^\d+\s?\d+$/.test(telefono.val())) {
				$('#bad-data-telefono').html('El <strong>Teléfono</strong> no tiene un formato válido.');
				datosCorrectos = false;
			} else {
				$('#bad-data-telefono').empty();
				datosCorrectos = true;
			}

		}

		// Email.
		let email = $('input[name="email"]');

		if (email.val() == null || email.val().length == 0) {
			$('#bad-data-email').html('El <strong>Email</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {

			if (!/[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9]+([.][a-zA-Z0-9]+)*[.][a-zA-Z]{1,5}/.test(email.val())) {
				$('#bad-data-email').html('El <strong>Email</strong> no tiene un formato válido.');
				datosCorrectos = false;
			} else {
				$('#bad-data-email').empty();
				datosCorrectos = true;
			}

		}

		// Contraseña.
		let password = $('input[name="password"]');

		if (password.val() == null || password.val().length == 0) {
			$('#bad-data-password').html('La <strong>Contraseña</strong> no puede estar en blanco o vacía.');
			datosCorrectos = false;
		} else {

			if (password.val().length < 4) {
				$('#bad-data-password').html('La longitud de la <strong>Contraseña</strong> debe ser mínimo de cuatro caracteres.');
				datosCorrectos = false;
			} else {
				$('#bad-data-password').empty();
				datosCorrectos = true;
			}

		}

		// Repetir contraseña.
		let repassword = $('input[name="repassword"]');

		if (repassword.val() == null || repassword.val().length == 0) {
			$('#bad-data-repassword').html('La <strong>Repetición de contraseña</strong> no puede estar en blanco o vacío.');
			datosCorrectos = false;
		} else {

			if (password.val() != repassword.val()) {
				$('#bad-data-repassword').html('La <strong>Repetición de contraseña</strong> y la <strong>Contraseña</strong> deben ser iguales.');
				datosCorrectos = false;
			} else {
				$('#bad-data-repassword').empty();
				datosCorrectos = true;
			}

		}

		// Comprobamos de nuevo si los datos han sido validados correctamente

		if (validarNombre() &&
			validarApellido1() &&
			validarApellido2() &&
			validarDni() &&
			validarDireccion() &&
			validarProvincia() &&
			validarLocalidad() &&
			validarTelefono() &&
			validarEmail() &&
			validarPassword() &&
			validarRepassword()
		) {
			return true;
		} else {
			return false;
		}

	});

});

function validarNombre() {

	// Nombre.
	let nombreInput = $('input[name="nombre"]');

	if (nombreInput.val() == null || nombreInput.val().length == 0) {
		$('#bad-data-nombre').html('El <strong>Nombre</strong> no puede estar en blanco o vacío.');
		return false;
	} else {
		$('#bad-data-nombre').empty();
		return true;
	}

}

function validarApellido1() {

	// Primer apellido.
	let primerApellido = $('input[name="apellido1"]');

	if (primerApellido.val() == null || primerApellido.val().length == 0) {
		$('#bad-data-apellido1').html('El <strong>Primer apellido</strong> no puede estar en blanco o vacío.');
		return false;
	} else {
		$('#bad-data-apellido1').empty();
		return true;
	}

}

function validarApellido2() {

	// Segundo apellido.
	let segundoApellido = $('input[name="apellido2"]');

	if (segundoApellido.val() == null || segundoApellido.val().length == 0) {
		$('#bad-data-apellido2').html('El <strong>Segundo apellido</strong> no puede estar en blanco o vacío.');
		return false;
	} else {
		$('#bad-data-apellido2').empty();
		return true;
	}

}

function validarDni() {

	// DNI.
	let dni = $('input[name="dni"]');

	if (dni.val() == null || dni.val().length == 0) {
		$('#bad-data-dni').html('El <strong>DNI</strong> no puede estar en blanco o vacío.');
		return false;
	} else {

		// Validar el formato del DNI.

		if (!/^([0-9]{8}[a-zA-Z]{1})$/.test(dni.val())) {
			$('#bad-data-dni').html('El <strong>DNI</strong> no tiene un formato válido.');
			return false;
		} else {
			$('#bad-data-dni').empty();
			return true;
		}

	}

}

function validarDireccion() {
	// Dirección.
	let direccion = $('input[name="direccion"]');

	if (direccion.val() == null || direccion.val().length == 0) {
		$('#bad-data-direccion').html('La <strong>Dirección</strong> no puede estar en blanco o vacía.');
		return false;
	} else {
		$('#bad-data-direccion').empty();
		return true;
	}
}

function validarProvincia() {

	// Provincia.
	let provincia = $('select#provincia');

	if (provincia.val().length == 0) {
		$('#bad-data-provincia').html('La <strong>Provincia</strong> no puede estar en blanco o vacío.');
		return false;
	} else {
		$('#bad-data-provincia').empty();
		return true;
	}

}

function validarLocalidad() {
	// Localidad.
	let localidad = $('input[name="localidad"]');

	if (localidad.val() == null || localidad.val().length == 0) {
		$('#bad-data-localidad').html('La <strong>Localidad</strong> no puede estar en blanco o vacía.');
		return false;
	} else {
		$('#bad-data-localidad').empty();
		return true;
	}
}

function validarTelefono() {
	// Teléfono.
	let telefono = $('input[name="telefono"]');

	if (telefono.val() == null || telefono.val().length == 0) {
		$('#bad-data-telefono').html('El <strong>Teléfono</strong> no puede estar en blanco o vacío.');
		datosCorrectos = false;
	} else {

		// Validar formato del teléfono:

		if (!/^\+{1,1}[1-9]+\s?\d+$|^\d+\s?\d+$/.test(telefono.val())) {
			$('#bad-data-telefono').html('El <strong>Teléfono</strong> no tiene un formato válido.');
			return false;
		} else {
			$('#bad-data-telefono').empty();
			return true;
		}

	}

}

function validarEmail() {
	// Email.
	let email = $('input[name="email"]');

	if (email.val() == null || email.val().length == 0) {
		$('#bad-data-email').html('El <strong>Email</strong> no puede estar en blanco o vacío.');
		return false;
	} else {

		if (!/[a-zA-Z0-9_]+([.][a-zA-Z0-9_]+)*@[a-zA-Z0-9]+([.][a-zA-Z0-9]+)*[.][a-zA-Z]{1,5}/.test(email.val())) {
			$('#bad-data-email').html('El <strong>Email</strong> no tiene un formato válido.');
			return false;
		} else {
			$('#bad-data-email').empty();
			return true;
		}

	}
}

function validarPassword() {
	// Contraseña.
	let password = $('input[name="password"]');

	if (password.val() == null || password.val().length == 0) {
		$('#bad-data-password').html('La <strong>Contraseña</strong> no puede estar en blanco o vacía.');
		return false;
	} else {

		if (password.val().length < 4) {
			$('#bad-data-password').html('La longitud de la <strong>Contraseña</strong> debe ser mínimo de cuatro caracteres.');
			return false;
		} else {
			$('#bad-data-password').empty();
			return true;
		}

	}
}

function validarRepassword() {
	// Repetir contraseña.
	let repassword = $('input[name="repassword"]');

	if (repassword.val() == null || repassword.val().length == 0) {
		$('#bad-data-repassword').html('La <strong>Repetición de contraseña</strong> no puede estar en blanco o vacío.');
		return false;
	} else {

		if (password.val() != repassword.val()) {
			$('#bad-data-repassword').html('La <strong>Repetición de contraseña</strong> y la <strong>Contraseña</strong> deben ser iguales.');
			return false;
		} else {
			$('#bad-data-repassword').empty();
			return true;
		}

	}
}