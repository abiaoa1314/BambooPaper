<template>
	<view class="page">
		<!-- 搜索框 -->
		<uni-search-bar @confirm="confirm" @input="input" :cancelButton="userStatus=='0' ? 'always' : 'none'" @cancel="compile"
		 cancelText="编辑" />
		<view v-if="userStatus=='0'">
			<bp-button text="删除专栏" @click='deleteColumn'></bp-button>
		</view>
		<view v-else>
			<bp-divider></bp-divider>
		</view>
		<!-- 没有文章时的卡片 -->
		<view v-if="status == '0'">
			<uni-card title="提示" isFull="false" isShadow="true" mode="basic">此专栏下还没有文章</uni-card>
			<bp-divider></bp-divider>
		</view>
		<!-- 有文章时的卡片 -->
		<view v-else-if="status == '1'">
			<view v-for="(article,index) in articleList" :key="index">
				<uni-card :title="article.title" :extra="article.time" isFull="false" isShadow="true" mode="basic" @click="openArticle(article.aid)">{{article.synopsis}}</uni-card>
				<bp-divider></bp-divider>
			</view>
		</view>
	</view>
</template>

<script>
	import uniCard from "@/components/uni-ui/uni-card/uni-card.vue";
	import bpDivider from "@/components/common/bp-divider.vue";
	import uniSearchBar from "@/components/uni-ui/uni-search-bar/uni-search-bar.vue";
	import bpButton from "@/components/common/bp-button.vue";
	export default {
		components: {
			uniCard,
			bpDivider,
			uniSearchBar,
			bpButton
		},
		// 监听页面加载
		onLoad(e) {
			this.cid = e.cid;
			this.userStatus = e.userStatus;
		},
		// 监听页面显示
		onShow() {
			this.allArticle();
		},
		// 监听用户下拉
		onPullDownRefresh() {
			this.allArticle();
			setTimeout(function () {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		data() {
			return {
				cid:'',
				articleList:[],
				userStatus:'',
				status:''
			}
		},
		methods: {
			allArticle(){
				this.$H.get('/article/getAllArticle/'+this.cid).then(res=>{
					let [err,result] = res;
					if(result.data.code == '200'){
						this.articleList = result.data.data;
						this.status = '1';
						return;
					}
					if(result.data.code == '402'){
						this.status = '0';
					}
				})
			},
			search(e){
				if (e.value == '') {
					e.value = 'null';
				}
				this.$H.get('/article/searchArticle?cId='+this.cid + '&title='+e.value).then(res=>{
					let [err,result] = res;
					this.articleList = result.data.data;
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
			// 跳转到新增文章
			compile(){
				uni.navigateTo({
					url: '../article-compile/article-compile?cid='+this.cid,
					animationType: 'slide-in-right',
					animationDuration: '200'
				})
			},
			// 删除专栏
			deleteColumn(){
				this.$H.del('/column/deleteColumn/'+this.cid).then(res=>{
					let [err,result] = res;
					if(result.data.code == '200'){
						// 改变用户信息
						this.$store.commit('alterUserInfo',result.data.data);
						uni.showModal({
							title:'成功提示',
							content:'保存成功,将返回上一页.',
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
				})
			},
			// 跳转到文章详情,携带用户状态和文章id
			openArticle(e){
				uni.navigateTo({
					url: '../article-detail/article-detail?aid='+e+'&userStatus='+this.userStatus,
					animationType: 'slide-in-right',
					animationDuration: '200'
				})
			}
		}
	}
</script>

<style>

</style>
