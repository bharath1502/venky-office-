package com.chatak.pg.acq.dao.model;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FAQ_MANAGEMENT")
public class FaqManagement implements Serializable {

		private static final long serialVersionUID = 1L;
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "FAQ_ID")
		private Long faqId;

		@Column(name = "CATEGORY_MAPPING_ID")
		private Long categoryMappingId;

		@Column(name = "QUESTION")
		private String questionName;

		@Column(name = "ANSWER")
		private String questionAnswer;

		@Column(name = "STATUS")
		private String status;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Long getFaqId() {
			return faqId;
		}

		public void setFaqId(Long faqId) {
			this.faqId = faqId;
		}

		public String getQuestionName() {
			return questionName;
		}

		public void setQuestionName(String questionName) {
			this.questionName = questionName;
		}

		public String getQuestionAnswer() {
			return questionAnswer;
		}

		public void setQuestionAnswer(String questionAnswer) {
			this.questionAnswer = questionAnswer;
		}

		public Long getCategoryMappingId() {
			return categoryMappingId;
		}

		public void setCategoryMappingId(Long categoryMappingId) {
			this.categoryMappingId = categoryMappingId;
		}


}
