require(['config'], function() {
	require(['jquery', 'nav'], function($, nav) {

		$('#main-context').on('click', '.btn-login', function(ev) {
			ev.preventDefault();
			nav.toPage({url: 'welcome.html'});
		});
	});
});