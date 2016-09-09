var gulp = require('gulp');
var concat = require('gulp-concat');
var concatVendor = require('gulp-concat-vendor');
var uglify = require('gulp-uglify');
var minify = require('gulp-minify-css');
var mainBowerFiles = require('main-bower-files');
var inject = require('gulp-inject');
var runSequence = require('run-sequence');
var gzip = require('gulp-gzip');
var clone = require('gulp-clone');
var series = require('stream-series');

var vendorJs;
var vendorCss;

var paths = {
    js: "src/main/webapp/resources/js/**/*.js"
};

// Watch Angular files for changes
gulp.task('watch', function() {
    gulp.watch(paths.js, ['app-js']);
});

// Minify the vendor libraries and zip them to one file
gulp.task('lib-js-files', function () {
    vendorJs = gulp.src(mainBowerFiles('**/*.js'),{ base: 'bower_components' })
        .pipe(concatVendor('lib.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('src/main/webapp/resources/vendor/js'));

    vendorJs.pipe(clone())
        .pipe(gzip())
        .pipe(gulp.dest('src/main/webapp/resources/vendor/js'));
});

// Minify and compact the CSS-files
gulp.task('lib-css-files', function () {
    vendorCss = gulp.src(mainBowerFiles('**/*.css'), {base: 'bower_components'})
        .pipe(concat('lib.min.css'))
        .pipe(minify())
        .pipe(gulp.dest('src/main/webapp/resources/vendor/css'));

    vendorCss.pipe(clone())
        .pipe(clone())
        .pipe(gzip())
        .pipe(gulp.dest('src/main/webapp/resources/vendor/css'));
});

// Inject the CSS-files as links to the index.html-page
gulp.task('index', function () {
    var target = gulp.src("src/main/webapp/index.html");
    var sources = gulp.src(['src/main/webapp/resources/css/*.css'], {read: false});
    return target
        .pipe(inject(series(vendorCss, sources), {relative: true}))
        .pipe(gulp.dest('src/main/webapp'));
});

// Concat all Angular code to one file
gulp.task('app-js', function () {
    var sources = gulp.src([paths.js]);
    return sources
        .pipe(concat('reference-connector.js'))
        .pipe(gulp.dest('src/main/webapp/resources'));
});

// Copy all fonts to the vendor directory
gulp.task('copyFonts', function() {
    gulp.src(mainBowerFiles('**/dist/fonts/*.{ttf,woff,woff2,eof,svg}'))
        .pipe(gulp.dest('src/main/webapp/resources/vendor/fonts'));
});

// Default Task
gulp.task('default', function () {
    runSequence('lib-js-files', 'lib-css-files', 'app-js', 'index', 'copyFonts');
});