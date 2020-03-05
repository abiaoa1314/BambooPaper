import Vue from 'vue'
import Vuex from 'vuex'
import $H from '@/common/request.js'

Vue.use(Vuex)

const store = new Vuex.Store({
	/* 全局变量定义处 */
	state:{	
		/* 用户是否登录 登录则true 反之则false */
		hasLogin:false,
		/* 用户基本信息 */
		userInfo:{}
	},
	/* 改变state */
	mutations:{
		alterHasLogin(state,e){
			state.hasLogin = e;
		},
		alterUserInfo(state,e){
			state.userInfo = e;
		}
	},
	/* 全局方法定义处 */
	actions:{
		login(context,user){
			$H.post('/user/login',user).then(res=>{
				let [err,result] = res;
				if(result.data.code == '200'){
					context.commit('alterHasLogin',true);
					context.commit('alterUserInfo',result.data.data);
				}
				if(result.data.code == '401'){
					uni.showModal({
						content:result.data.message,
						title:'错误消息',
						showCancel:false
					})
				}
			})
		},
		outLogin(context){
			context.commit('alterHasLogin',false);
			context.commit('alterUserInfo',{});
		}
	},
	getters:{
		doneHasLogin(state){
			return state.hasLogin;
		},
		doneUserInfo(state){
			return state.userInfo;
		}
	}
})

export default store
