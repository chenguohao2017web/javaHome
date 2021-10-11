import request from "../Request"

export default {
    select: (params) => {
        return request.get("/user/select", params)
    },

    selectById: (params) => {
        return request.post("/user/selectUserById", params)
    },

    add: (params) => {
        return request.post("/user/add", params)
    },

    del: (params) => {
        return request.post("/user/del", params)
    },

    update: (params) => {
        return request.post("/user/update", params)
    }
}