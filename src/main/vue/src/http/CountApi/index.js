import request from "../Request"

export default {
    select: (params) => {
        return request.post("/count/select", params)
    },

    add: (params) => {
        return request.post("/count/add", params)
    },

    update: (params) => {
        return request.post("/count/update", params)
    }
}