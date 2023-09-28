<template>
    <div>
          <a-page-header
                
                title="修改分类"
                sub-title=""
                @back="backF()"
            />
            <!-- <h3>新增文章</h3> -->
            <qy-article-category-form    
                ref="categoryForm"
                :categoryObjForm= "categoryObj"
                :parentTreeObj = "parentTreeObj"
                 :afterSubmit="afterSubmitForm"
                 :afterCancel= "backF">

               
        </qy-article-category-form>
    </div>
</template>

<script>
     import  QyArticleCategoryForm  from '../../../components/qy-article-category-form.vue';

    export default {
 
        components: {
            QyArticleCategoryForm
        },

        data() {
            return {
                categoryId: null,
                categoryObj:{},
                parentTreeObj: {},
                editLoading: false
            }
        },
        created() {
            let _this = this; 
            _this.categoryId = _this.$route.params.id;
            _this.categoryObj = _this.$route.params.obj;
            _this.parentTreeObj = _this.$route.params.parent;
         },
        mounted() {
            let _this = this;
                if(_this.categoryId && !_this.categoryObj ) {
                    _this.loadInfo();
                }
       
        },
        methods: {
            loadInfo() {

                    let  _this = this;
                    _this.$axios.get("/admin/blogCategorys/" + _this.categoryId).then(res => {
                            if(res.data.success) {
                                _this.categoryObj =  res.data.content;
                                _this.parentTreeObj  = _this.categoryObj.parent;
                            }
                    });
            },
            afterSubmitForm(param) {
                let _this = this;
                _this.$axios.post('/admin/blogCategorys/save', param).then(res => {
                                 _this.$refs.categoryForm.editLoading = false;
                                if(res.data.success) {
                                    _this.$message.success('保存成功',5);
                                     _this.backF() ;
                                } else {
                                      _this.$message.error(res.data.message,5);
                                }
                }).catch((response) => {
                    _this.$refs.categoryForm.editLoading = false;
                     _this.$message.error('保存失败: ' + response,5);
                });
            },
            backF() {
              let _this = this;
                _this.$router.push("/category");
            }
        }
    }
</script>