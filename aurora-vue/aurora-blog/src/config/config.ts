export default {
  qqLogin: {
    QQ_APP_ID: '758014943',
    QQ_REDIRECT_URI: 'https://60.205.204.59:8086/oauth/login/qq'
  },
  routes: [
    {
      name: 'Home',
      path: '/',
      i18n: {
        cn: '首页',
        en: 'Home'
      },
      children: []
    },
    {
      name: 'Talks',
      path: '/talks',
      i18n: {
        cn: '日常',
        en: 'talks'
      },
      children: []
    },
    {
      name: 'About',
      path: '/about',
      i18n: {
        cn: '关于',
        en: 'About'
      },
      children: []
    },
    {
      name: 'Archives',
      path: '/archives',
      i18n: {
        cn: '时光轮',
        en: 'Archives'
      },
      children: []
    },
    {
      name: 'Tags',
      path: '/tags',
      i18n: {
        cn: '标签',
        en: 'Tags'
      },
      children: []
    },
    {
      name: 'Message',
      path: '/message',
      i18n: {
        cn: '留言',
        en: 'Message'
      },
      children: []
    },
    {
      name: 'Friends',
      path: '/friends',
      i18n: {
        cn: '友链',
        en: 'Friends'
      },
      children: []
    }
  ]
}
