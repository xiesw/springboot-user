package com.gorge4j.user.job;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.gorge4j.user.core.UserService;
import com.gorge4j.user.vo.ViewVO;
import lombok.extern.slf4j.Slf4j;

/**
 * @Title: UserListViewJob.java
 * @Description: 定时任务示例
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-06-06 00:31:42
 * @version v1.0
 */

@Slf4j
@Component
public class UserListViewJob {

    /** 注入 UserService，用来处理业务，按需要注入具体的实现，存在多个实现时，可以通过实现类的不同别名来切换注入 */
    @Resource(name = "userServiceMyBatisImpl")
    private UserService userService;

    /** 每隔 30 秒打印以下最新普通用户的列表信息 */
    @Scheduled(cron = "0/30 * * * * ?")
    public void job() {
        List<ViewVO> lstViewVOs = userService.view();
        for (ViewVO viewVO : lstViewVOs) {
            log.info("用户信息：{}|{}|{}", viewVO.getId(), viewVO.getName(), viewVO.getType());
        }
    }

}
