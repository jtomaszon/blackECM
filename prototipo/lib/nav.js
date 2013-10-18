/*
A ideia deste modulo e servir para navegação de paginas, assumindo que
este é um Single Page App.
*/
define(['jquery'], function($) {

	var mainContext = $('#main-context');

	/*
	Este metodo substitui o div principal main-context
	*/
	function toPage(options) {

		if(!options) options = {};

		$.ajax({
			url: options.url,
			beforeSend: function() {
			}
		})
		.done(function(data) {

			mainContext.fadeTo('fast', 0, function() {
				$(this).empty();
				mainContext.html(data).fadeTo('fast', 1);
			});
			
		})
		.fail(function(a, b, c) {
			options.cb(a);
		})
		.always(function() {

		});
	}	

	return {
		toPage: toPage
	};
});