<template>
	<view class="page">
		<bp-input @value="usernameChange" placeholder="请输入新昵称" type="text"></bp-input>
		<bp-button text="修改昵称" @click='resetUsername'></bp-button>
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
				username:''
			}
		},
		methods: {
			usernameChange(e){
				this.username = e;
			},
			resetUsername(){
				this.$H.put('/user/resetUsername',{
					uId:this.$store.state.userInfo.uid,
					username:this.username
				}).then(res=>{
					let [err,result] = res;
					if(result.data.code == '401'){
						uni.showModal({
							title:'错误提示',
							content:result.data.message,
							showCancel:false
						})
						return;
					}
					if(result.data.code == '200'){
						this.$store.commit('alterUserInfo',result.data.data);
						uni.showModal({
							title:'成功',
							content:'修改昵称成功',
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
