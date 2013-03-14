package com.example.e4.rcp.todo.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;

import org.eclipse.e4.core.services.events.IEventBroker;

import com.example.e4.rcp.todo.event.EventConstants;

public class Todo {

	public static final String FIELD_ID = "id";
	public static final String FIELD_SUMMARY = "summary";
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_DONE = "done";
	public static final String FIELD_DUEDATE = "duedate";

	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(
			this);

	private String description = "";
	private boolean done;
	private Date dueDate;
	private long id;
	private String summary = "";
	private IEventBroker broker;

	public Todo() {
	}

	public Todo(long id, String summary, String description, boolean done,
			Date dueDate) {
		this();
		this.id = id;
		this.summary = summary;
		this.description = description;
		this.done = done;
		this.dueDate = dueDate;
	}

	public Todo copy() {
		return new Todo(this.id, this.summary, this.description, this.done,
				this.dueDate);
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changeSupport.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changeSupport.removePropertyChangeListener(l);
	}

	public void setEventBroker(IEventBroker broker) {
		this.broker = broker;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Todo other = (Todo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getDescription() {
		return description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public long getId() {
		return id;
	}

	public String getSummary() {
		return summary;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public boolean isDone() {
		return done;
	}

	public void setDescription(String description) {
		String oldValue = this.description;
		this.description = description;
		changeSupport.firePropertyChange(FIELD_DESCRIPTION, oldValue,
				this.description);
	}

	public void setDone(boolean done) {
		boolean oldValue = this.done;
		this.done = done;
		notifyChanged(FIELD_DONE, oldValue, this.done);
	}

	public void setDueDate(Date dueDate) {
		Date oldValue = this.dueDate;
		this.dueDate = dueDate;
		notifyChanged(FIELD_DUEDATE, oldValue, this.dueDate);
	}

	public void setId(long id) {
		long oldValue = this.id;
		this.id = id;
		notifyChanged(FIELD_ID, oldValue, this.id);
	}

	public void setSummary(String summary) {
		String oldValue = this.summary;
		this.summary = summary;
		notifyChanged(FIELD_SUMMARY, oldValue, this.summary);
	}

	private void notifyChanged(String fieldName, Object oldValue,
			Object newValue) {
		if (changeSupport != null) {
			changeSupport.firePropertyChange(fieldName, oldValue, newValue);
		}
		if (broker != null) {
			broker.post(EventConstants.TOPIC_TODO_DATA_UPDATE_UPDATED, this);
		}
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", summary=" + summary + ", description="
				+ description + ", done=" + done + ", dueDate=" + dueDate + "]";
	}
}
