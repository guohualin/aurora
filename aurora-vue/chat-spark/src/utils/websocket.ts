// @ts-ignore
import FingerprintJS from '@fingerprintjs/fingerprintjs'
const CHAT_SPARK_WEBSOCKET_URL = 'ws://localhost:9005/spark'

let socket: WebSocket

export default (onMessage: Function) => {
  const clientId = localStorage.getItem('LynhngClientId')
  if (!clientId) {
		FingerprintJS.get((components: any) => {
      const values = components.map((component: any, index: any) => {
        if (index === 0) { // 把微信浏览器里UA的wifi或4G等网络替换成空,不然切换网络会ID不一样
          return component.value.replace(/\bNetType\/\w+\b/, '')
        }
        return component.value
      })
      const new_clientId = FingerprintJS.x64hash128(values.join(''), 31)
      localStorage.setItem('LynhngClientId', `LynhngSpark_${new_clientId}`)
      return `LynhngSpark_${new_clientId}`
    })
  }

  // let socketUrl = socket_url.replace("https", "ws").replace("http", "ws")
  // const socketUrl = CHAT_SPARK_WEBSOCKET_URL + clientId
  // let socket: WebSocket
  let lockReconnect = false
  let timer: ReturnType<typeof setTimeout>
  const createSocket = () => {
    try {
      socket = new WebSocket(CHAT_SPARK_WEBSOCKET_URL)
			// eslint-disable-next-line @typescript-eslint/no-use-before-define
      init()
    }
    catch (e) {
      console.log(`catch${e}`)
      reconnect()
    }
  }
  const reconnect = () => {
    if (lockReconnect)
      return
    lockReconnect = true
    clearTimeout(timer)
    timer = setTimeout(() => {
      createSocket()
    }, 6000)
  }

  const init = () => {
    socket.onopen = function (event) {
      console.log('%c%s', 'color: greenfont-size: 14pxfont-weight: 700', 'Lynhng Space Client WebSocket Connection On Open')
      // 心跳检测重置
      heartCheck.reset().start()
    }

    // 接收到消息的回调方法
    socket.onmessage = function (event) {
      // console.log('WebSocket:收到一条消息', event.data)
      const isHeart = /pong/.test(event.data)
      if (onMessage && !isHeart) { // 触发自定义onMessage
        onMessage.call(null, event)
      }
      heartCheck.reset().start()
    }

    // 连接发生错误的回调方法
    socket.onerror = function (event) {
      console.log('%c%s', 'color: yellowfont-size: 14pxfont-weight: 700', 'Lynhng Space Client WebSocket Connection On Error')
      reconnect()
    }

    // 连接关闭的回调方法
    socket.onclose = function (event) {
      console.log('%c%s', 'color: redfont-size: 14pxfont-weight: 700', 'Lynhng Space Client WebSocket Connection On Close')
      heartCheck.reset() // 心跳检测
      reconnect()
    }

    // 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
      socket.close()
    }
  }

  var heartCheck = {
    timeout: 600000,
    timeoutObj: setTimeout(() => {
    }),
    serverTimeoutObj: setInterval(() => {
    }),
    reset() {
      clearTimeout(this.timeoutObj)
      clearTimeout(this.serverTimeoutObj)
      return this
    },
    start() {
      const that = this
      clearTimeout(this.timeoutObj)
      clearTimeout(this.serverTimeoutObj)
      this.timeoutObj = setTimeout(() => {
        // 这里发送一个心跳，后端收到后，返回一个心跳消息，
        // onmessage拿到返回的心跳就说明连接正常
        socket.send(JSON.stringify({
          action: 'ping',
          clientMsgType: 'user_heart',
        }))
        console.log('%c%s', 'color: black', 'Lynhng Space Client WebSocket Connection Ping')
        that.serverTimeoutObj = setTimeout(() => { // 如果超过一定时间还没重置，说明后端主动断开了
          console.log('%c%s', 'color: redfont-size: 14pxfont-weight: 700', 'Lynhng Space Client WebSocket Connection On Close')
          socket.close() // 如果onclose会执行reconnect，我们执行 websocket.close()就行了.如果直接执行 reconnect 会触发onclose导致重连两次
        }, that.timeout)
      }, this.timeout)
    },
  }

  // 发送消息
	// @ts-ignore
  const sendObject = (object) => {
    if (socket)
      socket.send(JSON.stringify(object))
  }

  createSocket()

  return socket
}

// @ts-ignore
export function sendObject(object) {
  if (socket)
    socket.send(JSON.stringify(object))
}
