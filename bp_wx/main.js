import Vue from 'vue'
import App from './App'

/* 引入vuex */
import store from './store'
Vue.prototype.$store = store

/* 引入配置文件 */
import $C from './common/config.js'
Vue.prototype.$C = $C

/* 引入全局网络请求 */
import $H from './common/request.js'
Vue.prototype.$H = $H

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
		/* 挂载vuex */
		store,
		...App
})
app.$mount()
