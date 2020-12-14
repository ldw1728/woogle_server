package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="woogle")
public class Woogle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	private String title;
	private String contents;
	private String woogle;
	
	@Column(name="user_id")
	private Long userId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getWoogle() {
		return woogle;
	}
	public void setWoogle(String woogle) {
		this.woogle = woogle;
	}
	
}