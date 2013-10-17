module.exports = function(grunt) {

	grunt.initConfig({

		pkg: grunt.file.readJSON('package.json'),

		copy: {
		  main: {
		    files: [
		      {expand: true, src: ['bower_components/bootstrap/dist/css/*'], dest: 'css/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/dist/js/*'], dest: 'lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/bootstrap/dist/fonts/*'], dest: 'fonts/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/requirejs/require.js'], dest: 'lib/', filter: 'isFile', flatten:true},
		      {expand: true, src: ['bower_components/jquery/*'], dest: 'lib/', filter: 'isFile', flatten:true},

		    ]
		  }
		}

	});

	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.registerTask('default', ['copy']);
};