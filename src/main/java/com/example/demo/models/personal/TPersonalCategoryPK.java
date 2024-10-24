package com.example.demo.models.personal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TPersonalCategoryPK implements Serializable {
	@Column(name = "id")
	private String id;

	@Column(name = "owner_id")
	private String ownerId;
}
