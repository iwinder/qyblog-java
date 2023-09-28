<template>
    <a-list item-layout="vertical" size="large" :pagination="paginationObj" :data-source="listData" data-server-rendered="true">
        <a-list-item slot="renderItem" key="item.title" slot-scope="item">
        
            <template   slot="extra" style="width:272px"> 
                <template v-if="item.category!=null">
                    <nuxt-link :to=" {name:'category-name',  params:{name:item.category.identifier, aid:item.category.id} }"     class="category-tag"> {{item.category.name}} </nuxt-link>
                </template>
                <nuxt-link  slot="title"  :to="{name:'name',params:{name:item.href, aid:item.id}}" class="img-a">
                                <img
                                width="272"
                                height="170"
                                alt="logo"
                                src="/img/image-pending.gif"
                                v-lazy="item.thumbnail"
                                data-server-rendered="true"
                                class="post-list-img lazy show"
                            />
                </nuxt-link>  
            </template>

        <template v-for="{ type, text } in actions" slot="actions">
            <span :key="type" v-if="type==='view-o'">
            <a-icon type="eye" style="margin-right: 8px" />
            {{ item.viewCount || text }} 
            </span>
            <span :key="type" v-else-if="type==='comment-o'">
                    <a-icon type="message" style="margin-right: 8px" />
            {{ item.commentCount || text }} 
            </span>
        </template>
            

            <a-list-item-meta >
                <template slot="description">
                    {{item.author.nickname}} {{item.publishedDateMD}}
                </template>
                <nuxt-link  slot="title"  :to="{name:'name',params:{name:item.href, aid:item.id}}"> 
                    <template v-if="item.status && item.status=='PRIVATE'">
                        私密：
                    </template>  {{item.title }}
                </nuxt-link>
            </a-list-item-meta>

            <a-row style="margin: 5px 0;">
                    <a-tag color="#f50" v-for=" tag in item.tags" :key="tag.id">
                    <nuxt-link :to=" {name:'tag-name',  params:{name:tag.identifier, aid:tag.id} }"> {{tag.name}} </nuxt-link>
                    </a-tag>
                
            </a-row> 
            <a-row class="post-summary">   {{ item.content }}...</a-row>
    </a-list-item>
  </a-list>
</template>

<script >
import Vue from 'vue' 
 
export default Vue.extend({
     async  asyncData (context) { 
     },
    props: { 
            pagination: {
                type:Object,
                default: {
                    pageSize: 3,
                    total: 0,
                    showLessItems: true,
                }
            },
            listData: {
                type: Array,
                default: [] 
            },
           actions: {
                  type: Array,
                 default: function() {
                     return  [
                            { type: 'view-o', text: '0' }, 
                            { type: 'comment-o', text: '0' },
                        ];
                 }
           },
            

    },
    watch: {
        pagination(val){  
            let _this = this;
            _this.paginationObj = val;
        }

    },
    mounted() {
        let _this = this; 
        _this.paginationObj = _this.pagination;

    },
    data() {
        return {
            paginationObj:{}
        }
    },

    
})
</script>

<style lang="scss" scoped>
.post-list-img{
     -webkit-transition: all .3s ease;
    -moz-transition: all .3s ease;
    -ms-transition: all .3s ease;
    -o-transition: all .3s ease;
    transition: all .3s ease;
}
.post-list-img:hover{
		transform: scale(1.2);
		/*transition: all 2s ease-in-out 0s;*/
}

img.lazy{
        opacity: .3;
    -webkit-transform: scale(.9);
    -moz-transform: scale(.9);
    transform: scale(.9);
    -webkit-transition: all ease-in-out .3s;
    -moz-transition: all ease-in-out .3s;
    transition: all ease-in-out .3s;
    -webkit-perspective-origin: top center;
    -moz-perspective-origin: top center;
    perspective-origin: top center;
}
img.show{
    opacity: 1;
    -webkit-transform: scale(1);
    -moz-transform: scale(1);
    transform: scale(1);
}
</style>