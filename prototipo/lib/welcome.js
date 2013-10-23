require(['jquery', 'nav'], function($, nav) {
	

	function logoff() {
		nav.toPage({
			url: 'loginform.html'
		});
	}

	$('body').on('click', '.system-logoff', logoff);


});