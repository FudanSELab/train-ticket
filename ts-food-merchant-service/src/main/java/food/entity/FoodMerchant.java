package food.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@GenericGenerator(name = "jpa-uuid", strategy = "org.hibernate.id.UUIDGenerator")
public class FoodMerchant {
	@Id
	@GeneratedValue(generator = "jpa-uuid")
	@Column(length = 36)
	private String id;

	public FoodMerchant() {
		// Default constructor
	}
}