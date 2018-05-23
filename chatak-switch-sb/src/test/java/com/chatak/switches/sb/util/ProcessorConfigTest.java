package com.chatak.switches.sb.util;

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
		PGParams params = new PGParams();
		params.setParamName("MAG_TEK_KEY");
		params.setParamValue("MD5");
		pgParams.add(params);
		processorConfig.setProcessorConfig(pgParams);
	}

	@Test
	public void testSetProcessorConfigElse() {
		List<PGParams> pgParams = new ArrayList<>();
		PGParams params = new PGParams();
		params.setParamName("543");
		pgParams.add(params);
		processorConfig.setProcessorConfig(pgParams);
	}

	@Test
	public void testGet() {
		processorConfig.get("abc");

	}

}
