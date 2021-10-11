import request from "../Request"

export default {
    select: (params) => {
        return request.get("/room/select",{params})
    },
    selectPage: (params) => {
        return request.post("/room/select", params)
    },

    selectById: (params) => {
        return request.post("/room/selectById", params)
    },

    add: (params) => {
        return request.post("/room/add", params)
    },

    del: (params) => {
        return request.post("/room/del", params)
    }
}