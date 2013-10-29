/*
A ideia deste modulo e servir para navegação de paginas, assumindo que
este é um Single Page App.
*/
define(['jquery'], function($) {

	var mainContext = $('#main-context');

	/*
	Este metodo substitui o div principal main-context
	*/
	function toPage(options, isSection) {

		var navDiv = mainContext;

		if(isSection) {
			navDiv = $('#module-section');
		}

		if(!options) options = {};

		$.ajax({
			url: options.url,
			cache: false
		})
		.done(function(data) {
			navDiv.fadeOut(80, function() {
				$(this).empty();
				$(this).html(data).fadeIn(80, function() {
					if(options.cb) options.cb(null, data);
				});
			});
		})
		.fail(function(a, b, c) {
			if(options.cb) options.cb(a);
		});
	}

	/*
	Este modulo substitui o div module-section 
	*/
	function toSection(options) {

		/*
		Se o usuario der refresh na pagina, eh preciso carregar
		a pagina welcome antes de chamar a section, senão a tela fica em branco
		*/
		if(!$('#module-section').length) {
			toPage({
				url: 'welcome.html',
				cb: function() {
					toPage(options, true);
				}
			});
		} else {
			toPage(options, true);
		}
	}

	return {
		toPage: toPage,
		toSection: toSection
	};
});