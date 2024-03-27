import { createApp } from 'vue'
import { createPinia } from 'pinia'
import axios from 'axios'

import App from './App.vue'
import router from './router'

// 부트스트랩 설정
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/js/bootstrap'

// vuetify 설정
import { vuetify } from './vuetify'

// 폰트 아이콘 설정
import { library } from '@fortawesome/fontawesome-svg-core'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faUserSecret, faHouse, faSearch, faPlus, faUserPlus } from '@fortawesome/free-solid-svg-icons'

// 국제화(i18n) 설정
import { i18n } from '@/locales'

// axios 설정
axios.defaults.baseURL = '/api'
axios.defaults.headers.common.Accept = 'application/json'
axios.interceptors.response.use(
  (response) => response,
  (error) => {
    return Promise.reject(error)
  }
)

library.add(faUserSecret, faHouse, faSearch, faPlus, faUserPlus)

const app = createApp(App)
const pinia = createPinia()

app.component('font-awesome-icon', FontAwesomeIcon).use(pinia).use(router).use(i18n).use(vuetify)

app.mount('#app')
