require(['bbgenerics', 'jquery'], function(gen, $) {

	var postTpl = $('.tpl_post').html();

	var postModel = new gen.Model({});

	var postsView = new gen.View({
		el: '.posts'
	});

	postsView.listenTo(postModel, 'change', function(model) {
		var html = this.template(postTpl, model.toJSON());
		this.$el.html(html);
	});

	$.ajax({
		url: '/collab/post/1'
	})
	.done(function(data) {
		postModel.set(data);
	})
	.error(function() {
		alert('erro');
	});

});