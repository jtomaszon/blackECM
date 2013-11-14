module.exports = function(grunt) {

	grunt.initConfig({

		pkg: grunt.file.readJSON('package.json'),

		copy: {
		  main: {
		    files: [
		      {expand: true, src: ['bower_components/bootstrap/dist/css/*.min.css'], dest: 'static/css/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/dist/js/bootstrap.min.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/dist/fonts/*'], dest: 'static/fonts/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/js/dropdown.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/js/collapse.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/requirejs/require.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/jquery/jquery.min.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/backbone/backbone-min.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/underscore/underscore-min.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/mustache/mustache.js'], dest: 'static/lib/', filter: 'isFile', flatten:true},

		    ]
		  }
		}

	});

	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.registerTask('default', ['copy']);
};