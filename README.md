# smart-copd-patient-monitoring-system

This is the repository for the CSE280 Capstone project: Smart COPD Patient Monitoring System.

## Installation

(include details of installation)

```bash
pip install foobar
```

## Usage (example)

```python
import foobar

foobar.pluralize('word') # returns 'words'
foobar.pluralize('goose') # returns 'geese'
foobar.singularize('phenomena') # returns 'phenomenon'
```

## Dependencies
1. NPM: [Link](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm)
2. Maven: [Link](https://www.baeldung.com/install-maven-on-windows-linux-mac)

## Running the Project
1. Clone the Github repository
2. Run ‘npm install’ to download all npm related dependencies

To run locally:
1. Inside the web folder run ‘npm run serve’
2. Provided this runs successfully, the project will be viewable on localhost

To deploy to Heroku:
1. Inside the web folder run ‘./deploy.sh’ to run the deploy script
2. This script will compile the frontend by running ‘npm run build’, and provided it is successful, will then move the necessary files over to the backend for deployment
3. Navigate to the backend folder, and run ‘mvn heroku:deploy’
4. Upon success, the project will be deployed and viewable at: https://smart-copd-patient.herokuapp.com/


## Airtable

Login using the smartCOPD gmail

Airtable is located at: [AirTable](https://airtable.com/tblN8AYVUNG1gzA1l/viww792xDcFNpmvqK?blocks=hide)

Airtable Base ID: appr5KeZfUEbAXRCY

Airtable API Key: keyTtJ1q0C5hWsmeh

Below is an example for how I have pulled data from airtable using python:
```python
# Imports data from airtable
airtab = Airtable('baseID', 'Table Name', 'API key')
airtabName = airtab.get_all()
```

You can also access data from a specific table by doing the following:
```python
base_id = "app●●●●●●●●●●●●●●"
table_name = "Table Name"
url = "https://api.airtable.com/v0/" + base_id + "/" + table_name
```

How to connect to AirTable w/ Java: [Link](https://github.com/Sybit-Education/airtable.java)

## PLSQL

1. Go to [PSQL](https://www.postgresql.org/download/) and select the appropriate download for you device
2. Follow the instructions and select the default options
3. Set a password (remember it!)
4. When StackBuilder opens, select the second option to connect to a server (this is the local server)
5. You then will be prompted to download additional drivers / plugins, select cancel since this is not necessary
6. From there, you can launch SQL Shell (the shell for running psql commands) or pgAdmin which is the user interface. Both do the same and connect to a database
7. When entering into the Shell, select all of the deafult options (Just hit enter) and enter the password. Now you're connected!
8. When entering into pgAdmin, you will be prompted for that password. Once that's entered, you can view the database.

# pgAdmin
1. Download [pgAdmin](https://www.pgadmin.org/)
2. After installation. Create new server group in the file browser on the left.
3. Create new server under the server group just created.
4. Connect with the credentials provided in heroku

For more documentation, check out the [PSQL Site](https://www.postgresql.org/docs/current/)

## Contributors

* Maximillian Machado : Project Manager
* Thomas Liu : Frontend
* Athanasios Kougionis(Thanos) : Backend
* Brian Snyder : Database 

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Resources
[Airtable Vue App](https://dev.to/codeply/build-a-simple-crud-app-with-airtable-api-vue-vuetify-5565)
