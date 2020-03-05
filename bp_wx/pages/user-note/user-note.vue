<template>
	<view class="page">
		<!-- 头像和昵称栏 -->
		<view class="flex align-center justify-center p-2 bg-main">
			<image class="rounded-circle border" style="width: 150rpx;height: 150rpx;" :src="user.imgUrl"></image>
		</view>
		<view class="flex align-center justify-center pb-2 bg-main">
			<text class="font">{{user.username}}</text>
		</view>
		<!-- 搜索框 -->
		<uni-search-bar @confirm="confirm" @input="input" cancelButton="always" @cancel="back" cancelText="返回" />
		<bp-divider></bp-divider>
		<!-- 没有笔记时的卡片 -->
		<view v-if="status == '0'">
			<uni-card title="提示" isFull="false" isShadow="true" mode="basic">此用户没有随笔</uni-card>
			<bp-divider></bp-divider>
		</view>
		<!-- 有笔记时的卡片 -->
		<view v-else-if="status == '1'">
			<view v-for="(note,index) in noteList" :key="index">
				<uni-card :title="note.title" :extra="note.time" isFull="false" isShadow="true" mode="basic" @click="openNote(note.nid)">{{note.synopsis}}</uni-card>
				<bp-divider></bp-divider>
			</view>
		</view>
	</view>
</template>

<script>
	import uniCard from "@/components/uni-ui/uni-card/uni-card.vue";
	import bpDivider from "@/components/common/bp-divider.vue";
	import uniSearchBar from "@/components/uni-ui/uni-search-bar/uni-search-bar.vue";
	export default {
		components: {
			uniCard,
			bpDivider,
			uniSearchBar
		},
		data() {
			return {
				user: '',
				noteList: [],
				status: '0'
			}
		},
		onLoad(e) {
			this.getUser(e.uid);
			this.getNote(e.uid);
		},
		methods: {
			getUser(uid) {
				this.$H.get('/user/getUserByUId/' + uid).then(res => {
					let [err, result] = res;
					if (result.data.code = '200') {
						this.user = result.data.data;
					}
				})
			},
			getNote(uid) {
				this.$H.get('/note/allNotes/' + uid).then(res => {
					let [err, result] = res;
					if (result.data.code == '200') {
						this.noteList = result.data.data;
						this.status = '1';
					} else {
						this.status = '0';
					}
				})
			},
			back() {
				uni.navigateBack({
					animationType: 'slide-out-left',
					animationDuration: '200'
				})
			},
			search(e) {
				if (e.value == '') {
					e.value = 'null';
				}
				this.$H.get('/note/search/' + e.value).then(res => {
					let [err, result] = res;
					this.noteList = result.data.data;
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
			openNote(e){
				uni.navigateTo({
					url:'../user-ndetail/user-ndetail?nid='+e,
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			}
		}
	}
</script>

<style>

</style>
