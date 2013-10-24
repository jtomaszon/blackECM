require(['config'], function() {
	require(['jquery', 'nav', 'eventbinder'], function($, nav, eventbinder) {

		if(!nav.refreshSafe()) {
			//carrega a div com o form de login 
			nav.toPage({url: 'loginform.html'});
		}

	});
});