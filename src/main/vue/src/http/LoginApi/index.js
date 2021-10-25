import request from "../Request"

export default {
    login: (params) => {
        return request.post("/login", params)
    }
}