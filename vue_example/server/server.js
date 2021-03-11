const express    = require('express');    // Express.js is a backend web application framework for Node.js allowing for defining endpoints
const path       = require('path');       // Node.js library to work with filepaths
const cors       = require('cors');
const vueHistory = require('connect-history-api-fallback');
const apiRoutes  = require('./modules/api.js');
require('dotenv').config();            // Loads environment variables from a local .env file

/* Constants for making the app run */
const app        = express();             // Creating the application instance

/*
 * process.env is an object that contains all the environment variables loaded from a local .env file
 * This is shorthand syntax for saying if process.env.PORT exists, use that value, else use 8080
 */
const port = process.env.PORT || 8080;

/* Application Settings*/
app.use(cors());
app.use(vueHistory());                              // Re-route all unknown pages to 'index.html' for client-side routing
app.use(express.json());                            // Allow the backend to recognize an incoming Request Object as a JSON object
app.use(express.urlencoded({ extended: true }));    // Allow the backend to parse incoming Request Objects with urlencoded payloads with nesting enabled
//app.use(cookieParser());

/* Tells the application where public files like images, styling, and the minified javascript code lives */
app.use(express.static(path.join(__dirname, '..', 'client', 'dist')));

/* Defines API endpoint routes prefixed with /api
    - all routes inside will be: http:localhost:[PORT]/api/[your-defined-endpoint]
*/
app.use('/api', apiRoutes); // API routes should be below '/api'

app.use(function (err, req, res, next) {
  status = err.status || 500
  message = err.message || 'Internal Server Error'

  console.error('[error] ' + err.stack)
  res.status(code).json({ status: status })

})

/* Start the server to listen on the defined port */
app.listen(port, () => {
  console.log('Server is running at localhost:' + port);
});
