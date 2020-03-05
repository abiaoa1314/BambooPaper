<template>
	<view class="page">
		<bp-input @value="titleChange" placeholder="请输入标题" type="text"></bp-input>
		<bp-input @value="synopsisChange" placeholder="请输入简介" type="text"></bp-input>
		<bp-button text="保存专栏" @click='saveColumn'></bp-button>
	</view>
</template>

<script>
	import bpInput from "@/components/common/bp-input.vue";
	import bpButton from "@/components/common/bp-button.vue";
	export default {
		components:{
			bpInput,
			bpButton
		},
		data() {
			return {
				title:'',
				synopsis:''
			}
		},
		methods: {
			// 接收输入框的值
			titleChange(e){
				this.title = e;
			},
			synopsisChange(e){
				this.synopsis = e;
			},
			// 保存专栏信息
			saveColumn(){
				// 为空则不弹出提示信息
				if(this.title == ''){
					uni.showModal({
						title:'错误提示',
						content:'标题不能为空',
						showCancel:false
					})
					return;
				}
				if(this.synopsis == ''){
					uni.showModal({
						title:'错误提示',
						content:'简介不能为空',
						showCancel:false
					})
					return;
				}
				this.$H.post('/column/saveColumn/'+this.$store.state.userInfo.uid,{
					title:this.title,
					synopsis:this.synopsis
				}).then(res=>{
					let [err,result] = res;
					if(result.data.code == '501'){
						uni.showModal({
							title:'错误提示',
							content:'服务器繁忙',
							showCancel:false
						})
						return;
					}
					if(result.data.code == '200'){
						// 修改用户信息
						this.$store.commit('alterUserInfo',result.data.data);
						uni.showModal({
							title:'成功提示',
							content:'保存成功,将返回上一页.',
							showCancel:false,
							success: (res) => {
								if(res.confirm){
									uni.navigateBack({
										animationType:'slide-out-left',
										animationDuration:'200'
									})
								}
							}
						})
					}
				})
			}
		}
	}
</script>

<style>

</style>
