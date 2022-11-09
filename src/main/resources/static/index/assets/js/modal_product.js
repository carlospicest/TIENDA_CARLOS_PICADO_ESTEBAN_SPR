$(function() {

	// Comprobar botones de ver información (para asignar evento modal).

	$('a.details-product').click(function(e) {

		const idProduct = $(this).prop('id');

		getProductInformation(idProduct);

	})

	// Eventos para los botones de sumar, restar y el input de cantidad.

	const minusButton = $('#modalProductMinus');
	const plusButton = $('#modalProductPlus');
	const stackInput = $('#modalProductStack');
	const addCartButton = $('button[name="modalAddCart"]');

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

		const idProduct = addCartButton.prop('id');
		const stack = stackInput.prop('value');

		//addSimpleProductCart(idProduct, stack);
		updateProductCartTable(idProduct, stack, 'MASIVE');

		showSuccessToastr('Nuevo producto en el carrito', $('#productName').text() + ' x' + stack);

		$('#exampleModal').modal('hide');

	});


});


function getProductInformation(idProduct) {

	$.ajax({
		url: '/producto/show/' + idProduct,
		type: 'GET',
		data: {
			idProduct: idProduct,
		},
		success: (data) => {

			showProductInformation(data);

		}
	});

	return false; // Para evitar el desplazamiento al top.

}


function showProductInformation(productData) {

	console.log(productData);

	// Cargamos la información del producto dentro del modal.

	const productName = $('#productName');
	const productValorations = $('#productValorations');
	const productStock = $('#productStock');
	const productPrice = $('#productPrice');
	const productDescription = $('#productDescription');
	const productStack = $('#modalProductStack');
	const addCartButton = $('button[name="modalAddCart"]');
	addCartButton.attr('id', productData.id);

	// Establecer los valores.

	productName.html(productData.nombre);
	productValorations.html(0);
	productStock.html('available');
	productPrice.html(productData.precio + " €");
	productDescription.html(productData.descripcion);
	productStack.val(1);
	
	// Mostrar la imagen
	
	const containerImages = $('.quickview-slider-active');
	
	$(containerImages).empty();
	$(containerImages).append('<div class="single-slider">' + '<img class="img-slider" src="/index/images/productos/' + productData.id + '/' + productData.id + '.jpg"' + '</div>');

	// Mostramos el formulario.

	$('#exampleModal').modal('show');

}