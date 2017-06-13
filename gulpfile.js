var gulp = require('gulp');
var clean = require('gulp-clean');
var runSequence = require('run-sequence');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var htmlmin = require('gulp-htmlmin');
var cleanCSS = require('gulp-clean-css');
var gulpCopy = require('gulp-copy');
var ngAnnotate = require('gulp-ng-annotate');
var htmlreplace = require('gulp-html-replace');
var path = require("path");

gulp.task('clean', function () {
	return gulp.src(path.join(__dirname, "./", 'dist'))
	    .pipe(clean());
});

gulp.task('uglify', function() {
    var sources = [
        'public/vendor/jquery/dist/jquery.min.js',
        'public/vendor/bootstrap/dist/js/bootstrap.min.js',
        'public/vendor/angular/angular.min.js',
        'public/vendor/angular-ui-router/release/angular-ui-router.min.js',
        'public/vendor/angular-sanitize/angular-sanitize.min.js',
        'public/vendor/angular-bootstrap/ui-bootstrap.min.js',
        'public/vendor/angular-bootstrap/ui-bootstrap-tpls.min.js',
        'public/vendor/angular-local-storage/dist/angular-local-storage.min.js',
        'public/resources/bootstrap-combobox/js/bootstrap-combobox.js',
        'public/vendor/angular-input-masks/angular-input-masks-standalone.min.js',
        'public/vendor/angular-confirm/dist/angular-confirm.min.js',
        'public/javascripts/**/*.js'
        ];

    return gulp.src(sources)
        .pipe(ngAnnotate())
        .pipe(uglify())
        .pipe(concat('all.min.js'))
        .pipe(gulp.dest('dist/public/javascripts'));
});

gulp.task('htmlreplace', function() {
    return gulp.src('app/views/**/*')
        .pipe(htmlreplace({
            'css' : 'css/styles.min.css',
            'js': 'javascripts/all.min.js'
        }))
        .pipe(gulp.dest('dist/app/views'));
});

gulp.task('htmlmin', function() {
    var htmlminOptions = {
        removeComments: true,
        collapseWhitespace: true
    };

    return gulp.src('public/partials/*.html')
        .pipe(htmlmin({collapseWhitespace: true}))
        .pipe(gulp.dest('dist/public/partials'));
});

gulp.task('cssmin', function() {
    var sources = [
        'public/vendor/bootstrap/dist/css/bootstrap.min.css',
        'public/vendor/bootstrap/dist/css/bootstrap-theme.min.css',
        'public/resources/css/style.css',
        'public/resources/css/owfont-regular.min.css',
        'public/vendor/font-awesome/css/font-awesome.min.css',
        'public/resources/bootstrap-combobox/css/bootstrap-combobox.css',
        'public/vendor/angular-confirm/dist/angular-confirm.min.css'
    ];

    return gulp.src(sources)
        .pipe(cleanCSS())
        .pipe(concat('styles.min.css'))
        .pipe(gulp.dest('dist/public/css'));
});

gulp.task('copyFontAwesome', function() {
    return gulp.src('public/vendor/font-awesome/fonts/**/*')
        .pipe(gulp.dest('dist/public/fonts'));
});

gulp.task('copyFontOw', function() {
    return gulp.src('public/resources/fonts/**/*')
        .pipe(gulp.dest('dist/public/fonts'));
});

gulp.task('copyGlyphicons', function() {
    return gulp.src('public/vendor/bootstrap/fonts/**/*')
        .pipe(gulp.dest('dist/public/fonts'));
});

gulp.task('copyResources', function() {
    var sources = [
        'public/resources/*.png',
        'public/resources/*.ico'
    ];

    return gulp.src(sources)
        .pipe(gulp.dest('dist/public/resources'));
});

gulp.task('copyProject', function() {
    var sources = [
        'app/**',
        'bin/**',
        'conf/**',
        'gatling/**',
        'logs/**',
        '!**/app/views/**',
        'node_modules/**',
        'project/**',
        'public/swagger-ui/**',
        'target/**',
        'test/**',
        '.bowerrc',
        '.classpath',
        '.gitignore',
        '.project',
        '.travis.yml',
        'build.sbt',
        'LICENSE',
        'package.json',
        'README.md'
    ];

    return gulp.src(sources)
        .pipe(gulpCopy('dist', { prefix: 0 }));
});

gulp.task('default', function(callBack) {
    return runSequence('clean', ['uglify', 'htmlmin', 'cssmin', 'copyFontAwesome', 'copyFontOw', 'copyGlyphicons', 'copyResources', 'copyProject', 'htmlreplace'], callBack);
});