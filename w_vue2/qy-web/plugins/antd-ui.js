import Vue from 'vue' 
import { Row,Col,Layout,ConfigProvider,Menu,Drawer,
         Card,Tag,Popover,Input,Avatar,List,Pagination,
         Modal,FormModel,Button,BackTop,Notification,
         Comment,Icon,Tooltip} from 'ant-design-vue';
 



Vue.use(Col).use(Row).use(Layout)
    .use(ConfigProvider).use(Menu).use(Drawer)
    .use(Card).use(Tag).use(Popover)
    .use(Input).use(Avatar).use(List)
    .use(Pagination).use(FormModel).use(Modal)
    .use(Button).use(BackTop).use(Comment)
    .use(Notification).use(Icon).use(Tooltip);
 

if(!Vue.prototype.$notification) {
    Vue.prototype.$notification =Notification;
}


 
 
 // 后期有需要可以放开 
//     Vues.prototype.$message = Message;
//     Vues.prototype.$notification = Notification;
//     Vues.prototype.$info = Modal.info;
//     Vues.prototype.$success = Modal.success;
//     Vues.prototype.$error = Modal.error;
//     Vues.prototype.$warning = Modal.warning;
//     Vues.prototype.$confirm = Modal.confirm;
//     Vues.prototype.$destroyAll = Modal.destroyAll;
//  
 