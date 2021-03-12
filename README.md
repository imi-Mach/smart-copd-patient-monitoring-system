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

## Airtable

Login using the smartCOPD gmail
Airtable is located at: [AirTable](https://airtable.com/tblN8AYVUNG1gzA1l/viww792xDcFNpmvqK?blocks=hide)
Airtable Base ID: appr5KeZfUEbAXRCY
Airtable API Key: keyTtJ1q0C5hWsmeh

Below is an example for how I have pulled data from airtable using python:
```
# Imports data from airtable
airtab = Airtable('baseID', 'Table Name', 'API key')
airtabName = airtab.get_all()
```

You can also access data from a specific table by doing the following:
```
base_id = "app●●●●●●●●●●●●●●"
table_name = "Table Name"
url = "https://api.airtable.com/v0/" + base_id + "/" + table_name
```

How to connect to AirTable w/ Java: [Link](https://github.com/Sybit-Education/airtable.java)

## Contributors

* Maximillian Machado : Project Manager
* Thomas Liu : Frontend
* Athanasios Kougionis : Backend
* Brian Snyder : Database 

## License
[MIT](https://choosealicense.com/licenses/mit/)

## Resources
[Airtable Vue App](https://dev.to/codeply/build-a-simple-crud-app-with-airtable-api-vue-vuetify-5565)
