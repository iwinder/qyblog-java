<template>
    
</template>


<script >
import Vue from 'vue'
 import { mapState } from 'vuex'
export default Vue.extend({
    async fetch({ store, params }) {
  // await store. dispatch('siteInfo/getSiteInfo');

},
  async   asyncData (context) {
    let _this = context;
         let old =  context.route.path; 
         let news  = old.replace("/go/", ""); 
          let url  =  _this.store.getters["siteInfo/getSiteGoUrl"](news); 
        if(!url) {
 return context.redirect("/");
        }
            return context.redirect(url);
    },
    computed: {
        ...mapState({
            siteInfo: state => state.siteInfo.siteInfo,
            siteGo: state => state.siteInfo.siteGo,
    }), 
},
created() {
         let _this = this; 
    
},
  mounted() {
          let _this = this; 
           let old =  _this.$route.path;
         let news  = old.replace("/go/", ""); 
       
  }
})
</script>