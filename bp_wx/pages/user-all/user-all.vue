<template>
	<view class="page">
		<!-- 搜索框 -->
		<uni-search-bar @confirm="confirm" @input="input" cancelButton="always" @cancel="back" cancelText="返回" />
		<bp-divider></bp-divider>

		<uni-swipe-action v-for="(user,index) in userList" :key="index">
			<uni-swipe-action-item :options="options" :auto-close="false" @click="bindClick($event,index)">
				<bp-user :imgUrl="user.imgUrl" :username="user.username" :status="user.status" :note="user.note"></bp-user>
			</uni-swipe-action-item>
			<bp-divider></bp-divider>
		</uni-swipe-action>
	</view>
</template>

<script>
	import bpDivider from "@/components/common/bp-divider.vue";
	import bpUser from "@/components/common/bp-user.vue";
	import uniSearchBar from "@/components/uni-ui/uni-search-bar/uni-search-bar.vue";
	import uniSwipeAction from "@/components/uni-ui/uni-swipe-action/uni-swipe-action.vue";
	import uniSwipeActionItem from "@/components/uni-ui/uni-swipe-action-item/uni-swipe-action-item.vue";
	export default {
		components: {
			bpDivider,
			bpUser,
			uniSearchBar,
			uniSwipeAction,
			uniSwipeActionItem
		},
		data() {
			return {
				userList: [],
				options1: [{
					text: '查看',
					style: {
						backgroundColor: '#007aff'
					}
				}],
				options2: [{
					text: '查看',
					style: {
						backgroundColor: '#007aff'
					}
				}, {
					text: '博主',
					style: {
						backgroundColor: '#dd524d'
					}
				}, {
					text: '用户',
					style: {
						backgroundColor: '#cfb56a'
					}
				}],
				options: []
			}
		},
		// 监听页面加载
		onLoad() {
			if (this.$store.state.userInfo.status == 0) {
				this.options = this.options2;
			} else {
				this.options = this.options1;
			}
			this.getAllUser();
		},
		// 监听用户下拉
		onPullDownRefresh() {
			this.getAllUser();
			setTimeout(function() {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		methods: {
			back() {
				uni.navigateBack({
					animationType: 'slide-out-left',
					animationDuration: '200'
				})
			},
			getAllUser() {
				this.$H.get('/user/getAllUser').then(res => {
					let [err, result] = res;
					if (result.data.code == '200') {
						this.userList = result.data.data;
					}
				})
			},
			openUser(e) {
				uni.navigateTo({
					url: '../user-note/user-note?uid=' + e,
					animationType: 'slide-in-right',
					animationDuration: '200'
				})
			},
			search(e) {
				if (e.value == '') {
					e.value = 'null';
				}
				this.$H.get('/user/getUserByUsername/' + e.value).then(res => {
					let [err, result] = res;
					if (result.data.code == '200') {
						this.userList = result.data.data;
					}
				})
			},
			// 当数据框点击确定时 , 触发该事件
			confirm(e) {
				this.search(e);
			},
			// 当输入框输入的时候触发该事件
			input(e) {
				this.search(e);
			},
			bindClick(e, index) {
				let text = e.content.text;
				if (text == '查看') {
					this.openUser(this.userList[index].uid)
				}else if(text == '博主'){
					this.alterZero(this.userList[index].uid);
				}else if(text == '用户'){
					this.alterOne(this.userList[index].uid);
				}
			},
			alterZero(uid){
				this.$H.put('/user/putUserZero/'+uid).then(res=>{
					let [err,result] = res;
					if(result.data.code == '200'){
						uni.showModal({
							title:'提示',
							content:'修改成功',
							showCancel:false
						})
					}else{
						uni.showModal({
							title:'提示',
							content:'修改失败',
							showCancel:false
						})
					}
				})
			},
			alterOne(uid){
				this.$H.put('/user/putUserOne/'+uid).then(res=>{
					let [err,result] = res;
					if(result.data.code == '200'){
						uni.showModal({
							title:'提示',
							content:'修改成功',
							showCancel:false
						})
					}else{
						uni.showModal({
							title:'提示',
							content:'修改失败',
							showCancel:false
						})
					}
				})
			}
		},
	}
</script>

<style>

</style>
