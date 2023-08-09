package com.wanted.preonboarding.common.testbase;

import com.wanted.preonboarding.common.fixture.DataSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@Sql(value = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public abstract class ServiceTestBase {

    @Autowired
    protected DataSetup dataSetup;
}
