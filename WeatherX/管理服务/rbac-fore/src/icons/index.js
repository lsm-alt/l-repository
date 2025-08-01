import './iconfont'
//.svg 目录下加载所有.vue后缀的组件  用于导入其他svg格式的图标
const svgFiles = require.context('./svg', true, /\.svg$/)
svgFiles.keys().map(item => svgFiles(item))
