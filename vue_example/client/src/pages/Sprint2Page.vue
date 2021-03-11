<template>
  <div class="page page--about">
    <div class="toggle" @click="toggleTheme">
      <span>Change the theme</span>
    </div>
    <div class="title">
      <h1>{{ title }}</h1>
      <router-link class="new-review" to="/new-review">Write a Review</router-link>
    </div>
    <p class="mt-3">{{ description }}</p>
    <div id="search_bar">
      <v-select :options="courses" taggable="true" @input="search"></v-select>
    </div>
    <div class="last-search" v-if="getCookie('last-search')">
      <h4 id="recent-searches">Recent Searches:</h4>
      <router-link
        class="review-link"
        :to="{ name: 'reviews', params: { course: getCookie('last-search') } }"
        >{{ getCookie("last-search") }}</router-link>
      <router-link
        class="review-link"
        v-if="getCookie('second-last-search')"
        :to="{
          name: 'reviews',
          params: { course: getCookie('second-last-search') },
        }"
        >{{ getCookie("second-last-search") }}</router-link>
      <router-link
        class="review-link"
        v-if="getCookie('third-last-search')"
        :to="{
          name: 'reviews',
          params: { course: getCookie('third-last-search') },
        }"
        >{{ getCookie("third-last-search") }}</router-link>
    </div>
    <h3>Courses</h3>
    <div class="course-links">
      <router-link
        v-for="course in courses"
        :to="{ name: 'reviews', params: { course: course } }"
        >{{ course }}</router-link>
    </div>
    <div>
      <h1 class="dog-api">Things you can do with the Doggo API</h1>
        <button id="dog-button" class="new-review" v-on:click="getRandomDoggo">
          Click for Random Doggo!
        </button>
      <div id="randomImageContainer"></div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import PostsDataService from "../services/PostsDataService";

@Component
export default class Sprint2Page extends Vue {
  private title: string = "Welcome to RateMyCourse!";
  private description: string =
    "Use the search bar below to find a class, then click on it to see reviews. Or, click on a course button below to see students' reviews of the class.";
  public courses: string[] = [];
  $cookies: any;
  public lastCookie: string = "";
  public cached: string[] = [];

  mounted(): void {
    PostsDataService.getReviews().then((response) => {
      let array: string[] = [];
      array = response.data.map(function(item: any) {
        let upper = item["courseNumber"].toUpperCase();
        let final = upper.replace(/\s+/g, "");
        return final;
      });
      let uniq = [...new Set(array)];
      this.courses = uniq;
      this.courses.sort();
      localStorage.setItem('existing-courses', this.courses.toString())
    });

    this.setTheme();
  }

  setCookie(value: any) {
    this.$cookies.set("third-last-search", this.$cookies.get("second-last-search"));
    this.$cookies.set("second-last-search", this.$cookies.get("last-search"));
    this.$cookies.set("last-search", value);
    this.lastCookie = value;
  }

  public getCookie(key: string) {
    let result = this.$cookies.get(key);
    console.log(result);
    if (result === "null") {
      return false;
    }
    return result;
  }

  public search(value: any) {
    this.setCookie(value);
    location.reload();
    location.href = "/reviews/" + value;
  }

  setTheme() {
    let h1 = document.getElementsByTagName("h1");
    let buttons = document.getElementsByClassName("new-review");
    let routers = document.getElementsByClassName("course-links");

    let toAdd = this.getCookie("theme");
    let toRemove;

    if (toAdd === "purple") toRemove = "green";
    else if (toAdd === "red") toRemove = "purple";
    else if (toAdd === "blue") toRemove = "red";
    else toRemove = "blue";

    for (let i = 0; i < h1.length; i++) {
      h1[i].classList.add(toAdd);
      h1[i].classList.remove(toRemove);
    }
    for (let i = 0; i < buttons.length; i++) {
      buttons[i].classList.add(toAdd + "-button");
      buttons[i].classList.remove(toRemove + "-button");
    }
    for (let i = 0; i < routers.length; i++) {
      routers[i].classList.add(toAdd + "-button");
      routers[i].classList.remove(toRemove + "-button");
    }
  }

  toggleTheme() {
    let theme = this.getCookie("theme");
    let newTheme;
    if (theme === "green") {
      newTheme = "purple";
    } else if (theme === "purple") {
      newTheme = "red";
    } else if (theme === "red") {
      newTheme = "blue";
    } else {
      newTheme = "green";
    }

    this.$cookies.set("theme", newTheme);
    this.setTheme();
  }

  getRandomDoggo() {
    console.log("Button Clicked");
    fetch("https://dog.ceo/api/breeds/image/random")
      .then((response) => response.json())
      .then((data) => this.handleData(data));
    console.log("Getting Random Dog");
  }

  handleData(data: { message: string; status: string }) {
    let url = data.message;
    console.log(url);
    let regex = /https:\/\/images\.dog\.ceo\/breeds\/(\w+\-?\w+)\/.+/g;
    let breedName = regex.exec(url);
    var test = document.getElementById("randomImageContainer");
    if (test === null) {
      alert("Error");
    } else {
      test.innerHTML = `<img alt="random image" src='${url}'/>`;
    }
  }
}
</script>

<style scoped>
.about-logo a {
  padding: 20px;
  outline: 1px solid rgb(169, 169, 169);
  display: inline-block;
}
.about-logo a:hover {
  opacity: 0.8;
}
h1, .mt-3 {
  font-family: "Roboto Slab", serif;
  padding: 0;
}
h1 {
  color: #4987e8;
  font-weight: 700;
  margin: 8px 0;
}
.last-search {
  font-family: "Roboto Slab", serif;
  padding-top: 10px;
  display: flex;
  align-items: flex-end;
}
.last-search * {
  font-family: "Roboto Slab", serif;
  padding-top: 10px;
  margin: 8px;
}
.review-link {
  text-decoration: underline;
  color: #4987e8;
  font-weight: 700;
}
.review-link:hover {
  color: #6399ee;
}
.new-review {
  background-color: #4987e8;
  color: white;
  font-family: "Roboto Slab", sans-serif;
  text-align: center;
  margin: 8px 0;
  padding: 7px 10px;
  border-radius: 10px;
  font-size: 20px;
}
.new-review:hover {
  cursor: pointer;
  text-decoration: underline;
}
.toggle span {
  background-color: white;
  color: #4e4e4e;
  font-family: "Roboto Slab", sans-serif;
  margin: 8px 0;
  padding: 7px 10px;
  padding-left: 0;
  border-radius: 10px;
  font-size: 20px;
  width: auto;
  display: table;
}
.toggle span:hover {
  color: #7e7e7e;
  cursor: pointer;
}
.title {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
}
#recent-searches {
  margin-left: 0;
}
h3 {
  margin-top: 20px;
  font-family: "Roboto Slab", sans-serif;
}
.course-links {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  grid-gap: 15px;
}
.course-links * {
  text-align: center;
  color: #4987e8;
  border: 3px solid #4987e8;
  border-radius: 10px;
  font-family: "Roboto Slab", sans-serif;
  font-size: 24px;
  padding: 10px 0;
  font-weight: 700;
}
.course-links *:hover {
  color: white;
  font-weight: 900;
  background-color: #4987e8;
  text-decoration: none;
}
.purple {
  background-color: white;
  color: rebeccapurple;
}
.purple-button *:hover {
  background-color: rebeccapurple;
  color: white;
}
.purple-button * {
  background-color: white;
  color: rebeccapurple;
  border: 3px solid rebeccapurple;
}
.green {
  background-color: white;
  color: darkolivegreen;
}
.green-button *:hover {
  background-color: darkolivegreen;
  color: white;
}
.green-button * {
  background-color: white;
  color: darkolivegreen;
  border: 3px solid darkolivegreen;
}
.red {
  background-color: white;
  color: darkred;
}
.red-button *:hover {
  background-color: darkred;
  color: white;
}
.red-button * {
  background-color: white;
  color: darkred;
  border: 3px solid darkred;
}
.dog-api {
  margin-top: 75px;
}
</style>
