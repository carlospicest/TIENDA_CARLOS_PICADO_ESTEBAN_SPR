$(function() {
	cargarDatatables();
});

function cargarDatatables() {

	$.ajax({
		url: '/dashboard/cancelaciones_pedidos/show',
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

				$('#tablaCancelacionesPedidos').DataTable({
					dom: "QBfrltip",
					autoWidth: true,
					data: data,
					"columns": [
						{ "data": "num_solicitud" },
						{ "data": "fecha" },
						{ "data": "estado" },
						{
							targets: [2],
							"render": function(data, type, row, meta) {
								return '<div class="text-center">' +
									'<button name="procesarCancelacionPedido" class="btn btn-warning mr-2" id="' + row.id + '" href="#"><i class="bi bi-box-seam-fill" style="font-size: 1.2vw;"></i></button>' +
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
								columns: [0, 1, 2], // Ocultar la columna 11 (Acciones) al exportar.
							},
						},
						{
							extend: "excelHtml5",
							title: "Listado de cancelaciones de pedidos",
							exportOptions: {
								columns: [0, 1, 2],
							},
						},
						{
							extend: "pdfHtml5",
							orientation: 'landscape',
							pageSize: 'LEGAL',
							title: "Listado de cancelaciones de pedidos",
							exportOptions: {
								columns: [0, 1, 2],
							},
						},

						{
							extend: "print",
							title: "Listado de cancelaciones de pedidos",
							exportOptions: {
								columns: [0, 1, 2],
							},
						},
						{
							// Botón para elegir las columnas que se quieren mostrar en la tabla.
							extend: "colvis",
						},
					],

				});

				// Eventos para los botones de editar y eliminar.

				$('button[name="procesarCancelacionPedido"]').click(function(e) {

					const idCancelacionPedido = $(this).prop("id");

					window.location.href = "/dashboard/cancelaciones_pedidos/procesar/" + idCancelacionPedido;

				});

			});

		}

	});

















}