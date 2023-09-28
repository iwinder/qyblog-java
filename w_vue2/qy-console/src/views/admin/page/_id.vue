<template>
    <div>
         <a-page-header
                title="修改文章"
                sub-title=""
                @back="backF()"
            />
            <qy-page-form    
                :articleObj="pageObj"  
                ref="pageForm"
                 :afterSubmit="submitForm"> </qy-page-form>
    </div>
</template>

<script  >
import Vue from 'vue' 
import   QyPageForm  from '../../../components/qy-page-form.vue';


export default Vue.extend({
    components: {
        QyPageForm,
    },
    data() {
        return {
            pageId: null,
            pageObj: null
        }
    },
    created() {
        let _this = this; 
        _this.pageId = _this.$route.params.id;
    },
    mounted() {
         let _this = this;
        _this.loadInfo();
    },
    methods: {
        loadInfo() {
            let  _this = this;
            _this.$axios.get("/admin/pages/" + _this.pageId).then(res => {
                    if(res.data.success) {
                            _this.pageObj = res.data.content;
                    }
            });
        },
        submitForm(page) {
            let  _this = this;
            _this.$axios.post('/admin/pages/save',page).then(res => { 
                    _this.$refs.pageForm.loading = false;
                    if(res.data.success) {
                          this.$message.success('保存成功',15);
                    }
            }).catch((response) => {
                    _this.$refs.pageForm.loading = false;
                     _this.$message.error('保存失败: ' + response,5);
            });
           
        },
        backF() {
              let _this = this;
                _this.$router.push("/page");
        }
    }
    
})
</script>