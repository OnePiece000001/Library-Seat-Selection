// Mock 数据文件 - 用于 GitHub Pages 静态部署
// 当后端服务不可用时，使用此文件中的模拟数据

export const mockData = {
  // ==================== 用户登录相关 ====================

  // 用户登录
  '/login': (params) => {
    const { userphone, password } = params;
    
    // 管理员账号
    if (userphone === 'admin' && password === 'admin') {
      return {
        code: 200,
        data: {
          user_phone: userphone,
          user_name: '管理员',
          user_img: null,
          identity: 'admin'
        },
        msg: '登录成功'
      };
    }
    
    // 普通用户账号 - 任意手机号+密码都能登录
    return {
      code: 200,
      data: {
        user_phone: userphone,
        user_name: '测试用户',
        user_img: null,
        identity: 'user'
      },
      msg: '登录成功'
    };
  },

  // 用户注册
  '/register': () => {
    return {
      code: 1,
      data: null,
      msg: '注册成功'
    };
  },

  // 获取用户信息
  '/getuserinfo': (params) => {
    return {
      code: 1,
      data: {
        user_phone: params.user_phone,
        user_name: '测试用户',
        user_img: null,
        identity: 'user'
      },
      msg: '获取成功'
    };
  },

  // ==================== 图书相关 ====================

  // 获取推荐书籍
  '/recommentbook': () => {
    return {
      code: 1,
      data: [
        { book_no: 'B001', book_name: 'Java编程思想', book_author: 'Bruce Eckel', book_img: null, book_desc: '经典Java教程' },
        { book_no: 'B002', book_name: '深入理解计算机系统', book_author: 'Randal E. Bryant', book_img: null, book_desc: '计算机系统基础' },
        { book_no: 'B003', book_name: '算法导论', book_author: 'Thomas H. Cormen', book_img: null, book_desc: '算法学习经典' },
        { book_no: 'B004', book_name: '设计模式', book_author: 'Erich Gamma', book_img: null, book_desc: '软件设计模式' },
        { book_no: 'B005', book_name: 'Clean Code', book_author: 'Robert C. Martin', book_img: null, book_desc: '代码整洁之道' }
      ],
      msg: '获取成功'
    };
  },

  // 获取所有图书
  '/getallbook': () => {
    return {
      code: 1,
      data: [
        { book_id: 1, book_no: 'B001', book_name: 'Java编程思想', book_author: 'Bruce Eckel', book_img: null, book_desc: '经典Java教程', book_status: '可借阅' },
        { book_id: 2, book_no: 'B002', book_name: '深入理解计算机系统', book_author: 'Randal E. Bryant', book_img: null, book_desc: '计算机系统基础', book_status: '可借阅' },
        { book_id: 3, book_no: 'B003', book_name: '算法导论', book_author: 'Thomas H. Cormen', book_img: null, book_desc: '算法学习经典', book_status: '已借出' },
        { book_id: 4, book_no: 'B004', book_name: '设计模式', book_author: 'Erich Gamma', book_img: null, book_desc: '软件设计模式', book_status: '可借阅' },
        { book_id: 5, book_no: 'B005', book_name: 'Clean Code', book_author: 'Robert C. Martin', book_img: null, book_desc: '代码整洁之道', book_status: '可借阅' },
        { book_id: 6, book_no: 'B006', book_name: '人月神话', book_author: 'Frederick P. Brooks', book_img: null, book_desc: '软件项目管理', book_status: '可借阅' },
        { book_id: 7, book_no: 'B007', book_name: '重构', book_author: 'Martin Fowler', book_img: null, book_desc: '改善既有代码', book_status: '可借阅' },
        { book_id: 8, book_no: 'B008', book_name: '代码大全', book_author: 'Steve McConnell', book_img: null, book_desc: '软件构建大全', book_status: '已借出' }
      ],
      msg: '获取成功'
    };
  },

  // 搜索图书
  '/searchbook': (params) => {
    const allBooks = [
      { book_id: 1, book_no: 'B001', book_name: 'Java编程思想', book_author: 'Bruce Eckel', book_img: null, book_desc: '经典Java教程', book_status: '可借阅' },
      { book_id: 2, book_no: 'B002', book_name: '深入理解计算机系统', book_author: 'Randal E. Bryant', book_img: null, book_desc: '计算机系统基础', book_status: '可借阅' },
      { book_id: 3, book_no: 'B003', book_name: '算法导论', book_author: 'Thomas H. Cormen', book_img: null, book_desc: '算法学习经典', book_status: '已借出' },
      { book_id: 4, book_no: 'B004', book_name: '设计模式', book_author: 'Erich Gamma', book_img: null, book_desc: '软件设计模式', book_status: '可借阅' },
      { book_id: 5, book_no: 'B005', book_name: 'Clean Code', book_author: 'Robert C. Martin', book_img: null, book_desc: '代码整洁之道', book_status: '可借阅' }
    ];

    if (!params.book_name) {
      return { code: 1, data: allBooks, msg: '获取成功' };
    }

    const filtered = allBooks.filter(book =>
      book.book_name.includes(params.book_name) ||
      book.book_author.includes(params.book_name)
    );
    return { code: 1, data: filtered, msg: '搜索成功' };
  },

  // 获取推荐榜top50
  '/gettop50': () => {
    return {
      code: 1,
      data: [
        { book_no: 'B001', book_name: 'Java编程思想', book_author: 'Bruce Eckel', borrow_count: 156 },
        { book_no: 'B002', book_name: '深入理解计算机系统', book_author: 'Randal E. Bryant', borrow_count: 142 },
        { book_no: 'B003', book_name: '算法导论', book_author: 'Thomas H. Cormen', borrow_count: 138 },
        { book_no: 'B004', book_name: '设计模式', book_author: 'Erich Gamma', borrow_count: 125 },
        { book_no: 'B005', book_name: 'Clean Code', book_author: 'Robert C. Martin', borrow_count: 118 }
      ],
      msg: '获取成功'
    };
  },

  // ==================== 座位/桌位相关 ====================

  // 获取座位信息
  '/getseat': () => {
    return {
      code: 1,
      data: [
        { seat_name: 'A-01', seat_x: 100, seat_y: 100, seat_status: '空闲', desk_name: 'Desk-A1' },
        { seat_name: 'A-02', seat_x: 150, seat_y: 100, seat_status: '空闲', desk_name: 'Desk-A1' },
        { seat_name: 'A-03', seat_x: 100, seat_y: 150, seat_status: '占用', desk_name: 'Desk-A2' },
        { seat_name: 'A-04', seat_x: 150, seat_y: 150, seat_status: '空闲', desk_name: 'Desk-A2' },
        { seat_name: 'B-01', seat_x: 300, seat_y: 100, seat_status: '空闲', desk_name: 'Desk-B1' },
        { seat_name: 'B-02', seat_x: 350, seat_y: 100, seat_status: '空闲', desk_name: 'Desk-B1' }
      ],
      msg: '获取成功'
    };
  },

  // 获取桌位信息
  '/getdesk': () => {
    return {
      code: 1,
      data: [
        { desk_name: 'Desk-A1', desk_x: 100, desk_y: 100, floor_no: '1', seat_count: 2 },
        { desk_name: 'Desk-A2', desk_x: 100, desk_y: 150, floor_no: '1', seat_count: 2 },
        { desk_name: 'Desk-B1', desk_x: 300, desk_y: 100, floor_no: '2', seat_count: 2 }
      ],
      msg: '获取成功'
    };
  },

  // 获取所有楼层信息
  '/getfloor': () => {
    return {
      code: 1,
      data: [
        { floor_no: '1', floor_name: '一楼自习区' },
        { floor_no: '2', floor_name: '二楼自习区' },
        { floor_no: '3', floor_name: '三楼自习区' }
      ],
      msg: '获取成功'
    };
  },

  // 获取当前时间段的座位列表
  '/getcurrent_time_seat_list': () => {
    return {
      code: 1,
      data: [
        { seat_name: 'A-01', status: '空闲' },
        { seat_name: 'A-02', status: '空闲' },
        { seat_name: 'A-03', status: '占用' },
        { seat_name: 'A-04', status: '空闲' },
        { seat_name: 'B-01', status: '空闲' },
        { seat_name: 'B-02', status: '占用' }
      ],
      msg: '获取成功'
    };
  },

  // 获取禁用时间段列表
  '/getbookingtime': () => {
    return {
      code: 1,
      data: ['09:00', '10:00', '14:00', '15:00'], // 已被预约的时间段
      msg: '获取成功'
    };
  },

  // 获取当前半小时被预约的座位
  '/getOccupiedSeats': () => {
    return {
      code: 1,
      data: ['A-03', 'B-02'],
      msg: '获取成功'
    };
  },

  // ==================== 预约相关 ====================

  // 用户预约信息
  '/bookingmessage': (params) => {
    const currentBookings = [
      { booking_id: 1, user_phone: '13800138000', seat: 'A-01', floor: '1', start_time: '2026-04-26 09:00', end_time: '2026-04-26 12:00', status: '预约中' },
      { booking_id: 2, user_phone: '13800138000', seat: 'A-02', floor: '1', start_time: '2026-04-27 14:00', end_time: '2026-04-27 17:00', status: '预约中' }
    ];

    const historyBookings = [
      { booking_id: 3, user_phone: '13800138000', seat: 'B-01', floor: '2', start_time: '2026-04-20 09:00', end_time: '2026-04-20 12:00', status: '已完成' },
      { booking_id: 4, user_phone: '13800138000', seat: 'A-03', floor: '1', start_time: '2026-04-18 14:00', end_time: '2026-04-18 17:00', status: '已取消' }
    ];

    return {
      code: 1,
      data: params.historyYOrN === 'Y' ? historyBookings : currentBookings,
      msg: '获取成功'
    };
  },

  // 添加个人预约
  '/addbooking': () => {
    return {
      code: 1,
      data: { booking_id: Date.now() },
      msg: '预约成功'
    };
  },

  // 获取所有用户预约信息（管理）
  '/bookingmanage': () => {
    return {
      code: 1,
      data: [
        { booking_id: 1, user_phone: '13800138000', user_name: '张三', seat: 'A-01', floor: '1', start_time: '2026-04-26 09:00', end_time: '2026-04-26 12:00', status: '预约中' },
        { booking_id: 2, user_phone: '13800138001', user_name: '李四', seat: 'A-02', floor: '1', start_time: '2026-04-26 14:00', end_time: '2026-04-26 17:00', status: '预约中' },
        { booking_id: 3, user_phone: '13800138002', user_name: '王五', seat: 'B-01', floor: '2', start_time: '2026-04-27 09:00', end_time: '2026-04-27 12:00', status: '预约中' }
      ],
      msg: '获取成功'
    };
  },

  // 删除用户预约
  '/deletebooking': () => {
    return {
      code: 1,
      data: null,
      msg: '删除成功'
    };
  },

  // 修改用户预约
  '/updatebooking': () => {
    return {
      code: 1,
      data: null,
      msg: '修改成功'
    };
  },

  // ==================== 借阅相关 ====================

  // 获取用户图书借阅信息
  '/getuserborrowmsg': (params) => {
    return {
      code: 1,
      data: [
        { user_name: '测试用户', user_phone: params.user_phone, book_name: 'Java编程思想', book_no: 'B001', borrow_time: '2026-04-01', return_time: '2026-05-01', return_status: '借阅中' },
        { user_name: '测试用户', user_phone: params.user_phone, book_name: '算法导论', book_no: 'B003', borrow_time: '2026-03-15', return_time: '2026-04-15', return_status: '已归还' }
      ],
      msg: '获取成功'
    };
  },

  // 借阅书籍
  '/borrowbook': () => {
    return {
      code: 1,
      data: null,
      msg: '借阅成功'
    };
  },

  // 获取所有用户图书借阅信息（管理）
  '/manageuserborrow': () => {
    return {
      code: 1,
      data: [
        { user_name: '张三', user_phone: '13800138000', book_name: 'Java编程思想', book_no: 'B001', borrow_time: '2026-04-01', return_time: '2026-05-01', return_status: '借阅中' },
        { user_name: '李四', user_phone: '13800138001', book_name: '算法导论', book_no: 'B003', borrow_time: '2026-03-15', return_time: '2026-04-15', return_status: '已归还' },
        { user_name: '王五', user_phone: '13800138002', book_name: '设计模式', book_no: 'B004', borrow_time: '2026-04-10', return_time: '2026-05-10', return_status: '借阅中' }
      ],
      msg: '获取成功'
    };
  },

  // 修改用户图书借阅信息
  '/updateuserborrow': () => {
    return {
      code: 1,
      data: null,
      msg: '修改成功'
    };
  },

  // 删除用户图书借阅信息
  '/deleteuserborrow': () => {
    return {
      code: 1,
      data: null,
      msg: '删除成功'
    };
  },

  // ==================== 收藏相关 ====================

  // 获取用户收藏书籍
  '/getShouCangbook': () => {
    return {
      code: 1,
      data: [
        { book_no: 'B001', book_name: 'Java编程思想', book_author: 'Bruce Eckel', book_img: null },
        { book_no: 'B004', book_name: '设计模式', book_author: 'Erich Gamma', book_img: null }
      ],
      msg: '获取成功'
    };
  },

  // 添加收藏
  '/addShouCangbook': () => {
    return {
      code: 1,
      data: null,
      msg: '收藏成功'
    };
  },

  // 删除收藏
  '/deleteShouCangbook': () => {
    return {
      code: 1,
      data: null,
      msg: '取消收藏成功'
    };
  },

  // ==================== 管理操作 ====================

  // 添加楼层
  '/addfloor': () => {
    return {
      code: 1,
      data: null,
      msg: '添加成功'
    };
  },

  // 删除楼层
  '/deletefloor': () => {
    return {
      code: 1,
      data: null,
      msg: '删除成功'
    };
  },

  // 保存桌子座位信息
  '/savedeskseat': () => {
    return {
      code: 1,
      data: null,
      msg: '保存成功'
    };
  },

  // 删除图书
  '/deletebook': () => {
    return {
      code: 1,
      data: null,
      msg: '删除成功'
    };
  },

  // 删除座位
  '/deleteseat': () => {
    return {
      code: 1,
      data: null,
      msg: '删除成功'
    };
  },

  // 删除桌位
  '/deletedesk': () => {
    return {
      code: 1,
      data: null,
      msg: '删除成功'
    };
  },

  // 添加图书
  '/addbook': () => {
    return {
      code: 1,
      data: { book_id: Date.now() },
      msg: '添加成功'
    };
  },

  // 修改图书
  '/updatebook': () => {
    return {
      code: 1,
      data: null,
      msg: '修改成功'
    };
  },

  // 修改个人信息
  '/updateuserinfo': () => {
    return {
      code: 1,
      data: null,
      msg: '修改成功'
    };
  },

  // ==================== 签到/二维码 ====================

  // 扫码签到
  '/qianDao': () => {
    return {
      code: 1,
      data: null,
      msg: '签到成功'
    };
  },

  // 生成座位二维码
  '/getCode': (params) => {
    return {
      code: 1,
      data: `https://api.qrserver.com/v1/create-qr-code/?size=200x200&data=${encodeURIComponent(params.seat_name)}`,
      msg: '生成成功'
    };
  },

  // ==================== 可视化数据 ====================

  // 获取可视化数据
  '/getvisualization': () => {
    return {
      code: 1,
      data: {
        total_users: 1256,
        total_books: 5000,
        total_borrowed: 1234,
        total_seats: 200,
        occupied_seats: 85,
        today_bookings: 156
      },
      msg: '获取成功'
    };
  }
};

// 解析 URL 参数的辅助函数
export function parseParams(url) {
  const params = {};
  const queryString = url.split('?')[1];
  if (queryString) {
    const pairs = queryString.split('&');
    pairs.forEach(pair => {
      const [key, value] = pair.split('=');
      if (key && value) {
        params[key] = decodeURIComponent(value);
      }
    });
  }
  return params;
}

// 获取 API 路径（去掉 /api 前缀）
export function getApiPath(url) {
  // 处理 /api/xxx 或 /xxx 格式
  const path = url.replace(/^\/api/, '').split('?')[0];
  return path;
}

// 模拟延迟
export function mockDelay(ms = 300) {
  return new Promise(resolve => setTimeout(resolve, ms));
}
