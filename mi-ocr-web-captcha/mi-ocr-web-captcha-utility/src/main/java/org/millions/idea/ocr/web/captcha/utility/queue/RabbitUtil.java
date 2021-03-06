/***
 * @pName org.millions-idea.ocr
 * @name RabbitUtil
 * @user HongWei
 * @date 2018/6/8
 * @desc
 */
package org.millions.idea.ocr.web.captcha.utility.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitUtil {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publish(String queue, String message){
        rabbitTemplate.convertAndSend(queue, message);
    }
}
