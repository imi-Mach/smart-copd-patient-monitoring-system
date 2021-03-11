<template>
    <div>
        <form id='newReview' onsubmit="return false">
            <label for='course-number'>Course Number</label>
            <label for='professor'>Professor</label>
            <textarea type='text' name='courseNumber' :placeholder=coursePlaceholder v-model='courseNumber' id='courseNumber' required @keyup="formError = ''"></textarea>
            <textarea type='text' name='professor' v-model='professor' id='professor' required @keyup="formError = ''"></textarea>

            <label for='semester'>Semester</label>
            <label for='year'>Year</label>
            <select id='semester' v-model='semester' name='semester' required form='newReview' @keyup="formError = ''">
                <option value='Fall'>Fall</option>
                <option value = 'Winter'>Winter</option>
                <option value='Spring'>Spring</option>
                <option value='Summer'>Summer</option>
            </select>
            <select id='year' v-model='year' name='year' form='newReview' required @keyup="formError = ''">
                <option value='2021'>2021</option>
                <option value='2020'>2020</option>
                <option value='2019'>2019</option>
                <option value='2018'>2018</option>
                <option value='2017'>2017</option>
                <option value='2016'>2016</option>
                <option value='2015'>2015</option>
                <option value='2014'>2014</option>
                <option value='2013'>2013</option>
                <option value='2012'>2012</option>
                <option value='2011'>2011</option>
            </select>

            <label for='review'>Review</label>
            <textarea class="form-control" id="review" v-model="review" required @keyup="formError = ''"></textarea>

            <button type="button" id='submitDetails' name='submitDetails' @click="addReview">Post</button>

            <h6 id="error">Fill in all fields to submit form.</h6>
        </form>
    </div>
</template>

<script lang="ts">

import { Component, Vue } from 'vue-property-decorator';
import PostsDataService from '../services/PostsDataService';

interface Review {
    id?: number,
    name: string,
    courseNumber: string,
    professor: string,
    semester: string,
    year: string,
    review: string,
    date?: string
}

@Component
export default class AddReview extends Vue {
    $cookies: any;
    private coursePlaceholder = this.$cookies.get('last-search');
    private name = this.$cookies.get('profile');

    private reviews: Review[] = [];
    private courseNumber: string = '';
    private professor: string = '';
    private semester: string = '';
    private year: string = '';
    private review: string = '';
    private formError: string = '';

    public addReview(): void {
        
        const newReview: Review = {
            name: this.name,
            courseNumber: this.courseNumber,
            professor: this.professor,
            semester: this.semester,
            year: this.year,
            review: this.review
        }

        console.log('Review', newReview);

        PostsDataService.createReview(newReview)
        .then(response => {
            this.reviews.unshift(response.data);
            location.href = '/reviews/' + this.courseNumber.toUpperCase().replace(/\s+/g, '');
        })
        .catch(err => {
            this.formError = err.reponse.statusText;
        })

        if(!this.courseNumber || !this.professor || !this.semester || !this.year || !this.review) {
            if(document.getElementById('error')) {
                document.getElementById('error')!.style.visibility = 'visible';
            }
        }
    }
}
</script>

<style scoped>
  form {
      display: grid;
      grid-template: repeat(6, 40px 30px) / repeat(2, 300px);
      grid-column-gap: 20px;
      grid-row-gap: 5px;
  }
  #submitDetails {
      grid-row: 10 / 11;
      grid-column: 1 / 3;
      background-color: #4987E8;
      color: white;
      font-family: 'Roboto Slab', serif;
      font-weight: 700;
      border-radius: 10px;
      border-color: #93b7f0;
      margin-top: 20px;
      height: 40px;
      text-transform: uppercase;
      letter-spacing: 2px;
      font-size: 20px;
      padding: auto auto;
  }
  #submitDetails:hover { 
      background-color: #6399ee;
  }
  #review {
      grid-row: 6 / 10;
      grid-column: 1 / 3;
      padding: 0;
  }
  textarea {
      resize: none;
  }
  label {
      margin-top: 20px;
      font-family: 'Roboto Slab', serif;
      font-weight: 700;
  }
  #error {
      color: red;
      font-weight: 700;
      font-family: 'Roboto Slab', serif;
      grid-row: 12 / 13;
      grid-column: 1 / 3;
      text-align: center;
      visibility: hidden;
  }
</style>