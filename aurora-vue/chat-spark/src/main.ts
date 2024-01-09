import { createApp } from 'vue'
import App from './App.vue'
import { setupI18n } from './locales'
import { setupAssets, setupScrollbarStyle } from './plugins'
import { setupStore } from './store'
import { setupRouter } from './router'

// 全局websocket
import Websocket from "./utils/websocket"
// 声明一个全局变量来收集onMessage列表，因为在不同的页面可能会有不同的处理
const onMessageList: Array<Function> = [];
const onMessage = (event: any) => {
	// 遍历onMessage集合并触发
	onMessageList.forEach(f => {
		f.call(null, event);
	})
}
let webSocket = Websocket(onMessage); // 启动websocket

async function bootstrap() {
  const app = createApp(App).provide("onMessageList", onMessageList)
  setupAssets()
  setupScrollbarStyle()
  setupStore(app)
  setupI18n(app)
  await setupRouter(app)
  app.mount('#app')
	app.config.globalProperties.$socket = webSocket;
	// app.provide("onMessageList", onMessageList); // 全局注入
}

bootstrap()
