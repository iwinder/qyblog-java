<template>
    <a-row> 
         <template v-if="!editLinkShow"> 
            永久链接： 
            <a-tooltip placement="topLeft"  :title="siteInfo.site_url.endsWith('/')?siteInfo.site_url+oldLink:siteInfo.site_url+'/'+oldLink" arrow-point-at-center>
            <a  :href="siteInfo.site_url.endsWith('/')?siteInfo.site_url+oldLink:siteInfo.site_url+'/'+oldLink" class="viewlinka" target="_blank" > {{siteInfo.site_url.endsWith('/')?siteInfo.site_url:siteInfo.site_url+'/'}}<span class="viewlink">{{oldLink}}</span> 
                </a>   </a-tooltip>
                <a-button type="dashed" @click="opneEditLink(oldLink)">
                        编辑
            </a-button>
        </template>
        <template  v-else> 
            永久链接： <span > {{siteInfo.site_url}}/<span class="viewlinkInpu"><a-input type="text"  size="small"  v-model="newLink" /></span> </span>
                
                <a-button type="dashed" @click="saveEditLink">
                        保存
            </a-button>
        </template>
    </a-row>
</template>

<script >
import Vue from 'vue'
 import { mapState } from 'vuex'

export default Vue.extend({
    props: { 
       permaLink: {
           default: "",
       },
       postId: {
              default: null,
       },
        afterSave: {
            type: Function,
            default: null
        }, 
        
    },

    data() {
        return {
            oldLink: "",
            editLinkShow: false,
            newLink: ""
        }
     },
    watch: { 
        permaLink:  {
                handler( val) {
                    this.oldLink = val;
                },
                immediate: true,
          
        }, 

    },
    computed: {
        ...mapState({
        siteInfo: state => state.siteInfo.siteInfo
        })
    },
    mounted() { 
        let _this  =  this;
    },
     methods: {
        opneEditLink(val) {
            let _this  =  this;
            _this.editLinkShow = true;
            _this.newLink = val;
        },
        saveEditLink() {
            let _this  =  this;
            let params = {
                id:  _this.postId,
                permaLink: _this.newLink
            }
            if( _this.oldLink == _this.newLink) {
                  _this.editLinkShow = false;
                return; 
            }
            _this.$axios.post('/admin/pages/checkPermaLink', params).then((res) => {
                let resp = res.data;
                if(resp.success) { 
                    _this.afterSave( resp.content); 
                      _this.editLinkShow = false;
                    // _this.articleForm.permaLink =;
                    // _this.showPermaLink = true;
                }
            });
          
           
        },
        checkAndGetNewLink(params) {
             let _this = this;

        },

     }
})
</script>

<style lang="scss" scoped>
a {
   padding-right: 10px;
}
.viewlinka{
     display: inline-block;
}
.viewlink {
    display: inline-block;
    max-width: 200px;
    overflow-x: hidden;
    white-space: nowrap;
    text-overflow: ellipsis;
    vertical-align: bottom;
}
</style>