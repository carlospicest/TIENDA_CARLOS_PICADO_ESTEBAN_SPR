$(function() {

	// Comprobar botones de ver información (para asignar evento modal).

	const buttonList = $('a[class="product_modal"]');

	for (let button of buttonList) {

		$(button).click(() => {

			const idProduct = $(button).attr('id');

			$.ajax({
				url: 'producto_show',
				type: 'GET',
				data: {
					idProduct: idProduct,
				},
				success: (data) => {

					showProductInformation(data);

				}
			});

		});

	}

	// Eventos para los botones de sumar, restar y el input de cantidad.
	
	const minusButton = $('#modalProductMinus');
	const plusButton = $('#modalProductPlus');
	const stackInput = $('#modalProductStack');
	const addCartButton = $('#modalAddCart');

	minusButton.click(() => {

		let currentQuantity = parseInt(stackInput.val());

		if (currentQuantity > 1) {

			currentQuantity--;

			stackInput.val(currentQuantity);

		}

	});

	plusButton.click(() => {

		let currentQuantity = parseInt(stackInput.val());

		currentQuantity++;

		stackInput.val(currentQuantity);

	});

	// Evento para el botón de agregar al carrito.

	addCartButton.click(() => {

		// Obtenemos número de unidades y el id del producto.
		
		const idProduct = addCartButton.prop('value');
		const stack = stackInput.val();
		
		addSimpleProductCart(idProduct, stack);
		
		$('#exampleModal').modal('hide');

	});


});



function showProductInformation(productData) {

	console.log(productData);

	// Cargamos la información del producto dentro del modal.

	const productName = $('#productName');
	const productValorations = $('#productValorations');
	const productStock = $('#productStock');
	const productPrice = $('#productPrice');
	const productDescription = $('#productDescription');
	const addCartButton = $('#modalAddCart');
	addCartButton.attr('value', productData.id);
	
	// Establecer los valores.

	productName.html(productData.nombre);
	productValorations.html(0);
	productStock.html('available');
	productPrice.html(productData.precio + " €");
	productDescription.html(productData.descripcion);

	// Mostramos el formulario.

	//$('#exampleModal').modal('show');

}