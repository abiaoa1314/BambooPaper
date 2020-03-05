<script>
	export default {
		onLaunch: function() {
			console.log('App Launch')
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		}
	}
</script>

<style>
	/*每个页面公共css */
	/* 引入自定义样式 */
	@import url("/common/free.css");
	/* 引入官方样式 */
	@import url("/common/uni.css");
	/* 引入本项目公共样式 */
	@import url("/common/common.css");
	/* 引入animate动画库 */
	@import url("/common/animate.css");
	/* 引入icon图标库 */
	@import url("/common/iconfont.css");
</style>
