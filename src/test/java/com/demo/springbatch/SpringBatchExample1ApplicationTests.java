package com.demo.springbatch;

import com.demo.springbatch.model.User;
import com.demo.springbatch.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.batch.test.StepScopeTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		StepScopeTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
public class SpringBatchExample1ApplicationTests {

	@Autowired
	private Job job;

	@Autowired
	private FlatFileItemReader<User> reader;

	@Autowired
	private UserRepository userRepository;


	private JobParameters jobParameters;


	@Test
	public void testJobConfiguration() {
		assertNotNull(job);
		assertEquals("ETL-Load", job.getName());
	}
	@Test
	public void testReader() throws Exception {
		StepExecution stepExecution = MetaDataInstanceFactory.createStepExecution(jobParameters);
		int count = 0;
		try {
			count = StepScopeTestUtils.doInStepScope(stepExecution, () -> {
				int totalUser = 0;
				User user;
				try {
					reader.open(stepExecution.getExecutionContext());
					while ((user = reader.read()) != null) {
						System.out.println(user.getFirstName());
						assertNotNull(user);
						totalUser++;
					}
				} finally {
					try { reader.close(); } catch (Exception e) { fail(e.toString()); }
				}
				return totalUser;
			});
		} catch (Exception e) {
			fail(e.toString());
		}
		System.out.println("Total count : "+count);
		assertEquals(9, count);
	}

}
