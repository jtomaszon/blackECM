require(['config'], function() {
	require(['jquery', 'nav'], function($, nav) {

		//carrega a div com o form de login 
		nav.toPage({url: 'loginform.html'});


		$('#main-context').on('click', '.btn-login', function(ev) {
			ev.preventDefault();
			nav.toPage({url: 'welcome.html'});
		});
	});
});