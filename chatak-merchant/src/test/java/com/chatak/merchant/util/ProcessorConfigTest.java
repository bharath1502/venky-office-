package com.chatak.merchant.util;

import static org.junit.Assert.*;

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
		List<PGParams> pgParams=new ArrayList<>();
		PGParams params=new PGParams();
		pgParams.add(params);
		processorConfig.setProcessorConfiguration(pgParams);
	}
	
	@Test
	public void testGet() {
		processorConfig.get("key");
	}


}
