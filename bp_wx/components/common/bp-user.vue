<template>
	<view class="flex bg-main" @click="onClick">
		<view class="mx-1 mt-1">
			<image class="rounded-circle border" style="width: 150rpx;height: 150rpx;" :src="imgUrl"></image>
		</view>
		<view class="flex-column">
			<view class="mt-2 ml-1">
				<text class="font-lg">{{username}}</text>
				<text class="font-sm">({{status==0?'博主':'用户'}})</text>
			</view>
			<view class="mt-1 ml-1">
				<text>随笔数量 : {{note}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			imgUrl: {
				type: String,
				default: ''
			},
			username: {
				type: String,
				default: ''
			},
			status: {
				type: Number,
				default: undefined
			},
			note: {
				type: Number,
				default: undefined
			}
		},
		methods: {
			onClick() {
				this.$emit('click');
			}
		}
	}
</script>

<style>
</style>
