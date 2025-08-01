// src/utils/weatherPdfTemplate.js

/**
 * 生成天气预报 HTML 页面
 * @param {string} rawText 天气原始文本
 * @param {string} title 标题，例如“沾化天气预报”
 * @param {string} source 发布机构，例如“滨州市沾化区气象局”
 * @param {string} publishTime 发布时间，例如“7月10日15时发布”
 * @returns {string} 返回完整 HTML 字符串
 */
export function generateWeatherHtml(rawText, title, source, publishTime) {
  const content = rawText
    .split('\n')
    .map(line =>
      line.includes('：') && !line.includes('。')
        ? `<div class="${line.split('：')[0].length <= 8 ? 'content-title' : 'content-bold'}">${line.trim()}</div>`
        : `<div class="content-title">${line.trim()}</div>`
    )
    .join('\n')

  return `
  <html lang="zh-CN"><head><meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <style>
      div, html, img, body, head,a, footer { margin: 0; padding: 0; }
      body { padding: 10px 0; background-color: #FFFFFF; }
      .left { padding: 0 15px; float: left; }
      .right { padding: 0 15px; float: right; }
      .title {
        text-align: center;
        font-family: 微软雅黑;
        font-weight: bold;
        font-size: 25px;
        color: #FF0000;
        text-shadow: 1px 1px 2px #696969;
        padding: 5px 0 15px 0;
      }
      .hr {
        width: 100%;
        height: 1px;
        border: 0;
        border-top: 1px solid #FF0000;
        border-bottom: 3px solid #FF0000;
        box-shadow: 2px 2px 5px #696969;
      }
      .clear { clear: both; }
      .content-title {
        color: #000000;
        padding: 0px 15px 5px 5px;
        text-align: left;
        text-indent: 2em;
        font-size: 16px;
      }
      .content-bold {
        color: #000000;
        padding: 0px 15px 5px 5px;
        text-align: left;
        text-indent: 2em;
        font-size: 18px;
        font-weight: bold;
      }
    </style>
  </head>
  <body>
    <div class="main">
      <div class="header">
        <div class="title">${title}</div>
        <div class="describe">
          <span class="left">${source}</span>
          <span class="right">${publishTime}</span>
          <div class="clear"></div>
        </div>
        <hr class="hr">
      </div>
      <br>
      <div class="content">
        ${content}
        <br><hr class="hr"><br>
      </div>
    </div>
  </body></html>
  `
}
