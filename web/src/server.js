const express = require('express')
const serveStatic = require('serve-static')
const path = require('path')

const app = express()

app.use('/', serveStatic(path.join_dirname, '/dist'))

app.get(/.*/, function (req, res) {
    res.sendFile(path.join(_dirname, 'dist/index.html'))
})

const port = provess.env.PORT || 8080
app.listen(port)
console.log('app is listening on port:' + port)
