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
                        <label class="label">First name</label>
                        <input 
                            type="text"
                            class="input" 
                            name="firstName"
                            v-model="firstName">
                    </div>

                    <!-- email -->
                    <div class="field">
                        <label class="label">Last name</label>
                        <input 
                            type="email" 
                            class="input" 
                            name="lastName" 
                            v-model="lastName">
                    </div>

                    <div class="field">
                        <label class="label">DOB</label>
                        <input 
                            type="text"
                            class="input" 
                            name="DOB"
                            v-model="DOB">
                    </div>

                    <div class="field">
                        <label class="label">Phone Number</label>
                        <input 
                            type="text"
                            class="input" 
                            name="phoneNumber"
                            v-model="phoneNumber">
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

    export default {
        data: () => ({
            firstName: '',
            lastName: '',
            DOB: '',
            phoneNumber: '',
            errors: {
                firstName: false,
                lastName: false,
                DOB: false,
            }
        }),
        methods: {
            processForm: function() {
                console.log("User Session Key before signup route "+this.$store.getters.getSession);
                //console.log(this.data);
                setTimeout(() => {  console.log("World!"); }, 2000);
                var test = this.$store.getters.getSession;
                console.log("Testing getSession: "+ test);
                //this.data.sessionID = this.$store.getters.getSession;

                //var request = {};
                var request = {"sessionID": this.$store.getters.getSession, "firstName": this.firstName, "lastName": this.lastName, "DOB": this.DOB, "phoneNumber": this.phoneNumber};
                console.log(request);
                var test2 = this.data;
                console.log(test2);
                //request = this.data;
                //request.sessionID = this.$store.getters.getSession;
                
                this.$http.post("https://smart-copd-patient.herokuapp.com/register", request).then(
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