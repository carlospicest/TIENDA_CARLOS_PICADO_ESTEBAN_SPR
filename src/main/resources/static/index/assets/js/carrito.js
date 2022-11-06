$(function() {

	getCartDetail();
	
	// Asignamos evento al botón de Agregar carrito.
	
	$("input[name='addCart']").click(function() {
		
		const idProduct = $(this).attr("id");
		
		$.ajax({
			url : 'carrito_add',
			data : {
				idProduct : idProduct,
				stack : 1
			},
			success : function(data) {
				
				if (data !== null) {

					// Una vez obtenidos los datos, refrescamos carrito.
					refreshProductCart(data);
					
				}
				
			}
		});
		
	});

});