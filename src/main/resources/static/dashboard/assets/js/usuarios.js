$(function() {
	cargarDatatables();
});

function cargarDatatables() {


	$.ajax({
		url: '/usuarios/show',
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

				$('#tablaUsuarios').DataTable({
					dom: "QBfrltip",
					autoWidth: true,
					data: data,
					"columns": [
						{ "data": "dni" },
						{ "data": "nombre" },
						{ "data": "apellido1" },
						{ "data": "apellido2" },
						{ "data": "email" },
						{ "data": "direccion" },
						{ "data": "provincia" },
						{ "data": "localidad" },
						{ "data": "telefono" },
						{ "data": "baja" },
						{ "data": "fecha_alta" },
						{
							targets: [2],
							"render": function(data, type, row, meta) {
								return '<div class="text-center">' +
									'<button name="editarUsuario" class="btn btn-success mr-2" id="' + row.id + '" href="#"><i class="bi bi-pencil" style="font-size: 1.2vw;"></i></button>' +
									'<button name="bajaUsuario" class="btn btn-danger" id="' + row.id + '" href="#"><i class="bi bi-box-arrow-in-down" style="font-size: 1.2vw;"></i></button>' +
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
								columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9], // Ocultar la columna 11 (Acciones) al exportar.
							},
						},
						{
							extend: "excelHtml5",
							title: "Listado de usuarios",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
							},
						},
						{
							extend: "pdfHtml5",
							orientation: 'landscape',
							pageSize: 'LEGAL',
							title: "Listado de usuarios",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
							},
						},

						{
							extend: "print",
							title: "Listado de usuarios",
							exportOptions: {
								columns: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
							},
						},
						{
							// Botón para elegir las columnas que se quieren mostrar en la tabla.
							extend: "colvis",
						},
					],

				});

				// Eventos para los botones de editar y eliminar.

				$('button[name="nuevoUsuario"]').click(function(e) {

					window.location.href = "/usuarios/agregar";

				});

				$('button[name="editarUsuario"]').click(function(e) {

					const idUsuario = $(this).prop("id");

					window.location.href = "/usuarios/editar/" + idUsuario;

				});

				$('button[name="bajaUsuario"]').click(function(e) {

					const idUsuario = $(this).prop("id");

					if (confirm('¿Dar de baja al usuario seleccionado?')) {
						window.location.href = "/usuarios/baja/" + idUsuario;
					}

				});

			});

		}

	});

















}