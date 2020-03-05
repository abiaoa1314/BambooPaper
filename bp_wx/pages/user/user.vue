<template>
	<view class="page">
		<view v-if="hasLogin">
			<!-- 头像和昵称栏 -->
			<view class="flex align-center justify-center p-2 bg-main">
				<image class="rounded-circle border" style="width: 150rpx;height: 150rpx;" :src="userInfo.imgUrl"></image>
			</view>
			<view class="flex align-center justify-center pb-2 bg-main">
				<text class="font">{{userInfo.username}}</text>
			</view>
			<bp-divider></bp-divider>
			<!-- 文章栏 -->
			<view class="flex justify-center align-center bg-main" style="height: 100rpx;">
				<text class="flex-1 font text-center">专栏</text>
				<text class="flex-1 font text-center">文章</text>
				<text class="flex-1 font text-center">随笔</text>
			</view>
			<view class="flex justify-center align-center bg-main" style="margin-top: -30rpx;">
				<text class="flex-1 font-lg font-weight-bold text-center">{{userInfo.column}}</text>
				<text class="flex-1 font-lg font-weight-bold text-center">{{userInfo.article}}</text>
				<text class="flex-1 font-lg font-weight-bold text-center">{{userInfo.note}}</text>
			</view>
			
			<bp-divider></bp-divider>
			<!-- 修改栏 -->
			<view class="bg-main border-bottom" >
				<uni-list-item title="修改密码" showExtraIcon @click="alterPassword">
					<text slot="icon" class="iconfont icon-suoding"></text>
				</uni-list-item>
			</view>
			<view class="bg-main border-bottom" >
				<uni-list-item title="修改昵称" showExtraIcon @click="alterUserName">
					<text slot="icon" class="iconfont icon-biaoqian"></text>
				</uni-list-item>
			</view>
			<view class="bg-main border-bottom" >
				<uni-list-item title="修改头像" showExtraIcon @click="alterImgUrl">
					<text slot="icon" class="iconfont icon-tupian"></text>
				</uni-list-item>
			</view>
			<view class="bg-main border-bottom" >
				<uni-list-item title="查看用户" showExtraIcon @click="allUser">
					<text slot="icon" class="iconfont icon-shouji"></text>
				</uni-list-item>
			</view>
			<view class="bg-main border-bottom" >
				<uni-list-item title="退出登录" showExtraIcon @click="outLogin">
					<text slot="icon" class="iconfont icon-daochu"></text>
				</uni-list-item>
			</view>
		</view>
		<view v-else>
			<view class="flex align-center justify-center mt-5">
				<image class="rounded-circle border" style="width: 200rpx;height: 200rpx;" src="../../static/logo/logo.png"></image>
			</view>
			<bp-input @value="accountChange" placeholder="请输入手机号" type="text"></bp-input>
			<bp-input @value="passwordChange" placeholder="请输入密码" type="password"></bp-input>
			<view class="my-3"><bp-button text="登录/注册" @click='login'></bp-button></view>
			<view class="my-3"><bp-button text="忘记密码?" @click='toForgetPassword'></bp-button></view>
		</view>
	</view>
</template>

<script>
	import bpInput from "@/components/common/bp-input.vue";
	import bpButton from "@/components/common/bp-button.vue";
	import uniListItem from "@/components/uni-ui/uni-list-item/uni-list-item.vue";
	import bpDivider from "@/components/common/bp-divider.vue";
	export default {
		components:{
			bpInput,
			bpButton,
			uniListItem,
			bpDivider
		},
		data() {
			return {
				account:'',
				password:''
			}
		},
		computed:{
			hasLogin(){
				return this.$store.getters.doneHasLogin;
			},
			userInfo(){
				return this.$store.getters.doneUserInfo;
			}
		},
		methods: {
			login(){
				let user = {account:this.account,password:this.password}
				this.$store.dispatch('login',user);
			},
			accountChange(e){
				this.account = e;
			},
			passwordChange(e){
				this.password = e;
			},
			toForgetPassword(){
				uni.navigateTo({
					url:'../user-forget/user-forget',
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			},
			alterPassword(){
				uni.navigateTo({
					url:'../user-password/user-password',
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			},
			alterUserName(){
				uni.navigateTo({
					url:'../user-username/user-username',
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			},
			alterImgUrl(){
				uni.navigateTo({
					url:'../user-imgurl/user-imgurl',
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			},
			outLogin(){
				this.$store.dispatch('outLogin');
			},
			allUser(){
				uni.navigateTo({
					url:'../user-all/user-all',
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			}
		}
	}
</script>

<style>

</style>
