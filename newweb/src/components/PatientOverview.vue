<template>
  <div class="demo-image">
    <image src='src/assets/logo.png'></image>
    <div>
      <h3 style="text-align: center">Data Summary (Last 7 days)</h3>
    </div>
    <div class="block">
      <el-image style="width: 100%; height: 350px" :src='src' :fit="fit">
        <div slot="error" class="image-slot">
          <i class="el-icon-picture-outline"></i>
        </div>
      </el-image>
    </div>
  </div>
</template>

<script>
export default {
  name: "PatientOverview",
  data() {
    return {
      fit: 'contain',
      //for presentation only
      src: 'https://lh3.googleusercontent.com/FLygZ_fqmtIReTAakHb4OWANmwSVy8sLVznfQkVEpNOnbEmUdn3oNHNrg3pNp7tVBORVtd57y44JSC_Wc1TgpSSQN7yqmiyy68xMePT4JTzPk-cRgeCTIiiecs5-ZMoMZkNIfPcUQfNwGtbUyZVYfrVvclmena7q9hkOsaWbuBCj2sXEerF2drqHvBItRMxS35eAH2w3JzMoU1A-Ki58RB13Wdsj5-6YvXLmD7ZEy6whqWRN7RRd7TIfGoTl0fWm79IcEGIfBq89j9o0hq1UvQuO82h-JeV2mltH5jopDpiDdR6qzBjFkNZLSPw8iCcnmTe7yEz2J2fx0VEDSfjBH_o7lmMraY_NwzfSNoOumSm1izjc7WhFmTXOTyhQVu7UGltvUfS8qxKsuP-tH7VUZBxaIHFEsc5vgGl19Mr0cXV6EmE5Wvj-oiaxxfOcnNRzilz4eger_zZPHcTdUOvD4xtm_ubqguhpEh-oakP3m-SNayUA8MZJbzjMED4IZN_1b732E2sKEIkX3E9ccexMCJL6OKUifuWBt0XwEFBGOIaZp55IaUQOUkiSusPqGQaNB_R8-svpbqcS8oi89zjX_JfKpZEIIhfiTBNrjlOyRhfmHheog6G04Y4E850ngKokJHI6nrpMLn4rW6ikWofP5-Pj1NTxkABb1S2s393HztSQe2s5VH_dx9tmgKLY2r809_BxStM56oJhdCXWw_j83bLU=w1519-h909-no?authuser=0'
    };
  },
};
</script>