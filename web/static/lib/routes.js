/*
Possui as rotas de hash para o history API
Evita que refresh e back do browser volte para a home
*/
require(['backbone', 'nav'], function(backbone, nav) {

	function setActive(hashname) {
		$('.top-menu .active').removeClass('active');
		$('li a[href="#' + hashname + '"]').parents('li').addClass('active');
	}

	var Routes = backbone.Router.extend({

		routes: {
			'loginform': 'loginform',
			'': 'loginform',
			'welcome': 'welcome',
			'documents': 'documents',
			'workflow': 'workflow',
			'social': 'social',
			'security': 'security',
			'system-logoff': 'loginform',
			'dologin': 'dologin'
		},
		
		loginform: function() {
			nav.toPage({url: '/loginform'});
		},

		welcome: function() {
			nav.toPage({url: '/welcome'});
		},

		documents: function(a, b, c) {
			setActive('documents');
			nav.toSection({url: '/documents'});
		},

		workflow: function() {
			setActive('workflow');
			nav.toSection({url: '/workflow'});
		},

		social: function() {
			setActive('social');
			nav.toSection({url: '/social'});
		},

		security: function() {
			setActive('security-fake');
			nav.toSection({url: '/security'});
		},

		dologin: function() {
			var email = $('#email').val(),
				password = $('#password').val(),
				url = '/dologin/' + email + '/' + password,
				that = this;

			$.ajax({
				url: url,
				type: 'POST',
				data: {lero: email, laro: password}
			})
			.done(function(data) {
				if(data.success) {
					location = '#welcome';
				} else {
					alert('usuario e senha invalidos. must be adm@foo.com / adm');
					location = '#loginform';
				}
			})
			.error(function() {
				console.log('due merda');
			});
		}

	}),

	routes = new Routes();
	backbone.history.start();
});