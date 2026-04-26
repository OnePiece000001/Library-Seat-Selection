// 1. 引入Vue和Vuex
import Vue from 'vue'
import Vuex from 'vuex'
import { getUserInfo } from '@/api/dept'

Vue.use(Vuex)

// 从 localStorage 获取用户信息
const getUserFromStorage = () => {
    const user = localStorage.getItem('userInfo')
    if (user) {
        try {
            return JSON.parse(user)
        } catch (e) {
            return null
        }
    }
    return null
}

const userInfo = getUserFromStorage()

// 默认头像
// const defaultAvatar = require('@/assets/touxiang.png')

// 验证图片URL是否有效
// const getValidAvatar = (avatarUrl) => {
//     if (!avatarUrl || avatarUrl === '123123' || !avatarUrl.startsWith('http')) {
//         return defaultAvatar
//     }
//     return avatarUrl
// }

export default new Vuex.Store({
    actions: {
        // 异步获取用户信息
        async fetchUserInfo({ commit }, user_phone) {
            try {
                const res = await getUserInfo(user_phone)
                if (res.code === 200) {
                    const userData = res.data
                    commit('SETUSER', {
                        user_name: userData.user_name,
                        user_phone: userData.user_phone,
                        user_picture: userData.user_picture1,
                        user_type: userData.user_type
                    })
                    return { success: true, data: userData }
                } else {
                    return { success: false, message: res.message || '获取用户信息失败' }
                }
            } catch (error) {
                return { success: false, message: error.message || '网络请求失败' }
            }
        }
    },
    mutations: {
        SETUSER(state, userobj) {
            state.username = userobj.user_name
            state.userPhone = userobj.user_phone
            // state.userpicture = getValidAvatar(userobj.user_picture)
            state.userpicture = userobj.user_picture
            // state.userpicture = userobj.user_picture
            state.usertype = userobj.user_type == 1 ?  true : false
            // 持久化到 localStorage
            localStorage.setItem('userInfo', JSON.stringify(userobj))
        },
        CLEARUSER(state) {
            state.username = ''
            state.userPhone = ''
            state.userpicture = ''
            state.usertype = ''
            localStorage.removeItem('userInfo')
        }
    },
    state: {
        // userpicture: userInfo ? getValidAvatar(userInfo.user_picture) : defaultAvatar,
        userpicture: userInfo ? userInfo.user_picture : '',
        username: userInfo ? userInfo.user_name : '',
        userPhone: userInfo ? userInfo.user_phone : '',
        usertype: userInfo ? (userInfo.user_type == 1 ? true : false) : ''
    }
})
