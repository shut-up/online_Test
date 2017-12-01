import axios from 'axios';

let base = 'http://live-admin-api.letsport.com.cn/';
let commentBase = "http://localhost:8015/";

/******* user *******/
export const userLogin = params => {
    return axios.post(`${commentBase}user/login`, params).then(res=>res.data);
};
export const userRegister = params => {
    return axios.post(`${commentBase}user/register`, params).then(res=>res.data);
};
export const updateInfo = params => {
    return axios.post(`${commentBase}user/updateInfo`, params).then(res=>res.data);
};


/******* room *******/
//addRoom
export const roomAdd = params => {
    return axios.post(`${base}room/add`, params).then(res=>res.data);
};
//getRoomList
export const roomGetList = params => {
    return axios.get(`${base}room/getAll`, {params:params}).then(res=>res.data);
};
//getRoomSize
export const roomGetSize = () => {
    return axios.get(`${base}room/getSize`).then(res=>res.data);
}

//searchRoom
export const roomGetSearch = params => {
    return axios.get(`${base}room/search`, {params: params}).then(res=>res.data);
};

//updateRoom Status
export const roomStatusUpate = params => {
    return axios.post(`${base}room/updateDelete`,params).then(res=>res.data);
}
//updateEndTime
export const roomUpdateEndTime = params => {
    return axios.post(`${base}room/updateEndTime`,params).then(res=>res.data)
}

//getRoom
export const roomGetEditRoom = (params) => {
    return axios.get(`${base}room/editByRoomId`, {params:params}).then(res=>res.data);
}
//updateByRoomId
export const roomUpate = params => {
    return axios.post(`${base}room/updateByRoomId`,params).then(res=>res.data);
}
//initEditRoom
export const roomGetByRoomId = () =>{
    return axios.get(`${base}room/editRoom`).then(res=>res.data);
}
//updateAnchor
export const roomUpdateAnchor = params => {
    return axios.post(`${base}room/updateAnchor`, params).then(res=>res.data);
}


//updateRegainGame
export const roomRegainGame = params => {
    return axios.post(`${base}room/updateRegainGame`, params).then(res=>res.data);
}


//updateRoom

//scoreGetRoom
export const scoreGetRoom = params => {
    return axios.get(`${base}room/getById`,{params:params}).then(res=>res.data);
};
//scoreAddContent
export const scoreAddContent = params => {
    return axios.post(`${base}room/addContent`,params).then(res=>res.data);
};
//scoreUpdateContent
export const scoreUpdateContent = params => {
    return axios.post(`${base}room/updateConten`,params).then(res=>res.data);
}
//updateHideTime
export const scoreHideTime = params =>{
    return axios.post(`${base}room/updateHideTime`,params).then(res=>res.data);
}
//updateScore
export const scoreUpateScore = params => {
    return axios.post(`${base}room/updateScore`, params).then(res=>res.data);
}
//deleteTalkContent
export const scoreDeleteContent = params => {
    return axios.post(`${base}room/delete`,params,{headers: {"Content-Type": "application/x-www-form-urlencoded"}}).then(res=>res.data);
}
//talkList
export const scoreGetTalkList = (params) => {
    return axios.get(`${base}room/getAnchorList`, {params:params}).then(res=>res.data);
}
//
export const scoreGetTalkListSize = (params) => {
    return axios.get(`${base}room/getAnchorListSize`,{params:params}).then(res=>res.data);
}



/** anchor **/
//add
export const anchorAdd = params => {
    return axios.post(`${base}anchor/anchorAdd`, params).then(res=>res.data);
};
//getOne
export const anchorEdit = params => {
    return axios.get(`${base}anchor/editById`, {params:params}).then(res=>res.data);
}
//getAll
export const anchorGetAll = params => {
    return axios.get(`${base}anchor/getAll`, {params:params}).then(res=>res.data);
}
//getSize
export const anchorGetSize = () => {
    return axios.get(`${base}anchor/getSize`).then(res=>res.data);
}
//search
export const anchorSearch = params => {
    return axios.post(`${base}anchor/search`,params).then(res=>res.data);
}
//update
export const anchorUpdate = params => {
    return axios.post(`${base}anchor/updateAnchor`,params).then(res=>res.data);
}


/** team **/
//add
export const teamAdd = params => {
    return axios.post(`${base}team/teamAdd`, params).then(res=>res.data);
}
//getAll
export const teamGetAll = params => {
    return axios.get(`${base}team/getAll`, {params:params}).then(res=>res.data);
}
//search
export const teamSearch = params => {
    return axios.post(`${base}team/search`, params).then(res=>res.data);
}
//update
export const teamUpate = params => {
    return axios.post(`${base}team/updateTeam`, params).then(res=>res.data);
}
//getSize
export const teamGetSize = () => {
    return axios.get(`${base}team/getSize`).then(res=>res.data);
}



/**** catalog *****/
//getAllCatalog
export const roomGetAllCatalog = () => {
    return axios.get(`${base}room/getAllCatalog`).then(res=>res.data);
}

/**** comment *****/
//roomGetAllComment，获取所有评论
export const roomGetAllComment = params => {
    return axios.get(`${commentBase}comment/getAllComment`, {params:params}).then(res=>res.data);
}
//roomGetCommentSize，获取总评论数
export const roomGetCommentSize = params => {
    return axios.get(`${commentBase}comment/getByComment`, params).then(res=>res.data);
}
//roomGetSelectComment，筛选查找评论
export const roomGetSelectedCommentpage = params => {
    return axios.post(`${commentBase}comment/search`, params).then(res=>res.data);
}
//roomUpdateSelectComment，更新评论状态
export const roomUpdateSelectedComment = params => {
    return axios.get(`${commentBase}comment/updateSelectComment`, {params:params}).then(res=>res.data);
}

