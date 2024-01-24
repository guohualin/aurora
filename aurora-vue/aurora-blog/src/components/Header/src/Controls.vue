<template>
  <div class="header-controls absolute top-10 right-0 flex flex-row" tabindex="0">
    <Dropdown v-if="multiLanguage === 1" @command="handleClick">
      <span class="ob-drop-shadow" data-dia="language">
        <svg-icon icon-class="globe" />
        <span v-if="$i18n.locale == 'cn'">中文</span>
        <span v-if="$i18n.locale == 'en'">EN</span>
      </span>
      <DropdownMenu>
        <DropdownItem name="en">English</DropdownItem>
        <DropdownItem name="cn">中文</DropdownItem>
      </DropdownMenu>
    </Dropdown>
    <template v-if="userInfo === ''">
      <span class="mr-3" @click="openLoginDialog">{{ t('settings.login') }}</span>
    </template>
    <template v-if="userInfo !== ''">
      <Dropdown hover>
        <span class="mr-2">
          <div class="flex-shrink-0">
            <div class="rounded-full ring-gray-100 overflow-hidden shaodw-lg w-9">
              <img class="avatar-img" :src="userInfo.avatar" alt="" />
            </div>
          </div>
        </span>
        <DropdownMenu>
          <template v-if="!isMobile">
            <DropdownItem @click="openUserCenter">{{ t('settings.personal-center') }}</DropdownItem>
          </template>
          <DropdownItem @click="logout">{{ t('settings.logout') }}</DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </template>
    <span no-hover-effect class="ob-drop-shadow" data-dia="light-switch">
      <ThemeToggle />
    </span>
  </div>
<!--  <el-dialog v-model="loginDialogVisible" width="30%" :fullscreen="isMobile">-->
<!--    <el-form @keyup.enter.native="login">-->
<!--      <el-form-item model="userInfo" class="mt-5">-->
<!--        <el-input v-model="loginInfo.username" placeholder="邮箱" />-->
<!--      </el-form-item>-->
<!--      <el-form-item model="userInfo" type="password" class="mt-8">-->
<!--        <el-input v-model="loginInfo.password" type="password" show-password placeholder="密码" />-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="primary" @click="login" size="large" class="mx-auto mt-3">登录</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="button" class="mx-auto my-el-button" @click="qqLogin">QQ登录</el-button>-->
<!--      </el-form-item>-->
<!--      <el-form-item>-->
<!--        <el-button type="button" class="mx-auto my-el-button" @click="openWeixinLoginDialog">微信登录</el-button>-->
<!--      </el-form-item>-->
<!--      <div class="mt-8">-->
<!--        <span class="text" @click="openRegisterDialog">立即注册</span>-->
<!--        <span class="text float-right" @click="openForgetPasswordDialog">忘记密码?</span>-->
<!--      </div>-->
<!--    </el-form>-->
<!--  </el-dialog>-->
  <el-dialog v-model="loginDialogVisible" :fullscreen="isMobile" :custom-class="login-dialog-body">
    <div  class="userLogin-container">
      <div  class="form-box" style="transform: translateX(0%);">
        <div  class="register-box hidden">
          <h1 >register</h1>
          <input  type="text" placeholder="昵称" maxlength="50" style="width: 55% !important; position: relative; left: -7.4%; margin: 0px !important;">
          <button  class="change-name-btn" title="随机一个昵称"> 随机 </button>
          <input  type="password" placeholder="密码(字母+数字)">
          <input  type="password" placeholder="确认密码">
          <button >注册</button>
        </div>
        <div  class="login-box">
          <h1 >login</h1>
          <input  type="text" v-model="loginInfo.username" placeholder="昵称">
          <input  type="password" v-model="loginInfo.password" placeholder="密码">
          <button @click="login">登录</button>
          <button @click="qqLogin">QQ登录</button>
          <button @click="openWeixinLoginDialog">微信登录</button>
        </div>
      </div>
      <div  class="con-box left">
        <h2 >欢迎来到<span >daLin</span>
        </h2>
        <p >
          <span >施主</span>请登记
        </p>
        <img  src="" alt="">
        <p >已有账号?</p>
        <button  id="login">去登录</button>
      </div>
      <div  class="con-box right">
        <h2 >欢迎来到<span >daLin</span>
        </h2>
        <img  alt="Static Badge" src="https://img.shields.io/badge/Beta-blue" style="width: unset; height: unset; margin: 0px; opacity: 1; padding-top: 12px;">
        <img  src="/img/lynhng-black.9cfcf27b.svg" alt="" style="transform: scale(3); z-index: -1;">
        <p >第一次来?</p>
        <button  id="register">去注册</button>
      </div>
    </div>
  </el-dialog>
  <el-dialog v-model="registerDialogVisible" width="30%" :fullscreen="isMobile">
    <el-form>
      <el-form-item model="userInfo" class="mt-5">
        <el-input v-model="loginInfo.username" placeholder="邮箱" />
      </el-form-item>
      <el-form-item model="userInfo" class="mt-8">
        <el-input v-model="loginInfo.code" placeholder="验证码">
          <template #append>
            <span class="text" @click="sendCode">发送</span>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item model="userInfo" type="password" class="mt-8">
        <el-input v-model="loginInfo.password" type="password" show-password placeholder="密码" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="register" size="large" class="mx-auto mt-3">注册</el-button>
      </el-form-item>
      <span class="text" @click="returnLoginDialog">已有帐号?登录</span>
    </el-form>
  </el-dialog>
  <el-dialog v-model="weixinLogVisible" width="30%" :fullscreen="isMobile">
    <el-form>
      <el-form-item class="mt-5">
        <el-image :src="require('@/assets/QR.png')" style=" margin-left: 25%;width: 50%;"/>
      </el-form-item>
      <span class="text" style="margin-left: 20%;">关注公众号后发送
        <b style="color: #d87184;">“验证码”</b>
        获取验证码</span>
      <el-form-item model="userInfo" style=" width: 300px;margin-left: 57px;" class="mt-8">
        <el-input v-model="loginInfo.code" placeholder="请输入验证码" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="weixinLogin" size="large" class="mx-auto mt-3">立即登录</el-button>
      </el-form-item>
      <span class="text" @click="returnLoginDialog">已有帐号?登录</span>
    </el-form>
  </el-dialog>

  <el-dialog v-model="forgetPasswordDialogVisible" width="30%" :fullscreen="isMobile">
    <el-form>
      <el-form-item model="userInfo" class="mt-5">
        <el-input v-model="loginInfo.username" placeholder="邮箱" />
      </el-form-item>
      <el-form-item model="userInfo" class="mt-8">
        <el-input v-model="loginInfo.code" placeholder="验证码">
          <template #append>
            <span class="text" @click="sendCode">发送</span>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item model="userInfo" type="password" class="mt-8">
        <el-input v-model="loginInfo.password" type="password" show-password placeholder="新密码" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updatePassword" size="large" class="mx-auto mt-3">确定</el-button>
      </el-form-item>
      <span class="text" @click="returnLoginDialog">返回登录</span>
    </el-form>
  </el-dialog>
  <el-dialog v-model="articlePasswordDialogVisible" width="30%" :fullscreen="isMobile">
    <el-form @submit.native.prevent @keyup.enter.native="accessArticle">
      <el-form-item model="userInfo" class="mt-5">
        <el-input id="article-password-input" v-model="articlePassword" placeholder="文章受密码保护,请输入密码" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="accessArticle" size="large" class="mx-auto mt-3">校验密码</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <teleport to="body">
    <SearchModel />
  </teleport>
</template>

<script lang="ts">
import { computed, defineComponent, toRef, toRefs, reactive, getCurrentInstance, nextTick } from 'vue'
import { Dropdown, DropdownMenu, DropdownItem } from '@/components/Dropdown'
import { useAppStore } from '@/stores/app'
import { useCommonStore } from '@/stores/common'
import { useUserStore } from '@/stores/user'
import { useRoute, useRouter } from 'vue-router'
import ThemeToggle from '@/components/ToggleSwitch/ThemeToggle.vue'
import api from '@/api/api'
import SearchModel from '@/components/SearchModel.vue'
import { useSearchStore } from '@/stores/search'
import config from '@/config/config'
import { useI18n } from 'vue-i18n'
import emitter from '@/utils/mitt'

export default defineComponent({
  name: 'Controls',
  components: {
    Dropdown,
    DropdownMenu,
    DropdownItem,
    ThemeToggle,
    SearchModel
  },
  setup() {
    const { t } = useI18n()
    const proxy: any = getCurrentInstance()?.appContext.config.globalProperties
    const appStore = useAppStore()
    const commonStore = useCommonStore()
    const userStore = useUserStore()
    const searchStore = useSearchStore()
    const route = useRoute()
    const router = useRouter()
    const loginInfo = reactive({
      username: '' as any,
      password: '' as any,
      code: '' as any
    })
    const reactiveDate = reactive({
      loginDialogVisible: false,
      registerDialogVisible: false,
      weixinLogVisible: false,
      forgetPasswordDialogVisible: false,
      articlePasswordDialogVisible: false,
      articlePassword: '',
      articleId: ''
    })
    emitter.on('changeArticlePasswordDialogVisible', (articleId: any) => {
      reactiveDate.articlePasswordDialogVisible = true
      reactiveDate.articlePassword = ''
      reactiveDate.articleId = articleId
      nextTick(() => {
        document.getElementById('article-password-input')?.focus()
      })
    })
    const handleClick = (name: string): void => {
      appStore.changeLocale(name)
    }
    const login = () => {
      if (loginInfo.username.trim().length == 0 || loginInfo.password.trim().length == 0) {
        proxy.$notify({
          title: 'Warning',
          message: '账号或者密码不能为空',
          type: 'warning'
        })
        return
      }
      let params = new URLSearchParams()
      params.append('username', loginInfo.username)
      params.append('password', loginInfo.password)
      api.login(params).then(({ data }) => {
        if (data.flag) {
          userStore.userInfo = data.data
          sessionStorage.setItem('token', data.data.token)
          userStore.token = data.data.token
          proxy.$notify({
            title: 'Success',
            message: '登录成功',
            type: 'success'
          })
          reactiveDate.loginDialogVisible = false
        }
      })
    }
    const weixinLogin = () => {
      let params = new URLSearchParams()
      params.append('code', loginInfo.code)
      api.getWeixinTicket(params).then(({ data }) => {
        console.log(data);
        if (data.flag) {
          userStore.userInfo = data.data
          sessionStorage.setItem('token', data.data.token)
          userStore.token = data.data.token
          proxy.$notify({
            title: 'Success',
            message: '登录成功',
            type: 'success'
          })
          reactiveDate.weixinLogVisible = false
        }
      })
    }

    const logout = () => {
      api.logout().then(({ data }) => {
        if (data.flag) {
          userStore.userInfo = ''
          userStore.token = ''
          userStore.accessArticles = []
          sessionStorage.removeItem('token')
          proxy.$notify({
            title: 'Success',
            message: '登出成功',
            type: 'success'
          })
        }
      })
    }
    const openUserCenter = () => {
      userStore.userVisible = true
    }
    const openLoginDialog = () => {
      reactiveDate.loginDialogVisible = true
    }
    const openWeixinLoginDialog = () => {
      loginInfo.code = ''
      reactiveDate.loginDialogVisible = false
      reactiveDate.loginDialogVisible = false
      reactiveDate.weixinLogVisible = true
    }
    const openRegisterDialog = () => {
      loginInfo.code = ''
      reactiveDate.loginDialogVisible = false
      reactiveDate.weixinLogVisible = false
      reactiveDate.registerDialogVisible = true
    }
    const returnLoginDialog = () => {
      reactiveDate.registerDialogVisible = false
      reactiveDate.weixinLogVisible = false
      reactiveDate.forgetPasswordDialogVisible = false
      reactiveDate.loginDialogVisible = true
    }
    const openForgetPasswordDialog = () => {
      loginInfo.code = ''
      reactiveDate.loginDialogVisible = false
      reactiveDate.forgetPasswordDialogVisible = true
    }
    const sendCode = () => {
      api.sendValidationCode(loginInfo.username).then(({ data }) => {
        if (data.flag) {
          proxy.$notify({
            title: 'Success',
            message: '验证码已发送',
            type: 'success'
          })
        }
      })
    }
    const register = () => {
      let params = {
        code: loginInfo.code,
        username: loginInfo.username,
        password: loginInfo.password
      }
      api.register(params).then(({ data }) => {
        if (data.flag) {
          proxy.$notify({
            title: 'Success',
            message: '注册成功',
            type: 'success'
          })
          reactiveDate.registerDialogVisible = false
          reactiveDate.weixinLogVisible = false
          reactiveDate.loginDialogVisible = true
        }
      })
    }
    // const handleOpenModel: any = (status: boolean) => {
    //   searchStore.setOpenModal(status)
    // }

    const qqLogin = () => {
      userStore.currentUrl = route.path
      reactiveDate.loginDialogVisible = false
      if (commonStore.isMobile) {
        //@ts-ignore
        QC.Login.showPopup({
          appId: config.qqLogin.QQ_APP_ID,
          redirectURI: config.qqLogin.QQ_REDIRECT_URI
        })
      } else {
        window.open(
          'https://graph.qq.com/oauth2.0/show?which=Login&display=pc&client_id=' +
            +config.qqLogin.QQ_APP_ID +
            '&response_type=token&scope=all&redirect_uri=' +
            config.qqLogin.QQ_REDIRECT_URI,
          '_self'
        )
      }
    }

    const updatePassword = () => {
      api.updatePassword(loginInfo).then(({ data }) => {
        if (data.flag) {
          proxy.$notify({
            title: 'Success',
            message: '修改成功',
            type: 'success'
          })
          reactiveDate.forgetPasswordDialogVisible = false
          reactiveDate.loginDialogVisible = true
        }
      })
    }
    const accessArticle = () => {
      if (reactiveDate.articlePassword.trim().length == 0) {
        proxy.$notify({
          title: 'Warning',
          message: '密码不能为空',
          type: 'warning'
        })
        return
      }
      api
        .accessArticle({
          articleId: reactiveDate.articleId,
          articlePassword: reactiveDate.articlePassword
        })
        .then(({ data }) => {
          if (data.flag) {
            reactiveDate.articlePasswordDialogVisible = false
            userStore.accessArticles.push(reactiveDate.articleId)
            router.push({ path: '/articles/' + reactiveDate.articleId })
          }
        })
    }
    return {
      //handleOpenModel,
      loginInfo,
      ...toRefs(reactiveDate),
      userInfo: toRef(userStore.$state, 'userInfo'),
      isMobile: toRef(commonStore.$state, 'isMobile'),
      login,
      qqLogin,
      weixinLogin,
      logout,
      handleClick,
      openUserCenter,
      openLoginDialog,
      openRegisterDialog,
      openWeixinLoginDialog,
      returnLoginDialog,
      sendCode,
      register,
      updatePassword,
      openForgetPasswordDialog,
      accessArticle,
      multiLanguage: computed(() => {
        let websiteConfig: any = appStore.websiteConfig
        return websiteConfig.multiLanguage
      }),
      t
    }
  }
})
</script>
<style lang="scss">
.my-el-button {
  width: 300px !important;
}
.el-button {
  width: 300px;
}
.el-dialog__headerbtn {
  outline: none !important;
}
.el-input-group__append {
  background-color: var(--background-primary-alt) !important;
}
.el-form-item__label {
  text-align: left;
  width: 70px;
  color: var(--text-normal) !important;
}
.el-input__inner {
  color: var(--text-normal) !important;
  background-color: var(--background-primary-alt) !important;
}
.el-input__wrapper {
  background: var(--background-primary-alt) !important;
}

</style>
<style lang="scss" scoped>
.text {
  color: var(--text-normal);
  cursor: pointer;
}
#submit-button {
  outline: none;
  background: #0fb6d6;
}
.header-controls {
  span {
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    cursor: pointer;
    transition: opacity 250ms ease;
    padding-right: 0.5rem;
    &[no-hover-effect] {
      &:hover {
        opacity: 1;
      }
    }
    &:hover {
      opacity: 0.5;
    }
    .svg-icon {
      stroke: #fff;
      height: 2rem;
      width: 2rem;
      margin-right: 0.5rem;
      pointer-events: none;
    }
  }
  .search-bar {
    @apply bg-transparent flex flex-row px-0 mr-2 rounded-full;
    opacity: 0;
    width: 0;
    transition: 300ms all ease-out;
    &.active {
      @apply bg-ob-deep-800;
      opacity: 0.95;
      width: 200px;
      imput {
        width: initial;
      }
    }
    &:focus {
      appearance: none;
      outline: none;
    }
    input {
      @apply flex flex-1 bg-transparent text-ob-normal px-6 box-border;
      width: 0;
      appearance: none;
      outline: none;
    }
    svg {
      @apply float-right;
    }
  }
}
.avatar-img {
  transition-property: transform;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 800ms;
  transform: rotate(-360deg);
}
.avatar-img:hover {
  transform: rotate(360deg);
}

.login-dialog-body {
  padding: 0;
  height: 0;
  width: 700px;
  background: transparent
}

.userLogin-container {
  margin: 5% auto;
  background-color: #fff;
  width: 650px;
  height: 415px;
  border-radius: 5px;
  box-shadow: 5px 5px 5px rgba(0,0,0,.1);
  position: relative
}

.form-box {
  position: absolute;
  top: -10%;
  left: 5%;
  background-color: #d3b7d8;
  width: 320px;
  height: 500px;
  border-radius: 5px;
  box-shadow: 2px 0 10px rgba(0,0,0,.1);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 2;
  transition: .5s ease-in-out
}

.login-box,.register-box {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%
}

.hidden {
  display: none;
  transition: .5s
}

h1 {
  text-align: center;
  margin-bottom: 25px;
  text-transform: uppercase;
  color: #fff;
  letter-spacing: 5px
}

input {
  background-color: transparent;
  width: 70%;
  color: #fff;
  border: none;
  border-bottom: 1px solid hsla(0,0%,100%,.4);
  padding: 10px 0;
  text-indent: 10px;
  margin: 8px 0;
  font-size: 14px;
  letter-spacing: 2px
}

input::-moz-placeholder {
  color: #fff
}

input::placeholder {
  color: #fff
}

input:focus {
  color: #a262ad;
  outline: none;
  border-bottom: 1px solid #a262ad80;
  transition: .5s
}

input:focus::-moz-placeholder {
  opacity: 0
}

input:focus::placeholder {
  opacity: 0
}

.form-box button {
  width: 70%;
  margin-top: 35px;
  background-color: #f6f6f6;
  outline: none;
  border-radius: 8px;
  padding: 13px;
  color: #a262ad;
  letter-spacing: 2px;
  border: none;
  cursor: pointer
}

.form-box button:hover {
  background-color: #a262ad;
  color: #f6f6f6;
  transition: background-color .5s ease
}

.con-box {
  width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 50%;
  transform: translateY(-50%)
}

.con-box.left {
  left: -2%
}

.con-box.right {
  right: -2%
}

.con-box h2 {
  color: #8e9aaf;
  font-size: 25px;
  font-weight: 700;
  letter-spacing: 3px;
  text-align: center;
  margin-bottom: 4px
}

.con-box p {
  font-size: 12px;
  letter-spacing: 2px;
  color: #8e9aaf;
  text-align: center
}

.con-box span {
  color: #d3b7d8
}

.con-box img {
  width: 150px;
  height: 150px;
  opacity: .9;
  margin: 40px 0
}

.con-box button {
  background-color: #fff;
  color: #a262ad;
  border: 1px solid #d3b7d8;
  padding: 6px 10px;
  border-radius: 5px;
  letter-spacing: 1px;
  outline: none;
  cursor: pointer
}

.con-box button:hover {
  background-color: #d3b7d8;
  color: #fff
}

</style>
