<template>
  <div class="aui-wrapper aui-page__not-found">
    <div class="aui-content__wrapper">
      <div class="aui-content">
        <h2 class="title">400</h2>
        <p class="desc" >抱歉！您访问的页面不存在...</p>
        <div class="btn-bar">
          <el-button @click="$router.go(-1)">上一页</el-button>
          <el-button type="primary" @click="$router.push({ name: 'home' })">首页</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
}
</script>
