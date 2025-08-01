import 'core-js/stable'
import Vue from 'vue'
import Element from 'element-ui'
import App from '@/App'
import router from '@/router'
import store from '@/store'
import '@/icons'
import '@/element-ui/theme/index.css'
import '@/assets/scss/aui.scss'
import http from '@/utils/request'
import { hasPermission } from '@/utils'
import cloneDeep from 'lodash/cloneDeep'
Vue.config.productionTip = false;
Vue.use(Element);
// 挂载全局 后续可以直接调用 $http 来直接使用
Vue.prototype.$http = http;
Vue.prototype.$hasPermission = hasPermission;
// 保存整站vuex本地储存初始状态
window.SITE_CONFIG['storeState'] = cloneDeep(store.state);
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app');