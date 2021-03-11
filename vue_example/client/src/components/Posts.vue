<template>
  <div>
    <div>
      <textarea class="form-control" v-model="formInput" @keyup="formError = ''"></textarea>
      <button type="button" class="btn btn-primary mt-3" @click="addPost">Add Class</button>
      <div class="post-form-error" v-if="formError">{{formError}}, probably should fill the form</div>
    </div>
    <div>
      <div v-for="(post, index) in posts" :key="index">
        <div class="card my-5">
          <div class="card-header">
            {{post.date}}
          </div>
          <div class="card-body">
            <p class="card-text">{{post.text}}</p>
            <div>
              <button type="button" class="btn btn-danger" @click="deletePost(post.id)">Delete Post</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator';
import PostsDataService from '../services/PostsDataService';

interface Post {
  id?: number,
  text: string,
  date?: string
};

@Component
export default class Posts extends Vue {
  private posts: Post[] = [];
  private formInput: string = '';
  private formError: string = '';

  created() {
    PostsDataService.getAll()
      .then(response => {
        console.log(response.data)
        this.posts = response.data.reverse();
      })
      .catch(err => {
        console.error(`Couldn't fetch all posts: ${err}`)
      })
  }

  public addPost(): void {
    const newPost: Post = {
      text: this.formInput
    }

    PostsDataService.create(newPost)
      .then(response => {
        this.posts.unshift(response.data);
      })
      .catch(err => {
        this.formError = err.reponse.statusText;
      })
  }

  public deletePost(id: number): void {
    PostsDataService.delete(id)
      .then(response => {
        let newPosts = this.posts.filter(post => post.id !== response.data[0].id);
        this.posts = newPosts;
      })
      .catch(err => {
        console.error(err.response);
      })
  }
  public getTextArry(): string[] {
    let textArry = [];
    for (let i = 0; i < this.posts.length; i++){
      textArry.push(this.posts[i].text)
    }

    return textArry;
  }
}
</script>

<style scoped>
textarea.form-control {
  height: 100px;
}

button {
  background-color: #4987E8;
}

.btn-danger {
  background-color: #E06666;
  border-color: #E06666;
}
</style>
