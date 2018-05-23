package com.chatak.acquirer.admin.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.chatak.pg.acq.dao.model.PGParams;

@RunWith(MockitoJUnitRunner.class)
public class ProcessorConfigTest {

	@InjectMocks
	ProcessorConfig processorConfig;

	@Test
	public void testSetProcessorConfig() {
		List<PGParams> pgParams = new ArrayList<>();
		PGParams pgParam = new PGParams();
		pgParams.add(pgParam);
		processorConfig.setProcessorConfig(pgParams);
	}

	@Test
	public void testGet() {
		processorConfig.get("key");
	}

}
