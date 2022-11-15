$('.input-group i').click(function() {

	let icon = $(this).prop('class');

	if (icon.indexOf('eye') != -1) {

		// Comprobamos si el icono del ojo está en visibilidad o no.

		if (icon.indexOf('eye-slash-fill') != -1) {
			$(this).prop('type', 'text');
			$(this).prop('class', 'bi bi-eye-fill');
		} else {
			$(this).prop('type', 'text');
			$(this).prop('class', 'bi bi-eye-slash-fill');
		}

	}

});