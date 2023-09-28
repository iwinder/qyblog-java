<template>
    <div>
         <a-page-header
                title="修改文章"
                sub-title=""
                @back="backF()"
            />
            <qy-article-form    
                :articleObj="articleObj"  
                ref="articleForm"
                :saveContentLabel="saveContentLabel"
                 :afterSubmit="submitForm"> </qy-article-form>
    </div>
</template>

<script  >
import Vue from 'vue' 
import   QyArticleForm  from '../../../components/qy-article-form.vue';


export default Vue.extend({
    components: {
        QyArticleForm,
    },
    data() {
        return {
            articleId: null,
            articleObj: null,
            saveContentInterval: {},
            saveContentLabel: "",
        }
    },
    created() {
        let _this = this; 
        _this.articleId = _this.$route.params.id;
    },
    mounted() {
         let _this = this;
        _this.loadInfo();
    },
    destroyed: function() {
      let _this = this; 
      _this.clear();
    },
    methods: {
        loadInfo() {
            let  _this = this; 
            _this.$axios.get("/admin/articles/" + _this.articleId).then(res => {
                    if(res.data.success) {
                            _this.articleObj = res.data.content;
                            _this.articleObj.oldUrl = _this.articleObj.permaLink; 
                                // 定时自动保存
                               _this.submitFormRefreh();
                    }
            });
        },
        submitForm(article) {
            let  _this = this;
            _this.$axios.post('/admin/articles/save',article).then(res => { 
                    _this.$refs.articleForm.loading = false;
                    if(res.data.success) {
                        //  _this.$message.destroy();
                        //   _this.$message.success('保存成功',15);
                            let now =QyTool.dateFormat("hh:mm:ss");
                         _this.saveContentLabel = "最后保存时间：" + now;
                    }
            }).catch((response) => {
                    _this.$refs.articleForm.loading = false;
                     _this.$message.destroy();
                     _this.$message.error('保存失败: ' + response,5);
            });
           
        },
        backF() {
            let _this = this;
            _this.$router.push("/article");
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
    }
    
})
</script>