# web


## todo
```
Convert the single html file to vue project format
Now with google login, we send the token returned from google server to backend via (get?), and 
store the token in local storage URL need to be change later
In login.vue, client ID is hardcoded as a variable in data, should be store in env later.
```

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```
## Packages used
```
vue-google-signin-button-directive (used for sign in with google)
npm install --save vue-google-signin-button-directive

vue-resource (to send http requests)
npm install vue-resource

bootstrap (styles)
npm install bootstrap
```

### Compiles and minifies for production
```
npm run build
```

### Run your unit tests
```
npm run test:unit
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).
