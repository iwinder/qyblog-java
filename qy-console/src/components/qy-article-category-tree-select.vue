<template>
    <a-tree-select ref="treeSelect"
            tree-data-simple-mode
             v-model="value"
            style="width: 100%"
            :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
            :tree-data="treeData"
            :replaceFields="replaceTreeFields" 
            :placeholder="placeholdera"
 
            :load-data="onLoadData"
            @select	= "onSelectTree"
        />
</template>

<script>
export default {
    props: { 
       selectedId:null,
       parentTreeObj: null,
        afterSelect: {
            type: Function,
            default: null
        }, 
        placeholdera: {
            type: String,
            default: '请选择新父级分类'
        }
    },
    data() {
        return {
            value: null,
                treeData: [
                ],
                replaceTreeFields: {
                    children:'children', 
                    value: 'id',
                    title:'name', 
                    key:'id' 
                },
                expandedKeys:[],
                placeholderText: ''
        }
    },
    watch: {
        parentTreeObj(val) {
            let _this = this;
            // _this.initTreeData(val);
        },
        value(value) { 
        },
        placeholdera(val) {
            let _this = this; 
            _this.placeholderText = val;
        }
  },
   mounted() {
       let _this = this;
    //    _this.initTreeData(_this.parentTreeObj);
    _this.initList();
   },
    methods: {
        initList() {
            let _this = this;
            _this.$axios.get('/admin/blogCategorys',{ params: {}}).then(res => { 
                    let resp = res.data;
                    if(resp.success) { 
                        this.treeData =[...resp.content]; 
                     
                    }
            });
        },
        initTreeData(obj){
            let _this = this;
            if(obj.id == 0) {
                this.treeData = [...obj.children]
            } else {
                this.treeData =  [obj]
                // _this.expandedKeys = [obj.id]
                _this.value = obj.id;
                _this.onLoadData({
                    dataRef: {
                        id:0
                    }
                });
            } 
        },
        onLoadData(treeNode) {
            let _this = this;
            return new Promise(resolve => { 
                if(!treeNode || treeNode.dataRef.id == 0) {
                    let params = {};
                    treeNode.dataRef.children = [];
                     _this.loadCategoryData(params, function(resp) {
                            if(resp.success) {
                                    treeNode.dataRef.children =   [...resp.content];
                                    _this.treeData = [ ... resp.content];
                                }
                            resolve();
                            return;
                     });
                } else {
                    let params = {
                        parentId: treeNode.dataRef.id 
                    }
                     _this.loadCategoryData(params, function(resp) {
                            if(resp.success) {
                                treeNode.dataRef.children =   [...resp.content];
                                _this.treeData = [..._this.treeData];
                            }
                            resolve();
                            return;
                     })
                }
            })
        },
        loadCategoryData(params, callBack) {
            let _this = this;
            _this.$axios.get('/admin/blogCategorys',{ params: params}).then(res => {
                    let resp = res.data;
                    callBack(resp);
            });
        },
        onSelectTree(selectedKeys, info) {
                    let _this = this; 
                    _this.afterSelect(selectedKeys,  info);
                    _this.value = undefined; 
        }

    },
}
</script>