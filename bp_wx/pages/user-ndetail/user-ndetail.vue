<template>
	<view class="page">
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
				content: ''
			}
		},
		onLoad(e) {
			this.getContent(e.nid);
			this.getNote(e.nid);
		},
		methods: {
			onEditorReady() {
				uni.createSelectorQuery().select('#editor').context((res) => {
					this.editorCtx = res.context;
					this.editorCtx.setContents({
						html: this.content
					})
				}).exec();
			},
			getContent(nid){
				this.$H.get('/note/noteContent/' + nid).then(res => {
					let [err, result] = res;
					if (result.data.code == '200') {
						this.content = result.data.data;
					}
				})
			},
			getNote(nid){
				this.$H.get('/note/getNote/' + nid).then(res => {
					let [err, result] = res;
					if (result.data.code == '200') {
						this.title = result.data.data.title;
						this.synopsis = result.data.data.synopsis;
					}
				})
			}
		}
	}
</script>

<style>
	@import url("../../common/editor-icon.css");
</style>
