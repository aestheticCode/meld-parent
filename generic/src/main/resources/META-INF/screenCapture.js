var system = require('system');
var webpage = require('webpage');

var url = system.args[1];

console.log(url);

var page = webpage.create();
page.viewportSize = { width: 1024, height: 768 };
page.clipRect = { top: 0, left: 0, width: 1024, height: 768 };
page.open(url, function() {
    page.render('screenShot.png');
    phantom.exit();
});
