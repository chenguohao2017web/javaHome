import axios from "axios"
const baseUrl = "/api"
import { Loading, Message } from 'element-ui';
import router from "../../router"
import store from "../../store"
let loadingInstance = null

axios.interceptors.request.use((config) => {
    loadingInstance = Loading.service({});
    config.headers.token = store.state.token
    return config
})

axios.interceptors.response.use((response) => {
    loadingInstance.close()
    if(response.data.msg === "token异常/已过期") {
        router.push("/login")
        Message.error("token失效")
        return false
    }
    return response
},() => {
    loadingInstance.close()
})

export default {
    post: (url, params) => {
        return new Promise((resolve, reject) => {
            axios.post(baseUrl + url, params).then(res => {
                resolve(res.data)
            }).catch((res) => {
                reject(res.data)
            })
        })
    },

    get: (url, params) => {
        return new Promise((resolve) => {
            axios.get(baseUrl + url, {params}).then(res => {
                resolve(res.data)
            })
        })
    }
}