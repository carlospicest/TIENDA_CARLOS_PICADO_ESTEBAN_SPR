$('.input-group i').click(function() {

	const inputPassword = $(this).parent().parent().find('input');
	
	if (inputPassword.prop('type') === 'password') {
		inputPassword.prop('type', 'text');
		$(this).prop('class', 'bi bi-eye-slash-fill');
	} else {
		inputPassword.prop('type', 'password');
		$(this).prop('class', 'bi bi-eye-fill');
	}
	
});