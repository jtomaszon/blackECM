define(['backbone', 'mustache'], function(bb, Mustache) {

	var View = bb.View.extend({
		template: Mustache.render
	});

	var Model = bb.Model.extend({});

	return {
		View: View,
		Model: Model
	};
});