/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : library_ai

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 26/04/2026 14:30:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for book_borrow
-- ----------------------------
DROP TABLE IF EXISTS `book_borrow`;
CREATE TABLE `book_borrow`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅人姓名',
  `user_phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅人手机号',
  `book_no` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅图书编号',
  `book_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅图书名称',
  `borrow_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '借阅时间（如2024-05-20 10:00）',
  `return_time` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '预计归还时间（如2024-06-20 10:00）',
  `return_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未归还' COMMENT '归还状态（未归还/已归还）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_phone_borrow`(`user_phone`) USING BTREE,
  INDEX `idx_book_no_borrow`(`book_no`) USING BTREE,
  INDEX `idx_return_status`(`return_status`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '图书借阅记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_borrow
-- ----------------------------
INSERT INTO `book_borrow` VALUES (44, 'MENG', '15263280916', 'BN030', '《简·爱》', '2026-03-31 00:00:00', '2026-03-31 12:09:00', '已归还');
INSERT INTO `book_borrow` VALUES (45, 'MENG', '15263280916', 'BN013', '《平凡的世界》', '2026-03-30 18:00:00', '2026-03-31 00:00:00', '已归还');
INSERT INTO `book_borrow` VALUES (48, 'MENG', '15263280916', 'BN038', '《就业、利息和货币通论》', '2026-03-31 00:00:00', '2026-03-31 20:00:00', '已归还');
INSERT INTO `book_borrow` VALUES (50, 'MENG', '15263280916', 'BN003', '《西游记》', '2026-04-29 00:00:00', '2026-04-30 00:00:00', '已归还');
INSERT INTO `book_borrow` VALUES (51, 'MENG', '15263280916', 'BN043', '《嫌疑人X的献身》', '2026-04-15 00:00:00', '2026-04-30 00:00:00', '已归还');

-- ----------------------------
-- Table structure for book_collect
-- ----------------------------
DROP TABLE IF EXISTS `book_collect`;
CREATE TABLE `book_collect`  (
  `book_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书ID（与book_no统一）',
  `book_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书编号（冗余字段，兼容不同接口）',
  `book_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书名称',
  `book_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书作者',
  `book_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '图书封面URL（兼容book_cover）',
  `book_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '图书简介（兼容book_msg）',
  `book_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '图书分类',
  `user_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`book_id`) USING BTREE,
  UNIQUE INDEX `uk_book_no`(`book_no`) USING BTREE,
  INDEX `idx_book_name`(`book_name`) USING BTREE COMMENT '图书名索引，适配模糊查询'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '图书基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_collect
-- ----------------------------
INSERT INTO `book_collect` VALUES ('BN043', 'BN043', '《嫌疑人X的献身》', '东野圭吾', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/979be7c8-d3c2-47d1-8989-4f062bb71831.png', '极致的逻辑诡计与纯粹的爱情', '小说', '15263280916');

-- ----------------------------
-- Table structure for book_info
-- ----------------------------
DROP TABLE IF EXISTS `book_info`;
CREATE TABLE `book_info`  (
  `book_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书ID（与book_no统一）',
  `book_no` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书编号（冗余字段，兼容不同接口）',
  `book_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书名称',
  `book_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '图书作者',
  `book_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '图书封面URL（兼容book_cover）',
  `book_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '图书简介（兼容book_msg）',
  `book_num` int(0) NOT NULL DEFAULT 0 COMMENT '图书库存（兼容stock_num）',
  `book_type` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '图书分类',
  PRIMARY KEY (`book_id`) USING BTREE,
  UNIQUE INDEX `uk_book_no`(`book_no`) USING BTREE,
  INDEX `idx_book_name`(`book_name`) USING BTREE COMMENT '图书名索引，适配模糊查询'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '图书基础信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of book_info
-- ----------------------------
INSERT INTO `book_info` VALUES ('BN001', 'BN001', '《三国演义》', '罗贯中', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/f0bc1089-97e1-47fd-963d-b74a8aadf687.png', '中国古典四大名著之一，讲述东汉末年到西晋初年的历史风云', 91, '古典文学');
INSERT INTO `book_info` VALUES ('BN002', 'BN002', '《红楼梦》', '曹雪芹', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/6b5c5975-9ead-4a33-9ac7-f24a792451b5.png', '中国古典四大名著之首，清代长篇人情小说', 67, '古典文学');
INSERT INTO `book_info` VALUES ('BN003', 'BN003', '《西游记》', '吴承恩', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/591202ea-c13c-4dec-ad58-4aee25f963ad.png', '中国古典四大名著之一，浪漫主义章回体长篇神魔小说', 73, '古典文学');
INSERT INTO `book_info` VALUES ('BN004', 'BN004', '《水浒传》', '施耐庵', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/f36bce7b-14ef-4ac2-9a91-94329f28aa6e.png', '中国古典四大名著之一，以北宋末年宋江起义为背景的英雄传奇', 72, '古典文学');
INSERT INTO `book_info` VALUES ('BN006', 'BN006', '《经济学原理》', '曼昆', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/f8e542cf-43fa-40ff-8abb-5d27089a7d35.png', '经典经济学教材，涵盖微观经济学与宏观经济学核心内容', 20, '经济学');
INSERT INTO `book_info` VALUES ('BN007', 'BN007', '《解忧杂货店》', '东野圭吾', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/179de2df-9996-4b9c-af02-9553b28a6d98.png', '东野圭吾的温情小说，讲述跨越时空的治愈故事', 40, '小说');
INSERT INTO `book_info` VALUES ('BN008', 'BN008', '《人类简史》', '尤瓦尔·赫拉利', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/9e761a6e-e070-4f94-b4f2-ddd06088f3d1.png', '讲述人类从认知革命到科技革命的发展历程', 35, '历史');
INSERT INTO `book_info` VALUES ('BN009', 'BN009', '《活着》', '余华', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/af9a6150-667b-464c-81d2-344e124820be.png', '讲述福贵一生的坎坷经历，展现生命的韧性', 55, '当代文学');
INSERT INTO `book_info` VALUES ('BN010', 'BN010', '《深入理解计算机系统》', '兰德尔·E·布莱恩特', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/49fb9c61-c437-44e4-a16e-05e5092ad020.png', '计算机系统经典教材，从程序员视角详解计算机底层原理', 6, '计算机');
INSERT INTO `book_info` VALUES ('BN011', 'BN011', '《Java核心技术卷I》', '凯·S·霍斯特曼', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/71bf0ddd-d1db-4f65-a17a-fb3d765b0800.png', 'Java编程经典入门与进阶教材', 12, '计算机');
INSERT INTO `book_info` VALUES ('BN01111', 'BN01111', '随风', '孟超', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/df254bb7-070a-4321-a02d-07f63f2cafd8.jpg', '往事随风', 22, '小说');
INSERT INTO `book_info` VALUES ('BN012', 'BN012', '《Spring Boot实战》', '克雷格·沃斯', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/0ba9edfd-5b4c-411e-a76a-d25d80a0a899.png', 'Spring Boot框架开发实战指南', 8, '计算机');
INSERT INTO `book_info` VALUES ('BN013', 'BN013', '《平凡的世界》', '路遥', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/06976bba-8392-4dde-bea8-2cda8a7349a8.png', '全景式表现中国当代城乡社会生活的长篇小说', 51, '当代文学');
INSERT INTO `book_info` VALUES ('BN014', 'BN014', '《三体》', '刘慈欣', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/f3b00b14-1717-453e-9952-0623c64f910d.png', '中国科幻文学里程碑作品，讲述地球人类文明与三体文明的碰撞', 60, '科幻');
INSERT INTO `book_info` VALUES ('BN015', 'BN015', '《追风筝的人》', '卡勒德·胡赛尼', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/c6434c52-b931-418c-8563-48a7896dad8d.png', '关于友谊、背叛与救赎的经典小说', 38, '外国文学');
INSERT INTO `book_info` VALUES ('BN016', 'BN016', '《资本论》', '卡尔·马克思', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/cbc85245-b07f-4edb-9e26-726c3b1566c6.png', '马克思主义政治经济学经典著作', 15, '经济学');
INSERT INTO `book_info` VALUES ('BN017', 'BN017', '《万历十五年》', '黄仁宇', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/96ea6e5a-902b-491a-97f0-c5e503b9c080.png', '以大历史观解读明代万历年间的历史事件与人物', 28, '历史');
INSERT INTO `book_info` VALUES ('BN018', 'BN018', '《你当像鸟飞往你的山》', '塔拉·韦斯特弗', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/d116720c-4d10-4c1e-a7a7-b6ab57d1b74e.png', '自传体小说，讲述教育改变命运的真实故事', 42, '自传');
INSERT INTO `book_info` VALUES ('BN019', 'BN019', '《梦想家》', '孟超', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/eea85154-9225-45fb-aa75-a43ff04351cd.png', '原创文学作品，记录平凡人的追梦历程', 11, '当代文学');
INSERT INTO `book_info` VALUES ('BN020', 'BN020', '《数据结构与算法分析》', '马克·艾伦·维斯', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/6ae76e88-6feb-4920-b4f5-a6518cb83a25.png', '计算机专业核心课程经典教材', 9, '计算机');
INSERT INTO `book_info` VALUES ('BN021', 'BN021', '《儒林外史》', '吴敬梓', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/be2d27f4-2ece-4a4a-a8e7-10e8ccca396f.png', '中国古代讽刺文学巅峰，刻画封建士林众生相', 52, '古典文学');
INSERT INTO `book_info` VALUES ('BN022', 'BN022', '《聊斋志异》', '蒲松龄', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/95f367fe-1043-41b9-83d2-d907b751a989.png', '文言短篇小说集，狐鬼花妖的奇幻故事', 69, '古典文学');
INSERT INTO `book_info` VALUES ('BN023', 'BN023', '《封神演义》', '许仲琳', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/37a687c5-bce4-4d83-b465-e4baa73371d8.png', '中国古代神魔小说经典，商周封神大战', 45, '古典文学');
INSERT INTO `book_info` VALUES ('BN024', 'BN024', '《镜花缘》', '李汝珍', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/cfcc0b19-ce6f-4e48-8248-a27f99402bed.png', '清代长篇神魔小说，海外奇国游历故事', 38, '古典文学');
INSERT INTO `book_info` VALUES ('BN025', 'BN025', '《牡丹亭》', '汤显祖', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/c09ed29b-b5ca-4769-b90a-d552e89b7fcb.png', '明代戏曲经典，杜丽娘与柳梦梅的爱情传奇', 33, '古典文学');
INSERT INTO `book_info` VALUES ('BN026', 'BN026', '《百年孤独》', '加西亚·马尔克斯', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/2c2e85ac-44f4-4afe-8ae2-56578bccb384.png', '魔幻现实主义文学代表作，布恩迪亚家族七代传奇', 48, '外国文学');
INSERT INTO `book_info` VALUES ('BN028', 'BN028', '《杀死一只知更鸟》', '哈珀·李', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/9c50c14f-a1bb-4f13-bb58-21ea55108e4d.png', '美国南方成长小说，探讨种族与正义', 39, '外国文学');
INSERT INTO `book_info` VALUES ('BN029', 'BN029', '《呼啸山庄》', '艾米莉·勃朗特', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/6906066f-86f4-45d2-b9e7-a9616cf7fe88.png', '英国文学经典，希斯克利夫的爱恨复仇', 35, '外国文学');
INSERT INTO `book_info` VALUES ('BN030', 'BN030', '《简·爱》', '夏洛蒂·勃朗特', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/df91c3a9-cbdd-4fd3-9eb8-989b059f816e.png', '女性独立意识觉醒的经典成长小说', 56, '外国文学');
INSERT INTO `book_info` VALUES ('BN031', 'BN031', '《傲慢与偏见》', '简·奥斯汀', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/3816a230-e35c-4044-b135-40efc667765c.png', '英国摄政时期爱情与婚姻的经典讽刺小说', 62, '外国文学');
INSERT INTO `book_info` VALUES ('BN032', 'BN032', '《巴黎圣母院》', '维克多·雨果', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/cff66874-772f-4c74-91b3-75f0ad975fa3.png', '法国浪漫主义文学经典，美与丑的极致对比', 40, '外国文学');
INSERT INTO `book_info` VALUES ('BN033', 'BN033', '《悲惨世界》', '维克多·雨果', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/dfc591e4-aac7-4ad4-8e35-9d79ba802efa.png', '法国社会史诗，冉·阿让的救赎之路', 45, '外国文学');
INSERT INTO `book_info` VALUES ('BN034', 'BN034', '《老人与海》', '欧内斯特·海明威', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/8596b12e-dd15-41b7-bd49-1bdf612ab4e7.png', '硬汉精神的象征，人与自然的搏斗', 30, '外国文学');
INSERT INTO `book_info` VALUES ('BN035', 'BN035', '《了不起的盖茨比》', '菲茨杰拉德', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/396108b8-7313-440c-8ae5-4e6d028b3388.png', '美国爵士时代的挽歌，梦想与幻灭', 38, '外国文学');
INSERT INTO `book_info` VALUES ('BN036', 'BN036', '《资本论》', '卡尔·马克思', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/4bc1b607-0d38-47bc-a423-a9197fd6ca77.png', '马克思主义政治经济学奠基之作', 18, '经济学');
INSERT INTO `book_info` VALUES ('BN037', 'BN037', '《国富论》', '亚当·斯密', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/159339e6-e70f-4ba8-9599-5f7c53f9c3cc.png', '现代经济学开山之作，自由市场理论起源', 22, '经济学');
INSERT INTO `book_info` VALUES ('BN038', 'BN038', '《就业、利息和货币通论》', '凯恩斯', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/c36077a3-1cbf-4b05-a231-c508aa32f085.png', '宏观经济学经典，政府干预经济理论', 16, '经济学');
INSERT INTO `book_info` VALUES ('BN039', 'BN039', '《穷查理宝典》', '彼得·考夫曼', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/da76755c-d23e-4fd1-a7b1-1343ff04edd5.png', '查理·芒格的投资智慧与人生哲学', 25, '经济学');
INSERT INTO `book_info` VALUES ('BN040', 'BN040', '《聪明的投资者》', '本杰明·格雷厄姆', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/150c2bbb-fc6d-40fa-b6c1-33b063792899.png', '价值投资经典，巴菲特的投资启蒙书', 28, '经济学');
INSERT INTO `book_info` VALUES ('BN041', 'BN041', '《解忧杂货店》', '东野圭吾', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/9c4ae6ee-994c-4b40-aade-9ef8908a0738.png', '东野圭吾温情代表作，跨越时空的治愈故事', 45, '小说');
INSERT INTO `book_info` VALUES ('BN042', 'BN042', '《白夜行》', '东野圭吾', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/84a03f32-bd4f-4a89-a71b-0283e205cc79.png', '东野圭吾悬疑巅峰，19年的黑暗守护', 52, '小说');
INSERT INTO `book_info` VALUES ('BN043', 'BN043', '《嫌疑人X的献身》', '东野圭吾', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/979be7c8-d3c2-47d1-8989-4f062bb71831.png', '极致的逻辑诡计与纯粹的爱情', 49, '小说');
INSERT INTO `book_info` VALUES ('BN044', 'BN044', '《平凡的世界》', '路遥', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/448e5d6d-607f-4bfa-8fcb-ad3624d155b9.png', '中国当代城乡生活全景式史诗', 60, '小说');
INSERT INTO `book_info` VALUES ('BN045', 'BN045', '《人生》', '路遥', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/695032fc-328a-48b1-83ee-80d2d1834d24.png', '高加林的人生抉择，城乡青年的命运挣扎', 35, '小说');
INSERT INTO `book_info` VALUES ('BN046', 'BN046', '《围城》', '钱钟书', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/70567c48-2562-4a6d-951d-81c71bf37494.png', '中国现代讽刺文学经典，婚姻与人生的围城', 58, '小说');
INSERT INTO `book_info` VALUES ('BN047', 'BN047', '《边城》', '沈从文', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/e2b7fd25-657d-4a02-aa99-431bc6b9fd71.png', '湘西田园牧歌，翠翠的纯美爱情悲剧', 42, '小说');
INSERT INTO `book_info` VALUES ('BN048', 'BN048', '《骆驼祥子》', '老舍', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/26276fcc-a467-4bdc-ad18-9c4402b2ed92.png', '北平人力车夫的悲剧命运，旧中国底层缩影', 50, '小说');
INSERT INTO `book_info` VALUES ('BN049', 'BN049', '《茶馆》', '老舍', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/3442bd30-bff5-4055-8b5a-d120bb3c4c74.png', '三幕话剧，半个世纪中国社会的变迁', 39, '小说');
INSERT INTO `book_info` VALUES ('BN050', 'BN050', '《白鹿原》', '陈忠实', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/d0144182-ceb6-4df0-a510-9519910624d5.png', '渭河平原50年家族史诗，中国乡村变迁史', 40, '小说');
INSERT INTO `book_info` VALUES ('BN051', 'BN051', '《万历十五年》', '黄仁宇', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/086fa35d-d03f-4811-ab5c-64555175352f.png', '大历史观解读明代万历年间的兴衰', 32, '历史');
INSERT INTO `book_info` VALUES ('BN052', 'BN052', '《人类简史》', '尤瓦尔·赫拉利', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/f39819b0-f88e-4304-b4e5-8313943b1818.png', '人类从认知革命到科技革命的发展历程', 40, '历史');
INSERT INTO `book_info` VALUES ('BN053', 'BN053', '《未来简史》', '尤瓦尔·赫拉利', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/bff2dd37-2330-4824-98e0-7aabba5f11ee.png', '人工智能时代人类的未来命运', 35, '历史');
INSERT INTO `book_info` VALUES ('BN054', 'BN054', '《明朝那些事儿》', '当年明月', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/6d7c236b-4029-41f9-9d4d-8862b2af2b6f.png', '白话解读明朝三百年历史，通俗历史经典', 65, '历史');
INSERT INTO `book_info` VALUES ('BN055', 'BN055', '《史记》', '司马迁', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/c58cc75d-33f3-4b21-ae60-6a5fef8ef5af.png', '中国第一部纪传体通史，史家之绝唱', 28, '历史');
INSERT INTO `book_info` VALUES ('BN056', 'BN056', '《活着》', '余华', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/96791ca1-6158-482b-9d20-2fb516ddae9b.png', '福贵一生的坎坷，生命韧性的极致展现', 60, '当代文学');
INSERT INTO `book_info` VALUES ('BN057', 'BN057', '《许三观卖血记》', '余华', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/19669855-60e9-4b07-9ac4-40b063e76aee.png', '许三观的苦难人生，底层小人物的温情', 45, '当代文学');
INSERT INTO `book_info` VALUES ('BN058', 'BN058', '《兄弟》', '余华', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/382d7f8d-c9b3-41b6-a8be-9f333ba831b9.png', '李光头与宋钢的兄弟情，时代变迁下的命运', 38, '当代文学');
INSERT INTO `book_info` VALUES ('BN059', 'BN059', '《蛙》', '莫言', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/de78a3ac-d096-4663-b359-69ad20b1b000.png', '诺贝尔文学奖作品，中国生育政策的时代缩影', 32, '当代文学');
INSERT INTO `book_info` VALUES ('BN060', 'BN060', '《红高粱家族》', '莫言', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/cb88f671-191f-4180-a293-b552b2f23d4c.png', '高密东北乡的抗日传奇，野性生命力', 35, '当代文学');
INSERT INTO `book_info` VALUES ('BN061', 'BN061', '《深入理解计算机系统》', '兰德尔·E·布莱恩特', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/1417ff9b-6ed7-42cc-80fd-22014a63f321.png', '程序员视角的计算机底层原理经典教材', 10, '计算机');
INSERT INTO `book_info` VALUES ('BN062', 'BN062', '《Java核心技术卷II》', '凯·S·霍斯特曼', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/ec1e55af-5320-4e45-a2f6-559ba0007608.png', 'Java高级特性进阶经典教材', 8, '计算机');
INSERT INTO `book_info` VALUES ('BN063', 'BN063', '《Spring实战》', '克雷格·沃斯', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/efe355fa-088d-4e71-b9e4-9c35230dbd8a.png', 'Spring框架开发权威指南', 7, '计算机');
INSERT INTO `book_info` VALUES ('BN064', 'BN064', '《数据结构与算法分析》', '马克·艾伦·维斯', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/4f2677ef-51d8-4159-aade-d1e35fa216b7.png', '计算机专业核心课程经典教材', 9, '计算机');
INSERT INTO `book_info` VALUES ('BN065', 'BN065', '《算法导论》', '托马斯·科尔曼', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/d6121a16-a0d4-445d-95e0-ee9e09540ece.png', '算法领域圣经级教材，MIT官方指定用书', 6, '计算机');
INSERT INTO `book_info` VALUES ('BN066', 'BN066', '《三体》', '刘慈欣', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/2a3bc845-b42a-4bc1-b9f6-9efd26f6e255.png', '中国科幻里程碑，地球文明与三体文明的碰撞', 65, '科幻');
INSERT INTO `book_info` VALUES ('BN067', 'BN067', '《流浪地球》', '刘慈欣', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/c0811884-99c3-44d0-a341-06d5168ed8f9.png', '太阳危机下人类的星际迁徙，硬核科幻经典', 58, '科幻');
INSERT INTO `book_info` VALUES ('BN068', 'BN068', '《沙丘》', '弗兰克·赫伯特', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/fed256cb-ca32-4df7-b3c0-c4f61df4ede9.png', '科幻史上的不朽史诗，厄拉科斯星球的权力斗争', 30, '科幻');
INSERT INTO `book_info` VALUES ('BN069', 'BN069', '《你当像鸟飞往你的山》', '塔拉·韦斯特弗', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/63a71155-ea22-4968-b84a-8231bd291c64.png', '教育改变命运的真实自传，原生家庭的救赎', 45, '自传');

-- ----------------------------
-- Table structure for booking_info
-- ----------------------------
DROP TABLE IF EXISTS `booking_info`;
CREATE TABLE `booking_info`  (
  `bookingID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键 BK001、BK002',
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seat` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `floor` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seat_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未签到',
  PRIMARY KEY (`bookingID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '座位预约信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for desk_info
-- ----------------------------
DROP TABLE IF EXISTS `desk_info`;
CREATE TABLE `desk_info`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桌位ID',
  `desk_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桌位编号',
  `desk_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '桌位名称（如DF1001）',
  `floor_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属楼层编号',
  `desk_x` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '桌位X坐标（前端展示用）',
  `desk_y` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '桌位Y坐标（前端展示用）',
  `desk_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '桌位类型（如desk1/desk2）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_desk_name`(`desk_name`) USING BTREE,
  INDEX `idx_floor_no_desk`(`floor_no`) USING BTREE,
  CONSTRAINT `fk_desk_floor` FOREIGN KEY (`floor_no`) REFERENCES `floor_info` (`floor_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '桌位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of desk_info
-- ----------------------------
INSERT INTO `desk_info` VALUES ('1774515414439_116', '001', 'DF2001', 'F2', '435px', '192px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630170905_103', '001', 'DF1001', 'F1', '1185px', '268px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630174079_299', '002', 'DF1002', 'F1', '946px', '265px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630219050_610', '003', 'DF1003', 'F1', '714px', '271px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630254080_845', '004', 'DF1004', 'F1', '495px', '262px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630258025_997', '005', 'DF1005', 'F1', '278px', '263px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630391014_796', '006', 'DF1006', 'F1', '268px', '519px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630395623_860', '007', 'DF1007', 'F1', '493px', '519px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630474217_324', '008', 'DF1008', 'F1', '710px', '519px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630491838_500', '009', 'DF1009', 'F1', '948px', '523px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630495886_791', '010', 'DF1010', 'F1', '1191px', '528px', 'desk2');
INSERT INTO `desk_info` VALUES ('1774630502110_615', '011', 'DF1011', 'F1', '73px', '264px', 'desk1');
INSERT INTO `desk_info` VALUES ('1774630510534_563', '012', 'DF1012', 'F1', '63px', '428px', 'desk1');
INSERT INTO `desk_info` VALUES ('1774856766146_226', '001', 'DF5001', 'F5', '724px', '132px', 'desk2');

-- ----------------------------
-- Table structure for floor_info
-- ----------------------------
DROP TABLE IF EXISTS `floor_info`;
CREATE TABLE `floor_info`  (
  `floor_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '楼层编号（如F1/F2）',
  `floor_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '楼层名称（如一楼借阅区）',
  PRIMARY KEY (`floor_no`) USING BTREE,
  UNIQUE INDEX `uk_floor_name`(`floor_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '楼层信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of floor_info
-- ----------------------------
INSERT INTO `floor_info` VALUES ('F1', '一楼借阅区');
INSERT INTO `floor_info` VALUES ('F5', '上档次的');
INSERT INTO `floor_info` VALUES ('F9', '九楼多媒体区');
INSERT INTO `floor_info` VALUES ('F2', '二楼自习区');
INSERT INTO `floor_info` VALUES ('F8', '八楼外文图书区');
INSERT INTO `floor_info` VALUES ('F6', '六楼休闲阅读区');
INSERT INTO `floor_info` VALUES ('F10', '第三方都是');
INSERT INTO `floor_info` VALUES ('F3', '考研f复习室');
INSERT INTO `floor_info` VALUES ('F4', '负一楼密集书库');

-- ----------------------------
-- Table structure for history_booking_info
-- ----------------------------
DROP TABLE IF EXISTS `history_booking_info`;
CREATE TABLE `history_booking_info`  (
  `bookingID` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seat` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `floor` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `date` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `seat_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '未签到'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '座位预约信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of history_booking_info
-- ----------------------------
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1011', 'F1', '2026-03-30 08:30', '2026-03-30 09:00', '2026-03-29 15:00:00', '已结束');
INSERT INTO `history_booking_info` VALUES ('BK002', '15263280916', 'CF1010', 'F1', '2026-03-30 09:30', '2026-03-30 10:00', '2026-03-29 15:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK003', '15263280911', 'CF1006', 'F1', '2026-03-31 08:00', '2026-03-31 09:00', '2026-03-29 16:00:00', '已取消');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1010', 'F1', '2026-04-13 09:30', '2026-04-13 10:30', '2026-04-12 19:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1010', 'F1', '2026-04-14 09:30', '2026-04-14 10:00', '2026-04-13 16:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1011', 'F1', '2026-04-14 09:00', '2026-04-14 10:00', '2026-04-14 10:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK002', '15263280916', 'CF1005', 'F1', '2026-04-14 10:30', '2026-04-14 11:00', '2026-04-14 10:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1018', 'F1', '2026-04-14 16:30', '2026-04-14 18:00', '2026-04-14 10:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1006', 'F1', '2026-04-14 09:00', '2026-04-14 09:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK003', '15263280916', 'CF1018', 'F1', '2026-04-14 09:00', '2026-04-14 10:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK004', '15263280916', 'CF1016', 'F1', '2026-04-14 10:00', '2026-04-14 11:00', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK005', '15263280916', 'CF1042', 'F1', '2026-04-14 08:00', '2026-04-14 11:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK006', '15263280916', 'CF1040', 'F1', '2026-04-14 08:30', '2026-04-14 10:00', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK007', '15263280916', 'CF1034', 'F1', '2026-04-14 09:00', '2026-04-14 09:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK008', '15263280916', 'CF1030', 'F1', '2026-04-14 09:30', '2026-04-14 10:00', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK009', '15263280916', 'CF1023', 'F1', '2026-04-14 09:30', '2026-04-14 11:00', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK010', '15263280916', 'CF1015', 'F1', '2026-04-14 09:30', '2026-04-14 10:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK011', '15263280916', 'CF1032', 'F1', '2026-04-14 08:30', '2026-04-14 10:00', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK002', '15263280916', 'CF1018', 'F1', '2026-04-15 08:00', '2026-04-15 10:30', '2026-04-14 23:00:00', '已取消');
INSERT INTO `history_booking_info` VALUES ('BK003', '15263280916', 'CF1010', 'F1', '2026-04-14 22:00', '2026-04-15 09:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK004', '15263280916', 'CF1009', 'F1', '2026-04-14 10:00', '2026-04-15 08:30', '2026-04-14 23:00:00', '未入座，已取消！');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1006', 'F1', '2026-04-14 08:30', '2026-04-14 10:00', '2026-04-14 23:00:00', '已取消');
INSERT INTO `history_booking_info` VALUES ('BK001', '15263280916', 'CF1010', 'F1', '2026-04-15 09:00', '2026-04-15 09:30', '2026-04-15 00:00:00', '未入座，已取消！');

-- ----------------------------
-- Table structure for seat_info
-- ----------------------------
DROP TABLE IF EXISTS `seat_info`;
CREATE TABLE `seat_info`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '座位ID',
  `seat_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '座位编号',
  `seat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '座位名称（如CF1001）',
  `floor_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '所属楼层编号',
  `seat_x` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '座位X坐标（前端展示用）',
  `seat_y` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '座位Y坐标（前端展示用）',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_seat_name`(`seat_name`) USING BTREE,
  INDEX `idx_floor_no_seat`(`floor_no`) USING BTREE,
  CONSTRAINT `fk_seat_floor` FOREIGN KEY (`floor_no`) REFERENCES `floor_info` (`floor_no`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '座位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seat_info
-- ----------------------------
INSERT INTO `seat_info` VALUES ('177424481924002', '004', 'CF1004', 'F1', '338px', '351px');
INSERT INTO `seat_info` VALUES ('177424481924003', '005', 'CF1005', 'F1', '775px', '209px');
INSERT INTO `seat_info` VALUES ('177424481924004', '006', 'CF1006', 'F1', '277px', '200px');
INSERT INTO `seat_info` VALUES ('177424481924005', '007', 'CF1007', 'F1', '713px', '357px');
INSERT INTO `seat_info` VALUES ('177424481924006', '008', 'CF1008', 'F1', '489px', '349px');
INSERT INTO `seat_info` VALUES ('177424481924007', '009', 'CF1009', 'F1', '716px', '207px');
INSERT INTO `seat_info` VALUES ('177424481924008', '010', 'CF1010', 'F1', '498px', '202px');
INSERT INTO `seat_info` VALUES ('177424481924009', '011', 'CF1011', 'F1', '338px', '201px');
INSERT INTO `seat_info` VALUES ('177424481924010', '012', 'CF1012', 'F1', '551px', '347px');
INSERT INTO `seat_info` VALUES ('1774274481924_362', '002', 'CF1002', 'F1', '556px', '203px');
INSERT INTO `seat_info` VALUES ('1774515417879_236', '001', 'CF2001', 'F2', '407px', '109px');
INSERT INTO `seat_info` VALUES ('1774515420310_50', '002', 'CF2002', 'F2', '510px', '114px');
INSERT INTO `seat_info` VALUES ('1774515433014_167', '001', 'CF5001', 'F5', '100px', '100px');
INSERT INTO `seat_info` VALUES ('1774515433959_732', '002', 'CF5002', 'F5', '311px', '187px');
INSERT INTO `seat_info` VALUES ('1774515434139_798', '003', 'CF5003', 'F5', '120px', '193px');
INSERT INTO `seat_info` VALUES ('1774515434326_132', '004', 'CF5004', 'F5', '723px', '53px');
INSERT INTO `seat_info` VALUES ('1774515438174_504', '005', 'CF5005', 'F5', '210px', '262px');
INSERT INTO `seat_info` VALUES ('1774539933382_45', '001', 'CF4001', 'F4', '100px', '100px');
INSERT INTO `seat_info` VALUES ('1774630217024_836', '013', 'CF1013', 'F1', '278px', '353px');
INSERT INTO `seat_info` VALUES ('1774630352303_445', '014', 'CF1014', 'F1', '773px', '356px');
INSERT INTO `seat_info` VALUES ('1774630365097_113', '015', 'CF1015', 'F1', '1244px', '213px');
INSERT INTO `seat_info` VALUES ('1774630366312_425', '016', 'CF1016', 'F1', '1186px', '213px');
INSERT INTO `seat_info` VALUES ('1774630366816_201', '017', 'CF1017', 'F1', '1004px', '205px');
INSERT INTO `seat_info` VALUES ('1774630366976_689', '018', 'CF1018', 'F1', '945px', '208px');
INSERT INTO `seat_info` VALUES ('1774630367152_55', '019', 'CF1019', 'F1', '1004px', '347px');
INSERT INTO `seat_info` VALUES ('1774630367296_434', '020', 'CF1020', 'F1', '945px', '349px');
INSERT INTO `seat_info` VALUES ('1774630401392_868', '021', 'CF1021', 'F1', '330px', '599px');
INSERT INTO `seat_info` VALUES ('1774630403206_660', '022', 'CF1022', 'F1', '265px', '600px');
INSERT INTO `seat_info` VALUES ('1774630404439_797', '023', 'CF1023', 'F1', '327px', '462px');
INSERT INTO `seat_info` VALUES ('1774630407906_426', '024', 'CF1024', 'F1', '267px', '461px');
INSERT INTO `seat_info` VALUES ('1774630408312_775', '025', 'CF1025', 'F1', '1245px', '349px');
INSERT INTO `seat_info` VALUES ('1774630408472_461', '026', 'CF1026', 'F1', '1187px', '348px');
INSERT INTO `seat_info` VALUES ('1774630453823_356', '027', 'CF1027', 'F1', '767px', '462px');
INSERT INTO `seat_info` VALUES ('1774630454431_724', '028', 'CF1028', 'F1', '708px', '463px');
INSERT INTO `seat_info` VALUES ('1774630454624_766', '029', 'CF1029', 'F1', '554px', '602px');
INSERT INTO `seat_info` VALUES ('1774630454776_647', '030', 'CF1030', 'F1', '490px', '604px');
INSERT INTO `seat_info` VALUES ('1774630454942_693', '031', 'CF1031', 'F1', '556px', '452px');
INSERT INTO `seat_info` VALUES ('1774630455112_755', '032', 'CF1032', 'F1', '493px', '453px');
INSERT INTO `seat_info` VALUES ('1774630484302_154', '033', 'CF1033', 'F1', '770px', '609px');
INSERT INTO `seat_info` VALUES ('1774630484648_276', '034', 'CF1034', 'F1', '708px', '605px');
INSERT INTO `seat_info` VALUES ('1774630507038_255', '035', 'CF1035', 'F1', '159px', '268px');
INSERT INTO `seat_info` VALUES ('1774630507670_319', '036', 'CF1036', 'F1', '1251px', '613px');
INSERT INTO `seat_info` VALUES ('1774630514903_354', '037', 'CF1037', 'F1', '1184px', '615px');
INSERT INTO `seat_info` VALUES ('1774630516686_722', '038', 'CF1038', 'F1', '1249px', '464px');
INSERT INTO `seat_info` VALUES ('1774630516838_613', '039', 'CF1039', 'F1', '1186px', '461px');
INSERT INTO `seat_info` VALUES ('1774630517007_955', '040', 'CF1040', 'F1', '1004px', '460px');
INSERT INTO `seat_info` VALUES ('1774630517151_157', '041', 'CF1041', 'F1', '942px', '462px');
INSERT INTO `seat_info` VALUES ('1774630517311_25', '042', 'CF1042', 'F1', '1004px', '617px');
INSERT INTO `seat_info` VALUES ('1774630517455_736', '043', 'CF1043', 'F1', '943px', '614px');
INSERT INTO `seat_info` VALUES ('1774630547039_76', '044', 'CF1044', 'F1', '100px', '100px');
INSERT INTO `seat_info` VALUES ('1774630548598_804', '045', 'CF1045', 'F1', '100px', '100px');
INSERT INTO `seat_info` VALUES ('1774630549248_157', '047', 'CF1047', 'F1', '0px', '488px');
INSERT INTO `seat_info` VALUES ('1774630549583_284', '048', 'CF1048', 'F1', '3px', '426px');
INSERT INTO `seat_info` VALUES ('1774630549767_444', '049', 'CF1049', 'F1', '146px', '492px');
INSERT INTO `seat_info` VALUES ('1774630549961_779', '050', 'CF1050', 'F1', '152px', '423px');
INSERT INTO `seat_info` VALUES ('1774630550151_460', '051', 'CF1051', 'F1', '14px', '322px');
INSERT INTO `seat_info` VALUES ('1774630550302_2', '052', 'CF1052', 'F1', '14px', '265px');
INSERT INTO `seat_info` VALUES ('1774630550454_414', '053', 'CF1053', 'F1', '154px', '331px');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户手机号（唯一标识）',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户密码（建议加密存储）',
  `user_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '用户头像URL',
  `user_type` tinyint(1) DEFAULT 0 COMMENT '用户类型（1=管理员，0=普通用户）',
  PRIMARY KEY (`user_phone`) USING BTREE,
  INDEX `idx_user_name`(`user_name`) USING BTREE COMMENT '用户名索引，适配借阅记录查询'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('12345678', '张杰', '123456', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/03/e429f609-9503-42da-a539-47062fe4af8c.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138002', '王五', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/avatar3.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138003', '赵六', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/avatar4.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138004', '钱七', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/avatar5.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138005', '孙八', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/avatar6.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138006', '周九', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/avatar7.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138007', '吴十', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/avatar8.jpg', 0);
INSERT INTO `sys_user` VALUES ('13800138008', '郑管理员', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/admin1.jpg', 1);
INSERT INTO `sys_user` VALUES ('13800138009', '冯管理员', 'e10adc3949ba59abbe56e057f20f883e', 'https://example.com/admin2.jpg', 1);
INSERT INTO `sys_user` VALUES ('15263280911', 'Meng11', '123456', NULL, 0);
INSERT INTO `sys_user` VALUES ('15263280916', 'MENG', '123456', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/64fbbb61-dd49-408f-b8ff-faefce21dfea.jpg', 0);
INSERT INTO `sys_user` VALUES ('15588464096', 'Admin', '123456', 'https://java-ai-meng.oss-cn-beijing.aliyuncs.com/2026/04/8b963425-fba3-4411-bf7e-f14c8ad92d32.png', 1);
INSERT INTO `sys_user` VALUES ('17564270162', '孟超11', '123', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
