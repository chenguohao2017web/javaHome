import request from "../Request"

export default {
    select: (params) => {
        return request.post("/count/select", params)
    }
}