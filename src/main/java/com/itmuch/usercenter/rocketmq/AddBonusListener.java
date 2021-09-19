package com.itmuch.usercenter.rocketmq;

import com.itmuch.usercenter.dao.bonus.BonusEventLogMapper;
import com.itmuch.usercenter.dao.user.UserMapper;
import com.itmuch.usercenter.domain.dto.messaging.UserAddBonusMsgDTO;
import com.itmuch.usercenter.domain.entity.bonus.BonusEventLog;
import com.itmuch.usercenter.domain.entity.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "consumer-group",topic = "add-bonus")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class AddBonusListener implements RocketMQListener<UserAddBonusMsgDTO>{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BonusEventLogMapper bonusEventLogMapper;
    @Override
    public void onMessage(UserAddBonusMsgDTO userAddBonusMsgDTO) {
        {
            // 1. 为用户加积分
            Integer userId = userAddBonusMsgDTO.getUserId();
            Integer bonus = userAddBonusMsgDTO.getBonus();
            User user = this.userMapper.selectByPrimaryKey(userId);

            user.setBonus(user.getBonus() + bonus);
            this.userMapper.updateByPrimaryKeySelective(user);

            // 2. 记录日志到bonus_event_log表里面
            this.bonusEventLogMapper.insert(
                    BonusEventLog.builder()
                            .userId(userId)
                            .value(bonus)
                            .event("CONTRIBUTE")
                            .createTime(new Date())
                            .description("投稿加积分。。")
                            .build()
            );
            log.info("积分添加完毕...");
        }

    }
}
