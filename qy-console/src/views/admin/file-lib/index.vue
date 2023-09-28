<template>
    <div>
        <a-row>
            <a-page-header
                style="border: 1px solid rgb(235, 237, 240)"
                :title="'媒体库'"
                sub-title="This is a subtitle" 
            />
            <a-tabs default-active-key="1" @change="tabChange">
                <a-tab-pane   :tab="type.name" v-for=" type in typeList" :key="type.id">
                   <a-row>
                       <a-row  class="table-operations">
                            <a-col :xs="{span:24}"  :lg="{ span: 12}" >
                                <a-form-model layout="inline" :model="searchFrom"    >
                                        <a-form-model-item>
                                            <a-input v-model="searchFrom.searchText" placeholder="请输入标题">
                                            <a-icon slot="prefix" type="search" style="color:rgba(0,0,0,.25)" />
                                            </a-input>
                                        </a-form-model-item>
                                        <a-form-model-item :wrapper-col="{ span: 14 }">
                                                <a-button type="primary"  :loading ="searchLoading"  @click="searchForm('articleForm')">
                                                    搜索
                                                </a-button>
                                        </a-form-model-item>
                                </a-form-model>
                            </a-col>
                            <a-col  :xs="{span:24}"  :lg="{ span: 6, offset: 6 }" style=" margin-top: 5px;">
                                <a-button  type="primary"  @click="add()">
                                    上传文件
                                </a-button>
                                <template>
                                    <a-button v-if="targetTypeId>1" @click="onLoadMore" :disabled="isEOF" :loading="loading">
                                        {{loadMoreMessage}}
                                    </a-button>
                                </template>
                            </a-col>

                       </a-row>
                       <a-row>
                           <a-table :columns="columns"
                                :data-source="data"
                                :rowKey = "record =>  record.id?record.id:record.fname"
                                :pagination="pagination"
                                :loading="loading"
                                :scroll = "{ x:  800}"
                                :row-selection="{ selectedRowKeys: selectedIds, onChange: onSelectChange }"
                                @change="handleTableChange"
                                >
                                <span slot="fname" slot-scope="text, record"> 
                                    {{targetTypeId == 1? record.originFileName:record.fname }} 
                                </span>
                    
                                <span slot="fsize" slot-scope="text, record"> 
                                    {{getSize(record.fsize)}}
                                </span>
         

                                <span slot="action" slot-scope="text, record"> 
                                    <a  href="javascript:void(0)"  @click="moreInfo(record)" >详情</a>
                                </span>
                            </a-table>
                            <a-row :style="{'margin-top':(data.length==0?'10px':0)}" v-if="targetTypeId>1">
                                 <a-spin v-if="loading" />
                                 <a-button v-else @click="onLoadMore" :disabled="isEOF" >
                                    {{loadMoreMessage}}
                                </a-button>
                            </a-row>
                       </a-row>
                   </a-row>
                </a-tab-pane>
            </a-tabs>
        </a-row>
        <a-drawer   placement="right" :closable="false" :visible="visibleOfInfo" @close="onInfoClose">
            <template slot="title">{{targetObj.fname}}</template>
            <a-row style="margin-bottom: 20px;">
                <a-col class="imageWrapper">
                    <img class="previewImage" v-if="targetObj.mimeType && targetObj.mimeType.indexOf('image')==0" :src="targetObj.defUrl"  @error="loadError()" />
                    <img v-else :src="defImg" class="previewImage"/>
                 </a-col>
            </a-row>
            <a-descriptions title="" :column="1">
                <a-descriptions-item label="文件名" class="wordBreak">
                    {{targetObj.fname}}
                </a-descriptions-item>
                 <a-descriptions-item label="原始文件名" class="wordBreak">
                    {{targetObj.originFileName}}
                </a-descriptions-item>
               <a-descriptions-item label="文件类型" class="wordBreak">
                    {{targetObj.mimeType}}
                </a-descriptions-item>
                <a-descriptions-item label="ETag" class="wordBreak">
                    {{targetObj.fhash}}
                </a-descriptions-item>
                <a-descriptions-item label="文件大小" class="wordBreak">
                     {{getSize(targetObj.fsize)}}
                </a-descriptions-item>
                <a-descriptions-item v-if="targetTypeId>1" label="存储类型" class="wordBreak">
                   <template v-if="targetObj.status==0">
                       标准存储
                   </template>
                    <template v-if="targetObj.status==1">
                        低频存储
                    </template>
                </a-descriptions-item>
                <a-descriptions-item label="文件链接" class="wordBreak"> 

                    <span style="margin-right: 10px;" class="wordBreak"><a :href="targetObj.defUrl" target="_blank" rel="noopener"  >{{targetObj.defUrl}}</a></span>
                    <a-button class="copy_btn" type="dashed" icon="copy"  :data-clipboard-text="targetObj.defUrl" @click="copy">  </a-button>
                  
                   
                </a-descriptions-item>
                <a-descriptions-item label="最后更新" class="wordBreak">
                    {{targetObj.lastModifiedDate}}
                </a-descriptions-item>
            </a-descriptions>
        </a-drawer>
          <a-modal v-model="visibleSave" :maskClosable="false" :title="'上传文件到'+targetTypeName" :footer="null" @cancel="handleSaveCancel">
            <a-row>
                <a-upload-dragger
                    name="file"
                    :multiple="false"
                    :action="'api/admin/fileMeta/upload/'+targetTypeId"
                    @change="handleChange"
                    @preview="handlepreview"
                >
                    <p class="ant-upload-drag-icon">
                    <a-icon type="inbox" />
                    </p>
                    <p class="ant-upload-text">
                        将文件拖到此处，或点击上传
                    </p>
                    
                </a-upload-dragger>
            </a-row>
          </a-modal>
        <a-modal v-model="visiblePreview"  :title="'预览 '+previewObj.fname" :footer="null" @cancel="handlePreviewCancel">
            <a-row style="margin-bottom: 20px;">
                <a-col class="imageWrapper"> 
                    <img   :src="previewObj.defImg" class="previewImage"  @error="loadError()"/>
                 </a-col>
                 <a-descriptions title="" :column="1">
                     <a-descriptions-item label="文件链接" class="wordBreak"> 
                        <span style="margin-right: 10px;" class="wordBreak"><a :href="previewObj.defUrl" target="_blank" rel="noopener"  >{{previewObj.defUrl}}</a></span>
                        <a-button class="copy_btn" type="dashed" icon="copy"  :data-clipboard-text="previewObj.defUrl" @click="copy">  </a-button>
                    </a-descriptions-item>
                 </a-descriptions>
            </a-row>
            <a-row>

            </a-row>
        </a-modal>
    </div>
</template>

<script  >
import Vue from 'vue'
import Clipboard from 'clipboard';

const columns = [
                    {
                            title: '文件名',
                            dataIndex: 'fname',
                            key: 'fname', 
                            scopedSlots: { customRender: 'fname' },
                    },
                     {
                            title: '文件类型',
                            dataIndex: 'mimeType',
                            key: 'mimeType', 
                    },
                    {
                            title: '文件大小',
                            dataIndex: 'fsize',
                            key: 'fsize', 
                            scopedSlots: { customRender: 'fsize' },
                    },
                    {
                        title: '操作',
                        key: 'action',
                        scopedSlots: { customRender: 'action' },
                    },
                ]
export default Vue.extend({
    data() {
        return {
            columns,
            loading: false,
            typeList:{},
            targetTypeId:0,
            targetTypeName:"",
            targetObj: {},
            searchLoading: false,
            searchFrom: {
              searchText: null
            },
            selectedIds:[],
            data: [],
            pagination: {
              pageSize: 10,
              current: 1,
              total: 0
            },
            marker:null,
            isEOF: false,
            loadMoreMessage: "加载更多",
            visibleOfInfo: false,
            defImg:"/static/images/no-prev-file.png",
            currentPage:0,
            visibleSave:false,
            visiblePreview:false,
            previewObj:{}, 
        }
        
    },
    mounted() {
        let _this  = this; 
        _this.initTypeList();
    },
    methods: {
        initTypeList() {
            let _this = this;
            _this.typeLoading = true;
            let params = {
                status:  true, 
            }  
            _this.$axios.get('/admin/fileLibType/allList',
                { params: params}).then(res => {
                let resp  = res.data
                _this.typeList = resp.content; 
                _this.targetTypeId =  _this.typeList[0].id;
                _this.targetTypeName = _this.typeList[0].name;
                _this.typeLoading = false; 
                 _this.initData();
            }).catch((response) => {
                _this.typeLoading = false; 
            });
        },
        tabChange(e) {
            let _this = this;
            _this.data = [];
            _this.targetTypeId = e; 
            let len =  _this.typeList.length; 
            for(let i =0;i<len;i++) { 
                let t = _this.typeList[i]; 
                if(t.id == _this.targetTypeId) { 
                    _this.targetTypeName = t.name; 
                    break;
                }
            }  
             if(e<3) {
                 _this.initData();
             }
        },
        initData() {
            let _this = this;
            _this.marker  = null;
            _this.isEOF = false;
            if(_this.targetTypeId ==1) {
                // 本地
               _this.initFilelist({
                   page: 1,
                   size: _this.pagination.pageSize
               }); 
            }else {
                 _this.initFilelist({
                   marker: _this.marker ,
                   size: _this.pagination.pageSize
               }); 
            }
        },
        initFilelist(pageInfo) {
            let _this = this;
            if (_this.isEOF) {
                return;
            }
            _this.loading = true;
            let params = {
                searchText:  _this.searchFrom.searchText,
                typeId:  _this.targetTypeId,
                ...pageInfo
            }
            _this.$axios.get('/admin/fileMeta/'+ _this.targetTypeId+"/list",
                { params: params}).then(res => {
                let resp  = res.data;
                if(resp.success) {
                   
                    if(_this.targetTypeId == 1) { 
                         _this.data = resp.content.list;
                        _this.pagination.total =   resp.content.total;
                        _this.pagination.current =   resp.content.page;
                        _this.pagination.pageSize =   resp.content.size;
                        _this.isEOF = false;
                        if(_this.data.lenght==0 ||resp.content.page*resp.content.size >= resp.content.total) {
                             _this.isEOF = true;
                        }
                    } else {
                        if(_this.marker  == null) { 
                            _this.data = resp.content.list ;
                            _this.currentPage = 1;
                        } else {
                            _this.data = _this.data.concat(resp.content.list);
                            _this.currentPage = _this.currentPage+1;
                        } 
                        _this.pagination.current =   _this.currentPage;
                        _this.pagination.pageSize =   resp.content.size;
                        _this.marker = resp.content.marker;
                        _this.isEOF = resp.content.eofflag;
                        if(_this.isEOF) {
                             _this.loadMoreMessage = "暂无更多数据";
                        }
                    }
                   
                    _this.loading = false;
                    _this.selectedIds = [];
                }
            });
        },
        handleTableChange(pagination, filters, sorter ) {
            let _this = this;
            const pager = { ..._this.pagination };
            pager.current = pagination.current;
            this.pagination = pager;
            if(_this.targetTypeId == 1) {
                _this.list({
                    size: pagination.pageSize,
                    page: pagination.current,
                })
            } 
        },
        searchForm() {
            let _this = this;
            _this.initData();

        },
        add() {
            let _this = this;
            _this.visibleSave = true;
        },
        setRowKey(record) { 
        },
        moreInfo(obj) {
            let _this = this;
            _this.targetObj = obj;
            _this.visibleOfInfo = true;

        },
        onSelectChange(selectedRowKeys) {
            let _this = this;
            _this.selectedIds = selectedRowKeys;
        }, 
        onLoadMore() { 
            let _this = this;
            if(!_this.isEOF) {
                _this.loadMoreMessage = "加载更多";
                _this.initFilelist({
                    marker: _this.marker,
                    size: _this.pagination.pageSize
                }); 
            } else {
                _this.loadMoreMessage = "暂无更多数据";
            }

        },
        onInfoClose() {
            let _this = this;
            _this.visibleOfInfo = false;
        },
        loadError() {
            let _this = this;
            let img= event.srcElement; 
            img.src = _this.defImg;
            img.onerror = null; 
        },
        copy() {
            var clipboard = new Clipboard('.copy_btn')
            clipboard.on('success', e => {
                    let _this = this;
                _this.$message.destroy();
                _this.$message.success('复制成功',2);  

                // 释放内存
                clipboard.destroy()
            })

            clipboard.on('error', e => {
                    let _this = this;
                // 不支持复制 
                    _this.$message.destroy();
                _this.$message.error('该浏览器不支持自动复制',2);
                // 释放内存
                clipboard.destroy()
            })
        },
        getSize(size) {
            if (!size){
                return "";
            }
            var num = 1024.00; //byte
            if (size < num) {
                return size + "B";
            } else if (size < Math.pow(num, 2)) {
                return (size / num).toFixed(2) + "KB"; //kb
            } else if (size < Math.pow(num, 3)) {
                return (size / Math.pow(num, 2)).toFixed(2) + "MB"; //M
            } else if (size < Math.pow(num, 4)) {
                return (size / Math.pow(num, 3)).toFixed(2) + "G"; //G
            } else {
                return (size / Math.pow(num, 4)).toFixed(2) + "T"; //T
            }
               
            
        },
        handleSaveCancel() {
            let _this = this;
            _this.visibleSave = false;
        },
        handleChange(info) {
            console.log("handleChange info",info);
            const status = info.file.status;
            if (status === 'done') {
                this.$message.success(`${info.file.name} 文件上传成功`);
            } else if (status === 'error') {
                this.$message.error(`${info.file.name} 文件上传失败.`);
            }
        },
        handlepreview(info) {
            console.log("handlepreview info",info);
            const status = info.status;
            let _this = this; 
            if (status === 'done') {
                let data = info.response.content
                _this.visiblePreview = true;
                _this.previewObj = data; 
                _this.previewObj.defImg = (info.type.indexOf("image")==0)? _this.previewObj.defUrl: _this.defImg;
            }

        },
        handlePreviewCancel() {
            let _this = this;
            _this.visiblePreview = false;
        }
    }
})
</script>

<style lang="scss" scoped>
.table-operations  {
  button {
    margin-right: 8px;
  }
}
.imageWrapper {
    width: 40%; 
    padding-right: 10px;
    background-color: transparent;
    overflow: hidden;
    .previewImage{
        max-width: 100%;
        max-height: 210px;  
    }
}
.wordBreak{
    word-break:break-all;  
    word-wrap:break-word; 
}
</style>