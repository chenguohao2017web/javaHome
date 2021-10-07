import request from "../Request"

export default {
    select: (params) => {
        return request.get("/user/select", params)
    }
}