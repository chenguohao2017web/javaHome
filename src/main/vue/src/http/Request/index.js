import axios from "axios"

const baseUrl = "http://127.0.0.1:8080"

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