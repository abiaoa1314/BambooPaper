<template>
	<view class="page">
		<view class="flex justify-between align-center">
			<ripple-button color="#cdcdcd" width='375' text='编辑' @btap="noteCompile"></ripple-button>
			<ripple-button color="#cdcdcd" width='375' text='删除' @btap="deleteNote">></ripple-button>
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
				nid: ''
			}
		},
		onLoad(e) {
			this.title = e.title;
			this.synopsis = e.synopsis;
			this.nid = e.nid;
			this.$H.get('/note/noteContent/' + e.nid).then(res => {
				let [err, result] = res;
				if (result.data.code == '200') {
					this.content = result.data.data;
				}
			})
		},
		methods: {
			noteCompile() {
				uni.navigateTo({
					url: '../note-compile/note-compile?title=' + this.title + '&synopsis=' + this.synopsis + '&nid=' + this.nid,
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
			deleteNote() {
				this.$H.del('/note/deleteNote/' + this.nid).then(res => {
					let [err, result] = res;
					if (result.data.code == '500') {
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
							content: '随笔删除成功,即将返回上一页',
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
