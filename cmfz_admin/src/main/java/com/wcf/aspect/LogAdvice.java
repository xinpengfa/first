package com.wcf.aspect;
import com.wcf.entity.Log;
import com.wcf.entity.Manager;
import com.wcf.service.LogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * aspectJ 风格的aop
 *
 * Before: 前置通知
 * After: 后置通知--一定会执行(出现异常页执行, 相当于try-catch-finally中的finally)
 * AfterReturning: 后置通知(目标方法正常执行之后执行)
 * AfterThrowing: 异常通知
 */
@Aspect  // 代表这是一个切面类
@Component // 交给Spring工厂统一管理--组件
//@Order(1)  //多个aop的排序, 大于0的整数, 值越小, 优先级越高
public class LogAdvice {

    @Autowired
    private LogService logService;
    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(LogAdvice.class);

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.wcf.service..*.modify*(..)) || execution(* com.wcf.service..*.add*(..))")
    public void pc(){}

    /**
     * 额外功能
     * 环绕通知
     */
    @Around("pc()")
    public void doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 获得目标方法的参数内容
        Object[] args = proceedingJoinPoint.getArgs();
        String message = "";
        String actionString = "修改";
        for (Object arg : args) {
            logger.info("LogAdvice=====arg:-----" + arg);
            message += arg.getClass().getName()+": "+arg.toString() + " ; ";
        }
        logger.info("LogAdvice=====message:-----" + message);
        Object target = proceedingJoinPoint.getTarget();
        String[] split = target.toString().split("\\."); // target : com.gzx.service.XXXServiceImpl@ ...
        String[] ss = split[split.length-1].split("ServiceImpl");
        logger.info("LogAdvice=====target:-----"+ ss[0]);
        String name = proceedingJoinPoint.getSignature().getName();
        logger.info("LogAdvice=====name:-----"+ name);
        if ( name.contains("add") ) {
            actionString = "新增";
        }
        Log log = new Log();
        try {
            // 放行目标方法
            proceedingJoinPoint.proceed();
            log.setResult("success");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            log.setResult("fail");
        } finally {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = servletRequestAttributes.getRequest();
            HttpSession session = request.getSession();
            Manager manager = (Manager) session.getAttribute("manager");
            logger.info("LogAdvice=====Manager:-----"+manager);
            log.setAction(actionString);
            log.setMessage(message);
            log.setResource(ss[0]);
            // 方便测试方法, session中没有值报NullPointerException, 正常登录后, 加上强制登录, session一定存在值
            if ( manager != null ) {
                log.setUser(manager.getName());
            }
            logger.info("LogAdvice=====log:-----"+log);
            logService.save(log);
        }
    }

}
