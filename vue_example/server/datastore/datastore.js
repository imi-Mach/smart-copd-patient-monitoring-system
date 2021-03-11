const low = require('lowdb');
const path = require('path');
const FileSync = require('lowdb/adapters/FileSync');

let adapter = null;
if (process.env.NODE_ENV !== 'test') {
  adapter = new FileSync(path.join(__dirname, './db.json'));
}
else {
  adapter = new FileSync(path.join(__dirname, './testdb.json'));
}
const db = low(adapter);

module.exports = db;
