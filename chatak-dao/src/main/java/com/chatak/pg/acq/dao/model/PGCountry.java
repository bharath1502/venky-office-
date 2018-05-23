package com.chatak.pg.acq.dao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * This class represents the Country
 * 
 * @Author: Girmiti Software
 * @Date: Nov 27, 2014
 * @Time: 11:00:00
 * @Version: 1.0
 * @Comments:
 * 
 */
@Entity
@Table(name = "PG_COUNTRY")
public class PGCountry implements Serializable {

	private static final long serialVersionUID = -2607507977495855516L;

	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME")
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id")
	@Fetch(FetchMode.SELECT)
	private List<PGState> state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PGState> getState() {
		return state;
	}

	public void setState(List<PGState> state) {
		this.state = state;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Country{");
		sb.append("[id:" + id + "],");
		sb.append("[name:" + name + "],");

		sb.append("[state:" + state.toString() + "],");

		sb.append("}");
		return sb.toString();
	}
}
