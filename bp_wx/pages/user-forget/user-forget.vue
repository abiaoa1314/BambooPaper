<template>
	<view class="page">
		<view class="flex align-center justify-center mt-5">
			<image class="rounded-circle border" style="width: 200rpx;height: 200rpx;" src="../../static/logo/logo.png"></image>
		</view>
		<bp-input @value="accountChange" placeholder="请输入手机号" type="text"></bp-input>
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
				account:'',
				oldPassword:'',
				newPassword:''
			}
		},
		methods: {
			accountChange(e){
				this.account = e;
			},
			oldPasswordChange(e){
				this.oldPassword = e;
			},
			newPasswordChange(e){
				this.newPassword = e;
			},
			resetPassword(){
				this.$H.put('/user/reset',{
					account:this.account,
					oldPassword:this.oldPassword,
					newPassword:this.newPassword
				}).then(res=>{
					let [err,result] = res;
					console.log(result.data)
					if(result.data.code == '401'){
						uni.showModal({
							title:'错误提示',
							content:result.data.message,
							showCancel:false
						})
					}
					if(result.data.code == '200'){
						uni.showModal({
							title:'修改成功',
							content:'密码修改成功,即将返回上一页',
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
