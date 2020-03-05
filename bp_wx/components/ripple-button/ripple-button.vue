<style scoped>
	.wrap {
		overflow: hidden;
		position: relative;
		box-shadow: 0px 1px 2px 1px #d3d2d3;
		user-select: none;
		color: white;
		font-size: 32rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.ripple {
		position: absolute;
		background-color: rgba(255, 255, 255, 0.3);
		border-radius: 100%;
		top: 0rpx;
		left: 0rpx;
		transform: scale(0);
	}

	.animation {
		animation: ripple 0.6s linear;
	}

	@keyframes ripple {
		from {
			transform: scale(0.0);
			opacity: 1;
		}

		to {
			transform: scale(2);
			/*因为涟漪的大小为标签的最长边，为了保证点击标签边缘时，涟漪也能覆盖整个标签，scale值最小应为2*/
			opacity: 0;
		}
	}

	.wrap_img {
		width: 30rpx;
		height: 30rpx;
		margin-right: 20rpx;
	}
</style>

<template>
	<view class="wrap" @longpress.native="handlePress($event)" @tap.native="handleTap($event)" :style="{'width':width+'rpx','height':height+'rpx','background':(plain?'#ffffff':color),'border':(plain?'1rpx solid'+color:'0px')}">
		<image v-if="iconImage!=''" :src="iconImage" mode="aspectFit" class="wrap_img">
			<label :style="{'color':(plain?color:'#ffffff')}"> {{text}}</label>
			<view class="ripple" :class="{'animation':showAnimate?true:false}" :style="{'top':marginTop+'px','left':marginLeft+'px','width':rippleWidth+'px','height':rippleHeight+'px','background-color':(plain?color:'rgba(255, 255, 255, 0.3)')}">
			</view>
	</view>
</template>

<script>
	export default {
		props: {
			width: {
				type: Number,
				default: 400
			},
			height: {
				type: Number,
				default: 80
			},
			color: {
				type: String,
				default: "#45319D"
			},
			text: {
				type: String,
				default: "确定"
			},
			iconImage: {
				type: String,
				default: ""
			},
			plain: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				showAnimate: false,
				marginTop: 0,
				marginLeft: 0,
				rippleWidth: uni.upx2px(this.width),
				rippleHeight: uni.upx2px(this.width),
				backOpcity: ""
			}
		},
		mounted() {},
		methods: {
			//产生涟漪效果动画
			showRipple(e) {
				this.showAnimate = false
				let scorllTop = 0
				uni.createSelectorQuery().selectViewport().scrollOffset(res => {
					scorllTop = res.scrollTop
				}).exec();
				let view = uni.createSelectorQuery().in(this).select(".wrap");
				view.boundingClientRect(data => {
					this.showAnimate = true
					this.marginTop = e.touches[0].pageY - (scorllTop + data.top) - this.rippleHeight / 2
					this.marginLeft = e.touches[0].pageX - data.left - this.rippleWidth / 2
				}).exec();
			},
			//轻触事件
			handleTap(e) {
				this.showRipple(e);
				this.$emit("btap", this.text);
			},
			//长按事件
			handlePress(e) {
				this.showRipple(e);
			}
		}
	}
</script>
