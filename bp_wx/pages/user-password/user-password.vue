<template>
	<view class="page">
		<bp-input @value="oldPasswordChange" placeholder="请输入旧密码" type="password"></bp-input>
		<bp-input @value="newPasswordChange" placeholder="请输入新密码" type="password"></bp-input>
		<bp-button text="重设密码" @click='resetPassword'></bp-button>
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
				oldPassword:'',
				newPassword:''
			}
		},
		methods: {
			oldPasswordChange(e){
				this.oldPassword = e;
			},
			newPasswordChange(e){
				this.newPassword = e;
			},
			resetPassword(){
				this.$H.put('/user/reset',{
					account:this.$store.state.userInfo.account,
					oldPassword:this.oldPassword,
					newPassword:this.newPassword
				}).then(res=>{
					let [err,result] = res;
					if(result.data.code == '401'){
						uni.showModal({
							title:'错误提示',
							content:'密码输入错误',
							showCancel:false
						})
						return;
					}
					if(result.data.code == '200'){
						uni.showModal({
							title:'修改成功',
							content:'密码修改成功,即将返回登录页面',
							showCancel:false,
							success: (res) => {
								this.$store.commit('alterHasLogin',false)
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
