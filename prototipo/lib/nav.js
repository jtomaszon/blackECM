/*
A ideia deste modulo e servir para navegação de paginas, assumindo que
este é um Single Page App.
*/
define(['jquery'], function($) {

	var mainContext = $('#main-context'),
		initialHash = location.hash.substr(1);

	//se o usuario deu refresh na pagina, verifica se ha uma url no location.hash
	//e tenta redirecionar para aquela pagina
	function refreshSafe() {
		if(initialHash.trim()) {
			toPage({url: initialHash});
			return true;
		}

		return false;
	}

	/*
	Este metodo substitui o div principal main-context
	*/
	function toPage(options) {

		if(!options) options = {};

		$.ajax({
			url: options.url
		})
		.done(function(data) {
			mainContext.fadeOut(80, function() {
				$(this).empty();
				$(this).html(data).fadeIn(80);
				location.hash = options.hash || options.url
			});
		})
		.fail(function(a, b, c) {
			options.cb(a);
		});
	}

	return {
		toPage: toPage,
		refreshSafe: refreshSafe
	};
});