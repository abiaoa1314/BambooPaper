<template>
	<view class="page">
		<!-- 搜索框 -->
		<uni-search-bar @confirm="confirm" @input="input" :cancelButton="userStatus=='0' ? 'always' : 'none'" @cancel="compile"
		 cancelText="编辑" />
		<bp-divider></bp-divider>
		
		<!-- 没有专栏时的卡片 -->
		<view v-if="status == '0'">
			<uni-card title="提示" isFull="false" isShadow="true" mode="basic">当前还没有专栏</uni-card>
			<bp-divider></bp-divider>
		</view>
		<!-- 有专栏时的卡片 -->
		<view v-else-if="status == '1'">
			<view v-for="(column,index) in columnList" :key="index">
				<uni-card :title="column.title" :extra="'文章数量 : '+column.article" isFull="false" isShadow="true" mode="basic" @click="openColumn(column.cid)">{{column.synopsis}}</uni-card>
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
				columnList:[],
				status:'1'
			}
		},
		// 监听页面显示
		onShow() {
			this.allColumn();
		},
		// 监听用户下拉
		onPullDownRefresh() {
			this.allColumn();
			setTimeout(function () {
				uni.stopPullDownRefresh();
			}, 1000);
		},
		// 计算用户的状态码 0是博主,可以显示编辑,1是访客,不能显示编辑
		computed:{
			userStatus(){
				let userInfo = this.$store.getters.doneUserInfo;
				console.log(userInfo);
				if(userInfo.status == 0) return '0';
				if(userInfo.status == undefined) return '1';
				if(userInfo.status == 1) return '1';
			}
		},
		methods: {
			allColumn(){
				this.$H.get('/column/allColumn').then(res=>{
					let [err,result] = res;
					// 200则请求成功 , 可以进行数据渲染
					if (result.data.code == '200') {
						this.columnList = result.data.data;
						this.status = '1';
						return;
					}
					// 402则没有专栏 , 可以让用户去写
					if (result.data.code == '402') {
						this.status = '0';
						return;
					}
				})
			},
			search(e){
				if (e.value == '') {
					e.value = 'null';
				}
				this.$H.get('/column/search/'+e.value).then(res=>{
					let [err,result] = res;
					this.columnList = result.data.data;
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
			compile(){
				uni.navigateTo({
					url:'../column-add/column-add',
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			},
			openColumn(e){
				uni.navigateTo({
					url:'../article-list/article-list?cid='+e+'&userStatus='+this.userStatus,
					animationType:'slide-in-right',
					animationDuration:'200'
				})
			}
		}
	}
</script>

<style>

</style>
