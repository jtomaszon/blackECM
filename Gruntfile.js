module.exports = function(grunt) {

	grunt.initConfig({

		pkg: grunt.file.readJSON('package.json'),

		copy: {
		  main: {
		    files: [
		      {expand: true, src: ['bower_components/bootstrap/dist/css/*.min.css'], dest: 'web/static/css/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/dist/js/*'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/dist/fonts/*'], dest: 'web/static/fonts/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/js/dropdown.js'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/js/collapse.js'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/requirejs/require.js'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/jquery/*'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/backbone/backbone-min.js'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/underscore/underscore-min.js'], dest: 'web/static/lib/', filter: 'isFile', flatten:true},

		    ]
		  }
		}

	});

	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.registerTask('default', ['copy']);
};