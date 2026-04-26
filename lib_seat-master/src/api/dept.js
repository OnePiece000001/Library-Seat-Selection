import request from "@/utils/request";
// export const ceshi =()=>request.get('/m1/7604053-7342783-default/ceshi')
//用户登录
export const login = (userphone,password) => request.post(`/login?userphone=${userphone}&password=${password}`)
//用户注册
export const register = (userphone,password) => request.post(`/register?userphone=${userphone}&password=${password}`)
//获取 推荐书籍
export const getrecombook = (user_phone) => request.get(`/recommentbook?user_phone=${user_phone}`)
//用户预约信息
export const bookingmessage = (userphone,historyYOrN) => request.post(`/bookingmessage?userphone=${userphone}&historyYOrN=${historyYOrN}`)
//获取 用户 图书借阅信息
export const getuserborrowmsg = (user_phone,user_name) => request.get(`/getuserborrowmsg?user_phone=${user_phone}&user_name=${user_name}`)
//获取所有图书信息
export const getallbook = () => request.get(`/getallbook`)
//获取处于当前时间段的座位列表
export const getcurrent_time_seat_list = () => request.get(`/getcurrent_time_seat_list`) 
//获取禁用时间段列表
export const getbookingtime = (seat) => request.get(`/getbookingtime?seat=${seat}`)
//获取座位信息
export const getseat = () => request.get(`/getseat`)
//获取桌位信息
export const getdesk = () => request.get(`/getdesk`)
//获取所有楼层信息
export const getfloor = () => request.get(`/getfloor`)
//保存桌子座位信息
export const savedeskseat = (data) => request.post(`/savedeskseat`, data)
//获取书籍信息
export const getbook = () => request.get(`/getbook`)
//添加楼层
export const addfloor = (floor_name,floor_no) => request.get(`/addfloor?floor_name=${floor_name}&floor_no=${floor_no}`)
//删除楼层
export const deletefloor = (floor_no,floor_name) => request.delete(`/deletefloor?floor_no=${floor_no}&floor_name=${floor_name}`)
//获取所有用户预约信息 管理
export const bookingmanage = () => request.post(`/bookingmanage`)
//删除用户预约信息 管理
export const deletebooking = (bookingID) => request.delete(`/deletebooking?bookingID=${bookingID}`)
//修改用户预约信息 管理
export const updatebooking = (bookingID,seat,floor,start_time,end_time) => request.post(`/updatebooking?bookingID=${bookingID}&seat=${seat}&floor=${floor}&start_time=${start_time}&end_time=${end_time}`)

//获取 所有用户 图书借阅信息 管理
export const manageuserborrow = () => request.get(`/manageuserborrow`)
//修改用户图书借阅信息 管理
export const updateuserborrow = (data) => request.post(`/updateuserborrow?user_name=${data.user_name}&user_phone=${data.user_phone}&book_name=${data.book_name}&book_no=${data.book_no}&borrow_time=${data.borrow_time}&return_time=${data.return_time}&return_status=${data.return_status}`)
//删除用户图书借阅信息 管理
export const deleteuserborrow = (data) => request.delete(`/deleteuserborrow?user_name=${data.user_name}&user_phone=${data.user_phone}&book_no=${data.book_no}`)

//删除图书
export const deletebook = (book_id) => request.delete(`/deletebook?book_id=${book_id}`)

//删除座位
export const deleteseat = (seat_name) => request.delete(`/deleteseat?seat_name=${seat_name}`)
//删除桌位
export const deletedesk = (desk_name) => request.delete(`/deletedesk?desk_name=${desk_name}`)

//添加个人预约信息
export const addbooking = (data) => request.post(`/addbooking?user_phone=${encodeURIComponent(data.user_phone)}&seat=${encodeURIComponent(data.seat)}&floor=${encodeURIComponent(data.floor)}&start_time=${encodeURIComponent(data.start_time)}&end_time=${encodeURIComponent(data.end_time)}`)
//借阅书籍
export const borrowbook = (data) => request.post(`/borrowbook?user_name=${encodeURIComponent(data.user_name)}&user_phone=${encodeURIComponent(data.user_phone)}&book_no=${encodeURIComponent(data.book_no)}&book_name=${encodeURIComponent(data.book_name)}&borrow_time=${encodeURIComponent(data.borrow_time)}&return_time=${encodeURIComponent(data.return_time)}`)
//搜索图书
export const searchbook = (book_name) => {
  // 如果没有传入参数或参数为空，获取所有图书
  if (!book_name) {
    return request.get(`/searchbook`)
  }
  // 有搜索关键词时，进行编码后发送
  return request.get(`/searchbook?book_name=${book_name}`)
}    

//增加图书
export const addbook = (data) => request.post(`/addbook`, data, {
  headers: {
    'Content-Type': 'multipart/form-data'
  }
})

//修改图书                 bug
export const updatebook = (data) => request.post(`/updatebook`, data, {
  headers: {
    'Content-Type': 'multipart/form-data'
  }
})

// 当前半小时时间段被预约的座位号列表
export const getOccupiedSeats = () => request.get(`/getOccupiedSeats`)

//获取推荐榜top50
export const gettop50 = () => request.get(`/gettop50`)

// 用户扫码签到 通过座位号和手机号 修改该用户距离当前时间最近的一条预约记录的状态为 已签到  若查不到 则返回没有预约记录
export const qianDao = (seatName,phoneNumber) => request.post(`/qianDao?user_phone=${encodeURIComponent(phoneNumber)}&seat_name=${seatName}`)
// 生成座位二维码
export const getCode = (seatName) => request.get(`/getCode?seat_name=${encodeURIComponent(seatName)}`)
// 修改个人信息
export const updateuserinfo = (data) => request.post(`/updateuserinfo`, data, {
  headers: {
    'Content-Type': 'multipart/form-data'
  }
})

// 获取用户信息
export const getUserInfo = (user_phone) => request.get(`/getuserinfo?user_phone=${user_phone}`)

//根据手机号查询用户收藏书籍
export const getShouCangbook = (user_phone) => request.get(`/getShouCangbook?user_phone=${user_phone}`)

//添加收藏书籍
export const addShouCangbook = (user_phone,book_no) => request.post(`/addShouCangbook?user_phone=${user_phone}&book_no=${book_no}`)

//删除收藏书籍
export const deleteShouCangbook = (user_phone,book_no) => request.delete(`/deleteShouCangbook?user_phone=${user_phone}&book_no=${book_no}`)
