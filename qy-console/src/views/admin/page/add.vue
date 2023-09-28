<template>
    <div>
          <a-page-header
                
                title="新增页面"
                sub-title=""
                @back="backF()"
            />
            <!-- <h3>新增文章</h3> -->
            <qy-page-form    
                :articleObj="null"  
                ref="pageForm"
                 :afterSubmit="submitForm">

               
        </qy-page-form>
    </div>
</template>

<script >
import Vue from 'vue'
import   QyPageForm  from '../../../components/qy-page-form.vue';


export default Vue.extend({
    components: {
        QyPageForm,
    },
     data() {
        return {
                pageForm: {
                    title: '',
                    thumbnail: '',
                    content: '',
                    published: true,
                    deleted:  false
                },
                loading: false
        }
    },
    methods: {
        submitForm(page) {
            let  _this = this;
            _this.$axios.post('/admin/pages/save',page).then(res => { 
                    _this.$refs.pageForm.loading = false;
                    if(res.data.success) {
                          this.$message.success('保存成功',5);
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
    },
})
</script>