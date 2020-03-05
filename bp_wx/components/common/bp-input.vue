<template>
	<view class="flex align-center justify-center my-3">
		<input style="height: 70rpx;" class="uni-input rounded border bg-main" :value="value" :disabled="disabled" @click="onClick" @input="onInput" :type="type" :placeholder="placeholder" />
	</view>
</template>

<!-- 全局通用输入框组件封装 -->
<script>
	export default {
		props:{
			placeholder:{
				type:String,
				default:''
			},
			type:{
				type:String,
				default:'text'
			},
			value:{
				type:String,
				default:''
			},
			disabled:{
				type:Boolean,
				default:false
			}
		},
		methods:{
			onInput(e){
				this.$emit("value",e.detail.value);
			},
			onClick(){
				this.$emit("click");
			}
		}
	}
</script>

<style>

</style>
