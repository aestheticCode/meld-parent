let system = require('system');
let webpage = require('webpage');

let url = system.args[0];

let page = webpage.create();
page.viewportSize = { width: 1024, height: 768 };
page.clipRect = { top: 0, left: 0, width: 1024, height: 768 };
page.open(url, function() {
    page.render('screenshot.png');
    phantom.exit();
});