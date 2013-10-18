requirejs.config({
	baseUrl: 'lib',
	urlArgs: 'nocache=' + Math.random()*1e3,

	paths: {
		jquery: 'jquery.min',
		bootstrap: 'bootstrap.min'
	},

	shim: {
		jquery: {

		},

		bootstrap: {
			deps: ['jquery']
		}
	}
});