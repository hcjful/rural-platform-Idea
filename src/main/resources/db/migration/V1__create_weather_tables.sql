-- 城市表
CREATE TABLE cities (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL COMMENT '城市名称',
    code VARCHAR(20) NOT NULL COMMENT '城市代码',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_code (code)
) COMMENT '城市信息表';

-- 天气数据表
CREATE TABLE weather_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    city_code VARCHAR(20) NOT NULL COMMENT '城市代码',
    temperature DECIMAL(4,1) NOT NULL COMMENT '温度',
    feels_like DECIMAL(4,1) NOT NULL COMMENT '体感温度',
    description VARCHAR(50) NOT NULL COMMENT '天气描述',
    weather_type VARCHAR(20) NOT NULL COMMENT '天气类型',
    wind_speed DECIMAL(4,1) NOT NULL COMMENT '风速',
    humidity INT NOT NULL COMMENT '湿度',
    sunrise VARCHAR(5) NOT NULL COMMENT '日出时间',
    sunset VARCHAR(5) NOT NULL COMMENT '日落时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_city_code (city_code)
) COMMENT '天气数据表';

-- 空气质量表
CREATE TABLE air_quality (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    city_code VARCHAR(20) NOT NULL COMMENT '城市代码',
    aqi INT NOT NULL COMMENT 'AQI指数',
    level VARCHAR(20) NOT NULL COMMENT '空气质量等级',
    pm25 DECIMAL(5,1) NOT NULL COMMENT 'PM2.5浓度',
    pm10 DECIMAL(5,1) NOT NULL COMMENT 'PM10浓度',
    no2 DECIMAL(5,1) NOT NULL COMMENT '二氧化氮浓度',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_city_code (city_code)
) COMMENT '空气质量表';

-- 天气预警表
CREATE TABLE weather_warnings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    city_code VARCHAR(20) NOT NULL COMMENT '城市代码',
    type VARCHAR(20) NOT NULL COMMENT '预警类型',
    content TEXT NOT NULL COMMENT '预警内容',
    time TIMESTAMP NOT NULL COMMENT '预警时间',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_city_code (city_code)
) COMMENT '天气预警表';

-- 天气预报表
CREATE TABLE weather_forecast (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    city_code VARCHAR(20) NOT NULL COMMENT '城市代码',
    forecast_date DATE NOT NULL COMMENT '预报日期',
    high_temp DECIMAL(4,1) NOT NULL COMMENT '最高温度',
    low_temp DECIMAL(4,1) NOT NULL COMMENT '最低温度',
    precipitation INT NOT NULL COMMENT '降水概率',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_city_code (city_code),
    KEY idx_forecast_date (forecast_date)
) COMMENT '天气预报表';

-- 插入示例数据
INSERT INTO cities (name, code) VALUES 
('北京', 'BJ'),
('上海', 'SH'),
('广州', 'GZ'),
('深圳', 'SZ');

-- 插入天气数据
INSERT INTO weather_data (city_code, temperature, feels_like, description, weather_type, wind_speed, humidity, sunrise, sunset) VALUES
('BJ', 25.0, 26.0, '晴', 'sunny', 3.5, 65, '06:32', '18:45'),
('SH', 23.0, 24.0, '多云', 'cloudy', 4.2, 70, '06:15', '18:30'),
('GZ', 28.0, 30.0, '局部小雨', 'rain', 2.8, 75, '06:45', '19:00'),
('SZ', 27.0, 29.0, '阴', 'partlyCloudy', 3.0, 72, '06:40', '18:55');

-- 插入空气质量数据
INSERT INTO air_quality (city_code, aqi, level, pm25, pm10, no2) VALUES
('BJ', 75, '良好', 35.0, 68.0, 40.0),
('SH', 45, '优', 20.0, 45.0, 30.0),
('GZ', 95, '良好', 45.0, 85.0, 50.0),
('SZ', 55, '良好', 25.0, 50.0, 35.0);

-- 插入天气预警数据
INSERT INTO weather_warnings (city_code, type, content, time) VALUES
('BJ', 'warning', '大风蓝色预警', '2024-04-03 10:00:00'),
('GZ', 'danger', '暴雨红色预警', '2024-04-03 09:30:00');

-- 插入天气预报数据
INSERT INTO weather_forecast (city_code, forecast_date, high_temp, low_temp, precipitation) VALUES
('BJ', '2024-04-03', 25.0, 18.0, 30),
('BJ', '2024-04-04', 26.0, 19.0, 40),
('BJ', '2024-04-05', 24.0, 17.0, 60),
('BJ', '2024-04-06', 23.0, 16.0, 70),
('BJ', '2024-04-07', 25.0, 18.0, 50),
('BJ', '2024-04-08', 27.0, 20.0, 20),
('BJ', '2024-04-09', 26.0, 19.0, 10); 