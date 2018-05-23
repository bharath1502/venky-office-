package com.chatak.switches.sb;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BINUpstreamRouterTest {

	@Mock
	List<Long> binList;

	@InjectMocks
	BINUpstreamRouter bINUpstreamRouter = new BINUpstreamRouter();

	BINUpstreamRouter bINUpstreamRouter1 = new BINUpstreamRouter(binList);

	@Test
	public void testIsOnUsBIN() {
		bINUpstreamRouter.isOnUsBIN("5432");
	}

	@Test
	public void testIsOnUsBINIf() {
		bINUpstreamRouter.isOnUsBIN(null);
	}

	@Test(expected = NullPointerException.class)
	public void testGetPaymentService() {
		bINUpstreamRouter.getPaymentService();
	}

	@Test
	public void testGetBinList() {
		bINUpstreamRouter.getBinList();
	}

}
