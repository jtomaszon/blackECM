requirejs.config({
	baseUrl: '/static/lib',
	urlArgs: 'nocache=' + Math.random()*1e3,

	paths: {
		jquery: 'jquery.min',
		bootstrap: 'bootstrap.min',
		backbone: 'backbone-min',
		underscore: 'underscore-min',
		mustache: 'mustache'
	},

	shim: {
		jquery: {

		},

		bootstrap: {
			deps: ['jquery']
		},

		backbone: {
			deps: ['underscore'],
			exports: 'Backbone'
		},

		mustache: {
			exports: 'Mustache'
		}
	}
});