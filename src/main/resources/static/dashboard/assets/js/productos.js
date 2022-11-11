$(function() {
	cargarDatatables();
});

function cargarDatatables() {

	$.ajax({
		url: '/dashboard/productos/show',
		type: 'GET',
		success: (data) => {

			$.when(data).done(function(data) {

				// Obtenemos los datos del controlador.
				// Mostrar en formato legible la información de baja y fecha de alta.

				data.forEach(function(element) {

					// Baja.

					if (element.baja) {
						element.baja = 'Inactivo';
					} else {
						element.baja = 'Activo';
					}

					// Fecha de alta.

					let fechaAlta = element.fecha_alta;
					fechaAlta = moment(fechaAlta).format('DD-MM-YYYY');
					element.fecha_alta = fechaAlta;

				});

				// Inicializamos datatables.

				$('#tablaProductos').DataTable({
					dom: "QBfrltip",
					autoWidth: true,
					data: data,
					"columns": [
						{ "data": "categoria.nombre" },
						{ "data": "nombre" },
						{ "data": "precio" },
						{ "data": "stock" },
						{ "data": "impuesto" },
						{ "data": "baja" },
						{ "data": "fecha_alta" },
						{
							targets: [2],
							"render": function(data, type, row, meta) {
								return '<div class="text-center">' +
									'<button name="editarProducto" class="btn btn-success mr-2" id="' + row.id + '" href="#"><i class="bi bi-pencil" style="font-size: 1.2vw;"></i></button>' +
									'<button name="bajaProducto" class="btn btn-danger" id="' + row.id + '" href="#"><i class="bi bi-box-arrow-in-down" style="font-size: 1.2vw;"></i></button>' +
									'</div>';
							}
						}
					],
					destroy: true,
					// Botones que se mostrarán en la tabla.
					buttons: [
						"selectAll",
						"selectNone",
						{
							extend: "copyHtml5",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5], // Ocultar la columna 11 (Acciones) al exportar.
							},
						},
						{
							extend: "excelHtml5",
							title: "Listado de productos",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5],
							},
						},
						{
							extend: "pdfHtml5",
							orientation: 'landscape',
							pageSize: 'LEGAL',
							title: "Listado de productos",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5],
							},
						},

						{
							extend: "print",
							title: "Listado de productos",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5],
							},
						},
						{
							// Botón para elegir las columnas que se quieren mostrar en la tabla.
							extend: "colvis",
						},
					],

				});

				// Eventos para los botones de editar y eliminar.

				$('button[name="nuevoProducto"]').click(function(e) {

					window.location.href = "/dashboard/productos/agregar";

				});

				$('button[name="editarProducto"]').click(function(e) {

					const idProducto = $(this).prop("id");

					window.location.href = "/dashboard/productos/editar/" + idProducto;

				});

				$('button[name="bajaProducto"]').click(function(e) {

					const idProducto = $(this).prop("id");

					if (confirm('¿Dar de baja el producto seleccionado?')) {
						window.location.href = "/dashboard/productos/baja/" + idProducto;
					}

				});

			});

		}

	});

















}