$(function() {
	cargarDatatables();
});

function cargarDatatables() {

	$.ajax({
		url: '/pedidos/show',
		type: 'GET',
		success: (data) => {

			$.when(data).done(function(data) {

				// Obtenemos los datos del controlador.
				// Mostrar en formato legible la información de baja y fecha de alta.

				data.forEach(function(element) {

					// Fecha.

					let fecha = element.fecha;
					fecha = moment(fecha).format('DD-MM-YYYY');
					element.fecha = fecha;

				});

				// Inicializamos datatables.

				$('#tablaPedidos').DataTable({
					dom: "QBfrltip",
					autoWidth: true,
					data: data,
					"columns": [
						{ "data": "num_factura" },
						{ "data": "metodo_pago" },
						{ "data": "fecha" },
						{ "data": "total" },
						{ "data": "estado" },
						{
							targets: [2],
							"render": function(data, type, row, meta) {
								return '<div class="text-center">' +
									'<button name="procesarPedido" class="btn btn-warning mr-2" id="' + row.id + '" href="#"><i class="bi bi-box-seam-fill" style="font-size: 1.2vw;"></i></button>' +
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
							title: "Listado de pedidos",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5],
							},
						},
						{
							extend: "pdfHtml5",
							orientation: 'landscape',
							pageSize: 'LEGAL',
							title: "Listado de pedidos",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5],
							},
						},

						{
							extend: "print",
							title: "Listado de pedidos",
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

				$('button[name="procesarPedido"]').click(function(e) {

					const idPedido = $(this).prop("id");

					window.location.href = "/pedidos/procesar/" + idPedido;

				});

			});

		}

	});

















}