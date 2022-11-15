// Wait for the DOM to be ready
$(function() {
	// Initialize form validation on the registration form.
	// It has the name attribute "registration"
	$("form[name='login']").validate({
		// Specify validation rules
		rules: {
			email: {
				required: true,
				// Specify that email should be validated
				// by the built-in "email" rule
				email: true
			},
			password: {
				required: true,
				minlength: 4
			}
		},
		// Specify validation error messages
		messages: {
			password: {
				required: "Proporcione una contraseña válida.",
				minlength: "La contraseña debe tener una longitud mínima de 4 caracteres."
			},
			email: "Proporcione una dirección de email válida."
		},
		// Make sure the form is submitted to the destination defined
		// in the "action" attribute of the form when valid
		submitHandler: function(form) {
			form.submit();
		}
	});
});