/**
 * 
 */
package com.chatak.pg.user.bean;

/**
 * @Author: Girmiti Software
 * @Date: Aug 4, 2016
 * @Time: 12:59:54 PM
 * @Version: 1.0
 * @Comments: 
 *
 */
public class DeleteResellerResponse extends Response{

		private static final long serialVersionUID = -2258804006230082677L;
		
		private boolean isdeleated;

		/**
		 * @return the isdeleated
		 */
		public boolean isIsdeleated() {
			return isdeleated;
		}

		/**
		 * @param isdeleated the isdeleated to set
		 */
		public void setIsdeleated(boolean isdeleated) {
			this.isdeleated = isdeleated;
		}


}
