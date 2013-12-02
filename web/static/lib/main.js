require(['config'], function() {
	require(['routes', 'jquery', 'nav'],
		function(routes, $, nav) {


            $.ajax({
                url: '/security/session',
                type: 'POST'
            })
            .done(function(data) {
                console.log('retorno do json qdo eu solicito uma sessão', data);
            })
            .error(function() {
                console.log('err');
            });

	});
});