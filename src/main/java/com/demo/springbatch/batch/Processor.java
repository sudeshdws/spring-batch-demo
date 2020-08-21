package com.demo.springbatch.batch;

import com.demo.springbatch.model.User;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class Processor implements ItemProcessor<User, User> {

    private static final String ACTIVE = "ACTIVE" ;
    private static final String UPDATE = "UPDATE" ;
    private static final String ADD = "ADD" ;
    private static final String DEACTIVE = "DEACTIVE" ;


    @Override
    public User process(User user) throws Exception {
        if(user.getAction().equalsIgnoreCase(ADD) || user.getAction().equalsIgnoreCase(UPDATE))
            user.setStatus(ACTIVE);
        else{
            user.setStatus(DEACTIVE);
        }
        user.setTime(new Date());
        return user;
    }
}
