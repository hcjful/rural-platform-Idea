package com.rural.platform.filter;

import com.alibaba.fastjson.JSON;
import com.rural.platform.entity.Result;
import com.rural.platform.utils.WarehouseConstants;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 登录限制过滤器:
 */
public class SecurityFilter implements Filter {

    //将redis模板定义为其成员变量
    private StringRedisTemplate redisTemplate;

    //成员变量redis模板的set方法
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 过滤器拦截到请求执行的方法:
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //获取请求url接口
        String path = request.getServletPath();

        // 白名单路径前缀
        List<String> prefixList = new ArrayList<>();
        prefixList.add("/captcha/captchaImage");
        prefixList.add("/login");
        prefixList.add("/logout");
        prefixList.add("/auth/register");
        prefixList.add("/auth/check-usercode");
        prefixList.add("/auth/check-email");
        prefixList.add("/auth/check-phone");
        prefixList.add("/cultural-activities");
        prefixList.add("/api/cultural-activities");
        prefixList.add("/product/img-upload");
        prefixList.add("/img/upload");
        prefixList.add("/orders");
        prefixList.add("/products");
        prefixList.add("/cart");
        prefixList.add("/volunteer-activities");
        prefixList.add("/weather");


        // 检查路径是否在白名单中
        boolean isWhitelisted = false;
        for (String prefix : prefixList) {
            if (path.startsWith(prefix)) {
                isWhitelisted = true;
                break;
            }
        }

        if (isWhitelisted) {
            chain.doFilter(request, response);
            return;
        }

        /*
          其它请求都校验token:
         */
        //拿到前端归还的token
        String clientToken = request.getHeader(WarehouseConstants.HEADER_TOKEN_NAME);
        //校验token,校验通过请求放行
        if (StringUtils.hasText(clientToken) && redisTemplate.hasKey(clientToken)) {
            chain.doFilter(request, response);
            return;
        }
        //校验失败,向前端响应失败的Result对象转成的json串
        Result result = Result.err(Result.CODE_ERR_UNLOGINED, "请登录！");
        String jsonStr = JSON.toJSONString(result);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonStr);
        out.flush();
        out.close();
    }
}
