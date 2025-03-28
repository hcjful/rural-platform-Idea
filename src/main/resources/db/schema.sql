-- 产品表
CREATE TABLE product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL COMMENT '产品名称',
    category VARCHAR(50) NOT NULL COMMENT '产品分类',
    price DECIMAL(10,2) NOT NULL COMMENT '产品价格',
    origin VARCHAR(100) NOT NULL COMMENT '产地',
    description TEXT COMMENT '产品描述',
    image VARCHAR(255) COMMENT '产品图片URL',
    stock INT NOT NULL COMMENT '库存数量',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 用户表
CREATE TABLE user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '手机号',
    address TEXT COMMENT '收货地址',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 订单表
CREATE TABLE `order` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    total_amount DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    status VARCHAR(20) NOT NULL COMMENT '订单状态',
    shipping_address TEXT NOT NULL COMMENT '收货地址',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- 订单详情表
CREATE TABLE order_detail (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    quantity INT NOT NULL COMMENT '购买数量',
    price DECIMAL(10,2) NOT NULL COMMENT '购买时的单价',
    FOREIGN KEY (order_id) REFERENCES `order`(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- 购物车表
CREATE TABLE shopping_cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_id BIGINT NOT NULL COMMENT '产品ID',
    quantity INT NOT NULL COMMENT '数量',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);

-- 角色表
CREATE TABLE role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    code VARCHAR(50) NOT NULL UNIQUE COMMENT '角色编码',
    description VARCHAR(200) COMMENT '角色描述',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 用户角色关联表
CREATE TABLE user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (role_id) REFERENCES role(id)
);

-- 支付表
CREATE TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    payment_no VARCHAR(64) NOT NULL UNIQUE COMMENT '支付单号',
    amount DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    payment_method VARCHAR(20) NOT NULL COMMENT '支付方式',
    status VARCHAR(20) NOT NULL COMMENT '支付状态',
    transaction_id VARCHAR(64) COMMENT '第三方交易号',
    paid_at TIMESTAMP COMMENT '支付时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

-- 商品评价表
CREATE TABLE review (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT NOT NULL COMMENT '商品ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    order_id BIGINT NOT NULL COMMENT '订单ID',
    rating INT NOT NULL COMMENT '评分',
    content TEXT COMMENT '评价内容',
    images TEXT COMMENT '评价图片',
    is_anonymous BOOLEAN DEFAULT FALSE COMMENT '是否匿名',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

-- 商品收藏表
CREATE TABLE favorite (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    product_id BIGINT NOT NULL COMMENT '商品ID',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (product_id) REFERENCES product(id),
    UNIQUE KEY uk_user_product (user_id, product_id)
);

-- 物流表
CREATE TABLE logistics (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL COMMENT '订单ID',
    tracking_no VARCHAR(50) NOT NULL COMMENT '物流单号',
    carrier VARCHAR(50) NOT NULL COMMENT '快递公司',
    status VARCHAR(20) NOT NULL COMMENT '物流状态',
    current_location VARCHAR(200) COMMENT '当前位置',
    estimated_delivery_time TIMESTAMP COMMENT '预计送达时间',
    delivered_at TIMESTAMP COMMENT '实际送达时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES `order`(id)
);

-- 物流跟踪记录表
CREATE TABLE logistics_track (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    logistics_id BIGINT NOT NULL COMMENT '物流ID',
    location VARCHAR(200) NOT NULL COMMENT '位置',
    description VARCHAR(500) NOT NULL COMMENT '描述',
    track_time TIMESTAMP NOT NULL COMMENT '跟踪时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (logistics_id) REFERENCES logistics(id)
); 