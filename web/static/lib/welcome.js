require(['dropdown', 'collapse'], function(dd, coll) {

	$.ajax({
		url: '/security/session'
	})
	.done(function(data) {
		console.log(data);
	})
	.error(function(a, b, c) {
		console.log('err', a, b, c);
	});

});