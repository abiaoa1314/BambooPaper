<template>
	<view class="flex align-center justify-center">
		<ripple-button width="750" color="#cdcdcd" :text="text" @btap="onClick"></ripple-button>
	</view>
</template>

<!-- 全局通用button组件封装 -->
<script>
	import rippleButton from "@/components/ripple-button/ripple-button.vue";
	export default{
		components:{
			rippleButton
		},
		props:{
			text:{
				type:String,
				default:''
			}
		},
		methods:{
			onClick(){
				this.$emit('click');
			}
		}
	}
</script>

<style>
</style>
