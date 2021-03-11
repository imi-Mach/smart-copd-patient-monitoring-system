var express = require('express');
const router = express.Router();
var db = require('../datastore/datastore.js');
var moment = require('moment');

router.get('/posts',  (req, res) => {
  var data = db.get('posts').value();
  res.status(200).json(data);
});

router.get('/reviews', (req, res) => {
  var data = db.get('reviews').value();
  res.status(200).json(data);
})

router.get('/reviews/:id', (req, res) => {
  var data = db.get('reviews').value();
  var result = [];
  for(let i = 0; i < data.length; i++){
    if(data[i].courseNumber == req.params.id){
      result.push(data[i]);
    }
  }
  console.log('reviews', result);
  res.status(200).json(result);
})

router.get('/reviews/user/:id', (req, res) => {
  var data = db.get('reviews').value();
  var result = [];
  for(let i = 0; i < data.length; i++) {
    if(data[i].name && data[i].name == req.params.id) {
      result.push(data[i]);
    }
  }
  console.log('reviews', result);
  res.status(200).json(result);
})

router.post('/posts', (req, res) => {
  var newPost = {
    text: req.body.text,
    id: new Date().getTime(),
    date: moment().format('MMM Do, HH:mm')
  };

  if (req.body.text) {
    db.get('posts').push(newPost).write();
    res.send(newPost);
  } else {
    res.status(400).send(newPost);
  }
});

router.post('/reviews', (req, res) => {

  var newReview = {
    name: req.body.name,
    courseNumber: req.body.courseNumber.toUpperCase().replace(/\s+/g, ''),
    professor: req.body.professor,
    semester: req.body.semester,
    year: req.body.year,
    review: req.body.review,
    id: new Date().getTime(),
    date: moment().format('MMM Do, HH:mm')
  }

  console.log('review from post', newReview);

  if (req.body.courseNumber && req.body.professor && req.body.semester && req.body.year && req.body.review) {
    db.get('reviews').push(newReview).write();
    res.send(newReview);
  }
  else{
    res.status(400).send(newPost);
  }

})

router.post('/reports', (req, res) => {
  var newReport = {
    reviewId: req.body.reviewId,
    reason: req.body.reason,
    report: req.body.report,
    id: new Date().getTime(),
    date: moment().format('MMM Do, HH:mm'),
  }

  console.log('report submitted:', newReport);

  if (req.body.reviewId && req.body.reason) {
    db.get('reports').push(newReport).write();
    res.send(newReport);
    console.log('passed:', newReport);

  }
  else{
    res.status(400).send(newReport);
  }


})

router.delete('/reviews/:id', (req, res) => {
  var deleteResult = db.get('reviews').remove({ id: parseInt(req.params.id, 10) }).write();

  if (deleteResult.length) {
    res.status(200).send(deleteResult);
  } else {
    res.status(400).send(deleteResult);
  }
});

router.delete('/posts/:id', (req, res) => {
  var deleteResult = db.get('posts').remove({ id: parseInt(req.params.id, 10) }).write();

  if (deleteResult.length) {
    res.status(200).send(deleteResult);
  } else {
    res.status(400).send(deleteResult);
  }
});

module.exports = router;