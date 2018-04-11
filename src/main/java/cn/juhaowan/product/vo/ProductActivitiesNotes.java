package cn.juhaowan.product.vo;

import cn.jugame.dal.annotation.DbType;
import cn.jugame.dal.annotation.Id;
import cn.jugame.dal.annotation.Table;
import cn.jugame.dal.annotation.Column;

/**
 * 
 * 活动须知
 */
@Table(name = "product_activities_notes")
public class ProductActivitiesNotes implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", type = DbType.Int)
	private int id;

	@Column(name = "activitie_id", type = DbType.Varchar)
	private java.lang.String activitieId;

	@Column(name = "question", type = DbType.Varchar)
	private java.lang.String question;
	
	@Column(name = "answer", type = DbType.Varchar)
	private java.lang.String answer;

	@Column(name = "weight", type = DbType.Int)
	private int weight;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.lang.String getActivitieId() {
		return activitieId;
	}

	public void setActivitieId(java.lang.String activitieId) {
		this.activitieId = activitieId;
	}

	public java.lang.String getQuestion() {
		return question;
	}

	public void setQuestion(java.lang.String question) {
		this.question = question;
	}

	public java.lang.String getAnswer() {
		return answer;
	}

	public void setAnswer(java.lang.String answer) {
		this.answer = answer;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	
}
