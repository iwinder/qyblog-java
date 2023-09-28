<template>
  <!-- <span v-if="targetObj.url" >  -->
        <a :href="targetObj.url"  v-if="validateUrl(targetObj.url)"  :target="targetObj.blanked?  '_blank':'_self'">   {{targetObj.name}}  </a>

        <nuxt-link  :to="targetObj.url"  v-else    :target="targetObj.blanked?  '_blank':'_self'"  >      {{targetObj.name}} </nuxt-link>
  <!-- </span> -->
</template>

<script >
import Vue from 'vue'
export default Vue.extend({
    props: { 
           targetObj: {
               name: "",
               url: "",
               blanked: false
           },
    },
    methods: {
        validateUrl(url) {
            const reg = /^(http:\/\/|https:\/\/)/;
           return reg.test(url);
        }
    }
})
</script>