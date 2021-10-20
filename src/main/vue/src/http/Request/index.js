import axios from "axios"
const baseUrl = "/api"
import { Loading } from 'element-ui';
let loadingInstance = null

axios.interceptors.request.use((config) => {
    loadingInstance = Loading.service({});
    return config
})

axios.interceptors.response.use((response) => {
    loadingInstance.close()
    return response
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