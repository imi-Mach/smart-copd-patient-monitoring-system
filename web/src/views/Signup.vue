<template>
    <div>
        <form id='newReview' onsubmit="return false">
            <label for='firstName'>First Name</label>
            <textarea type='text' name='firstName' v-model='firstName' id='firstName' required @keyup="formError = ''"></textarea>
            <label for='lastName'>Last Name</label>
            <textarea type='text' name='lastName' v-model='lastName' id='lastName' required @keyup="formError = ''"></textarea>

            <button type="button" id='submitDetails' name='submitDetails' @click="addReview">Post</button>

            <h6 id="error">Fill in all fields to submit form.</h6>
        </form>
    </div>
</template>

<script lang="ts">

</script>

