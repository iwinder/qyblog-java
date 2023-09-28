<template>
    <div>
          <a-page-header
                
                title="新增分类"
                sub-title=""
                @back="backF()"
            />
            <!-- <h3>新增文章</h3> -->
            <qy-article-category-form    
                :categoryForm="null"  
                ref="articleForm"
                :loading = "editLoading"
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
                parentTreeObj: {},
                editLoading: false
            }
        },
        mounted() {
            let _this = this;
            if(this.$route.query.parent ) {
               _this.parentTreeObj =   JSON.parse(this.$route.query.parent )
             
            }
       
        },
        methods: {
            afterSubmitForm(param) {
                let _this = this;
                _this.$axios.post('/admin/blogCategorys/save', param).then(res => {
                                _this.editLoading = false;
                                if(res.data.success) {
                                    this.$message.success('保存成功',5);
                                     _this.backF() ;
                                } else {
                                    this.$message.error(res.data.message,5);
                                }
                }).catch((response) => {
                    _this.editLoading = false;
                     this.$message.error(response,5);
                });
            },
            backF() {
              let _this = this;
                _this.$router.push("/category");
            }
        }
    }
</script>