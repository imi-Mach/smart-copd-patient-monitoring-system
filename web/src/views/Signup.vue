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

                    <!-- submit button -->
                    <div class="field has-text-right">
                        <button v-on:click="processForm">
                            Submit            
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</template>

<script>
    export default {
        data: () => ({
            name: '',
            email: '',
            errors: {
                name: false,
                email: false,
            }
        }),
        methods: {
            processForm: function() {
                console.log({ name: this.name, email: this.email });
                 this.$router.push('home');
            },
        }
    };
</script>