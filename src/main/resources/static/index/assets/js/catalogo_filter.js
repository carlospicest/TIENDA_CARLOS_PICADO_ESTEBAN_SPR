$(function() {

	setMinMaxSlider(0, 10000); // Inicializamos el slider de precio con unos valores.

	// Evento que se lanza cuando el slider ha sido modificado (barra con los dos puntos encima del input).

	$("#slider-range").on("slidestop", function(event, ui) {
		prepareFilter();
	});

	// Evento que establece el valor min y max del Slider cuando el usuario establece los límites en el input.

	$('#amount').change(function() {

		let amount = $(this).val();
		let values = amount.match(/[0-9]+/g);

		let min = values[0];
		let max = values[1];

		setMinMaxSlider(min, max);

		prepareFilter();

	});

	// Eventos de los checkbox de Categorías:

	$('input[type="checkbox"], [class="category"]').change(function() {
		prepareFilter();
	});

	// Eventos de los botones de Ordenación:

	$('button.sortCriteria').click(function() {

		if ($(this).attr('class').indexOf('criteria_active') === -1) {
			$(this).addClass('criteria_active');
		} else {
			$(this).removeClass('criteria_active');
		}

		// Solo puede permanecer activo un criterio de ordenación.

		let buttons = $('button.sortCriteria');

		Object.keys(buttons).forEach((e) => {

			const btn = $(buttons[e]);

			if (btn.prop('id') !== undefined) {
				if (btn.prop('id') !== $(this).prop('id')) {
					btn.removeClass('criteria_active');
				}
			}

		});

		prepareFilter();

	});

	// Evento para el botón de borrar filtros.

	$('#clearFilter').click(() => {
		clearFilters();
		prepareFilter();
	});

});

/*
	Establece los valores de precio mínimo y máximo que indique
	el usuario.
 */

function setMinMaxSlider(min, max) {

	// Validar si los valores de min y max están definidos.

	if (min === undefined || max === undefined) {

		$("#amount").val(null);

	} else {

		min = parseInt(min);
		max = parseInt(max);

		$("#slider-range").slider({
			range: true,
			min: min,
			max: max,
			values: [min, max],
			slide: function(event, ui) {
				$("#amount").val(ui.values[0] + " € - " + ui.values[1] + " €");
			}
		});

		$("#amount").val($("#slider-range").slider("values", 0) +
			" € - " + $("#slider-range").slider("values", 1) + " €");

	}

}

/*
	Elimina los filtros de categorías y de ordenación.
 */

function clearFilters() {

	// Filtro de categorías.

	const categories = $('input[type="checkbox"][name="category"]:checked');

	Object.keys(categories).forEach((e) => {

		const element = $(categories[e]);

		if (element !== undefined && element !== null) {
			element.prop("checked", false);
		}

	});

	// Filtro de ordenación.

	const buttons = $('button.sortCriteria');

	Object.keys(buttons).forEach((e) => {

		const btn = $(buttons[e]);

		if (btn.prop('id') !== undefined) {
			btn.removeClass('criteria_active');
		}

	});

}

/*
	Función que genera un nuevo filtro.
*/

function buildFilter() {

	// Elementos de filtro.

	let criteria = {

		categories: null,
		price: null,
		sort: null

	};

	return criteria;

}

/*
	Devuelve un array con los id's de las categorías seleccionadas.
 */

function getIdCategorySelected() {

	const categories = $('input[type="checkbox"][name="category"]:checked');

	const elementId = new Array();

	Object.keys(categories).forEach((e) => {

		const id = $(categories[e]).prop('id');

		if (id !== undefined && id !== null) {
			elementId.push(parseInt(id));
		}

	});

	return elementId;

}

/*
	Devuelve el rango de precio mínimo y máximo establecido por el usuario.
 */

function getPrice() {

	const priceValues = $("#slider-range").slider("option", "values");

	let price = {}

	if (priceValues !== null && priceValues.length == 2) {

		price = {
			min: priceValues[0],
			max: priceValues[1]
		}

	}

	return price;

}

/*
	Devuelve el criterio de ordenación que está activo.
 */

function getSortCriteria() {

	let sortCriteria = null;
	let buttons = $('button.sortCriteria');

	Object.keys(buttons).forEach((e) => {

		const btn = $(buttons[e]);

		if (btn.prop('id') !== undefined) {
			if (btn.attr('class').indexOf('criteria_active') !== -1) {
				let idCriteria = btn.prop('id');

				switch (idCriteria) {

					case 'lowest_price':

						sortCriteria = 0;
						break;

					case 'highest_price':

						sortCriteria = 1;
						break;

					case 'best_sellers':

						sortCriteria = 2;
						break;

					case 'top_rated':

						sortCriteria = 3;
						break;

				}

			}
		}

	});

	return sortCriteria;

}

/*
	Permite preparar un filtro con los ajustes establecidos y enviar
	la solicitud al servlet para obtener la información resultante.
 */

function prepareFilter() {
	
	clearProductContainer();
	
	let criteria = buildFilter();

	criteria.categories = getIdCategorySelected();
	criteria.price = getPrice();
	criteria.sort = getSortCriteria();

	// Obtenemos JSON de criteria.

	const criteriaJSON = JSON.stringify(criteria);

	// Enviamos la información al servlet.

	$.ajax({

		url: 'catalogo_filter',
		type: 'POST',
		data: {
			filter: criteriaJSON
		},
		success: (data) => {
			if (data !== undefined && data !== null) {
				fillProductsCatalog(data.products);
			}
		}

	});

}

/*
	Limpia el contenedor de productos del html.
 */

function clearProductContainer() {

	const catalogContainer = $('#productsList');

	emptyHtml(catalogContainer);

}

/*
	Permite agregar a la vista de catálogo los artículos obtenidos
	desde el servlet.
 */

function fillProductsCatalog(products) {

	const catalogContainer = $('#productsList');

	if (products !== undefined && products !== null) {

		products.forEach(product => {
			paintProduct(catalogContainer, product);
		});

	}

	// Evento para los botones de agregar producto.
	
	$('input[name="addCart"]').click(function() {
		const idProduct = $(this).prop('id');
		addSimpleProductCart(idProduct, 1);
	});

}

/*
	Agrega un elemento producto con su respectiva información
	en un contenedor de productos.
 */

function paintProduct(container, product) {

	let productTemplate = '<div class="col-lg-4 col-md-6 col-12">' +
		'<div class="single-product">' +
		'<div class="product-img">' +
		'<a href="product-details.html"> <img class="default-img"' +
		'src="https://cdn.iconscout.com/icon/premium/png-256-thumb/best-product-3102470-2583790.png" alt="#">' +
		'<img class="hover-img" src="https://cdn.iconscout.com/icon/premium/png-256-thumb/best-product-3102470-2583790.png"' +
		'alt="#"> <span class="new">New</span> <!--<span class="price-dec">30% Off</span> Para artículos en descuento -->' +
		'<!--<span class="out-of-stock">Hot</span> Para artículos sin stock -->' +
		'</a>' +
		'<div class="button-head">' +
		'<div class="product-action">' +
		'<a title="Wishlist" href="#"><i class=" ti-heart "> </i><span>Añadir a lista de deseos</span></a>' +
		'</div>' +
		'<div class="product-action-2">' +
		'<input type="button" name="addCart" id="' + product.id + '" class="btn" value="Añadir al carrito">' +
		'</div>' +
		'</div>' +
		'</div>' +
		'<div class="product-content">' +
		'<h3>' +
		'<a href="product-details.html">' + product.nombre + '</a>' +
		'</h3>' +
		'<div class="product-price">' +
		'<span>' + product.precio + ' €</span>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>';

	container.append(productTemplate);

}
