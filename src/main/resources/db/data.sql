-- 插入示例用户数据
INSERT INTO user (username, password, phone, address) VALUES
('test_user', '$2a$10$8KxX5H1Q1Y9YV6.VnI6H8esFb8V2TqRX0WU1Q3MXsN7BwHk3wFIjm', '13800138000', '北京市朝阳区xx街道xx号'),
('demo_user', '$2a$10$8KxX5H1Q1Y9YV6.VnI6H8esFb8V2TqRX0WU1Q3MXsN7BwHk3wFIjm', '13900139000', '上海市浦东新区xx路xx号');

-- 插入示例产品数据
INSERT INTO product (name, category, price, origin, description, image, stock) VALUES
('有机胡萝卜', 'vegetables', 5.80, '山东寿光', '新鲜采摘的有机胡萝卜，富含胡萝卜素，适合生食或烹饪。', 'https://example.com/carrot.jpg', 100),
('红富士苹果', 'fruits', 12.80, '陕西延安', '皮薄肉厚，甜度适中，果肉爽脆可口。', 'https://example.com/apple.jpg', 200),
('东北大米', 'grains', 59.90, '黑龙江五常', '五常大米，颗粒饱满，煮后香糯可口。', 'https://example.com/rice.jpg', 500),
('有机白菜', 'vegetables', 3.50, '河北保定', '新鲜有机白菜，口感清脆，营养丰富。', 'https://example.com/cabbage.jpg', 150),
('砀山梨', 'fruits', 8.90, '安徽砀山', '汁多味甜，果肉细腻，清香可口。', 'https://example.com/pear.jpg', 300),
('菜籽油', 'grains', 45.80, '四川成都', '纯天然压榨，色泽金黄，香味浓郁。', 'https://example.com/oil.jpg', 400);

-- 插入示例购物车数据
INSERT INTO shopping_cart (user_id, product_id, quantity) VALUES
(1, 1, 2),
(1, 3, 1),
(2, 2, 3);

-- 插入示例订单数据
INSERT INTO `order` (user_id, total_amount, status, shipping_address) VALUES
(1, 71.50, 'COMPLETED', '北京市朝阳区xx街道xx号'),
(2, 38.40, 'PENDING', '上海市浦东新区xx路xx号');

-- 插入示例订单详情数据
INSERT INTO order_detail (order_id, product_id, quantity, price) VALUES
(1, 1, 2, 5.80),
(1, 3, 1, 59.90),
(2, 2, 3, 12.80);

-- 插入角色数据
INSERT INTO role (name, code, description) VALUES
('管理员', 'ADMIN', '系统管理员'),
('商家', 'MERCHANT', '商品销售者'),
('用户', 'USER', '普通用户');

-- 插入用户角色关联数据
INSERT INTO user_role (user_id, role_id) VALUES
(1, 1), -- test_user 是管理员
(2, 3); -- demo_user 是普通用户

-- 插入支付数据
INSERT INTO payment (order_id, payment_no, amount, payment_method, status) VALUES
(1, 'PAY202403230001', 71.50, 'ALIPAY', 'SUCCESS'),
(2, 'PAY202403230002', 38.40, 'WECHAT', 'PENDING');

-- 插入商品评价数据
INSERT INTO review (product_id, user_id, order_id, rating, content, is_anonymous) VALUES
(1, 1, 1, 5, '胡萝卜非常新鲜，很满意！', false),
(2, 2, 2, 4, '苹果很甜，但是包装稍微简单了点。', false);

-- 插入商品收藏数据
INSERT INTO favorite (user_id, product_id) VALUES
(1, 2),
(2, 1);

-- 插入物流数据
INSERT INTO logistics (order_id, tracking_no, carrier, status, current_location) VALUES
(1, 'SF1234567890', '顺丰快递', 'DELIVERED', '已签收'),
(2, 'YT0987654321', '圆通快递', 'SHIPPING', '正在配送中');

-- 插入物流跟踪记录数据
INSERT INTO logistics_track (logistics_id, location, description, track_time) VALUES
(1, '北京市朝阳区', '包裹已签收，签收人：张三', '2024-03-23 15:30:00'),
(1, '北京市朝阳区', '快递员正在派送中', '2024-03-23 14:30:00'),
(1, '北京市', '包裹已到达北京分拨中心', '2024-03-23 08:30:00'),
(2, '上海市浦东新区', '包裹正在派送中', '2024-03-23 14:00:00'),
(2, '上海市', '包裹已到达上海分拨中心', '2024-03-23 09:00:00'); 