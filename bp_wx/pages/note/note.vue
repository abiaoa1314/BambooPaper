<template>
	<view class="page">
		<!-- 搜索框 -->
		<uni-search-bar @confirm="confirm" @input="input" :cancelButton="hasLogin ? 'always' : 'none'" @cancel="compile"
		 cancelText="编辑" />
		<bp-divider></bp-divider>

		<!-- 卡片 -->
		<!-- 未登录时的卡片 -->
		<view v-if="!hasLogin">
			<uni-card title="提示" isFull="false" isShadow="true" mode="basic" @click="toLogin">您还未登录,请点击跳转登录页面.</uni-card>
			<bp-divider></bp-divider>
		</view>
		<!-- 没有笔记时的卡片 -->
		<view v-else-if="status == '0'">
			<uni-card title="提示" isFull="false" isShadow="true" mode="basic">您还没有随笔,请点击上方按钮编写.</uni-card>
			<bp-divider></bp-divider>
		</view>
		<!-- 有笔记时的卡片 -->
		<view v-else-if="status == '1'">
			<view v-for="(note,index) in noteList" :key="index">
				<uni-card :title="note.title" :extra="note.time" isFull="false" isShadow="true" mode="basic" @click="openNote(note)">{{note.synopsis}}</uni-card>
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
				noteList: [],
				// 随笔状态码 0:没有随笔 1:有随笔
				status: ''
			}
		},
		computed: {
			hasLogin() {
				return this.$store.getters.doneHasLogin;
			}
		},
		watch: {
			hasLogin(e) {
				// 监听hasLogin变化 , 只要登录状态为true , 则证明用户已经登录 , 向后端发送请求
				// 若是登录状态为false , 则证明用户没有登录 , 则向前端展示跳转登录
				if (e) {
					this.$H.get("/note/allNotes/" + this.$store.state.userInfo.uid).then(res => {
						let [err, result] = res;
						// 200则请求成功 , 可以进行数据渲染
						if (result.data.code == '200') {
							this.noteList = result.data.data;
							this.status = '1';
							return;
						}
						// 402则没有随笔 , 可以让用户去写
						if (result.data.code == '402') {
							this.status = '0';
							return;
						}
					})
				}
				//若是为false 则把status设成空
				if (!e) {
					this.status = '';
				}
			}
		},
		onPullDownRefresh() {
			if (this.hasLogin) {
				this.$H.get("/note/allNotes/" + this.$store.state.userInfo.uid).then(res => {
					let [err, result] = res;
					// 200则请求成功 , 可以进行数据渲染
					if (result.data.code == '200') {
						this.noteList = result.data.data;
						this.status = '1';
						return;
					}
					// 402则没有随笔 , 可以让用户去写
					if (result.data.code == '402') {
						this.status = '0';
						return;
					}
				})
				setTimeout(function () {
					uni.stopPullDownRefresh();
				}, 1000);
			}
		},
		// 监听页面显示
		onShow() {
			if (this.hasLogin) {
				this.$H.get("/note/allNotes/" + this.$store.state.userInfo.uid).then(res => {
					let [err, result] = res;
					// 200则请求成功 , 可以进行数据渲染
					if (result.data.code == '200') {
						this.noteList = result.data.data;
						this.status = '1';
						return;
					}
					// 402则没有随笔 , 可以让用户去写
					if (result.data.code == '402') {
						this.status = '0';
						return;
					}
				})
			}
		},
		methods: {
			search(e) {
				// 如果用户登录 , 则进行检索
				if (this.hasLogin) {
					if (e.value == '') {
						e.value = 'null';
					}
					this.$H.get('/note/search/' + e.value).then(res => {
						let [err, result] = res;
						this.noteList = result.data.data;
					})
				}
			},
			// 当数据框点击确定时 , 触发该事件
			confirm(e) {
				this.search(e);
			},
			// 当输入框输入的时候触发该事件
			input(e) {
				this.search(e);
			},
			// 跳转到登录界面
			toLogin() {
				// 点击则跳转到登录页面
				uni.switchTab({
					url: '../user/user'
				})
			},
			compile() {
				uni.navigateTo({
					url: '../note-compile/note-compile',
					animationType: 'slide-in-right',
					animationDuration: '200'
				})
			},
			openNote(note) {
				uni.navigateTo({
					url: '../note-detail/note-detail?title=' + note.title + '&synopsis=' + note.synopsis + '&nid=' + note.nid,
					animationType: 'slide-in-right',
					animationDuration: '200'
				})
			}
		}
	}
</script>

<style>

</style>
