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
			'system-logoff': 'loginform'
		},
		
		loginform: function() {
			nav.toPage({url: 'loginform.html'});
		},

		welcome: function() {
			nav.toPage({url: 'welcome.html'});
		},

		documents: function(a, b, c) {
			setActive('documents');
			nav.toSection({url: 'documents.html'});
		},

		workflow: function() {
			setActive('workflow');
			nav.toSection({url: 'workflow.html'});
		},

		social: function() {
			setActive('social');
			nav.toSection({url: 'social.html'});
		},

		security: function() {
			setActive('security');
			nav.toSection({url: 'security.html'});
		}

	}),

	routes = new Routes();
	backbone.history.start();
});