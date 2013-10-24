//todos os binds devem estar aqui
require(['jquery','nav'], function($, nav) {

	function login(ev) {
		ev.preventDefault();
		nav.toPage({url: 'welcome.html'});
	}

	function logoff() {
		nav.toPage({url: 'loginform.html'});
	}

	function documents() {
		nav.toSection({url: 'documents.html'});
	}

	function workflow() {
		nav.toSection({url: 'workflow.html'});
	}

	function social() {
		nav.toSection({url: 'social.html'});
	}

	function security() {
		nav.toSection({url: 'security.html'});
	}

	$('#main-context').on('click', '.btn-login', login);


	$('body').on('click', '.system-logoff', logoff);

	$('body').on('click', '.db-card.documents, .mnu-documents', documents);
	$('body').on('click', '.db-card.workflow, .mnu-workflow', workflow);
	$('body').on('click', '.db-card.social, .mnu-social', social);
	$('body').on('click', '.db-card.security, .mnu-security', security);

});