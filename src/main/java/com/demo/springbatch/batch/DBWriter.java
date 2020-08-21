package com.demo.springbatch.batch;

import com.demo.springbatch.job.WriterJob;
import com.demo.springbatch.model.User;
import com.demo.springbatch.repository.UserRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class DBWriter implements ItemWriter<User> {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WriterJob writerJob;
     int count = 0 ;

    @Override
    public void write(List<? extends User> users) throws Exception {

        Predicate<User> addPredicate = data -> data.getAction().equalsIgnoreCase("ADD");
        Predicate<User> updatePredicate = data -> data.getAction().equalsIgnoreCase("UPDATE");
        Predicate<User> deletePredicate = data -> data.getAction().equalsIgnoreCase("DELETE");
        Predicate<User> checkID = data -> data.getId() != null;

        Long addCount = users.stream().filter(addPredicate).filter(checkID)
                .map(data -> userRepository.save(data))
                .collect(Collectors.counting());

        int deleteCount = users.stream()
                .filter(deletePredicate).filter(checkID)
                .mapToInt(data -> {
                    return userRepository.deactivateUsersFromRecord(
                            data.getStatus(),
                            data.getAction(),
                            data.getId());
                }).sum();

        Long updateCount = users.stream().
                filter(updatePredicate).filter(checkID)
                .map(data -> userRepository.save(data)).count();

        System.out.println("IN WRITER CLASS COUNT ------> "+count++);
        writerJob.outPutWriter(addCount, updateCount, deleteCount);
    }
}
