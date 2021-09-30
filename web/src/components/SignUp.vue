<template>
  <div class="hero is-fullheight is-info is-bold">
    <div class="hero-body">
    <div class="container">
            <h1 class="title has-text-centered">Sign Up</h1>
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

                var request = {"sessionID": this.$store.getters.getSession, "firstName": this.firstName, "lastName": this.lastName, "DOB": this.DOB, "phoneNumber": this.phoneNumber};
                console.log(request);
                
                this.$http.post("https://smart-copd-patient.herokuapp.com/register", request).then(
                    (response) => {
                        this.someData = response.body;
                        console.log(this.someData);
                        console.log(this.someData.mStatus);
                        console.log(this.someData.mExists);
                        console.log(!this.someData.mExists);
                        if (this.someData.mStatus == 'ok') {
                            console.log('it worked');
                            this.$router.push({ name: "Patient"});
                        } else {
                            alert('Error with submission, returning to home');
                            this.$router.push({ name: "Home"});
                            console.log('This point should not of been reached');
                            console.log('Brian screwed up, blame him');
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