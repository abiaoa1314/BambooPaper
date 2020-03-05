<template>
	<view class="page">
		<!-- 头像 -->
		<view class="flex align-center justify-center p-2 bg-main">
			<image class="rounded-circle border" style="width: 150rpx;height: 150rpx;" :src="imgUrl"></image>
		</view>
		<view class="my-3"><bp-button text="修改头像" @click='resetImgurl'></bp-button></view>
		<view class="my-3"><bp-button text="保存头像" @click='saveImgurl'></bp-button></view>
	</view>
</template>

<script>
	import bpButton from "@/components/common/bp-button.vue";
	export default {
		components: {
			bpButton
		},
		data() {
			return {
				imgUrl: '',
				uId: ''
			}
		},
		// 监听页面加载
		onLoad() {
			this.imgUrl = this.$store.state.userInfo.imgUrl;
			this.uId = this.$store.state.userInfo.uid;
		},
		methods: {
			resetImgurl() {
				uni.chooseImage({
					count: 1,
					sizeType: ['compressed'],
					sourceType: ['album'],
					success: (res) => {
						this.imgUrl = res.tempFilePaths[0]
					}
				})
			},
			saveImgurl() {
				uni.uploadFile({
					url: this.$C.webUrl + '/user/resetImgUrl/' + this.uId,
					filePath: this.imgUrl,
					fileType: 'image',
					name: 'file',
					success: (res) => {
						// uni-app的小坑 , 返回来的data中 , 不是json数据 , 要转换成json数据
						let data = JSON.parse(res.data);
						if(data.code == '401' || data.code == '501'){
							uni.showModal({
								title:'错误提示',
								content:data.message,
								showCancel:false
							})
							return;
						}
						//只要上面没有return回去,就会执行这一步
						//变换userInfo中的信息
						this.$store.commit('alterUserInfo',data.data);
						if(data.code == '200'){
							uni.showModal({
								title:'成功',
								content:'修改头像成功,即将返回上一页',
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
					}
				});
			}
		}
	}
</script>

<style>

</style>
