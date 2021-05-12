<template>
  <div class="hero is-fullheight is-info is-bold">
    <div class="hero-body">
    <div class="container">
            <h1 class="title has-text-centered">Vue.js Form Processing</h1>
            <div class="box">
                <!-- our signup form ===================== -->
                <form id="signup-form">
                    <!-- name -->
                    <div class="field">
                        <label class="label">Name</label>
                        <input 
                            type="text"
                            class="input" 
                            name="name"
                            v-model="name">
                    </div>

                    <!-- email -->
                    <div class="field">
                        <label class="label">Email</label>
                        <input 
                            type="email" 
                            class="input" 
                            name="email" 
                            v-model="email">

                        <p class="help is-danger" v-if="errors.email">
                            Please enter a valid email.
                        </p>
                    </div>

                    <div class="field">
                        <label class="label">Age</label>
                        <input 
                            type="text"
                            class="input" 
                            name="age"
                            v-model="age">
                    </div>

                    <div class="field">
                        <label class="label">Height</label>
                        <input 
                            type="text"
                            class="input" 
                            name="height"
                            v-model="height">
                    </div>

                    <div class="field">
                        <label class="label">Weight</label>
                        <input 
                            type="text"
                            class="input" 
                            name="weight"
                            v-model="weight">
                    </div>

                    <!-- submit button -->
                    <div class="field has-text-right">
                            <button type="submit" @click.stop.prevent="processForm()">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</template>

<script>
import {patientID} from '@/components/Login.vue'

    export default {
        data: () => ({
            name: '',
            email: '',
            age: '',
            weight: '',
            height: '',
            errors: {
                name: false,
                email: false,
            }
        }),
        methods: {
            processForm: function() {
                console.log("This is a test statement, plz work");
                console.log(patientID);
                console.log({ name: this.name, email: this.email, age: this.age, weight: this.weight, height: this.height});
                setTimeout(() => {  console.log("World!"); }, 2000);
                
                this.$http.post("/sign_in", patientID).then(
                    (response) => {
                        this.someData = response.body;
                        console.log(this.someData);
                        if (response.mData) {
                            console.log('it worked');
                            this.$router.push('patients')
                        }
                    },
                    (response) => {
                        console.log(reponse.mStatus);
                        console.log('it did not work');
                    }
                )
            },
        }
    };
</script>