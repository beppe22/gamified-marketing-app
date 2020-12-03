package it.polimi.gamifiedmarketingapp.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "question_choice", schema = "gamified_marketing_app_db")
public class QuestionChoice implements Serializable {

	private static final long serialVersionUID = -1613673532740796552L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String text;
	
	private int order;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;

	public QuestionChoice() {}

	public QuestionChoice(String text, int order, Question question) {
		this.text = text;
		this.order = order;
		this.question = question;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionChoice other = (QuestionChoice) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuestionChoice [id=" + id + ", text=" + text + ", order=" + order + ", question=" + question + "]";
	}

}
