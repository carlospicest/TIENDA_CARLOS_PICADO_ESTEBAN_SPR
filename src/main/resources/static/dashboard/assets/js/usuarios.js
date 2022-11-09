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
                            data: null,
                            className: "center",
                            defaultContent: '<a href="">Edit</a> / <a href="" class="editor_remove">Delete</a>'
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

			});

		}

	});

















}