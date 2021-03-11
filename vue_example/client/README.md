## Project setup
```
npm install
```

### Compile and hot-reload the development web page:
```
npm run watch
```

Note that the above command will not start the Node.JS API server located in `../server`.
As a result, none of your API routes will work with the above command (unless you [fake them with Cypress](https://docs.cypress.io/api/commands/intercept.html) during testing).
If you want to run the full production server, simply run `npm run watch-project` in the root directory of the project.

Once you run this, you should be able to view the development version of your web page at [http://localhost:3000](http://localhost:3000).

### Compiles and minifies for production
```
npm run build
```

You generally won't need to run this command, since `npm run watch-project` will do this for you every time you make a change to your code.

### Launch the Cypress UI:
```
npm run cypress
```

This command is especially useful for developing your end-to-end test cases with Cypress.

### Run your tests
```
npm test
```

Note that your development server must be running at the same time in order for the tests to execute.
This can be accomplished by running `npm run watch` in a separate terminal before running the test command.

### Customize your Vue Configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

