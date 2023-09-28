<template>
    <div>
          <a-page-header
                
                title="新增文章"
                sub-title=""
                @back="backF()"
            />
            <!-- <h3>新增文章</h3> -->
            <qy-article-form    
                :articleObj="null"  
                ref="articleForm"
                 :afterSubmit="submitForm">

               
        </qy-article-form>
    </div>
</template>

<script >
import Vue from 'vue'
import   QyArticleForm  from '../../../components/qy-article-form.vue';


export default Vue.extend({
    components: {
        QyArticleForm,
    },
     data() {
        return {
                articleForm: {
                    title: '',
                    thumbnail: '',
                    content: '',
                    published: true,
                    deleted:  false
                },
                loading: false,
                saveContentInterval: {}, 
        }
    },
    destroyed: function() {
      let _this = this; 
      _this.clear();
    },
    mounted() {
         let _this = this;
         _this.submitFormRefreh();
    },
    methods: {
        submitForm(article) {
            let  _this = this;
            _this.$axios.post('/admin/articles/save',article).then(res => { 
                    _this.$refs.articleForm.loading = false;
                    if(res.data.success) {
                          this.$message.success('保存成功',5);
                          _this.toEdit(res.data.content.id);
                    }
            }).catch((response) => {
                    _this.$refs.articleForm.loading = false;
                     _this.$message.error('保存失败: ' + response,5);
            });
           
        },
        backF() {
              let _this = this;
                _this.$router.push("/article");
        },
        toEdit(e) {
              let _this = this;
               _this.$router.push( {
                   name: 'article-id',
                   params:{
                       id: e
                   }
               });
        },
        submitFormRefreh() {
            let _this = this;
            if( _this.saveContentInterval!=null) {
                return;
            }
            _this.saveContentInterval = setInterval(function() {
                                    _this.$refs.articleForm.$refs.saveButton.$emit('click');
           }, 180000);
        },
        // 停止定时器
        clear() {
            let _this = this; 
            clearInterval(_this.saveContentInterval);
            _this.saveContentInterval = null; 
        }
    },
})
</script>