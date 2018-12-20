package br.com.micheltank.challenge.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "last_query")
public class LastQueryEntity {

	@Id
	private String id;
	private String cpf;
	private LocalDateTime date;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LastQueryEntity series = (LastQueryEntity) o;
        if (series.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), series.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
