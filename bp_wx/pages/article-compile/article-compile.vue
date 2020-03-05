<template>
	<view class="page">
		<bp-button text="保存" @click="saveNote"></bp-button>
		<bp-input @value="titleChange" :value="title?title:''" placeholder="请输入标题" type="text"></bp-input>
		<bp-input @value="synopsisChange" :value="synopsis?synopsis:''" placeholder="请输入简介" type="text"></bp-input>
		
		<!-- 富文本编辑器 -->
		<view class='toolbar' @click="format">
			<view :class="formats.bold ? 'ql-active' : ''" class="iconfont icon-zitijiacu" data-name="bold"></view>
			<view :class="formats.italic ? 'ql-active' : ''" class="iconfont icon-zitixieti" data-name="italic"></view>
			<view :class="formats.backgroundColor === '#00ff00' ? 'ql-active' : ''" class="iconfont icon-fontbgcolor" data-name="backgroundColor"
			 data-value="#00ff00"></view>
			<view class="iconfont icon--checklist" data-name="list" data-value="check"></view>
			<view :class="formats.list === 'ordered' ? 'ql-active' : ''" class="iconfont icon-youxupailie" data-name="list"
			 data-value="ordered"></view>
			<view :class="formats.list === 'bullet' ? 'ql-active' : ''" class="iconfont icon-wuxupailie" data-name="list"
			 data-value="bullet"></view>
			<view class="iconfont icon-fengexian" @click="insertDivider"></view>
			<view class="iconfont icon-charutupian" @click="insertImage"></view>
		</view>

		<editor id="editor" class="ql-container" placeholder="开始输入..." showImgSize showImgToolbar showImgResize @statuschange="onStatusChange"
		 :read-only="readOnly" @ready="onEditorReady">
		</editor>

	</view>
</template>

<script>
	import bpInput from "@/components/common/bp-input.vue";
	import bpButton from "../../components/common/bp-button.vue";
	export default {
		components: {
			bpInput,
			bpButton
		},
		data() {
			return {
				title: '',
				synopsis: '',
				readOnly: false,
				formats: {},
				content: '',
				aid: '',
				cid:''
			}
		},
		// 监听页面初次加载
		onLoad(e) {
			this.aid = e.aid;
			this.cid = e.cid;
			if(this.aid != undefined){
				this.$H.get('/article/getArticle/'+this.aid).then(res=>{
					let [err,result] = res;
					if(result.data.code == '200'){
						this.title = result.data.data.title;
						this.synopsis = result.data.data.synopsis;
					}
				})
				this.$H.get('/article/getContent/'+this.aid).then(res=>{
					let [err,result] = res;
					if(result.data.code == '200'){
						this.content = result.data.data;
					}
				})
			}
		},
		methods: {
			titleChange(e) {
				this.title = e;
			},
			synopsisChange(e) {
				this.synopsis = e;
			},
			readOnlyChange() {
				this.readOnly = !this.readOnly
			},
			onEditorReady() {
				uni.createSelectorQuery().select('#editor').context((res) => {
					this.editorCtx = res.context;
					if (this.content != '') {
						this.editorCtx.setContents({
							html: this.content
						})
					}
				}).exec()
			},
			format(e) {
				let {
					name,
					value
				} = e.target.dataset
				if (!name) return
				// console.log('format', name, value)
				this.editorCtx.format(name, value)
			},
			onStatusChange(e) {
				const formats = e.detail
				this.formats = formats
			},
			insertDivider() {
				this.editorCtx.insertDivider();
			},
			insertImage() {
				uni.chooseImage({
					count: 1,
					success: (res) => {
						var imageSrc = res.tempFilePaths[0];
						uni.uploadFile({
							url: this.$C.webUrl + '/upload',
							filePath: imageSrc,
							fileType: 'image',
							name: 'file',
							success: (res) => {
								let data = JSON.parse(res.data);
								console.log(data);
								this.editorCtx.insertImage({
									src: data.data,
									alt: '图像',
									success: function() {
										console.log('insert image success')
									}
								})
							}
						})
					}
				})
			},
			saveNote() {
				if (this.title == '') {
					uni.showModal({
						title: '错误提示',
						content: '标题不能为空',
						showCancel: false
					});
					return;
				}
				if (this.synopsis == '') {
					uni.showModal({
						title: '错误提示',
						content: '简介不能为空',
						showCancel: false
					});
					return;
				}
				this.editorCtx.getContents({
					success: (res) => {
						if (this.aid == undefined) {
							this.$H.post('/article/saveArticle/' + this.cid, {
								title: this.title,
								synopsis: this.synopsis,
								content: res.html
							}).then(res => {
								let [err, result] = res;
								if (result.data.code == '501') {
									uni.showModal({
										title: '错误提示',
										content: '网络繁忙,请稍后重试!',
										showCancel: false
									})
									return;
								}
								if(result.data.code == '200'){
									// 修改用户信息
									this.$store.commit('alterUserInfo',result.data.data);
									uni.showModal({
										title: '成功提示',
										content: '保存成功,即将返回上一页',
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
						} else {
							this.$H.put('/article/alterArticle/' + this.aid, {
								title: this.title,
								synopsis: this.synopsis,
								content: res.html
							}).then(res => {
								let [err, result] = res;
								if (result.data.code == '501') {
									uni.showModal({
										title: '错误提示',
										content: '网络繁忙,请稍后重试!',
										showCancel: false
									})
									return;
								}
								if(result.data.code == '200'){
									uni.showModal({
										title: '成功提示',
										content: '保存成功,即将返回.',
										showCancel: false,
										success: (res) => {
											if (res.confirm) {
												uni.navigateBack({
													animationType:'slide-out-left',
													animationDuration:'200',
													delta:2
												})
											}
										}
									})
								}
							})
						}
					}
				})
			}
		}
	}
</script>

<style>
	@import url("../../common/editor-icon.css");
</style>
