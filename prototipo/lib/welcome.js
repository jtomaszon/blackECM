require(['jquery', 'nav'], function($, nav) {
	

	function logoff() {
		location.href = '/prototipo';
	}

	$('body').on('click', '.system-logoff', logoff);


});