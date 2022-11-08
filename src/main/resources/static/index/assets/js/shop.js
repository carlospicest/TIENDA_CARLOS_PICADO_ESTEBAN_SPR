$(function() {

	// Establecer configuración de las notificaciones del plugin Toastr.

	$('.addCart').click(function() {
		const idProduct = $(this).attr('id');
		addSimpleProductCart(idProduct, 1);
		return false; // Evita que al pulsar el enlace, la página se desplaze hasta el principio.`
	});


	// Asignamos evento al botón de Agregar carrito (Del catálogo).

	$("input[name='addCart']").click(function() {
		const idProduct = $(this).attr('id');
		addSimpleProductCart(idProduct, 1);
	});

	// Obtenemos la información de los productos del carrito.

	$.ajax({

		url: '/carrito/show',
		type: 'GET',
		success: (data) => {

			fillIncludedCart(data); // Actualizamos la información del carrito acoplado en el header.
			fillCartTable(data); // Actualizamos la información de la tabla carrito (si nos encontramos en la vista carrito).

		}

	});

});

/* FUNCIONES PARA EL CARRITO DEL HEADER */

/* 
	Agrega un único producto al carrito. Esta función
	es utilizada por los diversos botones de Añadir al carrito de las
	distintas vistas en las que se muestren productos.
*/

function addSimpleProductCart(idProduct, stack) {

	$.ajax({
		url: 'carrito/update',
		type: 'POST',
		data: {
			idProduct: idProduct,
			stack: 1,
			mode: 'PLUS'
		},
		success: (data) => {



			let productName = getProductName(idProduct);
			
			$.when(productName).done(function(data) {

				// Cuando se obtenga el nombre del producto lanzamos la notificación de toastr.

				if (data !== null) {
					updateProductCart();
					showSuccessToastr('Nuevo producto en el carrito', data.nombre + ' x' + stack);
				}

			});

		}

	});

}

/*
	Función que consulta y muestra los productos del carrito
	del usuario.
 */

function updateProductCart() {

	$.ajax({

		url: 'carrito/show',
		type: 'GET',
		success: (data) => {
			fillIncludedCart(data); // Actualizamos la información del carrito acoplado en el header.
		}

	});

}

/*
	Realiza una petición para obtener los artículos que el cliente
	tiene en su carrito y los incluye en el carrito acoplado al header.
 */

function fillIncludedCart(data) {

	if (data !== null) {

		const includedCartElement = $('div[class="sinlge-bar shopping"]');

		if ($(includedCartElement).length === 1) {

			const includedCart = {
				totalCount: $('.total-count'), // Cantidad total de productos.
				shopArticles: $('.shopping-articles'), // Cantidad total de productos.
				cartListElement: $('.shopping-list'), // Lista de los productos.
				totalAmmount: $('.total-amount-cart') // Precio total de los productos.
			};

			$(includedCart.totalCount).html(data.totalProduct);

			$(includedCart.shopArticles).html(data.totalProduct);

			// Limpiar el contenido del carrito.

			emptyHtml(includedCart.cartListElement);

			// Agregar elementos con cada artículo.

			data.products.forEach(product => {

				includedCart.cartListElement.append('<li>' +
					'<a href="#" id="remove_cart_product_' + product.producto.id + '" class="remove" title="Eliminar artículo">' +
					'<i class="fa fa-remove"></i>' +
					'</a> <a class="cart-img" href="#">' +
					'<img src="https://via.placeholder.com/70x70" alt="#"></a>' +
					'<h4><a href="#"> ' + product.producto.nombre + '</a></h4>' +
					'<p class="quantity">' + product.unidades + 'x - <span class="amount"> ' +
					product.precio_unidad + ' €</span></p></li>');


				// Establecer evento para el eliminado.

				const deleteProduct = $('#remove_cart_product_' + product.producto.id);

				deleteProduct.click(() => {

					if (confirm('¿Está seguro que quiere eliminar el producto del carrito?')) {
						deleteProductCart(product.producto.id);
					}

				});

			});

			$(includedCart.totalAmmount).html(data.totalAmmount + ' €');

			// Si nos encontramos en la vista carrito, actualizar el importe total de la tabla.

			const cartTable = $('table, .table shopping-summery');
			const cartResumeTotalAmount = $('#cart_resume_total_amount');

			if (cartTable.length === 1) {

				// Estamos en la vista de la tabla carrito, incluimos la información.
				cartResumeTotalAmount.html(includedCart.totalAmmount);
			}

		}

	}


}


/* FUNCIONES PARA LA TABLA DEL CARRITO (VISTA) */


/*
	Función usada por la tabla de la vista carrito que permite actualizar
	el estado de los productos que se encuentran en el carrito.
	Permitiendo Incrementar, Disminuir o introducir una cantidad exacta
	de unidades de un determinado producto.
 */

function updateProductCartTable(idProduct, stack, mode) {

	$.ajax({
		url: '/carrito/update',
		type: 'POST',
		data: {
			mode: mode,
			idProduct: idProduct,
			stack: stack
		},
		success: (data) => {

			

			console.log('Respuesta controlador => ', data);

			if (data !== null) {
				updateProductCart();
				updateQuantityTable(data);
			}

		}
	});

}

/*
	Rellena una tabla con la información detallada de los productos
	que se encuentran agregados al carrito.
 */


function fillCartTable(data) {

	const cartTable = $('#cartTable');

	if (cartTable.length === 1) {

		// Limpiamos el contenido de la tabla.

		const cartTableBody = $('#cartTable > tbody');
		emptyHtml(cartTableBody);

		// Comenzamos a rellenar la tabla con la información de los productos.

		if (data.products.length === 0) {
			showNoProductsCartTableMsg();
		} else {

			data.products.forEach(product => {
				addRowProductCartTable(product);
			});

		}

		// Actualizamos el importe total en el resúmen final.

		const totalAmmount = $('.resume_total_ammount');
		totalAmmount.html(data.totalAmmount + ' €');

	}

}

/* 
	Agrega a la tabla del carrito de productos un mensaje cuando
	no haya ningún producto.
*/

function showNoProductsCartTableMsg() {

	const cartTableBody = $('#cartTable > tbody');

	cartTableBody.append('<tr>' +
		'<td colspan="6" class="text-center">' +
		'<span>No hay productos en el carrito.</span>' +
		'</td>' +
		'</tr>');

}

/* Complementa a la función fillCartTable(data), permitiendo agregar
   filas a la tabla por cada producto.
*/

function addRowProductCartTable(product) {

	const idProduct = product.producto.id;

	const cartTableBody = $('#cartTable > tbody');

	cartTableBody.append('<tr id="' + idProduct + '">' +
		'<td class="image" data-title="No"><img src="https://via.placeholder.com/100x100" alt="#"></td>' +
		'<td class="product-des" data-title="Description">' +
		'<p class="product-name">' +
		'<a href="#"> ' + product.producto.nombre + '</a>' +
		'</p>' +
		'<p class="product-des"> ' + product.producto.descripcion + '</p>' +
		'</td>' +
		'<td class="price" data-title="Price"><span>' + product.precio_unidad + '</span></td>' +
		'<td class="qty" data-title="Qty">' +

		'<div class="input-group">' +
		'<div class="button minus">' +
		'<button type="button" class="btn btn-primary btn-number"' +
		'id="minus_product_' + idProduct + '">' +
		'<i class="ti-minus"></i>' +
		'</button>' +
		'</div>' +
		'<input type="text" id="product_' + idProduct + '" class="input-number"' +
		' data-min="1" value="' + product.unidades + '">' +
		'<div class="button plus">' +
		'<button type="button" class="btn btn-primary btn-number"' +
		'id="plus_product_' + idProduct + '">' +
		'<i class="ti-plus"></i>' +
		'</button>' +
		'</div>' +
		'</div>' +
		'</td>' +
		'<td data-title="Total"><span id="totalProductAmount_' + idProduct + '">' + (product.precio_unidad * product.unidades) + '</span></td>' +
		'<td class="action" data-title="Remove"><a href="#" id="delete_product_' + idProduct + '"><i class="ti-trash remove-icon"></i></a></td>' +
		'</tr>');

	// Capturar eventos para los botones de sumar, restar, input y eliminar.

	const inputQuantity = $('input#product_' + idProduct);
	const minusButton = $('#minus_product_' + idProduct);
	const plusButton = $('#plus_product_' + idProduct);
	const deleteButton = $('#delete_product_' + idProduct);

	minusButton.click(() => {

		let currentQuantity = parseInt(inputQuantity.val());

		if (currentQuantity > 1) {

			currentQuantity--;

			inputQuantity.val(currentQuantity);

			updateProductCartTable(idProduct, currentQuantity, 'MINUS');

		}

	});

	plusButton.click(() => {

		let currentQuantity = parseInt(inputQuantity.val());

		currentQuantity++;

		inputQuantity.val(currentQuantity);

		updateProductCartTable(idProduct, currentQuantity, 'PLUS');

	});

	inputQuantity.keyup((e) => {

		if (e.keyCode == 13) {

			let currentQuantity = parseInt(inputQuantity.val());

			updateProductCartTable(idProduct, currentQuantity, 'MASIVE');

		}

	});

	deleteButton.click(() => {

		if (confirm('¿Está seguro que quiere eliminar el producto del carrito?')) {
			deleteProductCart(idProduct);
		}

	});

}

/*
	Actualiza los datos de cantidad, total e Importe total de la tabla
	de la vista carrito sin la necesidad de recargar toda la tabla.
	
	Este método, a diferencia de fullCartTable solo modifica las cantidades.
	
 */

function updateQuantityTable(data) {

	console.log(data);

	const totalAmmount = $('#totalProductAmount_' + data.producto.id);
	const quantityInput = $('#product_' + data.producto.id);

	totalAmmount.html(data.total);
	quantityInput.val(data.unidades);

}



/*
	Funciones de uso general.
 */

/*
	Elimina un determinado producto.
	
	Si la eliminación se ha realizado, se actualiza el carrito incluído
	en el header.
	
	Y también la tabla del carrito si se encuentra en la vista.
	
 */

function deleteProductCart(product) {

	$.ajax({
		url: 'carrito_delete',
		type: 'POST',
		data: {
			idProduct: parseInt(product)
		},
		success: (data) => {

			if (data !== null) {
				if (data.result === true) {

					$('.table shopping-summery, tr#' + product).remove();
					updateProductCart();

					if ($('#cartTable > tbody > tr').length == 0) {
						showNoProductsCartTableMsg();
					}

				}
			}

		}
	});

}

/*
	Recibe un objeto que contenga los elementos del DOM de los cuales
	eliminaremos la inforación que se encuentra en el interior.
	
	Ej: data = {
		el1 : elementoDOM_1,
		el2 : elementoDOM_2
	}
	
 */

function emptyHtml(data) {
	if (data !== null) {
		Object.keys(data).forEach((e) => {
			$(data[e]).empty();
		});
	}
}

/*
	Funciones de productos.
 */

function getProductName(idProduct) {

	return $.ajax({

		url: 'producto/show/' + idProduct,
		type: 'GET',
		success: function(e) {
			//console.log(e);
		}

	});

}

/*
	Funciones para llamar a Toastr.
 */

function showSuccessToastr(title, msg) {
	toastr.success(msg, title);
}

function showErrorToastr(title, msg) {
	toastr.error(msg, title);
}
