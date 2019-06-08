package com.gorge4j.user.dto;

import java.io.Serializable;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Title: DeleteDTO.java
 * @Description: 用户删除数据传输对象
 * @Copyright: © 2019 ***
 * @Company: ***有限公司
 *
 * @author gorge.guo
 * @date 2019-04-01 20:46:52
 * @version v1.0
 */

@ApiModel("用户删除实体")
@Data
public class DeleteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户id */
    @ApiModelProperty("用户 id")
    @NotNull(message = "用户id不能为空")
    private Integer id;

}
