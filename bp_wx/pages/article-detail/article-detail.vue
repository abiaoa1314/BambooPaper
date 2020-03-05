<template>
	<view class="page">
		<view v-if="userStatus=='0'">
			<view class="flex justify-between align-center">
				<ripple-button color="#cdcdcd" width='375' text='编辑' @btap="articleCompile"></ripple-button>
				<ripple-button color="#cdcdcd" width='375' text='删除' @btap="deleteArticle">></ripple-button>
			</view>
		</view>
		
		<bp-input @value="titleChange" type="text" :value='title' disabled></bp-input>
		<bp-input @value="synopsisChange" type="text" :value='synopsis' disabled></bp-input>

		<!-- 富文本编辑器 -->
		<editor id="editor" class="ql-container" placeholder="开始输入..." @ready="onEditorReady" read-only="true">
		</editor>
	</view>
</template>

<script>
	import bpInput from "@/components/common/bp-input.vue";
	import rippleButton from '../../components/ripple-button/ripple-button.vue';
	export default {
		components: {
			bpInput,
			rippleButton
		},
		data() {
			return {
				title: '',
				synopsis: '',
				content: '',
				aid: '',
				userStatus: ''
			}
		},
		// 监听页面加载
		onLoad(e) {
			this.aid = e.aid;
			this.userStatus = e.userStatus;
		},
		// 监听页面显示
		onShow() {
			this.$H.get('/article/getArticle/' + this.aid).then(res => {
				let [err, result] = res;
				if (result.data.code == '200') {
					this.title = result.data.data.title;
					this.synopsis = result.data.data.synopsis;
				}
			})
			this.$H.get('/article/getContent/' + this.aid).then(res => {
				let [err, result] = res;
				if (result.data.code == '200') {
					this.content = result.data.data;
				}
			})
		},
		methods: {
			articleCompile() {
				uni.navigateTo({
					url: '../article-compile/article-compile?aid=' + this.aid,
					animationType: 'slide-in-right',
					animationDuration: '200'
				})
			},
			onEditorReady() {
				uni.createSelectorQuery().select('#editor').context((res) => {
					this.editorCtx = res.context;
					this.editorCtx.setContents({
						html: this.content
					})
				}).exec();
			},
			deleteArticle() {
				this.$H.del('/article/deleteArticle/' + this.aid).then(res => {
					let [err, result] = res;
					if (result.data.code == '501') {
						uni.showModal({
							title: '错误提示',
							content: '服务器繁忙',
							showCancel: false
						})
						return;
					}
					if (result.data.code == '200') {
						// 修改用户信息
						this.$store.commit('alterUserInfo', result.data.data);
						uni.showModal({
							title: '删除成功',
							content: '文章删除成功,即将返回上一页',
							showCancel: false,
							success: (res) => {
								if (res.confirm) {
									uni.navigateBack({
										animationType: 'slide-out-left',
										animationDuration: '200'
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
	@import url("../../common/editor-icon.css");
</style>
