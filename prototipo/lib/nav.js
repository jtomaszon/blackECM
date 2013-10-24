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

			if(initialHash === 'loginform.html' || initialHash === 'welcome.html') {
				toPage({url: initialHash});
			} else {
				//se for uma section, vai para welcome, DEPOIS vai para a section
				toPage({url: 'welcome.html', cb: function() {
					toSection({url: initialHash});
				}});
			}
			return true;
		}

		return false;
	}

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

				location.hash = options.hash || options.url;
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
		toPage(options, true);
	}

	return {
		toPage: toPage,
		toSection: toSection,
		refreshSafe: refreshSafe
	};
});