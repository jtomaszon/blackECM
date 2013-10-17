requirejs.config({
	baseUrl: 'lib',
	urlArgs: 'nocache=' + Math.random()*1e3,

	paths: {
		jquery: '../bower_components/jquery/jquery.min',
		bootstrap: '../bower_components/bootstrap/dist/js/bootstrap.min'
	},

	shim: {
		jquery: {

		},

		bootstrap: {
			deps: ['jquery']
		}
	}
});